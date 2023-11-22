/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class P07_EstadisticasViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnSalir;
    @FXML
    private TableView<?> tbvHistorico;
    @FXML
    private TableColumn<?, ?> tbcPosicion;
    @FXML
    private TableColumn<?, ?> tbcNombre;
    @FXML
    private TableColumn<?, ?> tbcPuntaje;
    @FXML
    private Label lbPlayer;
    @FXML
    private Label lbTotalPuntos;
    @FXML
    private Label lbMayorPuntosNoVidas;
    @FXML
    private Label lbTotalVidasP;
    @FXML
    private Label lbFantasmasComidos;
    @FXML
    private Label lbTiempoTotalJ;
    @FXML
    private VBox vbxContPuntosXnivel;
    @FXML
    private VBox vbxContCantNivelJugado;
    @FXML
    private VBox bvxMejorTiempoXnivel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
    }
    
}
