package quinzical.controller;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import quinzical.model.Game;
import quinzical.util.Modal;
import quinzical.util.Router;

public class MainMenuController {

	@FXML
	private Button fxResume;

	/**
	 * Starts a new game
	 * 
	 * @param event
	 * @throws IOException
	 */

	public void initialize() {
		if (!Game.isInProgress()) {
			fxResume.setVisible(false);
			fxResume.setManaged(false);
		}
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
			alert.setContentText("This will remove all progress in the current game");

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
