package quinzical.model;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;

public class PracticeGame extends QuinzicalGame {
    private static PracticeGame _instance;
    private SimpleIntegerProperty _remainingAttempts = new SimpleIntegerProperty();
    private int _currentQuestionAttempts = 0;

    private PracticeGame() {
    }

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

        // As question has changed also reset attempts
        _currentQuestionAttempts = 0;
        _remainingAttempts.set(3);

        setCurrentQuestion(questionBank.getRandomQuestions(currentCategory, 1, false).get(0));
    }

    /**
     * @return the number of attempts for the current question
     */
    public int getAttempts() {
        return _currentQuestionAttempts;
    }

    /**
     * Increases the number of attempts for the current question by 1 and then
     * returns the total number of attempts
     */
    public void addAttempt() {
        _currentQuestionAttempts += 1;
        _remainingAttempts.set(3 - _currentQuestionAttempts);
    }

    public SimpleIntegerProperty getRemainingAttempts() {
        return _remainingAttempts;
    }
}
