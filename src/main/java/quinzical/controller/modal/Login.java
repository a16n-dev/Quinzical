package quinzical.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import quinzical.controller.View;
import quinzical.util.Modal;
import quinzical.util.UserConnect;

/**
 * Controller for the login modal. This allows the user to log into their
 * account using their username and password.
 * 
 * @author Alexander Nicholson
 */
public class Login {

    @FXML
    private TextField fxUsermameField;

    @FXML
    private PasswordField fxPasswordField;

    @FXML
    private Label fxMessage;

    /**
     * Method to handle when the user submits their login credentials
     */
    @FXML
    private void handleSubmit() {

        String username = fxUsermameField.getText();
        String password = fxPasswordField.getText();

        // Validate input
        if (username.length() == 0) {
            fxMessage.setText("Please enter a username");
        } else if (password.length() == 0) {
            fxMessage.setText("Please enter a password");
        } else {
            // If all input is valid
            UserConnect.signIn(username, password, fxMessage);
        }
    }

    /**
     * Handler for switching to the new account dialog
     */
    @FXML
    private void redirectNewAccount() {
        Modal.redirect(View.MODAL_SIGNUP);
    }

    /**
     * Handler for when the user wants to close the dialog
     */
    @FXML
    private void handleClose() {
        Modal.hide();
    }

}
