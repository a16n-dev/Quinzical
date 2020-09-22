package quinzical.application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameBoardController {

	public Button button1, button2, button3, button4, button5;
    
	public void handleButtonClick(ActionEvent event) throws IOException {
	    
    	Parent AnswerScreenView = FXMLLoader.load(getClass().getResource("AnswerScreen.fxml"));
    	Scene AnswerScreenScene = new Scene(AnswerScreenView, 700, 500);
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(AnswerScreenScene);
    	window.show();
    
    }  
	
}
