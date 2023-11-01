/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.model.Fantasma;
import cr.ac.una.pacman.model.Juego;
import cr.ac.una.pacman.model.Laberinto;
import cr.ac.una.pacman.model.PacMan;
import cr.ac.una.pacman.model.Partida;
import cr.ac.una.pacman.util.AppContext;
import cr.ac.una.pacman.util.FlowController;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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
    private Label lbNivel;
    @FXML
    private HBox hboxVidas;

    AnimationTimer animationTimer;
    GraphicsContext graficos;
    public static final int ROWS = 20;
    public static final int COLUMNS = 16;
    public static final int SIZE = 20;
    double iniX = (COLUMNS * SIZE) / 2;
    double iniY = (ROWS * SIZE) / 2;
    double segundoAct;
    double segundoAnt;
    int direccion;
    int nivel;
    Partida partida;
    Juego juego;
    Laberinto laberinto;

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
        partida = (Partida) AppContext.getInstance().get("Partida");
        nivel = Integer.parseInt((String) AppContext.getInstance().get("Nivel"));
        laberinto = partida.getNivel(nivel - 1);

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
//            fantasmaRojo.setVulnerable(true);
//            fantasmaRosa.setVulnerable(true);
//            fantasmaCian.setVulnerable(true);
//            fantasmaNaranja.setVulnerable(true);
        fantasmas.add(fantasmaRojo);
        fantasmas.add(fantasmaRosa);
        fantasmas.add(fantasmaCian);
        fantasmas.add(fantasmaNaranja);

        PacMan pacman = new PacMan(6, 0, "A", (COLUMNS * SIZE) / 2, (ROWS * SIZE) / 2, 0.69, 3, imagenPacman);

        juego = new Juego(pacman, fantasmas, ROWS, COLUMNS, nivel, laberinto);
        juego.posIniPacman();
//        this.getPacMan().setX(3 * SIZE);
//        this.getPacMan().setY(10 * SIZE);
        juego.contarPuntosTotales();

//            juego.nuevaPosEscape(juego.getFantasmas().get(0));
//            juego.nuevaPosEscape(juego.getFantasmas().get(1));
//            juego.nuevaPosEscape(juego.getFantasmas().get(2));
//            juego.nuevaPosEscape(juego.getFantasmas().get(3));
        juego.nuevaPosAleatoria(juego.getFantasmas().get(3));

        lbPlayer.setText(partida.getJugador());
        lbNivel.setText("Nivel " + juego.getNivel());
        lbHighScore.setText("" + partida.obtenerEstadistica("MayorPuntosN" + juego.getNivel()));
        graficos = cvLaberinto.getGraphicsContext2D();
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
                        if (!juego.pausa) {
                            juego.pausa();
                            P06_GameOverViewController gameOver = (P06_GameOverViewController) FlowController.getInstance().getController("P06_GameOverView");
                            gameOver.cargarInterfazPausa(juego, partida);
                            FlowController.getInstance().goViewInWindowModal("P06_GameOverView", stage, false);
                        }
                    }

                }
            }
        });
        juego.animacionInicialFantasmas();
        segundoAct = 0;
        segundoAnt = 0;
        direccion = 3;
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

                if (segAct >= targetFrameTime && !juego.pausa && juego.puntosActuales == 0) {
                    juego.pausa();
                    P06_GameOverViewController gameOver = (P06_GameOverViewController) FlowController.getInstance().getController("P06_GameOverView");
                    gameOver.cargarInterfazSiguienteNivel(juego, partida);

                    Platform.runLater(() -> {
                        FlowController.getInstance().goViewInWindowModal("P06_GameOverView", stage, false);
                    });
                }
                if (segAct >= targetFrameTime && !juego.pausa && juego.getPacMan().getVidas() <= 0) {
                    juego.pausa();
                    P06_GameOverViewController gameOver = (P06_GameOverViewController) FlowController.getInstance().getController("P06_GameOverView");
                    if (juego.getPacMan().getPuntos() > 1500) {
                        gameOver.cargarInterfazSinVidas(juego, partida);
                    } else {
                        gameOver.cargarInterfazSinVidasSinPuntos(juego, partida);
                    }
                    Platform.runLater(() -> {
                        FlowController.getInstance().goViewInWindowModal("P06_GameOverView", stage, false);
                    });
                }
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
        if (!juego.encierroUsado && !juego.pacmanMurio && juego.puntosActuales <= juego.puntosTotales / 2) {
            juego.encierro();
        }
        if (juego.getPacMan().getPuntos() >= 500 && juego.incrementoVelocidad == 0) {
            juego.incrementoVelocidad += 0.02;
            juego.getFantasmas().get(0).setVelocidad(0.7 + juego.incrementoVelocidad);
        } else if (juego.getPacMan().getPuntos() >= 1000 && juego.incrementoVelocidad == 0.02) {
            juego.incrementoVelocidad = 0.05;
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

    public void reiniciarJuego() {
        if (partida.obtenerEstadistica("MayorPuntosN" + juego.getNivel()) < juego.getPacMan().getPuntos()) {
            partida.actualizarEstadistica("MayorPuntosN" + juego.getNivel(), juego.getPacMan().getPuntos());
        }
        AppContext.getInstance().set("Partida", partida);
        AppContext.getInstance().set("Nivel", "" + nivel);
        animationTimer.stop();
        juego.matarHilos();
        inicializarJuego();
    }

    public void SiguienteNivel() {
        if (partida.obtenerEstadistica("MayorPuntosN" + juego.getNivel()) < juego.getPacMan().getPuntos()) {
            partida.actualizarEstadistica("MayorPuntosN" + juego.getNivel(), juego.getPacMan().getPuntos());
        }
        partida.getNivel(nivel).setDesbloqueado(true);
        AppContext.getInstance().set("Partida", partida);
        AppContext.getInstance().set("Nivel", "" + (nivel + 1));
        animationTimer.stop();
        juego.matarHilos();
        inicializarJuego();
    }

    public void FinalizarJuego() {
        if (partida.obtenerEstadistica("MayorPuntosN" + juego.getNivel()) < juego.getPacMan().getPuntos()) {
            partida.actualizarEstadistica("MayorPuntosN" + juego.getNivel(), juego.getPacMan().getPuntos());
        }
        AppContext.getInstance().set("Partida", partida);
        animationTimer.stop();
        juego.matarHilos();
        FlowController.getInstance().delete("JuegoView");
        FlowController.getInstance().goView("P05_PartidaView");
    }
}
