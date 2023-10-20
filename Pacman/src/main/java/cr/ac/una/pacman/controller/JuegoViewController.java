/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.model.Fantasma;
import cr.ac.una.pacman.model.Juego;
import cr.ac.una.pacman.model.PacMan;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class JuegoViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Canvas cvLaberinto;
    @FXML
    private Label lbPlayer;
    @FXML
    private Label lbScore;
    @FXML
    private Label lbHighScore;
    @FXML
    private HBox hboxVidas;

    AnimationTimer animationTimer;
    GraphicsContext graficos;
    public static final int ROWS = 20;
    public static final int COLUMNS = 16;
    public static final int SIZE = 20;
    double iniX = (COLUMNS * SIZE) / 2;
    double iniY = (ROWS * SIZE) / 2;
    double segundoAct = 0;
    double segundoAnt = 0;
    int direccion = 3;
    Juego juego;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarJuego();
    }

    @Override
    public void initialize() {
    }

    public void inicializarJuego() {
        graficos = cvLaberinto.getGraphicsContext2D();

        List<String> imagenPacman = new ArrayList<>();
        imagenPacman.add("cr/ac/una/pacman/resources/PacMan.png");
        imagenPacman.add("cr/ac/una/pacman/resources/PacMan2.png");

        List<Fantasma> fantasmas = new ArrayList<>();
        List<String> imagenFantasmaRojo = new ArrayList<>();
        imagenFantasmaRojo.add("cr/ac/una/pacman/resources/FantasmaRojo.png");
        List<String> imagenFantasmaRosa = new ArrayList<>();
        imagenFantasmaRosa.add("cr/ac/una/pacman/resources/FantasmaRosa.png");
        List<String> imagenFantasmaCian = new ArrayList<>();
        imagenFantasmaCian.add("cr/ac/una/pacman/resources/FantasmaCian.png");
        List<String> imagenFantasmaNaranja = new ArrayList<>();
        imagenFantasmaNaranja.add("cr/ac/una/pacman/resources/FantasmaNaranja.png");
        Fantasma fantasmaRojo = new Fantasma(iniX, iniY - SIZE * 2, 0.7, 3, imagenFantasmaRojo, "Rojo", "dijkstra");
        Fantasma fantasmaRosa = new Fantasma(iniX + SIZE, iniY, 0.7, 3, imagenFantasmaRosa, "Rosa", "dijkstraAlternativo");
        Fantasma fantasmaCian = new Fantasma(iniX - SIZE, iniY, 0.7, 3, imagenFantasmaCian, "Cian", "dijkstraAlternativo");
        Fantasma fantasmaNaranja = new Fantasma(iniX, iniY, 0.7, 3, imagenFantasmaNaranja, "Naranja", "floyd");
//        fantasmaRojo.setVulnerable(true);
//        fantasmaRosa.setVulnerable(true);
//        fantasmaCian.setVulnerable(true);
//        fantasmaNaranja.setVulnerable(true);
        fantasmas.add(fantasmaRojo);
        fantasmas.add(fantasmaRosa);
        fantasmas.add(fantasmaCian);
        fantasmas.add(fantasmaNaranja);

        PacMan pacman = new PacMan(6, 0, "A", (COLUMNS * SIZE) / 2, (ROWS * SIZE) / 2, 0.69, 3, imagenPacman);

        juego = new Juego(pacman, fantasmas, ROWS, COLUMNS);
        juego.generarLaberinto();
        juego.contarPuntosTotales();
        System.out.println(juego.puntosTotales);
        pintar();

        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evento) {
                switch (evento.getCode().toString()) {
                    case "D" ->
                        direccion = 0;
                    case "S" ->
                        direccion = 1;
                    case "A" ->
                        direccion = 2;
                    case "W" ->
                        direccion = 3;
                    case "SPACE" ->
                        juego.superVelocidad();
                    case "P" -> {
                        if (juego.pausa) {
                            juego.continuar();
                        } else {
                            juego.pausa();
                        }
                    }

                }
            }
        });

