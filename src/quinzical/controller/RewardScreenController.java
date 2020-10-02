package quinzical.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import quinzical.util.Router;

public class RewardScreenController {
	
public Button buttonGameBoard, buttonMenu;
    
	public void handleButtonGameBoardClick(ActionEvent event) throws IOException {
	    
		Router.show(Views.GAME_BOARD);
    
    }  
	
	public void handleButtonMenuClick(ActionEvent event) throws IOException {
	    
    	Router.show(Views.MAIN_MENU);
    
    }  

}
