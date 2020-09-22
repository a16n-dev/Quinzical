package quinzical.application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RootController {

    public Button button;
    
    public void handleButtonClick(ActionEvent event) throws IOException {
    
    	Parent gameBoardView = FXMLLoader.load(getClass().getResource("GameBoard.fxml"));
    	Scene gameBoardScene = new Scene(gameBoardView, 700, 500);
    	
    	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(gameBoardScene);
    	window.show();
    
    }    
    
}
