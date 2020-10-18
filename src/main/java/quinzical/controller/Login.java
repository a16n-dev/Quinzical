package quinzical.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import quinzical.util.Modal;

public class Login {
    
    @FXML
    private TextField fxUsermameField;

    @FXML
    private TextField fxPasswordField;

    @FXML
    public void handleSubmit(){

    }

    @FXML
    public void redirectNewAccount(){

    }

    @FXML
    public void handleClose(){
        Modal.hide();
    }

}
