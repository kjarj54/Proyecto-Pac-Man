/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.model.Partida;
import cr.ac.una.pacman.util.AppContext;
import cr.ac.una.pacman.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
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

    Partida partida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partida = (Partida) AppContext.getInstance().get("Partida");
        lbPlayer.setText(partida.getJugador());
        lbTotalPuntos.setText("" + partida.obtenerEstadistica("TotalPuntos"));
        lbMayorPuntosNoVidas.setText("" + partida.obtenerEstadistica("MayorPuntosVidas"));
        lbTotalVidasP.setText("" + partida.obtenerEstadistica("TotalVidasPerdidas"));
        lbFantasmasComidos.setText("" + partida.obtenerEstadistica("TotalFantasmasComidos"));
        lbTiempoTotalJ.setText("--");
        if (partida.obtenerEstadistica("TiempoTotal") != 9999999) {
            int time = partida.obtenerEstadistica("TiempoTotal");
            int minutes = time / 60;
            int seconds = time % 60;

            String minutesStr = String.format("%02d", minutes); // Formatea los minutos con dos dígitos
            String secondsStr = String.format("%02d", seconds); // Formatea los segundos con dos dígitos
            lbTiempoTotalJ.setText("Tiempo: " + minutesStr + ":" + secondsStr);
        }

        // Crear un bucle que se repita 10 veces
        for (int i = 0; i < 10; i++) {
            // Crear un HBox
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(25);

            // Crear dos Label y configurar su texto
            Label labelNumero = new Label("Nivel " + Integer.toString(i + 1));
            Label labelSeparador = new Label("--");
            if (partida.obtenerEstadistica("MayorPuntosN" + (i + 1)) != 0) {
                labelSeparador.setText("" + partida.obtenerEstadistica("MayorPuntosN" + (i + 1)));
            }

            // Agregar los Label al HBox
            hbox.getChildren().addAll(labelNumero, labelSeparador);

            // Agregar el HBox al VBox
            vbxContPuntosXnivel.getChildren().add(hbox);
        }
        // Crear un bucle que se repita 10 veces
        for (int i = 0; i < 10; i++) {
            // Crear un HBox
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(25);

            // Crear dos Label y configurar su texto
            Label labelNumero = new Label("Nivel " + Integer.toString(i + 1));
            Label labelSeparador = new Label("--");
            if (partida.obtenerEstadistica("CantNivelJugadoN" + (i + 1)) != 0) {
                labelSeparador.setText("" + partida.obtenerEstadistica("CantNivelJugadoN" + (i + 1)));
            }

            // Agregar los Label al HBox
            hbox.getChildren().addAll(labelNumero, labelSeparador);

            // Agregar el HBox al VBox
            vbxContCantNivelJugado.getChildren().add(hbox);
        }
        // Crear un bucle que se repita 10 veces
        for (int i = 0; i < 10; i++) {
            // Crear un HBox
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(25);

            // Crear dos Label y configurar su texto
            Label labelNumero = new Label("Nivel " + Integer.toString(i + 1));
            Label labelSeparador = new Label("--");
            if (partida.obtenerEstadistica("MejorTiempoN" + (i + 1)) != 0) {
                int time = partida.obtenerEstadistica("MejorTiempoN" + (i + 1));
                int minutes = time / 60;
                int seconds = time % 60;

                String minutesStr = String.format("%02d", minutes); // Formatea los minutos con dos dígitos
                String secondsStr = String.format("%02d", seconds); // Formatea los segundos con dos dígitos
                labelSeparador.setText("Tiempo: " + minutesStr + ":" + secondsStr);
            }

            // Agregar los Label al HBox
            hbox.getChildren().addAll(labelNumero, labelSeparador);

            // Agregar el HBox al VBox
            bvxMejorTiempoXnivel.getChildren().add(hbox);
        }
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().delete("P07_EstadisticasView");
        FlowController.getInstance().goView("P05_PartidaView");
    }

}
