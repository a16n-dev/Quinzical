package quinzical.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import quinzical.model.Game;
import quinzical.model.Question;
import quinzical.util.Macron;
import quinzical.util.Router;
import quinzical.util.TTS;

public class AnswerScreenController {
	
	public Button button;
	public Button buttonUnsure;
	
	public TextField input;
	public VBox textWrapper;

	@FXML
	public Label hintText;

	@FXML
	private Label categoryName;

	@FXML
	private Label ValueText;
    
	// stores a reference to the current game being played
	private Game game;
	private Question question;
	
	public void initialize() {
		game = Game.getInstance();
		question = game.getCurrentQuestion();
		TTS.getInstance().speak(question.getHint());

		//Fill text
		categoryName.setText(game.getCurrentCategory());
		ValueText.setText("$" + question.getValue());
		hintText.setText(question.getHint());

		// Macron.getInstance().bindToTextField(input, textWrapper);
	}

	public void onSubmit(ActionEvent event) {
		boolean correct = game.getCurrentQuestion().checkAnswer(input.getText());
		// Check whether the user's answer is correct
		if (correct) {
			game.addScore(question.getValue());
		}
		
		handleAnswer(
			correct ? "Congratulations!" : "Oops.",
			correct ? "Correct" : "Answer was: " + question.getAnswer(),
			"Your current score is: " + game.getScore().intValue()
		);
	}
	
	private void handleAnswer(String title, String header, String content) {
		// Alert box to inform user of score and whether answer was correct
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle(title);
    	alert.setHeaderText(header);
    	alert.setContentText(content);
    	alert.showAndWait();
    	
    	// Navigate to the 'reward screen' only if all questions are answered
    	if (game.getRemainingQuestions() == 0) {
    		Router.show(Views.REWARD_SCREEN);
    	} else {
    		Router.show(Views.GAME_BOARD);
		}
	}

	public void onUnsure() {
		handleAnswer(
			"Oops.",
			"Answer was: " + question.getAnswer(),
			"Your current score is: " + game.getScore().intValue()
		);
	}

	@FXML
	public void repeatClue(){
		TTS.getInstance().speak(question.getHint());
	}
}