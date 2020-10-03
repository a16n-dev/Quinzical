package quinzical.controller;

import java.io.IOException;
import java.util.Observable;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quinzical.model.Game;
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
		// TTS.getInstance().speak("hello test");

    	// Parent gameBoardView = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
    	// Scene gameBoardScene = new Scene(gameBoardView, 700, 500);
    	
    	// Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	// window.setScene(gameBoardScene);
		// window.show();
		
		Router.show(Views.GAME_BOARD);
    
    } 
	
	@FXML
    public void handleNewGame() {
		Game.clearGame();
		Router.show(Views.GAME_BOARD);
	}

	@FXML
    public void handleResumeGame() {
		Router.show(Views.GAME_BOARD);
	}

	@FXML
    public void handlePracticeGame() {
		Router.show(Views.PRACTICE_SCREEN);
	}
	
	// public void handleSpeechVolumeSliderChange(ObservableValue<Number> ovn, Number before, Number after) {
	// 	TTS.getInstance().setVolume(after.intValue());
	// }

	// public void handleSpeechSpeedSliderChange(ObservableValue<Number> ovn, Number before, Number after) {
	// 	TTS.getInstance().setSpeed(after.intValue());
	// }
    
}
