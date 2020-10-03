package quinzical;

import java.io.IOException;

import quinzical.controller.MainMenuController;
import quinzical.controller.Views;
import quinzical.model.User;
import quinzical.util.Router;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private Stage _stage;

    @Override
    public void start(Stage s) {
        this._stage = s;
        this._stage.setTitle("Quinzical");
        // Setup router
        BorderPane container = (BorderPane) Router.loadFXML("controller/GameContainer.fxml");

        User user = User.getInstance();
        Scene scene = new Scene(container);
        s.setScene(scene);

        Router.setContainer(container);
        Router.show(Views.MAIN_MENU);
        attachListeners();
        s.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Helper functions to expose app data to controllers
    public Stage getStage() {
        return _stage;
    }

    private void attachListeners() {
        _stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            User.getInstance().setPrefWidth(newVal);
        });

        _stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            User.getInstance().setPrefHeight(newVal);
        });
    }
}
