package quinzical.util;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import quinzical.App;
import quinzical.controller.Views;
import quinzical.model.User;

public class Router {

    private static BorderPane container;

    private static int gameState = 0; // 0 is menu, 1 is game, 2 is practice

    // Represents the history of the pages the user has visited
    private static Deque<Views> history = new ArrayDeque<Views>();

    /**
     * Sets the scene to show the specified fxml file
     * 
     * @param fxml the path to the fxml file, relative to App.java
     */
    public static void show(Views fxml) {

        // Update the game state
        switch(fxml){
            case ANSWER_SCREEN:
                gameState = 1;
				break;
            case GAME_BOARD:
                gameState = 1;
				break;
            case MAIN_MENU:
                gameState = 0;
				break;
            case PRACTICE_ANSWER_SCREEN:
                gameState = 2;
				break;
            case PRACTICE_SCREEN:
                gameState = 2;
				break;
            case REWARD_SCREEN:
                gameState = 1;
				break;
			default:
				break;
        }

        // Place the content into the container
        container.setCenter(loadFXML(fxml.getCenter()));

        container.setTop(loadFXML(fxml.getTop()));

        container.setRight(loadFXML(fxml.getRight()));

        container.setBottom(loadFXML(fxml.getBottom()));

        container.setLeft(loadFXML(fxml.getLeft()));

        // If it was all successful add screen to history
        history.add(fxml);

    }

    public static void back() {
        // First pop the current page off the stack
        history.pop();
        // Show the page on top of the stack
        show(history.peek());
    }

    public static void setContainer(BorderPane p) {
        container = p;
    }

    // TODO: Move this. This does not belong inside of the router class and should
    // be moved to another util file.
    // Possibly IOmanager?
    public static Node loadFXML(String fxml) {
        if (fxml != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.class.getResource(fxml.toString()));
                Node node = (Node) loader.load();

                return node;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // TODO: Move this. This does not belong inside of the router class and should
    // be moved to another util file.
    // Possibly IOmanager?
    public static FXMLLoader manualLoad(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(fxml.toString()));
            return loader;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getGameState(){
        return gameState;
    }
}
