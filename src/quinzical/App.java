package quinzical;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jeopardy.view.RootController;
public class App extends Application {

    private Stage _stage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage s) {
        this._stage = s;
        this._stage.setTitle("Jeopardy");

        initRootLayout();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/Root.fxml"));
            rootLayout = (BorderPane) loader.load();

            RootController controller = loader.getController();
            controller.loadAppData(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            _stage.setScene(scene);
            _stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Helper functions to expose app data to controllers
    public Stage getStage() {
        return _stage;
    }

}
