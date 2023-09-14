/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.pacman.model.Juego;
import cr.ac.una.pacman.model.PacMan;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class PruebaViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private GridPane gpLaberinto;
    @FXML
    private JFXButton jfxBtnGenerarMapa;

    private static final int ROWS = 20;
    private static final int COLUMNS = 32;
    private static final int TILE_SIZE = 20;
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

    @FXML
    private void OnActionJfxBtnGenerarMapa(ActionEvent event) {
        juego.generarLaberinto();
        laberintoInterfaz();
    }

    public void inicializarJuego() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                StackPane tile = new StackPane();
                tile.setPrefSize(TILE_SIZE, TILE_SIZE);
                gpLaberinto.add(tile, col, row);
            }
        }
        PacMan pacman = new PacMan(3, 0, "A", COLUMNS / 2, ROWS / 2, 2, 1, "cr/ac/una/pacman/resources/Muro.png");
        juego = new Juego(pacman, ROWS, COLUMNS);
        juego.generarLaberinto();
        laberintoInterfaz();

        root.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            System.out.println("Evento Teclado");
            switch (keyCode) {

                case RIGHT:
//                    juego.getPacMan().mover(0);
                    break;
                case DOWN:
//                    juego.getPacMan().mover(1);
                    break;
                case LEFT:
//                    juego.getPacMan().mover(2);
                    break;
                case UP:
//                    juego.getPacMan().mover(3);
                    break;
                default:
                    break;
            }
            actualizarInterfaz();
        });
        root.requestFocus();

    }

    public void actualizarInterfaz() {
        StackPane node = null;
        for (Node child : gpLaberinto.getChildren()) { // Limpia el laberinto, la interfaz
            node = (StackPane) child;
            if (!node.getChildren().isEmpty()) {
                node.getChildren().clear();
            }
        }
        for (Node child : gpLaberinto.getChildren()) {
            char celda = juego.getLaberinto().getMatrizCelda(GridPane.getRowIndex(child), GridPane.getColumnIndex(child));
            if (celda == '#') {
                node = (StackPane) child;
                ImageView imgView = new ImageView(new Image("cr/ac/una/pacman/resources/Muro.png"));
                node.getChildren().add(imgView);
            }
            if (GridPane.getRowIndex(child) == juego.getPacMan().getY() && GridPane.getColumnIndex(child) == juego.getPacMan().getX()) {
                node = (StackPane) child;
                ImageView imgView = new ImageView(new Image("cr/ac/una/pacman/resources/PacMan.png"));
                node.getChildren().add(imgView);
            }
        }
    }

    public void laberintoInterfaz() {
        StackPane node = null;
        for (Node child : gpLaberinto.getChildren()) { // Limpia el laberinto, la interfaz
            node = (StackPane) child;
            if (!node.getChildren().isEmpty()) {
                node.getChildren().clear();
            }
        }
        for (Node child : gpLaberinto.getChildren()) {
            char celda = juego.getLaberinto().getMatrizCelda(GridPane.getRowIndex(child), GridPane.getColumnIndex(child));
            if (celda == '#') {
                node = (StackPane) child;
                ImageView imgView = new ImageView(new Image("cr/ac/una/pacman/resources/Muro.png"));
                node.getChildren().add(imgView);
            }
            if (GridPane.getRowIndex(child) == juego.getPacMan().getY() && GridPane.getColumnIndex(child) == juego.getPacMan().getX()) {
                node = (StackPane) child;
                ImageView imgView = new ImageView(new Image("cr/ac/una/pacman/resources/PacMan.png"));
                node.getChildren().add(imgView);
            }
        }
    }
}
