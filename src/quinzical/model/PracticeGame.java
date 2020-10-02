package quinzical.model;

import java.util.ArrayList;

public class PracticeGame extends QuinzicalGame {
    private static PracticeGame _instance;

    private PracticeGame() { }

    public static PracticeGame getInstance() {
        if (_instance == null) {
            _instance = new PracticeGame();
        }
        return _instance;
    }

    private String currentCategory;

    public ArrayList<String> getCategories() {
        return questionBank.getCategories();
    }

    public void setCurrentCategory(String category) {
        currentCategory = category;
    }
    public void setRandomQuestion() {
        setCurrentQuestion(questionBank.getRandomQuestions(currentCategory, 1, false).get(0));
    }

    @Override
    public void newGame() {
        _instance = new PracticeGame();
    }
    
}
