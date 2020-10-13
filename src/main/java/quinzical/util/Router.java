package quinzical.util;

import java.util.ArrayDeque;
import java.util.Deque;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import quinzical.App;
import quinzical.controller.Views;

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

        // Clear the TTS queue
        TTS.getInstance().clearQueue();

        // Update the game state
        switch (fxml) {
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

    /**
     * Sets the container where content should be placed
     * @param p A borderPane which should contain the content to display
     */
    public static void setContainer(BorderPane p) {
        container = p;
    }

    // TODO: Move this. This does not belong inside of the router class and should
    // be moved to another util file.
    // Possibly IOmanager?
    /**
     * Loads the specified fxml file
     * @param fxml the path to the fxml file
     * @return a javafx node hierarchy
     */
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
    /**
     * Used when a reference to the controller is also required
     * @param fxml the path to the fxml file
     * @return the fxmlloader which can then be used to access the fxml as well as the controller object
     */
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

    /**
     * 
     * @return the current state of the game, 0 for menu, 1 for game, 2 for practice
     */
    public static int getGameState() {
        return gameState;
    }
}
