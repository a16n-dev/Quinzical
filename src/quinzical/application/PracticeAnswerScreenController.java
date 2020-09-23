package quinzical.application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PracticeAnswerScreenController {
	
	public Button button;
    
	public void handleButtonClick(ActionEvent event) throws IOException {
		
		/* Prompt user to try again after first and second incorrect attempts, 
		   show first letter hint on third attempt, or show clue and answer 
		   after third incorrect attempt. */
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Correct/Incorrect");
    	alert.setHeaderText("Correct/Incorrect");
    	alert.setContentText("Try again, hint, or show clue and answer.");				    	
    	alert.showAndWait();
	    
    	Parent RootView = FXMLLoader.load(getClass().getResource("Root.fxml"));
    	Scene RootScene = new Scene(RootView, 700, 500);
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(RootScene);
    	window.show();
    
    }

}
