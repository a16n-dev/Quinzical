package quinzical.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import quinzical.util.Modal;
import quinzical.util.UserConnect;

public class Signup {
    
    @FXML
    private TextField fxUsername;

    @FXML
    private PasswordField fxPassword;

    @FXML
    private PasswordField fxPasswordRepeat;

    @FXML
    private Button fxSubmit;

    @FXML
    private Button fxCancel;

    @FXML
    private Button fxLogin;

    @FXML
    private Label fxMessage;

    @FXML
    private void handleSubmit(){
        String username = fxUsername.getText();
        String password = fxPassword.getText();
        String passwordRepeat = fxPasswordRepeat.getText();

        //Validate input
        if(username.length() == 0){
            fxMessage.setText("Please enter a username");
        } else if(password.length() == 0){
            fxMessage.setText("Please enter a password");
        } else if (!password.equals(passwordRepeat)){
            fxMessage.setText("Passwords do not match");   
        }else {
            UserConnect.signUp(username, password, fxMessage);
        }
    }

    @FXML
    private void handleRedirectLogin(){
        Modal.redirect(View.MODAL_LOGIN);
    }

    @FXML
    private void handleCancel(){
        Modal.hide();
    }
}
