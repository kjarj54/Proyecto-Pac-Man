/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.pacman.model.Juego;
import cr.ac.una.pacman.model.PacMan;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class JuegoViewController extends Controller implements Initializable {
    
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton jfxBtnGenerarMapa;
    @FXML
    private Canvas cvLaberinto;
    
    AnimationTimer animationTimer;
    GraphicsContext graficos;
    private static final int ROWS = 20;
    private static final int COLUMNS = 32;
    private static final int TILE_SIZE = 20;
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

        PacMan pacman = new PacMan(3, 0, "A", (COLUMNS * 20) / 2, (ROWS * 20) / 2, 2, 3, "cr/ac/una/pacman/resources/PacMan.png");
        juego = new Juego(pacman, ROWS, COLUMNS);
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

        ciclo();
    }

    public void ciclo() {
        long tiempoInicial = System.nanoTime();
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long tiempoActual) {
                double t = (tiempoActual - tiempoInicial) / 1000000000.0;
                actualizar();
                pintar();
            }
        };
        animationTimer.start();
    }

    public void actualizar() {
        juego.getPacMan().mover(direccion, juego.getLaberinto());

    }

    public void pintar() {
        Image muro = new Image("cr/ac/una/pacman/resources/Muro.png");
        Image avatar = new Image("cr/ac/una/pacman/resources/AvatarFondo.png");
        graficos.drawImage(avatar, 0, 0);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                char celda = juego.getLaberinto().getMatrizCelda(row, col);
                if (celda == '#') {
                    graficos.drawImage(muro, col * 20, row * 20);
                }
            }
        }
        juego.getPacMan().pintar(graficos);
    }

    @FXML
    private void OnActionJfxBtnGenerarMapa(ActionEvent event) {
    }
    
}
