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
public enum Views {
    MAIN_MENU("controller/MainMenu.fxml", null, null, null, null),
    ANSWER_SCREEN("controller/AnswerScreen.fxml", Component.GAME_TITLE.path(), null, null, null),
    GAME_BOARD("controller/GameBoard.fxml", Component.GAME_TITLE.path(), null, null, null),
    PRACTICE_SCREEN("controller/PracticeScreen.fxml", Component.GAME_TITLE.path(), null, null, null),
    PRACTICE_ANSWER_SCREEN("controller/PracticeAnswerScreen.fxml", Component.GAME_TITLE.path(), null, null, null),
    REWARD_SCREEN("controller/RewardScreen.fxml", Component.TITLE.path(), null, null, null),
    TROPHY_CASE("controller/TrophyCase.fxml", Component.GAME_TITLE.path(), null, null, null),
    LEADERBOARD("controller/Leaderboard.fxml", Component.GAME_TITLE.path(), null, null, null),
    
	MODAL_SETTINGS("controller/Settings.fxml", null, null, null, null),
	;

    private final String center;
    private final String top;
    private final String right;
    private final String bottom;
    private final String left;

    private Views(String centerContent, String topContent, String rightContent, String bottomContent,
            String leftContent) {
        center = centerContent;
        top = topContent;
        right = rightContent;
        bottom = bottomContent;
        left = leftContent;
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
}
