package quinzical.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import quinzical.util.Modal;

/**
 * Controller for the Alert modal. This displays a message to the user, and does
 * not require them to take any action other than dismiss the message
 * 
 * @author Alexander Nicholson
 */
public class Alert {
    @FXML
    private Label fxTitle;

    @FXML
    private Label fxBody;

    /**
     * Initialise the modal window with provided information
     * 
     * @param title   the title text to display at the top of the modal
     * @param content the body text to display in the modal window
     */
    public void init(String title, String content) {
        fxTitle.setText(title);
        fxBody.setText(content);
    }

    /**
     * Method to run when user accepts the dialog message
     */
    @FXML
    private void OnAccept() {
        Modal.hide();
    }
}
