package quinzical.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import quinzical.components.CategoryListItemController;
import quinzical.components.Component;
import quinzical.model.PracticeGame;
import quinzical.util.QuestionBank;
import quinzical.util.Router;

public class PracticeScreenController {
	@FXML
	private VBox fxPanel;

	private PracticeGame game;

	public void initialize() {
		game = PracticeGame.getInstance();
		var categories = game.getCategories();
		for (String category : categories) {
			FXMLLoader loader = Router.manualLoad(Component.CATEGORY_LIST_ITEM.path());
			try {
				Node node = (Node) loader.load();

				CategoryListItemController controller = loader.getController();
				controller.config(category, QuestionBank.getInstance().getQuestionsByCategory(category).size());

				// Button button = new Button(category);
				// button.setOnAction(event -> {
				// game.setCurrentCategory(category);

				// Router.show(Views.PRACTICE_ANSWER_SCREEN);

				// });
				fxPanel.getChildren().add(node);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
