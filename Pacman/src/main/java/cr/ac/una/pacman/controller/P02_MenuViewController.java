/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class P02_MenuViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MediaView mdvMenu;
    @FXML
    private MFXButton btnNuevaPartida;
    @FXML
    private MFXButton btnContinuarPartida;
    @FXML
    private MFXButton btnAcercaDe;
    @FXML
    private MFXButton btnSalir;
    @FXML
    private TableView<?> tbvMejoresTiempos;

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
    private void onActionBtnNuevaPartida(ActionEvent event) {
        FlowController.getInstance().goView("P03_NuevaPartidaView");
    }

    @FXML
    private void onActionBtnContinuarPartida(ActionEvent event) {
        FlowController.getInstance().goView("P04_ContinuarPartidaView");
    }

    @FXML
    private void onActionBtnAcercaDe(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("P07_AcercaDeView", stage, Boolean.FALSE);
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().salir();
    }

}
