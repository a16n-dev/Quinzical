package quinzical.controller.component;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import quinzical.util.Modal;

/**
 * A container class which all views are placed inside of. This class is
 * responsible for displaying the background and any animation between screens
 */
public class Container {

    @FXML
    private StackPane rootStackPane;

    @FXML
    private BorderPane content;

    /**
     * The method to run when the fxml is initialised
     */
    public void initialize() {
        // Initialise modal
        Modal.init(rootStackPane);

        // Show animation for fly-in
        TranslateTransition moveIn = new TranslateTransition();
        moveIn.setDuration(Duration.millis(4500));
        moveIn.setNode(content);
        moveIn.setFromY(1500);
        moveIn.setByY(-1500);

        moveIn.play();
    }

}
