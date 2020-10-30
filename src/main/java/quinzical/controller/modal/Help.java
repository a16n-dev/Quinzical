package quinzical.controller.modal;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import quinzical.util.Modal;

/**
 * Controller for the help menu popup. This allows the user to learn about
 * different features in the application
 * 
 * @author Alexander Nicholson
 */
public class Help {

    @FXML
    private TitledPane p1;

    @FXML
    private TitledPane p2;

    @FXML
    private TitledPane p3;

    @FXML
    private TitledPane p4;

    private static BooleanProperty p1Open = new SimpleBooleanProperty(true);

    private static BooleanProperty p2Open = new SimpleBooleanProperty(true);

    private static BooleanProperty p3Open = new SimpleBooleanProperty(true);

    private static BooleanProperty p4Open = new SimpleBooleanProperty(true);

    /**
     * Event handler for when the user closes the dialog
     */
    @FXML
    private void handleClose() {
        Modal.hide();
    }

    /**
     * The method to run when the fxml is initialised
     */
    public void initialize() {
        // Pane remembers which panes are expanded and collapsed between it being closed
        // and opened again
        p1.expandedProperty().bindBidirectional(p1Open);
        p2.expandedProperty().bindBidirectional(p2Open);
        p3.expandedProperty().bindBidirectional(p3Open);
        p4.expandedProperty().bindBidirectional(p4Open);

    }
}
