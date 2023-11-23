/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.model.Partida;
import cr.ac.una.pacman.util.AppContext;
import cr.ac.una.pacman.util.FlowController;
import cr.ac.una.pacman.util.ManejoDatos;
import cr.ac.una.pacman.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class P04_ContinuarPartidaViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MediaView mdvCargarPartida;
    @FXML
    private MFXButton btnSalir;
    @FXML
    private MFXTextField txfNombre;
    @FXML
    private MFXButton btnCargarPartida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().goView("P02_MenuView");
    }

    @FXML
    private void onActionBtnIniciar(ActionEvent event) {
        if (txfNombre.getText().isBlank() || txfNombre.getText().isEmpty()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Ingresar Jugador", getStage(), "Es necesario digitar un nombre para cargar partida");
        } else if (ManejoDatos.buscarJugadorPorNombre(txfNombre.getText()) == null) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error cargando partida", getStage(), "No hay partidas guardadas con el nombre");
        } else {
            Partida partida = ManejoDatos.buscarJugadorPorNombre(txfNombre.getText());
            AppContext.getInstance().set("Partida", partida);
            FlowController.getInstance().delete("P03_ContinuarPartidaView");
            FlowController.getInstance().goView("P05_PartidaView");
        }
    }
    
}
