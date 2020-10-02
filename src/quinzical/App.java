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
        Router.show(Views.MAIN_MENU);
        s.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    //Helper functions to expose app data to controllers
    public Stage getStage() {
        return _stage;
    }
}
