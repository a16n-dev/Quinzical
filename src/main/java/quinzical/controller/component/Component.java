package quinzical.controller.component;

/**
 * A list of all components which have fxml files, which are smaller fxml
 * snippets to be reused throughout the app
 * 
 * @author Alexander Nicholson
 */
public enum Component {
    GAME_TITLE("view/component/GameTopBar.fxml"), 
    CATEGORY_LIST_ITEM("view/component/CategoryListItem.fxml"),
    GAME_BOARD_ITEM("view/component/GameBoardItem.fxml"), 
    EMPTY_CATEGORY("view/component/EmptyCategory.fxml"),
    GAME_CONTAINER("view/component/Container.fxml"), 
    SELECTED_CATEGORY("view/component/SelectedCategory.fxml"),
    SHOP_LIST_ITEM("view/component/ShopListItem.fxml");

    private final String path;

    /**
     * Private constructor
     * 
     * @param path the path to the associated fxml file
     */
    private Component(String path) {
        this.path = path;
    }

    /**
     * @return the path to the fxml file
     */
    public String getPath() {
        return path;
    }
}
