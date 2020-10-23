package quinzical.controller;

import quinzical.App.GameState;
import quinzical.components.Component;

/**
 * An enum to display different views within the app. Each view can be
 * programmed to set the content for a specific part of the display:
 * 
 *  *  =====================================
 * |                                   |
 * |                top                |
 * |                                   |
 * =====================================
 * |        |                |         |
 * |        |                |         |
 * |  left  |     center     |  right  |
 * |        |                |         |
 * |        |                |         |
 * =====================================
 * |                                   |
 * |              bottom               |
 * |                                   |
 * =====================================
 * 
 * The order is VIEW([center],[top],[right],[bottom],[left])
 */
public enum View {
    MAIN_MENU("controller/MainMenu.fxml", null, GameState.MENU),
    GAME_BOARD("controller/GameBoard.fxml", Component.GAME_TITLE.path(),GameState.GAME),
    ANSWER_SCREEN("controller/AnswerScreen.fxml", Component.GAME_TITLE.path(), new AnswerScreen(),GameState.GAME),
    PRACTICE_ANSWER_SCREEN("controller/AnswerScreen.fxml", Component.GAME_TITLE.path(), new PracticeAnswerScreen(),GameState.PRACTICE),
    MULTIPLAYER_ANSWER_SCREEN("controller/AnswerScreen.fxml", Component.GAME_TITLE.path(), new MultiplayerAnswerScreen(), GameState.MULTIPLAYER),
    REWARD_SCREEN("controller/RewardScreen.fxml", Component.GAME_TITLE.path(), GameState.GAME),
    TROPHY_CASE("controller/TrophyCase.fxml", Component.GAME_TITLE.path(),GameState.MENU),
    LEADERBOARD("controller/Leaderboard.fxml", Component.GAME_TITLE.path(),GameState.MENU),
    SELECT_CATEGORY_GAME("controller/CategorySelect.fxml", Component.GAME_TITLE.path(), new CategorySelectGame(),GameState.MENU),
    SELECT_CATEGORY_PRACTICE("controller/CategorySelect.fxml", Component.GAME_TITLE.path(), new CategorySelectPractice(),GameState.MENU),
    SELECT_CATEGORY_MULTIPLAYER("controller/CategorySelect.fxml", Component.GAME_TITLE.path(), new CategorySelectMultiplayer(),GameState.MENU),
    CUSTOM_CATEGORIES("controller/ManageCategories.fxml", Component.GAME_TITLE.path(),GameState.MENU),
    SHOP("controller/Shop.fxml", Component.GAME_TITLE.path(),GameState.SHOP),

    LOBBY("controller/Lobby.fxml", Component.GAME_TITLE.path(),GameState.MULTIPLAYER),
    MODAL_SETTINGS("controller/Settings.fxml", null, GameState.MODAL),
    MODAL_JOIN("controller/JoinGame.fxml", null, GameState.MODAL),
    MODAL_CONFIRM("controller/Confirm.fxml", null, GameState.MODAL),
    MODAL_LOGIN("controller/Login.fxml", null, GameState.MODAL),
    MODAL_SIGNUP("controller/Signup.fxml", null, GameState.MODAL),
    MODAL_HELP("controller/Help.fxml", null, GameState.MODAL),
    MODAL_ANSWER_FEEDBACK("controller/AnswerScreenPopup.fxml", null, GameState.MODAL),
    MODAL_ALERT("controller/Alert.fxml",null, GameState.MODAL),
	;

    private final String center;
    private final String top;
    private final String right;
    private final String bottom;
    private final String left;
    private final Object controller;
    private final GameState gameState;

    private View(String center, String top, String right, String bottom, String left, Object controller, GameState state) {
        this.center = center;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
        this.controller = controller;
        this.gameState = state;
    }
    private View(String center, String top, GameState state) {
        this.center = center;
        this.top = top;
        this.right = null;
        this.bottom = null;
        this.left = null;
        this.controller = null;
        this.gameState = state;
    }
    private View(String center, String top, Object controller, GameState state) {
        this.center = center;
        this.top = top;
        this.right = null;
        this.bottom = null;
        this.left = null;
        this.controller = controller;
        this.gameState = state;
    }

    public String getCenter() {
        return center;
    }

    public String getTop() {
        return top;
    }

    public String getRight() {
        return right;
    }

    public String getBottom() {
        return bottom;
    }

    public String getLeft() {
        return left;
    }
    
    public Object getController() {
        return controller;
    }

    public GameState getState(){
        return gameState;    
    }
}
