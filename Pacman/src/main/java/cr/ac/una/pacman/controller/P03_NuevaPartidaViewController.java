package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.model.Partida;
import cr.ac.una.pacman.util.AppContext;
import cr.ac.una.pacman.util.FlowController;
import cr.ac.una.pacman.util.Mensaje;
import cr.ac.una.pacman.util.SoundUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class P03_NuevaPartidaViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MediaView mdvFondoPartida;
    @FXML
    private MFXButton btnSalir;
    @FXML
    private MFXTextField txfNombre;
    @FXML
    private MFXRadioButton rdbFacil;
    @FXML
    private MFXRadioButton rdbMedio;
    @FXML
    private MFXRadioButton rdbDificil;
    @FXML
    private ToggleGroup tggModoJuego;
    @FXML
    private MFXButton btnIniciar;
    @FXML
    private ImageView imvPlayButton;

    Partida partida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdbFacil.setUserData("Facil");
        rdbMedio.setUserData("Medio");
        rdbDificil.setUserData("Dificil");
        onActionMouse();
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        FlowController.getInstance().delete("P03_NuevaPartidaViewController");
        FlowController.getInstance().goView("P02_MenuView");
    }

    @FXML
    private void onActionBtnIniciar(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        if (txfNombre.getText().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Ingresar Partida", getStage(), "Es necesario digitar un nombre para continuar");
        } else {
            partida = new Partida(txfNombre.getText(), (String) tggModoJuego.getSelectedToggle().getUserData());
            partida.generarNiveles();
            AppContext.getInstance().set("Partida", partida);
            FlowController.getInstance().delete("P03_NuevaPartidaViewController");

            FlowController.getInstance().goView("P05_PartidaView");
            txfNombre.clear();
        }
    }

    private void onActionMouse() {
        btnIniciar.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
            btnIniciar.setText("►Iniciar Partida");
        });

        btnIniciar.setOnMouseExited(event -> btnIniciar.setText(" Iniciar Partida"));

        btnSalir.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
            btnSalir.setText("►Salir");
        });

        btnSalir.setOnMouseExited(event -> btnSalir.setText(" Salir"));
    }
}
