/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.model.Partida;
import cr.ac.una.pacman.model.Trofeo;
import cr.ac.una.pacman.util.AppContext;
import cr.ac.una.pacman.util.FlowController;
import cr.ac.una.pacman.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class P05_PartidaViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnSalir;
    @FXML
    private Label lbTitulo;
    @FXML
    private VBox vxContJuegos;
    @FXML
    private VBox vxContTrofeos;
    @FXML
    private MFXButton btnEstadisticas;

    Partida partida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partida = (Partida) AppContext.getInstance().get("Partida");
        lbTitulo.setText(partida.getJugador());
        int z = 0;
        for (int i = 1; i <= 5; i++) {
            HBox hxItems = new HBox();
            hxItems.setAlignment(Pos.CENTER);
            for (int j = 0 + i; j < 2 + i; j++) {
                VBox vxItem = new VBox();
                vxItem.setId("" + (j + z));
                vxItem.setPrefHeight(150);
                vxItem.setPrefWidth(200);
                vxItem.setAlignment(Pos.TOP_CENTER);
                vxItem.setDisable(!partida.getNivel((j + z - 1)).isDesbloqueado());
                vxItem.setOnMouseClicked(event -> {
                    if (new Mensaje().showConfirmation("Iniciar nivel", getStage(), "Desea jugar el nivel " + vxItem.getId() + "?")) {
                        AppContext.getInstance().set("Partida", partida);
                        AppContext.getInstance().set("Nivel", vxItem.getId());
                        FlowController.getInstance().delete("P05_PartidaView");
                        FlowController.getInstance().goView("JuegoView");
                    }
                });
                // Cambiar el fondo a gris cuando el mouse pasa por encima
                vxItem.setOnMouseEntered(event -> {
                    vxItem.setStyle("-fx-background-color: grey;");
                });
                // Restaurar el fondo cuando el mouse sale
                vxItem.setOnMouseExited(event -> {
                    vxItem.setStyle("-fx-background-color: transparent;");
                });

                Label lbTituloItem = new Label("" + (j + z));
                vxItem.getChildren().add(lbTituloItem);
                hxItems.getChildren().add(vxItem);
            }
            z++;
            vxContJuegos.getChildren().add(hxItems);
        }

        String[] vector = {"Clasico", "Cazador", "Experto", "Encierro", "Flash", "Rey"};
        for (int i = 0; i < 6; i++) {
            Trofeo trofeo = partida.obtenerTrofeo(vector[i]);
            VBox vxItem = new VBox();
            vxItem.setId(vector[i]);
            vxItem.setPrefHeight(150);
            vxItem.setPrefWidth(200);
            vxItem.setAlignment(Pos.TOP_CENTER);
            vxItem.setDisable(!trofeo.isDesbloqueado());
            // Cambiar el fondo a gris cuando el mouse pasa por encima
            vxItem.setOnMouseEntered(event -> {
                vxItem.setStyle("-fx-background-color: grey;");
            });
            // Restaurar el fondo cuando el mouse sale
            vxItem.setOnMouseExited(event -> {
                vxItem.setStyle("-fx-background-color: transparent;");
            });

            Label lbTituloItem = new Label(trofeo.getNombre());
            vxItem.getChildren().add(lbTituloItem);
            vxContTrofeos.getChildren().add(vxItem);
        }
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().delete("P05_PartidaView");
        FlowController.getInstance().goView("P02_MenuView");
    }

    @FXML
    private void onActionBtnEstadisticas(ActionEvent event) {
    }

}
