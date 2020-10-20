package quinzical.util;

import java.util.ArrayDeque;
import java.util.Deque;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import quinzical.App;
import quinzical.controller.CategorySelectGame;
import quinzical.controller.View;

public class Router {
    // the container containing the entire application
    private static BorderPane container;

    // Represents the history of the pages the user has visited
    private static Deque<View> history = new ArrayDeque<View>();

    public static void navigateBack() {
        System.out.println(history);
        System.out.println(history.peekLast());

        if (history.peekLast() == null) {
            show(View.MAIN_MENU);
            return;
        }
        history.removeLast(); // current page

        View lastPage = history.peekLast();
        if (lastPage == null) {
            show(View.MAIN_MENU);
            return;
        }
        show(lastPage, false); // show last page without adding to history
        System.out.println(history.peekLast());
    }

    /**
     * Sets the scene to show the specified fxml file
     * 
     * @param fxml the path to the fxml file, relative to App.java
     */
    public static void show(View fxml, boolean addToHistory) {
        TTS.getInstance().clearQueue();
        Timer.getInstance().stop();

        FadeTransition ft = new FadeTransition(Duration.millis(300), container);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        // Place the content into the container
        container.setCenter(loadFXML(fxml.getCenter(), fxml.getController()));
        container.setTop(loadFXML(fxml.getTop()));
        container.setRight(loadFXML(fxml.getRight()));
        container.setBottom(loadFXML(fxml.getBottom()));
        container.setLeft(loadFXML(fxml.getLeft()));

        if (addToHistory) {
            history.add(fxml);
        }
    }
    public static void show(View fxml) {
        show(fxml, true);
    }

    /**
     * Sets the container where content should be placed
     * @param p A borderPane which should contain the content to display
     */
    public static void setContainer(BorderPane p) {
        container = p;
    }

    /**
     * Loads the specified fxml file
     * @param fxml the path to the fxml file
     * @return a javafx node hierarchy
     */
    public static Node loadFXML(String fxml, Object controller) {
        if (fxml != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                if (controller != null) {
                    loader.setController(controller);
                }
                loader.setLocation(App.class.getResource(fxml.toString()));
                Node node = (Node) loader.load();

                return node;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static Node loadFXML(String fxml) {
        return loadFXML(fxml, null);
    }
    
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
     * @return whether the game is in practice mode
     */
    public static boolean isGameMode() {
        return history.peekLast().getController() instanceof CategorySelectGame;
    }
}
