package quinzical.controller;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXTextField;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import quinzical.model.MultiplayerGame;
import quinzical.util.Connect;
import quinzical.util.Modal;
import quinzical.util.Multiplayer;
import quinzical.util.Router;

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

    @FXML
    public Label fxMessage;

    public void initialize() {
        fxMessage.setText("");
        /**
         * Credit to:
         * https://stackoverflow.com/questions/15159988/javafx-2-2-textfield-maxlength
         */
        UnaryOperator<Change> modifyChange = (c -> {
            if (c.isContentChange()) {
                c.setText(c.getControlNewText().toUpperCase());
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
                    // move the cursor to the next input
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
        });

        fxSlot1.setTextFormatter(new TextFormatter<Change>(modifyChange));
        fxSlot2.setTextFormatter(new TextFormatter<Change>(modifyChange));
        fxSlot3.setTextFormatter(new TextFormatter<Change>(modifyChange));
        fxSlot4.setTextFormatter(new TextFormatter<Change>(modifyChange));
        fxSlot5.setTextFormatter(new TextFormatter<Change>(modifyChange));
    }

    public void handleClose() {
        Modal.hide();
    }

    private String getCode() {
        return fxSlot1.textProperty().get() + fxSlot2.textProperty().get() + fxSlot3.textProperty().get()
                + fxSlot4.textProperty().get() + fxSlot5.textProperty().get();
    }

    @FXML
    public void handleJoin() {
        String code = getCode();

        // check if valid
        if (code.length() < 5 || Pattern.matches("[a-zA-Z]+", code)) {
            fxMessage.setText("Invalid code");
        }

        Multiplayer.joinLobby(code);
    }
}
