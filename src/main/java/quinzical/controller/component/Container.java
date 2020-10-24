package quinzical.controller.component;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import quinzical.util.Modal;

public class Container {

    @FXML
    public StackPane rootStackPane;

    @FXML
    private BorderPane content;

    @FXML
    public void hideModal() {
        // Modal.hide();
    }

    public void initialize() {
        // Initialise modal
        Modal.init(rootStackPane);

        // Show animation
        TranslateTransition moveIn = new TranslateTransition();

        moveIn.setDuration(Duration.millis(4500));

        // Setting the node for the transition
        moveIn.setNode(content);

        // Setting the value of the transition along the x axis.
        moveIn.setFromY(1500);
        moveIn.setByY(-1500);

        moveIn.play(); 

    }

}
