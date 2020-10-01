package quinzical.controller;

import java.io.IOException;
import java.util.Observable;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quinzical.util.TTS;

public class MainMenuController {

	public Button buttonGame, buttonPractice;
	public Slider sliderSpeech;
    /**
	 * Starts a new game
	 * @param event
	 * @throws IOException
	 */

	public void initialize() {
		sliderSpeech.setValue(TTS.getInstance().getSpeed());
	}

    public void handleGameButtonClick(ActionEvent event) throws IOException {

    	Parent gameBoardView = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
    	Scene gameBoardScene = new Scene(gameBoardView, 700, 500);
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(gameBoardScene);
    	window.show();
    
    } 
    
    public void handlePracticeButtonClick(ActionEvent event) throws IOException {
        
    	Parent PracticeScreenView = FXMLLoader.load(getClass().getResource("PracticeScreen.fxml"));
    	Scene PracticeScreenScene = new Scene(PracticeScreenView, 700, 500);
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(PracticeScreenScene);
    	window.show();
	}
	
	public void handleSpeechSliderChange(ObservableValue<Number> ovn, Number before, Number after) {
		TTS.getInstance().setSpeed(after.intValue());
	}
    
}
