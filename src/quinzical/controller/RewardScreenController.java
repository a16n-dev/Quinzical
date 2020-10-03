package quinzical.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import quinzical.model.Game;
import quinzical.model.Reward;
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
		Reward reward = user.addReward(score);

		labelScore.setText("Final score: " + score);
		labelMedal.setText("You have earned a " + reward.name() + " Medal!");

		Image image;
		// Set image
		switch (reward) {
			case Bronze:
				image = new Image(getClass().getResourceAsStream("../resources/images/md_bronze.png"));
				break;
			case Diamond:
				image = new Image(getClass().getResourceAsStream("../resources/images/md_diamond.png"));
				break;
			case Gold:
				image = new Image(getClass().getResourceAsStream("../resources/images/md_gold.png"));
				break;
			case Platinum:
				image = new Image(getClass().getResourceAsStream("../resources/images/md_platinum.png"));
				break;
			case Silver:
				image = new Image(getClass().getResourceAsStream("../resources/images/md_silver.png"));
				break;
			default:
				image = new Image(getClass().getResourceAsStream("../resources/images/md_bronze.png"));
				break;
		}
		imageMedal.setImage(image);
	}

	public void handleButtonMenuClick(ActionEvent event) throws IOException {
		// Reset game
		Game.clearGame();
		Router.show(Views.MAIN_MENU);

	}

}
