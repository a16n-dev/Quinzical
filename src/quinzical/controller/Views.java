package quinzical.controller;

public enum Views {
    MAIN_MENU("controller/MainMenu.fxml"),
    ANSWER_SCREEN("controller/AnswerScreen.fxml"),
    GAME_BOARD("controller/GameBoard.fxml"),
    PRACTICE_SCREEN("controller/PracticeScreen.fxml"),
    PRACTICE_ANSWER_SCREEN("controller/PracticeAnswerScreen.fxml")
    ;

    private final String filePath;

    private Views(String path){
        filePath = path;
    }

    public String toString(){
        return filePath;
    }
}
