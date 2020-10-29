package quinzical.controller.component;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import quinzical.model.Question;
import quinzical.util.ImageLoader;
import quinzical.util.Router;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 * Generates a node to display on the gameboard which is what the user presses
 * to select a question Each gameboarditem references a specific question.
 * @author Alexander Nicholson
 */
public class GameBoardItem {

    @FXML
    private StackPane fxBase;

    @FXML
    private Label fxValue;

    @FXML
    private Label fxStatus;

    @FXML
    private Label fxResult;

    @FXML
    private AnchorPane fxBack;

    @FXML
    private AnchorPane fxFront;

    @FXML
    private ImageView fxImageIndicator;

    private Question question;

    private Runnable action;

    private boolean active;

    /**
     * The different style classes that can be applied to the element to reflect its
     * state
     */
    private enum Style {
        DEFAULT("boardItem"), FOCUSED("boardItemFocused"), ANSWERED("boardItemAnswered");

        private String className;

        private Style(String styleClass) {
            className = styleClass;
        }

        public String toString() {
            return className;
        }
    }

    /**
     * Configures the node with the given data
     * 
     * @param q        the question the gameboard item represents
     * @param isActive a boolean that indicates if the node should be clickable
     * @param f        the function to run when the button is pressed
     */
    private void config(Question q, boolean isActive, Runnable f) {

        action = f;
        active = isActive;
        question = q;

        fxValue.textProperty().set("$" + question.getValue());
        fxResult.setText(question.getAnswerStatus());
        switch (question.getAnswerStatus()) {
            case "Incorrect":
                fxImageIndicator.setImage(ImageLoader.loadImage("images/feedback_incorrect.png"));
                break;
            case "Correct":
                fxImageIndicator.setImage(ImageLoader.loadImage("images/feedback_correct.png"));
                break;
            case "Timed out":
                fxImageIndicator.setImage(ImageLoader.loadImage("images/feedback_timeout.png"));
                break;
        }

        if (question.isAnswered()) {
            setStyle(Style.ANSWERED);
            fxFront.setVisible(false);
        } else {
            fxBack.setVisible(false);
            if (active) {
                setStyle(Style.FOCUSED);
            }
        }
    }

    /**
     * On press should add the category to the selection unless it is disabled or
     * already selected
     */
    @FXML
    public void handleButtonPress() {
        if (active && !question.isAnswered()) {
            action.run();
        }
    }

    /**
     * Sets the style of the element to the given class
     * 
     * @param style the style to apply
     */
    private void setStyle(Style style) {
        fxBase.getStyleClass().set(0, style.toString());
    }

    /**
     * Static factory method to generate a new intance of GameBoardItem.
     * 
     * @param q        the question the gameboard item represents
     * @param isActive a boolean that indicates if the node should be clickable
     * @param f        the function to run when the button is pressed
     * @return a region that contains a gameboarditem
     */
    public static Region create(Question question, boolean active, Runnable f) {
        FXMLLoader loader = Router.manualLoad(Component.GAME_BOARD_ITEM.getPath());
        try {
            Region content = (Region) loader.load();
            GameBoardItem controller = loader.getController();

            controller.config(question, active, f);
            return content;
        } catch (IOException e) {
            return null;
        }
    }
}
