package quinzical.util;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quinzical.App;
import quinzical.controller.Views;

public class Router {

    private static Stage stage;

    //Represents the history of the pages the user has visited
    private static Deque<Views> history = new ArrayDeque<Views>();

    /**
     * Sets the scene to show the specified fxml file
     * @param fxml the path to the fxml file, relative to App.java
     */
    public static void show(Views fxml) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(fxml.toString()));
            Parent view = (Parent) loader.load();

            Scene scene = new Scene(view);
            stage.setScene(scene);

            //If it was all successful add screen to history
            history.add(fxml);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void back(){
        //First pop the current page off the stack
        history.pop();
        //Show the page on top of the stack
        show(history.peek());
    }

    public static void setStage(Stage s) {
        stage = s;
    }
}
