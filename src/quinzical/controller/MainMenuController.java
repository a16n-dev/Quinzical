package quinzical.controller;

import java.io.IOException;
import java.util.Observable;
import java.util.Optional;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quinzical.model.Game;
import quinzical.util.Modal;
import quinzical.util.Router;
import quinzical.util.TTS;

public class MainMenuController {

	@FXML
	public Button newGame;
	@FXML
	public Button resumeGame;
	@FXML
	public Button practiceGame;
	public Slider sliderSpeed;
	public Slider sliderVolume;
    /**
	 * Starts a new game
	 * @param event
	 * @throws IOException
	 */

	public void initialize() {
		if(!Game.isInProgress()){
			resumeGame.setVisible(false);
			resumeGame.setManaged(false);
		}
	}

    public void handleGameButtonClick(ActionEvent event) throws IOException {
		Router.show(Views.GAME_BOARD);
    } 
	
	@FXML
    public void handleNewGame() {
		if(Game.isInProgress()){
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
	public void handleViewTrophyCase(){
		Router.show(Views.TROPHY_CASE);
	}

	@FXML
	public void showSettings(){
		Modal.show();
	}
	
	// public void handleSpeechVolumeSliderChange(ObservableValue<Number> ovn, Number before, Number after) {
	// 	TTS.getInstance().setVolume(after.intValue());
	// }

	// public void handleSpeechSpeedSliderChange(ObservableValue<Number> ovn, Number before, Number after) {
	// 	TTS.getInstance().setSpeed(after.intValue());
	// }
    
}
