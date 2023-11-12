/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.model.Juego;
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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class P06_GameOverViewController extends Controller implements Initializable {

    @FXML
    private Label lbGameWinOver;
    @FXML
    private Label lbPuntosTotales;
    @FXML
    private HBox hxContVidas;
    @FXML
    private HBox hxContBoton;
    @FXML
    private MFXButton btnAbandonar;

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
    private void onActionBtnAbandonar(ActionEvent event) {
        JuegoViewController juegoView = (JuegoViewController) FlowController.getInstance().getController("JuegoView");
        juegoView.FinalizarJuego();
        hxContBoton.getChildren().clear();
        getStage().close();
    }

    public void cargarInterfazJuegoCompletado(Juego juego, Partida partida) {
        Trofeo trofeo = partida.obtenerTrofeo("Experto");
        if (!juego.pacmanMurio && !trofeo.isDesbloqueado()) {
            trofeo.setCont(1);
            if (trofeo.getCont() >= 3) {
                trofeo.setDesbloqueado(true);
            }
            partida.actualizarTrofeo("Experto", trofeo);
        }
        trofeo = partida.obtenerTrofeo("Clasico");
        if (!trofeo.isDesbloqueado()) {
            trofeo.setDesbloqueado(true);
            partida.actualizarTrofeo("Clasico", trofeo);
        }
        trofeo = partida.obtenerTrofeo("Rey");
        if ("Dificil".equals(partida.getDificultad()) && !trofeo.isDesbloqueado()) {
            trofeo.setDesbloqueado(true);
            partida.actualizarTrofeo("Rey", trofeo);
        }
    }

    public void cargarInterfazSiguienteNivel(Juego juego, Partida partida) {
        Trofeo trofeo = partida.obtenerTrofeo("Experto");
        if (!juego.pacmanMurio && !trofeo.isDesbloqueado()) {
            trofeo.setCont(1);
            if (trofeo.getCont() >= 3) {
                trofeo.setDesbloqueado(true);
            }
            partida.actualizarTrofeo("Experto", trofeo);
        }
        MFXButton btnContinuar = new MFXButton("Siguiente Nivel");
        btnContinuar.setOnAction(event -> {
            JuegoViewController juegoView = (JuegoViewController) FlowController.getInstance().getController("JuegoView");
            juegoView.SiguienteNivel();
            hxContBoton.getChildren().clear();
            getStage().close();
        });
        hxContBoton.getChildren().add(btnContinuar);
    }

    public void cargarInterfazPausa(Juego juego, Partida partida) {
        MFXButton btnContinuar = new MFXButton("Continuar");
        btnContinuar.setOnAction(event -> {
            hxContBoton.getChildren().clear();
            juego.continuar();
            getStage().close();
        });
        hxContBoton.getChildren().add(btnContinuar);
    }

    public void cargarInterfazSinVidas(Juego juego, Partida partida) {
        MFXButton btnContinuar = new MFXButton("Comprar Vida y Continuar -1500pts.");
        btnContinuar.setOnAction(event -> {
            juego.getPacMan().setPuntos(juego.getPacMan().getPuntos() - 1500);
            juego.getPacMan().setVidas(juego.getPacMan().getVidas() + 1);
            hxContBoton.getChildren().clear();
            juego.continuar();
            getStage().close();
        });
        hxContBoton.getChildren().add(btnContinuar);
    }

    public void cargarInterfazSinVidasSinPuntos(Juego juego, Partida partida) {
        MFXButton btnContinuar = new MFXButton("Reintentar nivel " + juego.getNivel());
        btnContinuar.setOnAction(event -> {
            JuegoViewController juegoView = (JuegoViewController) FlowController.getInstance().getController("JuegoView");
            juegoView.reiniciarJuego();
            hxContBoton.getChildren().clear();
            getStage().close();
        });
        hxContBoton.getChildren().add(btnContinuar);
    }

}
