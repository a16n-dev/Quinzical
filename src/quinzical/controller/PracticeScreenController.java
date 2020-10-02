package quinzical.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PracticeScreenController {
	
	public Button button;
    
	public void handleButtonClick(ActionEvent event) throws IOException {
	    
    	Parent PracticeAnswerScreenView = FXMLLoader.load(getClass().getResource("PracticeAnswerScreen.fxml"));
    	Scene PracticeAnswerScreenScene = new Scene(PracticeAnswerScreenView, 700, 500);
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(PracticeAnswerScreenScene);
    	window.show();
    }

}
