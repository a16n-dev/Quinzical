package quinzical.controller;

import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import quinzical.model.Avatar;
import quinzical.model.Game;
import quinzical.model.User;
import quinzical.util.AvatarFactory;
import quinzical.util.Modal;
import quinzical.util.Router;

public class MainMenuController {
	private User user;

	@FXML
	private Button fxResume;

	@FXML
	private StackPane fxAvatarSlot;

	@FXML
	private Button fxAccountButton;

	@FXML
	private Button fxJoinGameButton;

	@FXML
	private Button fxHostGameButton;

	@FXML
	private Button fxLeaderboardButton;

	@FXML
	private Label fxUserStatus;

	/**
	 * Field to represent if the game has a connection to the internet. If online is false, multiplayer and other buttons are disabled.
	 */
	private BooleanProperty online = new SimpleBooleanProperty(true);

	@FXML 
	private Label fxCoinDisplay;

	public void initialize() {

		//Store reference to user object
		user = User.getInstance();
		
		//Check if resume button should be shown
		if (!Game.isInProgress()) {
			fxResume.setVisible(false);
			fxResume.setManaged(false);
		}

		
		//Load avatar
		Avatar avatar = user.getAvatar();
		AvatarFactory avatarFactory = new AvatarFactory(fxAvatarSlot, 300, true);
		avatarFactory.set(avatar);

		//Display coins
		fxCoinDisplay.setText(Integer.toString(user.getCoins()));

		//Show correct button text for login/logout button
		fxAccountButton.setText(user.getToken() == null ? "LOGIN" : "LOGOUT");
		fxUserStatus.setText(user.getToken() == null ? "Not logged in" : "Logged in as " + user.getName());

		//Bind online dependent buttons to be disabled when offline
		fxJoinGameButton.disableProperty().bind(online.not());
		fxHostGameButton.disableProperty().bind(online.not());
		fxLeaderboardButton.disableProperty().bind(online.not());
		fxAccountButton.disableProperty().bind(online.not());
	}

	// public void handleGameButtonClick(ActionEvent event) throws IOException {
	// 	Router.show(View.GAME_BOARD);
	// }

	@FXML
	public void handleNewGame() {
		if (Game.isInProgress()) {
			Modal.confirmation("New Game","Are you sure you want to start a new game?", e -> {
				Game.clearGame();
				Router.show(View.SELECT_CATEGORY_GAME);
			});
		} else {
			Game.clearGame();
			Router.show(View.SELECT_CATEGORY_GAME, false);
		}
	}

	@FXML
	public void handleResumeGame() {
		Router.show(View.GAME_BOARD);
	}

	@FXML
	public void handlePracticeGame() {
		Router.show(View.SELECT_CATEGORY_PRACTICE);
	}

	@FXML
	public void handleViewTrophyCase() {
		Router.show(View.TROPHY_CASE);
	}
	
	@FXML
	public void handleViewLeaderboard() {
		Router.show(View.LEADERBOARD);
	}

	@FXML
	public void handleViewCustomCategories() {
		Router.show(View.CUSTOM_CATEGORIES);
	}

	@FXML
	public void handleViewShop() {
		Router.show(View.SHOP);
	}

	@FXML
	public void showSettings() {
		Modal.show(View.MODAL_SETTINGS,500,600);
	}

	@FXML
	public void showJoinGame() {
		Modal.show(View.MODAL_JOIN,600,300);
	}

	@FXML
	public void showHelp(){
		Modal.show(View.MODAL_HELP, 800, 600);
	}

	@FXML
	public void handleHostMultiplayer(){
		Router.show(View.SELECT_CATEGORY_MULTIPLAYER, false);
	}

	@FXML
	public void handleAccountButtonPress(){
		if(user.getToken() != null){
			//If logged in show logout confirmation dialog to logout
			Modal.confirmation("Logout", "Are you sure you want to logout?", e -> {
				//Logout the user
				User.getInstance().setToken(null);
				User.getInstance().setName(null);
				fxAccountButton.setText("LOGIN");
				fxUserStatus.setText("Not logged in");
			});
		} else {
			//If not logged in show login box
			Modal.show(View.MODAL_LOGIN);
		}
	}

}
