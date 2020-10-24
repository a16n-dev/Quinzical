package quinzical.controller;

import quinzical.App.GameState;
import quinzical.controller.component.Component;
import quinzical.controller.game.AnswerScreen;
import quinzical.controller.game.MultiplayerAnswerScreen;
import quinzical.controller.game.PracticeAnswerScreen;
import quinzical.controller.menu.CategorySelectGame;
import quinzical.controller.menu.CategorySelectMultiplayer;
import quinzical.controller.menu.CategorySelectPractice;

/**
 * An enum to display different views within the app. Each view can be
 * programmed to set the content for a specific part of the display
 */
public enum View {

    // Game Views
    GAME_BOARD("view/game/GameBoard.fxml", Component.GAME_TITLE.path(), GameState.GAME),
    ANSWER_SCREEN("view/game/AnswerScreen.fxml", Component.GAME_TITLE.path(), new AnswerScreen(), GameState.GAME),
    PRACTICE_ANSWER_SCREEN("view/game/AnswerScreen.fxml", Component.GAME_TITLE.path(), new PracticeAnswerScreen(),
            GameState.PRACTICE),
    MULTIPLAYER_ANSWER_SCREEN("view/game/AnswerScreen.fxml", Component.GAME_TITLE.path(), new MultiplayerAnswerScreen(),
            GameState.MULTIPLAYER),
    REWARD_SCREEN("view/game/RewardScreen.fxml", Component.GAME_TITLE.path(), GameState.GAME),
    LOBBY("view/game/Lobby.fxml", Component.GAME_TITLE.path(), GameState.MULTIPLAYER),

    // Menu Views
    MAIN_MENU("view/menu/MainMenu.fxml", null, GameState.MENU),
    TROPHY_CASE("view/menu/TrophyCase.fxml", Component.GAME_TITLE.path(), GameState.MENU),
    LEADERBOARD("view/menu/Leaderboard.fxml", Component.GAME_TITLE.path(), GameState.MENU),
    SELECT_CATEGORY_GAME("view/menu/CategorySelect.fxml", Component.GAME_TITLE.path(), new CategorySelectGame(),
            GameState.MENU),
    SELECT_CATEGORY_PRACTICE("view/menu/CategorySelect.fxml", Component.GAME_TITLE.path(), new CategorySelectPractice(),
            GameState.MENU),
    SELECT_CATEGORY_MULTIPLAYER("view/menu/CategorySelect.fxml", Component.GAME_TITLE.path(),
            new CategorySelectMultiplayer(), GameState.MENU),
    CUSTOM_CATEGORIES("view/menu/ManageCategories.fxml", Component.GAME_TITLE.path(), GameState.MENU),
    SHOP("view/menu/Shop.fxml", Component.GAME_TITLE.path(), GameState.SHOP),

    // Modal Views
    MODAL_SETTINGS("view/modal/Settings.fxml", null, GameState.MODAL),
    MODAL_JOIN("view/modal/JoinGame.fxml", null, GameState.MODAL),
    MODAL_CONFIRM("view/modal/Confirm.fxml", null, GameState.MODAL),
    MODAL_LOGIN("view/modal/Login.fxml", null, GameState.MODAL),
    MODAL_SIGNUP("view/modal/Signup.fxml", null, GameState.MODAL),
    MODAL_HELP("view/modal/Help.fxml", null, GameState.MODAL),
    MODAL_ANSWER_FEEDBACK("view/modal/AnswerScreenPopup.fxml", null, GameState.MODAL),
    MODAL_ALERT("view/modal/Alert.fxml", null, GameState.MODAL),;

    private final String center;
    private final String top;
    private final String right;
    private final String bottom;
    private final String left;
    private final Object controller;
    private final GameState gameState;

    private View(String center, String top, String right, String bottom, String left, Object controller,
            GameState state) {
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

    public GameState getState() {
        return gameState;
    }
}
