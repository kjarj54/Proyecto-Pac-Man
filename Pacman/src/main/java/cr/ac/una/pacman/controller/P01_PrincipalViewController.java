package cr.ac.una.pacman.controller;

import cr.ac.una.pacman.util.FlowController;
import cr.ac.una.pacman.util.SoundUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ANTHONY
 */
public class P01_PrincipalViewController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private AnchorPane acPanePrincipal;
    @FXML
    private MFXButton btnIngresar;
    @FXML
    private ImageView imgPacman;
    @FXML
    private GridPane grdImages;
    @FXML
    private ImageView imgGhost;

    private static final int PACMAN_IMAGE_COUNT = 2;
    private static final int GHOST_IMAGE_COUNT = 2;
    private static final Duration BLINK_DURATION = Duration.seconds(1);

    private static final String[] PACMAN_IMAGE_PATHS = {
        "/cr/ac/una/pacman/resources/animation/pacman0.png",
        "/cr/ac/una/pacman/resources/animation/pacman1.png"
    // Agrega aquí las rutas de tus imágenes
    };

    private static final String[] PACMAN_IMAGE_GHOSTS = {
        "/cr/ac/una/pacman/resources/animation/ghosts.png",
        "/cr/ac/una/pacman/resources/animation/ghosts2.png"
    // Agrega aquí las rutas de tus imágenes
    };

    private int currentImageIndex = 0;
    private int currentImageGhost = 0;
    private double pacmanPositionX = -566; // Posición inicial fuera de la pantalla
    double avance = 25;
    Timeline timeline;
    Random random = new Random();

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        btnIngresar.setVisible(false);
        Stage stage1 = FlowController.getInstance().getMainStage();
        stage1.setOnShown(event -> {
            // Luego de que la escena se muestre, llamar la siguiente pantalla
            SoundUtil.pacmanMusic();
        });

        // Crear un temporizador
        Timer timer = new Timer();

        // Definir la tarea que se ejecutará después de unos segundos
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                // Coloca aquí la acción que deseas ejecutar después de unos segundos
                btnIngresar.setVisible(true);
                animationPacman();
                animationButtonIni();
            }
        };

        // Programar la tarea para que se ejecute después de 5000 milisegundos (5 segundos)
        timer.schedule(tarea, 5000);
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnIngresar(ActionEvent event) {
        SoundUtil.pacmanSiren();
        SoundUtil.pacmanMunch();
        timeline.setCycleCount(0);
        timeline.stop();
        FlowController.getInstance().goView("P02_MenuView");
    }

    public void animationPacman() {
        imgGhost.setImage(new Image(PACMAN_IMAGE_GHOSTS[currentImageGhost]));
        grdImages.setLayoutX(pacmanPositionX);
        grdImages.setLayoutY(360);
        SoundUtil.pacmanSiren();
        SoundUtil.pacmanMunch();

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {

            currentImageIndex = (currentImageIndex + 1) % PACMAN_IMAGE_COUNT;
            imgPacman.setImage(new Image(PACMAN_IMAGE_PATHS[currentImageIndex]));

            pacmanPositionX += avance; // Ajusta la velocidad del movimiento

            // Mueve el pacmanImageView de izquierda a derecha
            grdImages.setLayoutX(pacmanPositionX);

            if (pacmanPositionX >= root.getWidth()) {
                SoundUtil.pacmanMunch();
                SoundUtil.pacmanSiren();
            }

            // Si el pacmanImageView sale de la ventana, reinicia su posición
            if (pacmanPositionX > root.getWidth() + 100) {
                SoundUtil.pacmanFruit();
                //pacmanPositionX = -461; // Posición inicial fuera de la pantalla
                int numeroAleatorio = random.nextInt((int) root.getHeight() - 100);
                grdImages.setLayoutY(numeroAleatorio);
                avance = -25;
                currentImageGhost = (currentImageGhost + 1) % GHOST_IMAGE_COUNT;
                imgGhost.setImage(new Image(PACMAN_IMAGE_GHOSTS[currentImageGhost]));
                imgPacman.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                SoundUtil.pacmanSiren();
                SoundUtil.pacmanMunch();
            }
            if (pacmanPositionX < -566) {
                //pacmanPositionX = -461; // Posición inicial fuera de la pantalla

                int numeroAleatorio = random.nextInt((int) root.getHeight() - 100);
                grdImages.setLayoutY(numeroAleatorio);
                avance = 25;
                currentImageGhost = (currentImageGhost + 1) % GHOST_IMAGE_COUNT;
                imgGhost.setImage(new Image(PACMAN_IMAGE_GHOSTS[currentImageGhost]));
                imgPacman.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void animationButtonIni() {
        // Crea la animación de parpadeo
        Timeline timelineButton = new Timeline(
                new KeyFrame(BLINK_DURATION, event -> btnIngresar.setText("")),
                new KeyFrame(BLINK_DURATION.multiply(2), event -> btnIngresar.setText("●Presione la tecla espacio●"))
        );
        timelineButton.setCycleCount(Timeline.INDEFINITE);
        timelineButton.play();
    }
}
