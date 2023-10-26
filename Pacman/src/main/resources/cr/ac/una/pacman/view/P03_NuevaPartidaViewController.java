/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.view;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class P03_NuevaPartidaViewController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MediaView mdvFondoJugador;
    @FXML
    private MFXButton btnSalir;
    @FXML
    private MFXTextField txfNombre;
    @FXML
    private MFXRadioButton rdbAutomatico;
    @FXML
    private ToggleGroup tggModoJuego;
    @FXML
    private MFXRadioButton rdbManual;
    @FXML
    private MFXButton btnIniciar;
    @FXML
    private ImageView imvPlayButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
    }

    @FXML
    private void onActionBtnIniciar(ActionEvent event) {
    }
    
}
