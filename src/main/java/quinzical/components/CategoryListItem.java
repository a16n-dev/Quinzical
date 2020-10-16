package quinzical.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import quinzical.controller.PracticeAnswerScreen;
import quinzical.controller.Views;
import quinzical.model.Category;
import quinzical.model.PracticeGame;
import quinzical.util.Router;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class CategoryListItem {

    private Category category;

    @FXML
    private Label fxName;

    @FXML
    private Label fxCount;

    private void config(Category categoryName) {
        category = categoryName;
        fxName.textProperty().set(category.getName());
        fxCount.textProperty().set(Integer.toString(category.getQuestions().size()));
    }

    @FXML
    public void handleButtonPress() {
        PracticeGame.getInstance().setCurrentCategory(category);
        Router.show(Views.ANSWER_SCREEN, new PracticeAnswerScreen());
    }

    /**
     * Factory method to create a new instance of the javfx node and links it to the specified category
     * @param category
     * @return A Region containing an javafx element that displays information about a given category
     */
    public static Region create(Category category) {
        FXMLLoader loader = Router.manualLoad("components/CategoryListItem.fxml");
        try {
            Region content = (Region) loader.load();
            CategoryListItem controller = loader.getController();

            controller.config(category);
            return content;
        } catch (IOException e) {
            return null;
        }
    }
}
