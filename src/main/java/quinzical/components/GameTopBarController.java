package quinzical.components;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import quinzical.App;
import quinzical.App.GameState;
import quinzical.controller.View;
import quinzical.model.Game;
import quinzical.util.Modal;
import quinzical.util.Router;

public class GameTopBarController {
	@FXML
	private Label fxScoreLabel;

	@FXML
	private HBox fxScoreCard;

	/**
	 * This method is called once the fxml has been loaded
	 */
	public void initialize() {
		// Hide the score if in practice mode
		if (App.getState() == GameState.GAME) {
			SimpleIntegerProperty score = Game.getInstance().getScore();
			fxScoreLabel.textProperty().bind(Bindings.convert(score));
		} else {
			fxScoreCard.setVisible(false);
			fxScoreCard.setManaged(false);
		}
	}

	@FXML
	private void handleGoBack(ActionEvent event) throws IOException {
		Router.navigateBack();
	}

	@FXML
	private void showSettings() {
		Modal.show(View.MODAL_SETTINGS, 500, 600);
	}

	@FXML
	private void showHelp() {
		Modal.show(View.MODAL_HELP, 800, 600);
	}
}
