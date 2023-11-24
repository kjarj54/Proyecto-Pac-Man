package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.model.Partida;
import cr.ac.una.pacman.model.Trofeo;
import cr.ac.una.pacman.util.AppContext;
import cr.ac.una.pacman.util.FlowController;
import cr.ac.una.pacman.util.Mensaje;
import cr.ac.una.pacman.util.SoundUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

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
    @FXML
    private AnchorPane root;
    @FXML
    private Label lbDificultad;

    Partida partida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);
        partida = (Partida) AppContext.getInstance().get("Partida");
        lbDificultad.setText("Dificultad: " + partida.getDificultad());
        lbTitulo.setText("Jugador: " + partida.getJugador());
        for (int i = 0; i < 10; i += 3) {
            HBox hxItems = new HBox();
            hxItems.setAlignment(Pos.CENTER);
            hxItems.setSpacing(20);
            VBox.setVgrow(hxItems, Priority.ALWAYS);
            for (int j = 0; j < 3 && i + j < 10; j++) {
                VBox vxItem = new VBox();
                vxItem.setId("" + (i + j + 1));
                vxItem.setPrefHeight(150);
                vxItem.setPrefWidth(240);
                vxItem.setAlignment(Pos.CENTER);
                vxItem.setDisable(!partida.getNivel((i + j)).isDesbloqueado());
                vxItem.setStyle("-fx-background-image: url('cr/ac/una/pacman/resources/fondos/" + partida.getNivel((i + j)).getTema() + "');"
                        + "-fx-background-size: cover;"
                        + "-fx-border-width: 5px;");
                vxItem.setOnMouseClicked(event -> {
                    if (new Mensaje().showConfirmation("Iniciar nivel", getStage(), "Desea jugar el nivel " + vxItem.getId() + "?")) {
                        AppContext.getInstance().set("Partida", partida);
                        AppContext.getInstance().set("Nivel", vxItem.getId());
                        FlowController.getInstance().delete("P05_PartidaView");
                        FlowController.getInstance().goView("JuegoView");
                    }
                });
                vxItem.setOnMouseEntered(event -> vxItem.getStyleClass().add("vx-itemDentro"));
                vxItem.setOnMouseExited(event -> vxItem.getStyleClass().remove("vx-itemDentro"));

                Label lbTituloItem = new Label("" + (i + j + 1));
                lbTituloItem.setStyle("-fx-font-size: 60px;"
                        + "-fx-text-fill: white;"
                        + "-fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);"
                        + "-fx-stroke: black;"
                        + "-fx-stroke-width: 800;");
                vxItem.getChildren().add(lbTituloItem);
                hxItems.getChildren().add(vxItem);
            }
            vxContJuegos.getChildren().add(hxItems);
        }
        String[] vector = {"Clasico", "Cazador", "Experto", "Encierro", "Flash", "Rey del PacMan"};
        for (int i = 0; i < 6; i++) {
            Trofeo trofeo = partida.obtenerTrofeo(vector[i]);
            VBox vxItem = new VBox();
            vxItem.setId(vector[i]);
            vxItem.setPrefHeight(150);
            vxItem.setPrefWidth(200);
            vxItem.setAlignment(Pos.TOP_CENTER);
            vxItem.setDisable(!trofeo.isDesbloqueado());
            vxItem.setStyle("-fx-border-width: 5px;");
            vxItem.setOnMouseEntered(event -> {
                vxItem.setStyle("-fx-background-color: grey;");
                vxItem.getStyleClass().add("vx-itemDentro");
            });
            vxItem.setOnMouseExited(event -> {
                vxItem.setStyle("-fx-background-color: transparent;");
                vxItem.getStyleClass().remove("vx-itemDentro");
            });

            Label lbTituloItem = new Label(trofeo.getNombre());
            ImageView imageTrofeo = new ImageView(new Image("cr/ac/una/pacman/resources/Trofeos/" + vector[i] + ".png"));
            imageTrofeo.setPreserveRatio(false);
            imageTrofeo.setFitWidth(50);
            imageTrofeo.setFitHeight(50);
            lbTituloItem.getStyleClass().add("label-minus");

            Label lbDescripcion = new Label(trofeo.getDescripcion());
            lbDescripcion.setTextAlignment(TextAlignment.CENTER);
            //lbDescripcion.setFont(new Font(10));
            lbDescripcion.setWrapText(true);
            lbDescripcion.setPrefWidth(250);
            lbDescripcion.getStyleClass().add("label-minus");
            vxItem.getChildren().addAll(lbTituloItem, imageTrofeo, lbDescripcion);
            vxContTrofeos.getChildren().add(vxItem);
        }
        onActionMouse();
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        FlowController.getInstance().delete("P05_PartidaView");
        FlowController.getInstance().goView("P02_MenuView");
    }

    @FXML
    private void onActionBtnEstadisticas(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        AppContext.getInstance().set("Partida", partida);
        FlowController.getInstance().delete("P05_PartidaView");
        FlowController.getInstance().goView("P07_EstadisticasView");
    }

    private void onActionMouse() {
        btnEstadisticas.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
            btnEstadisticas.setText("►Estadísticas");
        });

        btnEstadisticas.setOnMouseExited(event -> btnEstadisticas.setText(" Estadísticas"));

        btnSalir.setOnMouseEntered(event -> {
            SoundUtil.mouseHoverSound();
            btnSalir.setText("►Salir");
        });

        btnSalir.setOnMouseExited(event -> btnSalir.setText(" Salir"));
    }

}
