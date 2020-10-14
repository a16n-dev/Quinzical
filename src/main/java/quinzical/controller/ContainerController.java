package quinzical.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import quinzical.util.Modal;

public class ContainerController {
	
	@FXML
	public StackPane rootStackPane;

    @FXML
    public void hideModal() {
//        Modal.hide();
    }

    public void initialize() {
    	//Initialise modal
    	Modal.init(rootStackPane);
    }

}
