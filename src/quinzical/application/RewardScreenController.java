package quinzical.application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RewardScreenController {
	
public Button buttonGameBoard, buttonMenu;
    
	public void handleButtonGameBoardClick(ActionEvent event) throws IOException {
	    
    	Parent GameBoardView = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
    	Scene GameBoardScene = new Scene(GameBoardView, 700, 500);
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(GameBoardScene);
    	window.show();
    
    }  
	
public void handleButtonMenuClick(ActionEvent event) throws IOException {
	    
    	Parent RootView = FXMLLoader.load(getClass().getResource("Root.fxml"));
    	Scene RootScene = new Scene(RootView, 700, 500);
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(RootScene);
    	window.show();
    
    }  

}
