package quinzical.components;

public enum Component {
    TITLE("components/Title.fxml"),
    GAME_TITLE("components/GameTopBar.fxml")
    ;

    private final String path;

    private Component(String filePath){
        path = filePath;
    }

    public String path(){
        return path;
    }
}
