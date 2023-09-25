package cr.ac.una.pacman;

import cr.ac.una.pacman.util.FlowController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage, null);
//        stage.getIcons().add(new Image(App.class.getResourceAsStream("/cr/ac/una/unaplanilla/resources/LogoUNArojo.png")));
        stage.setTitle("Pac-Man");
        FlowController.getInstance().goViewInWindow("JuegoView");
    }

    public static void main(String[] args) {
        launch();
    }
}
