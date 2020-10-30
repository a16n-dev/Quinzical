package quinzical.controller.modal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import quinzical.util.Modal;

/**
 * Controller for the Alert modal. This displays a message to the user, and
 * allows them to either confirm or cancel their action
 * 
 * @author Alexander Nicholson
 */
public class Confirm {

    @FXML
    private Label fxTitle;

    @FXML
    private Label fxBody;

    @FXML
    private Button fxConfirm;

    /**
     * Initialise the modal window with provided information
     * @param title   the title text to display at the top of the modal
     * @param content the body text to display in the modal window
     * @param event the event handler to run if the user confirms the action
     */
    public void init(String title, String content, EventHandler<ActionEvent> eventHandler) {
        fxTitle.setText(title);
        fxBody.setText(content);
        fxConfirm.setOnAction(eventHandler);
    }

    @FXML
    private void handleCancel() {
        Modal.hide();
    }
}
