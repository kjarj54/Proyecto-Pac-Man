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
//        Fantasma fantasmaRojo = new Fantasma((COLUMNS * SIZE) / 2, (ROWS * SIZE) / 2, 0.8, 3, imagenFantasmaRojo, "Rojo", "dijkstra");
//        Fantasma fantasmaRosa = new Fantasma((COLUMNS * SIZE) / 2 + SIZE, (ROWS * SIZE) / 2, 0.8, 3, imagenFantasmaRojo, "Rosa", "dijkstraAlternativo");
//        Fantasma fantasmaCian = new Fantasma((COLUMNS * SIZE) / 2 - SIZE, (ROWS * SIZE) / 2, 0.8, 3, imagenFantasmaRojo, "Cian", "dijkstraAlternativo");
//        Fantasma fantasmaNaranja = new Fantasma((COLUMNS * SIZE) / 2, (ROWS * SIZE) / 2 - SIZE, 0.8, 3, imagenFantasmaRojo, "Naranja", "floyd");
        Fantasma fantasmaRojo = new Fantasma((COLUMNS * SIZE) / 2, (ROWS * SIZE) / 2, 0.8, 3, imagenFantasmaRojo, "Rojo", "escapar");
        Fantasma fantasmaRosa = new Fantasma((COLUMNS * SIZE) / 2 + SIZE, (ROWS * SIZE) / 2, 0.8, 3, imagenFantasmaRojo, "Rosa", "escapar");
        Fantasma fantasmaCian = new Fantasma((COLUMNS * SIZE) / 2 - SIZE, (ROWS * SIZE) / 2, 0.8, 3, imagenFantasmaRojo, "Cian", "escapar");
        fantasmaCian.setVulnerable(true);
        Fantasma fantasmaNaranja = new Fantasma((COLUMNS * SIZE) / 2, (ROWS * SIZE) / 2 - SIZE, 0.8, 3, imagenFantasmaRojo, "Naranja", "floyd");
        fantasmas.add(fantasmaRojo);
        fantasmas.add(fantasmaRosa);
        fantasmas.add(fantasmaCian);
        fantasmas.add(fantasmaNaranja);

        PacMan pacman = new PacMan(3, 0, "A", (COLUMNS * SIZE) / 2, (ROWS * SIZE) / 2, 0.8, 3, imagenPacman);

        juego = new Juego(pacman, fantasmas, ROWS, COLUMNS);
        juego.generarLaberinto();
        pintar();

        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evento) {
                switch (evento.getCode().toString()) {
                    case "D":
                        direccion = 0;
                        break;
                    case "S":
                        direccion = 1;
                        break;
                    case "A":
                        direccion = 2;
                        break;
                    case "W":
                        direccion = 3;
                        break;
                }
            }
        });

        nuevaPosEscape(juego.getFantasmas().get(2));
        nuevaPosAleatoria();
        ciclo();
    }

    public void ciclo() {
        long tiempoInicial = System.nanoTime();
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long tiempoActual) {
                double t = (tiempoActual - tiempoInicial) / 1000000000.0;
                segundoAct = t;
                segundoAct1 = t;
                actualizar();
                pintar();
                root.requestFocus();
            }
        };
        animationTimer.start();
    }

    public void actualizar() {
        if (segundoAct > segundoAnt + 0.4) {
            segundoAnt = segundoAct;
//            System.out.println("X : " + juego.getPacMan().getX() / SIZE + " | Y: " + juego.getPacMan().getY() / SIZE);
        }
        if (segundoAct1 > segundoAnt1 + 10
                || (int) juego.getFantasmas().get(3).getX() / SIZE == (int) posXAle / SIZE
                && (int) juego.getFantasmas().get(3).getY() / SIZE == (int) posYAle / SIZE) {
            segundoAnt1 = segundoAct1;
            nuevaPosAleatoria();
        }

        juego.getPacMan().mover(direccion, juego.getLaberinto());
        juego.getPacMan().comer(juego.getLaberinto());
//        juego.getFantasmas().get(0).mover(juego.getLaberinto(), juego.getPacMan().getX(), juego.getPacMan().getY());
//        juego.getFantasmas().get(1).mover(juego.getLaberinto(), juego.getPacMan().getX(), juego.getPacMan().getY());
        if (juego.getFantasmas().get(2).isVulnerable()) {
            if ((int) juego.getFantasmas().get(2).getX() / SIZE == (int) juego.getFantasmas().get(2).ultPosX
                    && (int) juego.getFantasmas().get(2).getY() / SIZE == (int) juego.getFantasmas().get(2).ultPosY) {
                nuevaPosEscape(juego.getFantasmas().get(2));
            }
            juego.getFantasmas().get(2).mover(juego.getLaberinto(), juego.getPacMan(), juego.getFantasmas().get(2).ultPosX * SIZE, juego.getFantasmas().get(2).ultPosY * SIZE);            
        } else {
            juego.getFantasmas().get(2).mover(juego.getLaberinto(), null, juego.getPacMan().getX(), juego.getPacMan().getY());

        }
//        juego.getFantasmas().get(3).mover(juego.getLaberinto(), posXAle, posYAle);
    }

    public void pintar() {
        Image muro = new Image("cr/ac/una/pacman/resources/Muro.png");
        Image avatar = new Image("cr/ac/una/pacman/resources/AvatarFondo.png");
        graficos.setFill(Color.BLACK);
        graficos.fillRect(SIZE, SIZE, COLUMNS * SIZE, ROWS * SIZE);
//        graficos.drawImage(avatar, SIZE, SIZE);
        for (int row = 1; row < ROWS; row++) {
            for (int col = 1; col < COLUMNS; col++) {
                char celda = juego.getLaberinto().getMatrizCelda(row, col);
                if (celda == '#') {
                    graficos.drawImage(muro, col * SIZE, row * SIZE, SIZE, SIZE);
                }
                if (celda == 'p') {
                    graficos.setFill(Color.YELLOW);
                    graficos.fillOval(col * SIZE + 7, row * SIZE + 7, 6, 6);
                }
                if (celda == '*') {
                    graficos.setFill(Color.YELLOW);
                    graficos.fillOval(col * SIZE + 3, row * SIZE + 3, 14, 14);
                }
            }
        }
        juego.getPacMan().pintar(graficos, segundoAct, segundoAnt);
        juego.getFantasmas().get(0).pintar(graficos, segundoAct, segundoAnt);
        juego.getFantasmas().get(1).pintar(graficos, segundoAct, segundoAnt);
        juego.getFantasmas().get(2).pintar(graficos, segundoAct, segundoAnt);
        juego.getFantasmas().get(3).pintar(graficos, segundoAct, segundoAnt);
        lbScore.setText("" + juego.getPacMan().getPuntos());
    }

    public void nuevaPosAleatoria() {
        boolean Listo = false;
        while (!Listo) {
            int randomX = random.nextInt(COLUMNS - 1) + 1;
            int randomY = random.nextInt(ROWS - 1) + 1;
            if (juego.getLaberinto().getMatrizCelda(randomY, randomX) == ' ' || juego.getLaberinto().getMatrizCelda(randomY, randomX) == 'p') {
                posXAle = randomX * SIZE;
                posYAle = randomY * SIZE;
                Listo = true;
            }
        }
    }

    public void nuevaPosEscape(Fantasma fantasma) {
        boolean Listo = false;
        while (!Listo) {
            int randomX = random.nextInt(COLUMNS - 1) + 1;
            int randomY = random.nextInt(ROWS - 1) + 1;
            if ((juego.getLaberinto().getMatrizCelda(randomY, randomX) == ' '
                    || juego.getLaberinto().getMatrizCelda(randomY, randomX) == 'p'
                    || juego.getLaberinto().getMatrizCelda(randomY, randomX) == '*')
                    && (Math.sqrt((juego.getPacMan().getX() / SIZE - randomX) * (juego.getPacMan().getX() / SIZE - randomX)
                            + (juego.getPacMan().getY() / SIZE - randomY) * (juego.getPacMan().getY() / SIZE - randomY)) >= 10)
                    && (randomX != fantasma.ultPosX || fantasma.ultPosY != randomY)) {
                System.out.println("X ante: " + fantasma.ultPosX + " Y ante: " + fantasma.ultPosY);
                fantasma.ultPosX = randomX;
                fantasma.ultPosY = randomY;
                System.out.println("X nuevo: " + fantasma.ultPosX + " Y nuevo: " + fantasma.ultPosY);
                Listo = true;
            }
        }
    }

}
