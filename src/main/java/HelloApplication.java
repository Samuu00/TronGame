import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.GameView;
import controller.GameController;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        int gridSize = 30;
        int windowSize = 600;

        // Inizializza View e Controller
        GameView view = new GameView((double) windowSize, gridSize);
        GameController controller = new GameController(view, gridSize);

        // Setup Layout
        StackPane root = new StackPane(view.getCanvas());
        Scene scene = new Scene(root, windowSize, windowSize);

        stage.setTitle("Tron AI - ASP Edition");
        stage.setScene(scene);
        stage.show();

        // Avvia il gioco
        controller.startGame();
    }

    public static void main(String[] args) {
        launch();
    }
}