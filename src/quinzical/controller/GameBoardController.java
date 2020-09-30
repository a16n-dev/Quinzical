package quinzical.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import quinzical.model.Game;
import quinzical.model.Question;

public class GameBoardController {

	@FXML
	private GridPane grid;

	// stores a reference to the current game being played
	private Game game;

	/**
	 * This method is called once the fxml has been loaded
	 */
	public void initialize() {
		game = Game.getInstance();

		// Populate the gameboard grid
		ArrayList<String> categories = game.getCategories();
		for (int i = 0; i < categories.size(); i++) {
			String category = categories.get(i);
			// Place label
			grid.add(new Label(category), i, 0);

			//Place buttons
			ArrayList<Question> questions = game.getQuestionsByCategory(category);

			for (int j = 0; j < questions.size(); j++) {
				Question question = questions.get(j);
				// Place label
				Button button = new Button();
				grid.add(button, i, j+1);
			}
		}
	}

	public void handleButtonClick(ActionEvent event) throws IOException {

		Parent AnswerScreenView = FXMLLoader.load(getClass().getResource("AnswerScreen.fxml"));
		Scene AnswerScreenScene = new Scene(AnswerScreenView, 700, 500);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(AnswerScreenScene);
		window.show();

	}

}
