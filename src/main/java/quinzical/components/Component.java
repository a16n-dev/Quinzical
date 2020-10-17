package quinzical.components;

/**
 * A list of all components - smaller fxml snippets to be reused throughout the
 * app
 */
public enum Component {
    TITLE("components/Title.fxml"),
    GAME_TITLE("components/GameTopBar.fxml"),
    CATEGORY_LIST_ITEM("components/CategoryListItem.fxml");

    private final String path;

    private Component(String filePath) {
        path = filePath;
    }

    public String path() {
        return path;
    }
}
