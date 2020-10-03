package quinzical.components;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import quinzical.controller.Views;
import quinzical.model.Game;
import quinzical.util.Modal;
import quinzical.util.Router;

public class GameTopBarController {
    @FXML
	private Label scoreLabel;

	@FXML
	private HBox scoreCard;

	/**
	 * This method is called once the fxml has been loaded
	 */
	public void initialize() {
		//Hide the score if in practice mode
		if(Router.getGameState() != 1){
			scoreCard.setVisible(false);
			scoreCard.setManaged(false);
		}else {
			SimpleIntegerProperty score = Game.getInstance().getScore();
			scoreLabel.textProperty().bind(Bindings.convert(score));	
		}
	}

	@FXML
	public void handleGoBack(ActionEvent event) throws IOException {
		Router.show(Views.MAIN_MENU);
	}

	@FXML
	public void showSettings(){
		Modal.show();
	}
}
