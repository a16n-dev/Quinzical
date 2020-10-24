package quinzical.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import quinzical.model.Game;
import quinzical.model.Reward;
import quinzical.model.User;
import quinzical.util.AvatarFactory;
import quinzical.util.ImageLoader;
import quinzical.util.Router;
import quinzical.util.TTS;
import quinzical.util.Timer;

public class RewardScreenController {

	@FXML
	private Label fxScore;

	@FXML
	private Label fxTime;

	@FXML
	private Label fxCoins;

	@FXML
	private Label fxMedalText;

	@FXML
	private ImageView fxMedalImage;

	@FXML
	private StackPane fxAvatarContainer;

	// stores a reference to the current game being played
	private Game game;
	private User user;

	public void initialize() {

		// Display avatar
		// Show avatar
		AvatarFactory avatar = new AvatarFactory(fxAvatarContainer, true);
		avatar.set(User.getInstance().getAvatar());

		game = Game.getInstance();
		user = User.getInstance();

		int score = game.getScore().intValue();

		// Calculate rewards and coins earned
		int coinsEarned = score / 100;

		Reward reward = user.addReward(score);
		user.addCoins(coinsEarned);

		fxScore.setText(Integer.toString(score));
		fxCoins.setText("+$" + coinsEarned);

		fxTime.setText(game.getPrettyTimeTaken());

		fxMedalText.setText(reward.name() + " Medal!");

		// Speak results to user
		TTS.getInstance().speak("Your final score was " + score);
		TTS.getInstance().speak("You have earned a " + reward.name() + " Medal!");

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

	@FXML
	private void handleButtonMenuClick(ActionEvent event) {
		// Reset game
		Game.clearGame();
		Router.show(View.MAIN_MENU);
	}

	@FXML
	private void handlePlayAgain(ActionEvent event) {
		// Reset game
		Game.clearGame();
		Router.show(View.SELECT_CATEGORY_GAME);
	}

}
