package quinzical.controller;

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
    MAIN_MENU("controller/MainMenu.fxml", null),
    GAME_BOARD("controller/GameBoard.fxml", Component.GAME_TITLE.path()),
    ANSWER_SCREEN("controller/AnswerScreen.fxml", Component.GAME_TITLE.path(), new AnswerScreen()),
    PRACTICE_ANSWER_SCREEN("controller/AnswerScreen.fxml", Component.GAME_TITLE.path(), new PracticeAnswerScreen()),
    REWARD_SCREEN("controller/RewardScreen.fxml", Component.TITLE.path()),
    TROPHY_CASE("controller/TrophyCase.fxml", Component.GAME_TITLE.path()),
    LEADERBOARD("controller/Leaderboard.fxml", Component.GAME_TITLE.path()),
    SELECT_CATEGORY_GAME("controller/CategorySelect.fxml", Component.GAME_TITLE.path(), new CategorySelectGame()),
    SELECT_CATEGORY_PRACTICE("controller/CategorySelect.fxml", Component.GAME_TITLE.path(), new CategorySelectPractice()),
    CUSTOM_CATEGORIES("controller/ManageCategories.fxml", Component.GAME_TITLE.path()),

    MODAL_SETTINGS("controller/Settings.fxml", null),
    MODAL_JOIN("controller/JoinGame.fxml", null),
    MODAL_CONFIRM("controller/Confirm.fxml", null),
    MODAL_LOGIN("controller/Login.fxml", null),
    MODAL_SIGNUP("controller/Signup.fxml", null),
	;

    private final String center;
    private final String top;
    private final String right;
    private final String bottom;
    private final String left;
    private final Object controller;

    private View(String center, String top, String right, String bottom, String left, Object controller) {
        this.center = center;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
        this.controller = controller;
    }
    private View(String center, String top) {
        this.center = center;
        this.top = top;
        this.right = null;
        this.bottom = null;
        this.left = null;
        this.controller = null;
    }
    private View(String center, String top, Object controller) {
        this.center = center;
        this.top = top;
        this.right = null;
        this.bottom = null;
        this.left = null;
        this.controller = controller;
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
}
