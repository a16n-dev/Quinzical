package quinzical.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import quinzical.model.Game;
import quinzical.util.Router;

public class AnswerScreenController {
	
	public Button button;
	public TextField text;
    
	// stores a reference to the current game being played
	private Game game;
	
	public void handleButtonClick(ActionEvent event) throws IOException {
	    
		game = Game.getInstance();
		
		String title = "Oops!";
		String header = "Incorrect.";
		
		// Check whether the user's answer is correct
		if (game.getCurrentQuestion().checkAnswer(text.getText())) {				
			title = "Congratulations!";
			header = "Correct.";
			game.addScore(game.getCurrentQuestion().getValue());
		}
		
		String content = "Your current score is: " + game.getScore();
		
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

}