//        juego.nuevaPosEscape(juego.getFantasmas().get(0));
//        juego.nuevaPosEscape(juego.getFantasmas().get(1));
//        juego.nuevaPosEscape(juego.getFantasmas().get(2));
//        juego.nuevaPosEscape(juego.getFantasmas().get(3));
        juego.nuevaPosAleatoria(juego.getFantasmas().get(3));
        juego.animacionInicialFantasmas();
        ciclo();
    }

    public void ciclo() {
        final double targetFPS = 60.0;
        final double targetFrameTime = 1.0 / targetFPS;
        animationTimer = new AnimationTimer() {
            long tiempoInicial = System.nanoTime();
            private long lastUpdate = 0;
            double segAct = 0;

            @Override
            public void handle(long tiempoActual) {
                if (lastUpdate == 0) {
                    lastUpdate = tiempoActual;
                    return;
                }
                double elapsedSeconds = (tiempoActual - lastUpdate) / 1e9; // Resta el tiempo en pausa
                double t = (tiempoActual - tiempoInicial) / 1000000000.0;

                lastUpdate = tiempoActual;
                segundoAct = t;

                segAct += elapsedSeconds;

                if (segAct >= targetFrameTime && !juego.pausa) {
                    actualizar();
                    pintar();
                    root.requestFocus();
                    segAct -= targetFrameTime;
                } else if (segAct >= targetFrameTime && juego.pausa) {
                    root.requestFocus();
                    segAct -= targetFrameTime;
                }
            }
        };
        animationTimer.start();
    }

    public void actualizar() {
        if (juego.encierroUsado == false && juego.getPacMan().getVidas() == 6 && juego.puntosActuales <= juego.puntosTotales / 2) {
            juego.encierro();
        }
        if (juego.getPacMan().getPuntos() >= 500 && juego.incrementoVelocidad == 0) {
            juego.incrementoVelocidad += 0.05;
            juego.getFantasmas().get(0).setVelocidad(0.7 + juego.incrementoVelocidad);
        } else if (juego.getPacMan().getPuntos() >= 1000 && juego.incrementoVelocidad == 0.05) {
            juego.incrementoVelocidad += 0.1;
            juego.getFantasmas().get(0).setVelocidad(0.7 + juego.incrementoVelocidad);
        }
        if (segundoAct > segundoAnt + 0.4) {
            segundoAnt = segundoAct;
        }
        if ((int) juego.getFantasmas().get(3).getX() / SIZE == juego.getFantasmas().get(3).ultPosX
                && (int) juego.getFantasmas().get(3).getY() / SIZE == juego.getFantasmas().get(3).ultPosY
                && !juego.getFantasmas().get(3).isVulnerable() && !juego.getFantasmas().get(3).isEncerrado()) {
            juego.nuevaPosAleatoria(juego.getFantasmas().get(3));
        }
        juego.getPacMan().mover(direccion, juego.getLaberinto());
        juego.getPacMan().comer(juego.getLaberinto(), juego);

        for (Fantasma fant : juego.getFantasmas()) {
            if (fant.isMuerto()) {
                fant.mover(juego.getLaberinto(), null, fant.ultPosX * SIZE, fant.ultPosY * SIZE);
                if ((int) fant.getX() / SIZE == fant.ultPosX
                        && (int) fant.getY() / SIZE == fant.ultPosY) {
                    fant.setMuerto(false);
                    if ("Rojo".equals(fant.getColor()) && !juego.isHiloPowerPellets()) {
                        fant.setVelocidad(0.7 + juego.incrementoVelocidad);
                    } else if (!juego.isHiloPowerPellets()) {
                        fant.setVelocidad(0.7);
                    }
                }
            } else if (!fant.isEncerrado()) {
                if (!fant.isVulnerable()) {
                    fant.comer(juego.getLaberinto(), juego);
                }

                int fantX = (int) fant.getX() / SIZE;
                int fantY = (int) fant.getY() / SIZE;

                if (fant.isVulnerable() && fantX == (int) fant.ultPosX && fantY == (int) fant.ultPosY) {
                    juego.nuevaPosEscape(fant);
                }

                if (fant.isVulnerable()) {
                    fant.mover(juego.getLaberinto(), juego.getPacMan(), fant.ultPosX * SIZE, fant.ultPosY * SIZE);
                } else if (!"floyd".equals(fant.getAlgoritmo())) {
                    fant.mover(juego.getLaberinto(), null, juego.getPacMan().getX(), juego.getPacMan().getY());
                } else {
                    fant.mover(juego.getLaberinto(), null, fant.ultPosX * SIZE, fant.ultPosY * SIZE);
                }
            } else {
                if ((int) fant.getY() / SIZE == ROWS / 2 && (int) (fant.getY() + SIZE - 1) / SIZE == ROWS / 2) {
                    fant.ultPosY = (ROWS / 2) + 1;
                } else if ((int) fant.getY() / SIZE == ROWS / 2 + 1 && (int) (fant.getY() + SIZE - 1) / SIZE == ROWS / 2 + 1) {
                    fant.ultPosY = ROWS / 2;
                }
                fant.mover(juego.getLaberinto(), null, fant.ultPosX * SIZE, fant.ultPosY * SIZE);
            }
        }
    }

    public void pintar() {
        Image muro = new Image("cr/ac/una/pacman/resources/Muro.png");
        graficos.setFill(Color.BLACK);
        graficos.fillRect(SIZE, SIZE, (COLUMNS - 1) * SIZE, ROWS * SIZE);

        graficos.setStroke(Color.YELLOW);
        graficos.setLineWidth(SIZE / 3);
        graficos.strokeLine((COLUMNS / 2) * SIZE, ((ROWS / 2 - 1) * SIZE) + SIZE / 2, ((COLUMNS / 2) * SIZE) + SIZE - 1, ((ROWS / 2 - 1) * SIZE) + SIZE / 2);
        for (int row = 1; row < ROWS; row++) {
            for (int col = 1; col < COLUMNS; col++) {
                char celda = juego.getLaberinto().getMatrizCelda(row, col);
                double x = col * SIZE;
                double y = row * SIZE;

                switch (celda) {
                    case '#' ->
                        graficos.drawImage(muro, x, y, SIZE, SIZE);
                    case 'p' -> {
                        graficos.setFill(Color.YELLOW);
                        graficos.fillOval(x + SIZE / 2.5, y + SIZE / 2.5, SIZE / 5, SIZE / 5);
                    }
                    case '*' -> {
                        graficos.setFill(Color.YELLOW);
                        graficos.fillOval(x + SIZE / 4, y + SIZE / 4, SIZE / 2, SIZE / 2);
                    }
                    default -> {
                    }
                }
            }
        }
        juego.getPacMan().pintar(graficos, segundoAct, segundoAnt);
        for (Fantasma fantasma : juego.getFantasmas()) {
            fantasma.pintar(graficos, segundoAct, segundoAnt);
        }

        if (juego.getPacMan().getVidas() > hboxVidas.getChildren().size()) {
            hboxVidas.getChildren().add(new ImageView(new Image("cr/ac/una/pacman/resources/PacMan.png")));
        } else if (juego.getPacMan().getVidas() < hboxVidas.getChildren().size()) {
            hboxVidas.getChildren().remove(0);
        }
        lbScore.setText(String.valueOf(juego.getPacMan().getPuntos()));
    }
}
