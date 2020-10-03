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

    // Represents the history of the pages the user has visited
    private static Deque<Views> history = new ArrayDeque<Views>();

    /**
     * Sets the scene to show the specified fxml file
     * 
     * @param fxml the path to the fxml file, relative to App.java
     */
    public static void show(Views fxml) {

            //Place the content into the container
                container.setCenter(loadFXML(fxml.getCenter()));
            
            
                container.setTop(loadFXML(fxml.getTop()));
            
            if(fxml.getRight() != null){
                container.setRight(loadFXML(fxml.getRight()));
            }
            if(fxml.getBottom() != null){
                container.setBottom(loadFXML(fxml.getBottom()));
            }
            if(fxml.getLeft() != null){
                container.setLeft(loadFXML(fxml.getLeft()));
            }


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

    public static Node loadFXML(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(fxml.toString()));
            Node node = (Node) loader.load();

            return node;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
}
