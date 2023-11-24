package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.model.Juego;
import cr.ac.una.pacman.model.Partida;
import cr.ac.una.pacman.model.Trofeo;
import cr.ac.una.pacman.util.FlowController;
import cr.ac.una.pacman.util.ManejoDatos;
import cr.ac.una.pacman.util.SoundUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        onActionMouse();
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnAbandonar(ActionEvent event) {
        SoundUtil.mouseHoverSound();
        JuegoViewController juegoView = (JuegoViewController) FlowController.getInstance().getController("JuegoView");
        juegoView.FinalizarJuego();
        hxContBoton.getChildren().clear();
        getStage().close();
    }

    public void cargarInterfazJuegoCompletado(Juego juego, Partida partida) {
        lbGameWinOver.setText("Partida Completada");
        lbPuntosTotales.setText("" + juego.getPacMan().getPuntos());
        cargarVidas(juego);
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
        trofeo = partida.obtenerTrofeo("Rey del PacMan");
        if ("Dificil".equals(partida.getDificultad()) && !trofeo.isDesbloqueado()) {
            trofeo.setDesbloqueado(true);
            partida.actualizarTrofeo("Rey del PacMan", trofeo);
        }
        if (partida.obtenerEstadistica("MejorTiempoN" + juego.getNivel()) > juego.cronometro.getTime()) {
            partida.actualizarEstadistica("MejorTiempoN" + juego.getNivel(), juego.cronometro.getTime());
            partida.actualizarEstadistica("TiempoTotal", 0);
            for (int i = 1; i <= 10; i++) {
                if ((int) partida.obtenerEstadistica("MejorTiempoN" + i) != 9999999) {
                    partida.actualizarEstadistica("TiempoTotal", ((int) partida.obtenerEstadistica("TiempoTotal")
                            + (int) partida.obtenerEstadistica("MejorTiempoN" + i)));
                }
            }
        }
        if (partida.obtenerEstadistica("MayorPuntosN" + juego.getNivel()) < juego.getPacMan().getPuntos()) {
            partida.actualizarEstadistica("MayorPuntosN" + juego.getNivel(), juego.getPacMan().getPuntos());
            partida.actualizarEstadistica("TotalPuntos", 0);
            for (int i = 1; i <= 10; i++) {
                partida.actualizarEstadistica("TotalPuntos", ((int) partida.obtenerEstadistica("TotalPuntos")
                        + (int) partida.obtenerEstadistica("MayorPuntosN" + i)));
            }
        }
        if (!juego.pacmanMurio && partida.obtenerEstadistica("MayorPuntosVidas") < juego.getPacMan().getPuntos()) {
            partida.actualizarEstadistica("MayorPuntosVidas", juego.getPacMan().getPuntos());
        }
        ManejoDatos.guardarPartidas(partida);
        ManejoDatos.guardarRecord(partida);
    }

    public void cargarInterfazSiguienteNivel(Juego juego, Partida partida) {
        lbGameWinOver.setText("Nivel Completado");
        lbPuntosTotales.setText("" + juego.getPacMan().getPuntos());
        cargarVidas(juego);
        Trofeo trofeo = partida.obtenerTrofeo("Experto");
        if (!juego.pacmanMurio && !trofeo.isDesbloqueado()) {
            trofeo.setCont(1);
            if (trofeo.getCont() >= 3) {
                trofeo.setDesbloqueado(true);
            }
            partida.actualizarTrofeo("Experto", trofeo);
        }
        if (partida.obtenerEstadistica("MejorTiempoN" + juego.getNivel()) > juego.cronometro.getTime()) {
            partida.actualizarEstadistica("MejorTiempoN" + juego.getNivel(), juego.cronometro.getTime());
            partida.actualizarEstadistica("TiempoTotal", 0);
            for (int i = 1; i <= 10; i++) {
                if ((int) partida.obtenerEstadistica("MejorTiempoN" + i) != 9999999) {
                    partida.actualizarEstadistica("TiempoTotal", ((int) partida.obtenerEstadistica("TiempoTotal")
                            + (int) partida.obtenerEstadistica("MejorTiempoN" + i)));
                }
            }
        }
        if (partida.obtenerEstadistica("MayorPuntosN" + juego.getNivel()) < juego.getPacMan().getPuntos()) {
            partida.actualizarEstadistica("MayorPuntosN" + juego.getNivel(), juego.getPacMan().getPuntos());
            partida.actualizarEstadistica("TotalPuntos", 0);
            for (int i = 1; i <= 10; i++) {
                partida.actualizarEstadistica("TotalPuntos", ((int) partida.obtenerEstadistica("TotalPuntos")
                        + (int) partida.obtenerEstadistica("MayorPuntosN" + i)));
            }
        }
        if (!juego.pacmanMurio && partida.obtenerEstadistica("MayorPuntosVidas") < juego.getPacMan().getPuntos()) {
            partida.actualizarEstadistica("MayorPuntosVidas", juego.getPacMan().getPuntos());
        }
        MFXButton btnContinuar = new MFXButton(" Siguiente Nivel");
        btnContinuar.getStyleClass().add("mfx-button-menu");
        btnContinuar.setOnAction(event -> {
            JuegoViewController juegoView = (JuegoViewController) FlowController.getInstance().getController("JuegoView");
            juegoView.SiguienteNivel();
            hxContBoton.getChildren().clear();
            getStage().close();
        });
        btnContinuar.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
            btnContinuar.setText("►Siguiente Nivel");
        });
        btnContinuar.setOnMouseExited(event -> btnContinuar.setText(" Siguiente Nivel"));
        
        hxContBoton.getChildren().add(btnContinuar);
        partida.getNivel(juego.getNivel()).setDesbloqueado(true);
        ManejoDatos.guardarPartidas(partida);
        ManejoDatos.guardarRecord(partida);
    }

    public void cargarInterfazPausa(Juego juego, Partida partida) {
        lbGameWinOver.setText("Juego Pausado");
        lbPuntosTotales.setText("" + juego.getPacMan().getPuntos());
        cargarVidas(juego);
        MFXButton btnContinuar = new MFXButton("Continuar");
        btnContinuar.getStyleClass().add("mfx-button-menu");
        btnContinuar.setOnAction(event -> {
            hxContBoton.getChildren().clear();
            juego.continuar();
            getStage().close();
        });
        btnContinuar.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
            btnContinuar.setText("►Continuar");
        });
        btnContinuar.setOnMouseExited(event -> btnContinuar.setText(" Continuar"));
        hxContBoton.getChildren().add(btnContinuar);
    }

    public void cargarInterfazSinVidas(Juego juego, Partida partida) {
        lbGameWinOver.setText("Sin vidas");
        lbPuntosTotales.setText("" + juego.getPacMan().getPuntos());
        cargarVidas(juego);
        
        MFXButton btnContinuar = new MFXButton(" Comprar Vida y Continuar -1500pts.");
        btnContinuar.getStyleClass().add("mfx-button-menu-minus");
        btnContinuar.setOnAction(event -> {
            juego.getPacMan().setPuntos(juego.getPacMan().getPuntos() - 1500);
            juego.getPacMan().setVidas(juego.getPacMan().getVidas() + 1);
            hxContBoton.getChildren().clear();
            juego.continuar();
            getStage().close();
        });
        btnContinuar.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
            btnContinuar.setText("►Comprar Vida y Continuar -1500pts.");
        });
        btnContinuar.setOnMouseExited(event -> btnContinuar.setText(" Comprar Vida y Continuar -1500pts."));
        
        hxContBoton.getChildren().add(btnContinuar);
    }

    public void cargarInterfazSinVidasSinPuntos(Juego juego, Partida partida) {
        lbGameWinOver.setText("Sin vidas y puntos suficientes");
        lbPuntosTotales.setText("" + juego.getPacMan().getPuntos());
        cargarVidas(juego);
        MFXButton btnContinuar = new MFXButton("Reintentar nivel " + juego.getNivel());
        btnContinuar.setOnAction(event -> {
            JuegoViewController juegoView = (JuegoViewController) FlowController.getInstance().getController("JuegoView");
            juegoView.reiniciarJuego();
            hxContBoton.getChildren().clear();
            getStage().close();
        });
        hxContBoton.getChildren().add(btnContinuar);
    }

    private void cargarVidas(Juego juego) {
        hxContVidas.getChildren().clear();
        for (int i = 0; i < 6; i++) {
            if (juego.getPacMan().getVidas() < i + 1) {
                hxContVidas.getChildren().add(new ImageView(new Image("cr/ac/una/pacman/resources/PacManMuerto.png")));
            } else {
                hxContVidas.getChildren().add(new ImageView(new Image("cr/ac/una/pacman/resources/PacMan.png")));
            }
        }
    }

    private void onActionMouse() {
        btnAbandonar.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
            btnAbandonar.setText("►Abandonar");
        });
        btnAbandonar.setOnMouseExited(event -> btnAbandonar.setText(" Abandonar"));
    }

}
