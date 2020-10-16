package quinzical.components;

import javafx.scene.control.Label;
import quinzical.controller.PracticeAnswerScreen;
import quinzical.controller.View;
import quinzical.model.PracticeGame;
import quinzical.util.Router;
import javafx.fxml.FXML;

public class CategoryListItemController {

    private String category;

    @FXML
    private Label fxName;

    @FXML
    private Label fxCount;

    public void config(String categoryName, int questionCount) {
        category = categoryName;
        fxName.textProperty().set(categoryName);
        fxCount.textProperty().set(Integer.toString(questionCount));
    }

    @FXML
    public void handleButtonPress() {
        PracticeGame.getInstance().setCurrentCategory(category);
        Router.show(View.PRACTICE_ANSWER_SCREEN);
    }
}
