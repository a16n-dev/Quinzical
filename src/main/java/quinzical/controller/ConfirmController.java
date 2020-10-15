package quinzical.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import quinzical.util.Modal;

public class ConfirmController {
    
    @FXML 
    public Label fxTitle;

    @FXML
    public Label fxBody;

    @FXML
    public Button fxConfirm;

    public void init(String title, String content, EventHandler<ActionEvent> event){
        fxTitle.setText(title);
        fxBody.setText(content);
        fxConfirm.setOnAction(event);
    }

    @FXML
    public void handleCancel(){
        Modal.hide();
    }
}
