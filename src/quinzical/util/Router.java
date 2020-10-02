package quinzical.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import quinzical.App;

public class Router {

    private static Stage stage;

    /**
     * Sets the scene to show the specified fxml file
     * @param fxml the path to the fxml file, relative to App.java
     */
    public static void show(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(fxml));
            Parent view = (Parent) loader.load();

            Scene scene = new Scene(view);
            stage.setScene(scene);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setStage(Stage s) {
        stage = s;
    }
}
