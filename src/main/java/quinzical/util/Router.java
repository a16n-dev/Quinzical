package quinzical.util;

import java.util.ArrayDeque;
import java.util.Deque;

import javafx.animation.FadeTransition;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import quinzical.App;
import quinzical.controller.View;
import quinzical.controller.menu.CategorySelectGame;

/**
 * Class to navigate from view to view in the JavaFX application. JavaFX does
 * not have built in support for changing views which has a nice interface, so
 * this class acts as an interface to view changing.
 */
public class Router {
    // the container containing the entire application
    private static BorderPane container;

    // Represents the history of the pages the user has visited
    private static Deque<View> history = new ArrayDeque<View>();

    /**
     * Navigate the user to the last page in history and remove it from the history.
     */
    public static void navigateBack() {
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
    }

    /**
     * Sets the scene to show the specified fxml file.
     * 
     * @param fxml         the path to the fxml file, relative to App.java
     * @param addToHistory whether to add the navigation to the history
     */
    public static void show(View fxml, boolean addToHistory) {
        App.setState(fxml.getState());
        TTS.getInstance().clearQueue();
        Timer.getInstance().stop();

        FadeTransition ft = new FadeTransition(Duration.millis(300), container);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        // Place the content into the container
        container.setCenter(ViewLoader.loadFXML(fxml.getCenter(), fxml.getController()));
        container.setTop(ViewLoader.loadFXML(fxml.getTop()));
        container.setRight(ViewLoader.loadFXML(fxml.getRight()));
        container.setBottom(ViewLoader.loadFXML(fxml.getBottom()));
        container.setLeft(ViewLoader.loadFXML(fxml.getLeft()));

        if (addToHistory) {
            history.add(fxml);
        }
    }

    /**
     * Call the show method without adding to history
     * 
     * @param fxml
     */
    public static void show(View fxml) {
        show(fxml, true);
    }

    /**
     * Check whether the current view is equal to the parameter.
     * 
     * @param view the view to compare
     * @return whether the views are equal
     */
    public static boolean currentViewIs(View view) {
        return history.peekLast() == view;
    }

    /**
     * Sets the container where content should be placed
     * 
     * @param p A borderPane which should contain the content to display
     */
    public static void setContainer(BorderPane p) {
        container = p;
    }

    /**
     * @return whether the game is in practice mode
     */
    public static boolean isGameMode() {
        return history.peekLast().getController() instanceof CategorySelectGame;
    }
}
