package quinzical;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import quinzical.controller.View;
import quinzical.model.User;
import quinzical.util.Modal;
import quinzical.util.Router;
import quinzical.util.Sound;

public class App extends Application {

    public enum GameState {
        MENU, GAME, PRACTICE, MULTIPLAYER, SHOP, MODAL,
    }

    private static GameState gameState;

    private Stage _stage;

    @Override
    public void start(Stage s) {
        this._stage = s;
        this._stage.setMinWidth(1000);
        this._stage.setMinHeight(700);
        this._stage.setTitle("Quinzical");

        // Set app icon
        s.getIcons().add(new Image(App.class.getResourceAsStream("images/favicon.png")));

        // Setup router
        StackPane container = (StackPane) Router.loadFXML("controller/GameContainer.fxml");

        Scene scene = new Scene(container, User.getInstance().getPrefWidth().intValue(),
                User.getInstance().getPrefHeight().intValue());
        s.setScene(scene);

        Router.setContainer((BorderPane) container.lookup("#content"));

        Router.show(View.MAIN_MENU);
        s.show();

        // Sound.getInstance().playSound("ambient");

        scene.getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {

            // When user wants to quit, save their preferred window size
            User.getInstance().setPrefHeight(scene.heightProperty().get());
            User.getInstance().setPrefWidth(scene.widthProperty().get());

            if (gameState == GameState.GAME) {
                e.consume();
                Modal.confirmation("Are you sure",
                        "Are you sure you want to quit? This will mark the current question as wrong", f -> {
                            try {
                                Platform.exit();
                            } catch (Exception e1) {
                                System.out.println(e1.getStackTrace());
                            }
                        });
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setState(GameState state) {
        gameState = state;
    }

    public static GameState getState() {
        return gameState;
    }
}
