package quinzical.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import quinzical.util.Modal;

/**
 * Controller for the help menu popup.
 * @author Alexander Nicholson
 */
public class Help {

    @FXML
    private TitledPane p1, p2, p3, p4;

    private static BooleanProperty p1Open = new SimpleBooleanProperty(true);

    private static BooleanProperty p2Open = new SimpleBooleanProperty(true);

    private static BooleanProperty p3Open = new SimpleBooleanProperty(true);

    private static BooleanProperty p4Open = new SimpleBooleanProperty(true);

    @FXML
    private void handleClose(){
        Modal.hide();
    }

    public void initialize(){
        //Pane remembers which panes are expanded and collapsed between it being closed and opened again
        p1.expandedProperty().bindBidirectional(p1Open);
        p2.expandedProperty().bindBidirectional(p2Open);
        p3.expandedProperty().bindBidirectional(p3Open);
        p4.expandedProperty().bindBidirectional(p4Open);
 
    }
}
