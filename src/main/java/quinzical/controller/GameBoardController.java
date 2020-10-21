package quinzical.controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import quinzical.model.Game;
import quinzical.model.Question;
import quinzical.model.User;
import quinzical.util.Router;

public class GameBoardController {

	@FXML
	private GridPane fxGrid;

	// stores a reference to the current game being played
	private Game game;
	private User user;

	/**
	 * This method is called once the fxml has been loaded
	 */
	public void initialize() {
		game = Game.getInstance();
		user = User.getInstance();

		// Populate the gameboard grid
		ArrayList<String> categories = game.getCategories();
		for (int i = 0; i < categories.size(); i++) {
			boolean available = true;
			String category = categories.get(i);
			// Place label
			Label label = new Label(category);
			label.setTextFill(Color.web("#fff"));
			label.setStyle("-fx-font: 16 arial;");
			fxGrid.add(label, i, 0);

			// Place buttons
			ArrayList<Question> questions = game.getQuestionsByCategory(category);
			int pos = 0;
			for (int j = 0; j < questions.size(); j++) {
				final Integer intJ = j;
				Question question = questions.get(intJ);
				// Place button
				Button button = new Button("$" + question.getValue());

				button.setStyle("-fx-font: 16 arial;");
				button.setStyle("-fx-padding: 8px 16px 8px 16px;");

				if (!question.isAnswered()) {
					pos += 1;
					if (!available) {
						// Disable button if the question has been attempted
						button.setDisable(true);
					} else {
						available = false;
						// Add click events
						button.setOnAction((event) -> {
							game.setCurrentQuestion(category, intJ);
							user.attemptQuestion(category, question.getId());
							Router.show(View.ANSWER_SCREEN, false);
						});
					}

					fxGrid.add(button, i, pos);
				}
			}
		}
	}
}
