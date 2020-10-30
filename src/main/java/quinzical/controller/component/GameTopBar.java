package quinzical.controller.component;

import org.json.JSONException;
import org.json.JSONObject;

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
import quinzical.model.MultiplayerGame;
import quinzical.util.Connect;
import quinzical.util.Modal;
import quinzical.util.Router;

/**
 * Displays the top bar which appears at the top of most screens. This contains
 * the back button, as well as settings and help buttons During games it also
 * shows the users score.
 * 
 * @author Alexander Nicholson, Peter Geodeke
 */
public class GameTopBar {
	@FXML
	private Label fxScoreLabel;

	@FXML
	private HBox fxScoreCard;

	/**
	 * The method to run when the fxml is initialised
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

	/**
	 * Handles when the user presses the back button
	 * 
	 * @param event The ActionEvent generated from the user clicking the button
	 */
	@FXML
	private void handleGoBack(ActionEvent event) {
		// If player is in multiplayer
		if (Router.currentViewIs(View.LOBBY)) {
			Connect connect = Connect.getInstance();
			JSONObject obj = new JSONObject();
			try {
				obj.put("code", MultiplayerGame.getInstance().getCode());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			connect.emit("LEAVE_LOBBY", obj);
		}
		Router.navigateBack();
	}

	/**
	 * Handles when the user presses the settings button
	 */
	@FXML
	private void showSettings() {
		Modal.show(View.MODAL_SETTINGS, 500, 600);
	}

	/**
	 * Handles when the user presses the help button
	 */
	@FXML
	private void showHelp() {
		Modal.show(View.MODAL_HELP, 800, 600);
	}
}
