package quinzical.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import quinzical.model.Game;
import quinzical.model.User;
import quinzical.util.Router;

public class RewardScreenController {
	
	public ImageView imageMedal;
	public Button buttonMenu;
	public Label labelScore;
	public Label labelMedal;
	
	// stores a reference to the current game being played
	private Game game;
	private User user;
	
	public void initialize() {
		game = Game.getInstance();
		user = User.getInstance();
		
		int score = game.getScore().intValue();
		String reward = user.addReward(score).name() + " Medal";
		
		labelScore.setText("Final score: " + score);
		labelMedal.setText("You have earned a " + reward + "!");
	}  
	
	public void handleButtonMenuClick(ActionEvent event) throws IOException {
	    
    	Router.show(Views.MAIN_MENU);
    
    }  

}
