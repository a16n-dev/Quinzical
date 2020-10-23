package quinzical.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import quinzical.util.Modal;

public class Alert {
    @FXML 
    public Label fxTitle;

    @FXML
    public Label fxBody;
    
    public void init(String title, String content){
        fxTitle.setText(title);
        fxBody.setText(content);
    }

    @FXML
    public void OnAccept(){
        Modal.hide();
    }
}
