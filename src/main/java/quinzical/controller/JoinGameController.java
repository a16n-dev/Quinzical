package quinzical.controller;

import java.util.function.UnaryOperator;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import quinzical.util.Modal;

public class JoinGameController {

    @FXML
    public JFXTextField fxSlot1;

    @FXML
    public JFXTextField fxSlot2;

    @FXML
    public JFXTextField fxSlot3;

    @FXML
    public JFXTextField fxSlot4;

    @FXML
    public JFXTextField fxSlot5;

    public void initialize() {
        /**
         * Credit to: https://stackoverflow.com/questions/15159988/javafx-2-2-textfield-maxlength
         */
        UnaryOperator<Change> modifyChange = c -> {
            if (c.isContentChange()) {
                int newLength = c.getControlNewText().length();
                if (newLength > 1) {
                    // replace the input text with the last len chars
                    String tail = c.getControlNewText().substring(newLength - 1, newLength);
                    c.setText(tail);
                    // replace the range to complete text
                    // valid coordinates for range is in terms of old text
                    int oldLength = c.getControlText().length();
                    c.setRange(0, oldLength);

                } 
                if (newLength >= 1) {
                    //move the cursor to the next input
                    String elemId = c.getControl().getId();
                    
                    switch (elemId) {
                        case "fxSlot1":
                            fxSlot2.requestFocus();
                            break;
                        case "fxSlot2":
                            fxSlot3.requestFocus();
                            break;
                        case "fxSlot3":
                            fxSlot4.requestFocus();
                            break;
                        case "fxSlot4":
                            fxSlot5.requestFocus();
                            break;
                        default:
                            break;
                    }
                }
            }
            return c;
        };

        fxSlot1.setTextFormatter(new TextFormatter(modifyChange));
        fxSlot2.setTextFormatter(new TextFormatter(modifyChange));
        fxSlot3.setTextFormatter(new TextFormatter(modifyChange));
        fxSlot4.setTextFormatter(new TextFormatter(modifyChange));
        fxSlot5.setTextFormatter(new TextFormatter(modifyChange));
    }

    public void handleClose(){
        Modal.hide();
    }
}
