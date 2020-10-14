package quinzical;

import quinzical.controller.Views;
import quinzical.model.User;
import quinzical.util.Router;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    private Stage _stage;

    @Override
    public void start(Stage s) {
        this._stage = s;
        this._stage.setMinWidth(1000);
        this._stage.setMinHeight(600);
        this._stage.setTitle("Quinzical");

        //Set app icon
        s.getIcons().add(new Image(App.class.getResourceAsStream("images/favicon.png")));

        // Setup router
        StackPane container = (StackPane) Router.loadFXML("controller/GameContainer.fxml");

        Scene scene = new Scene(container, 1200, 800);
        s.setScene(scene);

        Router.setContainer((BorderPane) container.lookup("#content"));
//        Modal.setModalContainer((AnchorPane) container.lookup("#ModalContainer"));
//        Modal.hide();
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
