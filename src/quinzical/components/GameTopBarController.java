package quinzical.components;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import quinzical.controller.Views;
import quinzical.model.Game;
import quinzical.util.Router;

public class GameTopBarController {
    @FXML
	private Label scoreLabel;

	/**
	 * This method is called once the fxml has been loaded
	 */
	public void initialize() {
		SimpleIntegerProperty score = Game.getInstance().getScore();
		scoreLabel.textProperty().bind(Bindings.convert(score));
	}

	@FXML
	public void handleGoBack(ActionEvent event) throws IOException {
		Router.show(Views.MAIN_MENU);
	}
}
