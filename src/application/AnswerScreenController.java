package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AnswerScreenController {
	
	public Button button;
    
	public void handleButtonClick(ActionEvent event) throws IOException {
	    
    	Parent RewardScreenView = FXMLLoader.load(getClass().getResource("RewardScreen.fxml"));
    	Scene RewardScreenScene = new Scene(RewardScreenView, 700, 500);
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(RewardScreenScene);
    	window.show();
    
    }

}
