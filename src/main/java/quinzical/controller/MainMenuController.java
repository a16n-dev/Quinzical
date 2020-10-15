package quinzical.controller;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import quinzical.model.Game;
import quinzical.model.User;
import quinzical.util.Avatar;
import quinzical.util.Modal;
import quinzical.util.Router;

public class MainMenuController {
	private User user;

	@FXML
	private Button fxResume;

	@FXML
	private StackPane avatarSlot;

	public void initialize() {
		//Store reference to user object
		user = User.getInstance();
		
		//Check if resume button should be shown
		if (!Game.isInProgress()) {
			fxResume.setVisible(false);
			fxResume.setManaged(false);
		}

		//Load avatar
		Avatar avatar = new Avatar(avatarSlot);
		avatar.render();
	}

	public void handleGameButtonClick(ActionEvent event) throws IOException {
		Router.show(Views.GAME_BOARD);
	}

	@FXML
	public void handleNewGame() {
		if (Game.isInProgress()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("New Game");
			alert.setHeaderText("Are you sure you want to start a new game?");
			alert.setContentText("This will remove all progress in the current game.");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Game.clearGame();
				Router.show(Views.GAME_BOARD);
			}
		} else {
			Game.clearGame();
			Router.show(Views.GAME_BOARD);
		}
	}

	@FXML
	public void handleResumeGame() {
		Router.show(Views.GAME_BOARD);
	}

	@FXML
	public void handlePracticeGame() {
		Router.show(Views.PRACTICE_SCREEN);
	}

	@FXML
	public void handleViewTrophyCase() {
		Router.show(Views.TROPHY_CASE);
	}
	
	@FXML
	public void handleViewLeaderboard() {
		Router.show(Views.LEADERBOARD);
	}
	
	@FXML
	public void handleResetProgress() {
		String alertContent = (user.getInternationalUnlocked()) ? " and lock the international questions." : ".";
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset Progress");
		alert.setHeaderText("Are you sure you want to reset your progress?");
		alert.setContentText("This will remove all your rewards" + alertContent);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			user.clearRewards();
			user.setInternational(false);
						
			// Game.clearGame();
			// Need to refresh main menu?
		}				
	}

	@FXML
	public void showSettings() {
		Modal.show(Views.MODAL_SETTINGS);
	}

	// public void handleSpeechVolumeSliderChange(ObservableValue<Number> ovn,
	// Number before, Number after) {
	// TTS.getInstance().setVolume(after.intValue());
	// }

	// public void handleSpeechSpeedSliderChange(ObservableValue<Number> ovn, Number
	// before, Number after) {
	// TTS.getInstance().setSpeed(after.intValue());
	// }

}
