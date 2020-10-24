package quinzical.components;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import quinzical.model.Category;
import quinzical.model.Question;
import quinzical.model.User;
import quinzical.util.Router;

import java.io.IOException;
import java.util.function.Function;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class GameBoardItem {

    /**
     * The category the element represents
     */
    private Question question;

    private Runnable action;

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

    private void config(Question question, Runnable f) {
        action = f;
        fxValue.textProperty().set("$" + question.getValue());
        if(question.isAnswered()){
            setStyle(Style.FOCUSED);
        }
    }

    /**
     * On press should add the category to the selection unless it is disabled or
     * already selected
     */
    @FXML
    public void handleButtonPress() {
        action.run();
    }

    /**
     * Sets the style of the element to the given class
     * 
     * @param style the style to apply
     */
    private void setStyle(Style style) {
        fxBase.getStyleClass().set(0, style.toString());
    }


    public static Region create(Question question, Runnable f) {
        FXMLLoader loader = Router.manualLoad("components/GameBoardItem.fxml");
        try {
            Region content = (Region) loader.load();
            GameBoardItem controller = loader.getController();

            controller.config(question, f);
            return content;
        } catch (IOException e) {
            return null;
        }
    }
}
