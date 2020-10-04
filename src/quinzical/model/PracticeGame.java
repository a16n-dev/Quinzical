package quinzical.model;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;

public class PracticeGame extends QuinzicalGame {
    private static PracticeGame instance;
    private SimpleIntegerProperty remainingAttempts = new SimpleIntegerProperty();
    private int currentQuestionAttempts = 0;

    private PracticeGame() {
    }

    public static PracticeGame getInstance() {
        if (instance == null) {
            instance = new PracticeGame();
        }
        return instance;
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
        currentQuestionAttempts = 0;
        remainingAttempts.set(3);

        setCurrentQuestion(questionBank.getRandomQuestions(currentCategory, 1, true).get(0));
    }

    /**
     * @return the number of attempts for the current question
     */
    public int getAttempts() {
        return currentQuestionAttempts;
    }

    /**
     * Increases the number of attempts for the current question by 1 and then
     * returns the total number of attempts
     */
    public void addAttempt() {
        currentQuestionAttempts += 1;
        remainingAttempts.set(3 - currentQuestionAttempts);
    }

    public SimpleIntegerProperty getRemainingAttempts() {
        return remainingAttempts;
    }
}
