package quinzical.controller.component;

/**
 * A list of all components - smaller fxml snippets to be reused throughout the
 * app
 */
public enum Component {
    GAME_TITLE("view/component/GameTopBar.fxml"),
    CATEGORY_LIST_ITEM("view/component/CategoryListItem.fxml");

    private final String path;

    private Component(String filePath) {
        path = filePath;
    }

    public String path() {
        return path;
    }
}
