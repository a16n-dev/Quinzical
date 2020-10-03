package quinzical.controller;

import javafx.fxml.FXML;
import quinzical.util.Modal;

public class ContainerController {
    @FXML
    public void hideModal(){
        Modal.hide();
    }
}
