package quinzical;

import java.io.IOException;

import quinzical.controller.MainMenuController;
import quinzical.controller.Views;
import quinzical.util.Router;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class App extends Application {

    private Stage _stage;
    private VBox rootLayout;

    @Override
    public void start(Stage s) {
        this._stage = s;
        this._stage.setTitle("Quinzical");
        //Setup router
        Router.setStage(s);
        initRootLayout();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(Views.MAIN_MENU));
            rootLayout = loader.load();

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
