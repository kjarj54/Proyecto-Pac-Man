package cr.ac.una.pacman;

import cr.ac.una.pacman.util.FlowController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage, null);
//        stage.getIcons().add(new Image(App.class.getResourceAsStream("/cr/ac/una/unaplanilla/resources/LogoUNArojo.png")));
        stage.setTitle("Pac-Man");
        FlowController.getInstance().goMain();
    }

    public static void main(String[] args) {
        launch();
    }
}
