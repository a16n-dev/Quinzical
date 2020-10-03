package quinzical.components;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import quinzical.model.PracticeGame;
import quinzical.util.Router;
import javafx.fxml.FXML;

public class CategoryListItemController {
    

    @FXML
    private Label name;

    @FXML
    private Label count;

    public void config(String categoryName, int questionCount) {
        name.textProperty().set(categoryName);
        count.textProperty().set(Integer.toString(questionCount));
    }
}
