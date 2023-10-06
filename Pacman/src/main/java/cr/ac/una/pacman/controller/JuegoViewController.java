/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.model.Fantasma;
import cr.ac.una.pacman.model.Juego;
import cr.ac.una.pacman.model.PacMan;
import static io.github.palexdev.materialfx.utils.RandomUtils.random;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
    public static final int COLUMNS = 22;
    public static final int SIZE = 20;
    double segundoAct = 0;
    double segundoAct1 = 0;
    double segundoAnt = 0;
    double segundoAnt1 = 0;
    int direccion = 3;
    int posXAle;
    int posYAle;
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
        Fantasma fantasmaRojo = new Fantasma((COLUMNS * SIZE) / 2, (ROWS * SIZE) / 2, 0.8, 3, imagenFantasmaRojo, "Rojo", "dijkstra");
        Fantasma fantasmaRosa = new Fantasma((COLUMNS * SIZE) / 2 + SIZE, (ROWS * SIZE) / 2, 0.8, 3, imagenFantasmaRojo, "Rosa", "dijkstraAlternativo");
        Fantasma fantasmaCian = new Fantasma((COLUMNS * SIZE) / 2 - SIZE, (ROWS * SIZE) / 2, 0.8, 3, imagenFantasmaRojo, "Cian", "dijkstraAlternativo");
        Fantasma fantasmaNaranja = new Fantasma((COLUMNS * SIZE) / 2, (ROWS * SIZE) / 2 - SIZE, 0.8, 3, imagenFantasmaRojo, "Naranja", "floyd");
//        fantasmaRojo.setVulnerable(true);
//        fantasmaRosa.setVulnerable(true);
//        fantasmaCian.setVulnerable(true);
//        fantasmaNaranja.setVulnerable(true);
        fantasmas.add(fantasmaRojo);
        fantasmas.add(fantasmaRosa);
        fantasmas.add(fantasmaCian);
        fantasmas.add(fantasmaNaranja);

        PacMan pacman = new PacMan(3, 0, "A", (COLUMNS * SIZE) / 2, (ROWS * SIZE) / 2, 0.79, 3, imagenPacman);

        juego = new Juego(pacman, fantasmas, ROWS, COLUMNS);
        juego.generarLaberinto();
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
                }
            }
        });

//        juego.nuevaPosEscape(juego.getFantasmas().get(0));
//        juego.nuevaPosEscape(juego.getFantasmas().get(1));
//        juego.nuevaPosEscape(juego.getFantasmas().get(2));
//        juego.nuevaPosEscape(juego.getFantasmas().get(3));
        juego.nuevaPosAleatoria(juego.getFantasmas().get(3));
        ciclo();
    }

    public void ciclo() {
        final double targetFPS = 45.0;
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
                double elapsedSeconds = (tiempoActual - lastUpdate) / 1e9;
                double t = (tiempoActual - tiempoInicial) / 1000000000.0;

                lastUpdate = tiempoActual;
                segundoAct = t;
                segundoAct1 = t;

                segAct += elapsedSeconds;

                if (segAct >= targetFrameTime) {
                    actualizar();
                    pintar();
                    root.requestFocus();
                    segAct -= targetFrameTime;
                }
            }
        };
        animationTimer.start();
    }

    public void actualizar() {
        if (segundoAct > segundoAnt + 0.4) {
            segundoAnt = segundoAct;
        }
        if ((int) juego.getFantasmas().get(3).getX() / SIZE == juego.getFantasmas().get(3).ultPosX
                && (int) juego.getFantasmas().get(3).getY() / SIZE == juego.getFantasmas().get(3).ultPosY) {
            juego.nuevaPosAleatoria(juego.getFantasmas().get(3));
        }
        juego.getPacMan().mover(direccion, juego.getLaberinto());
        juego.getPacMan().comer(juego.getLaberinto(), juego);

        for (Fantasma fant : juego.getFantasmas()) {
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
        }

//        if (juego.getFantasmas().get(2).isVulnerable()) {
//            if ((int) juego.getFantasmas().get(2).getX() / SIZE == (int) juego.getFantasmas().get(2).ultPosX
//                    && (int) juego.getFantasmas().get(2).getY() / SIZE == (int) juego.getFantasmas().get(2).ultPosY) {
//                juego.nuevaPosEscape(juego.getFantasmas().get(2));
//            }
//            juego.getFantasmas().get(2).mover(juego.getLaberinto(), juego.getPacMan(), juego.getFantasmas().get(2).ultPosX * SIZE, juego.getFantasmas().get(2).ultPosY * SIZE);
//        } else {
//            juego.getFantasmas().get(2).mover(juego.getLaberinto(), null, juego.getPacMan().getX(), juego.getPacMan().getY());
//
//        }
//        juego.getFantasmas().get(3).mover(juego.getLaberinto(), posXAle, posYAle);
    }

    public void pintar() {
        Image muro = new Image("cr/ac/una/pacman/resources/Muro.png");
        graficos.setFill(Color.BLACK);
        graficos.fillRect(SIZE, SIZE, COLUMNS * SIZE, ROWS * SIZE);

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
                        graficos.fillOval(x + 7, y + 7, 6, 6);
                    }
                    case '*' -> {
                        graficos.setFill(Color.YELLOW);
                        double radio = 7; // Radio común para ambos casos
                        graficos.fillOval(x + SIZE / 2 - radio, y + SIZE / 2 - radio, 2 * radio, 2 * radio);
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

        lbScore.setText(String.valueOf(juego.getPacMan().getPuntos()));
    }
}
