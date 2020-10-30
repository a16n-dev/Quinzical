package quinzical.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class for a practice game
 */
public class PracticeGame extends QuinzicalGame {
    private static PracticeGame instance;
    private SimpleIntegerProperty remainingAttempts = new SimpleIntegerProperty();
    private int currentQuestionAttempts = 0;

    private int streak;

    /**
     * Private constructor
     */
    private PracticeGame() {
    }

    /**
     * 
     * @return the instance
     */
    public static PracticeGame getInstance() {
        if (instance == null) {
            instance = new PracticeGame();
        }
        return instance;
    }

    private Category currentCategory;

    /**
     * Set the current category
     * 
     * @param category
     */
    public void setCurrentCategory(Category category) {
        currentCategory = category;
    }

    /**
     * Set the current question to a random question
     */
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

    /**
     * 
     * @return the remaining number of attempts for the current question
     */
    public SimpleIntegerProperty getRemainingAttempts() {
        return remainingAttempts;
    }

    /**
     * 
     * @return the streak
     */
    public int getStreak() {
        return streak;
    }

    /**
     * Increment the streak
     */
    public void addStreak() {
        streak += 1;
    }

    /**
     * Reset the streak
     */
    public void clearStreak() {
        streak = 0;
    }
}
