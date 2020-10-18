package quinzical.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import quinzical.model.Game;
import quinzical.model.Reward;
import quinzical.model.User;
import quinzical.util.ImageLoader;
import quinzical.util.Router;
import quinzical.util.TTS;

public class RewardScreenController {

	public Label fxScore;
	public Label fxMedal;

	public ImageView fxMedalImage;

	// stores a reference to the current game being played
	private Game game;
	private User user;

	public void initialize() {
		game = Game.getInstance();
		user = User.getInstance();

		int score = game.getScore().intValue();
		Reward reward = user.addReward(score);

		fxScore.setText("Final score: " + score);
		fxMedal.setText("You have earned a " + reward.name() + " Medal!");

		//Speak results to user
		TTS.getInstance().speak("Your final score was " + score);
		TTS.getInstance().speak("You have earned a " + reward.name() + " Medal!" ) ;
		
		// Set image
		switch (reward) {
			case Bronze:
				fxMedalImage.setImage(ImageLoader.loadImage("images/md_bronze.png"));
				break;
			case Diamond:
				fxMedalImage.setImage(ImageLoader.loadImage("images/md_diamond.png"));
				break;
			case Gold:
				fxMedalImage.setImage(ImageLoader.loadImage("images/md_gold.png"));
				break;
			case Platinum:
				fxMedalImage.setImage(ImageLoader.loadImage("images/md_platinum.png"));
				break;
			case Silver:
				fxMedalImage.setImage(ImageLoader.loadImage("images/md_silver.png"));
			break;
			default:
				break;
		}
	}

	public void handleButtonMenuClick(ActionEvent event) throws IOException {
		// Reset game
		Game.clearGame();
		Router.show(View.MAIN_MENU);

	}

}
