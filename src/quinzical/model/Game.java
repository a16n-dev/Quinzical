package quinzical.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import quinzical.util.IOManager;

/**
 * This model represents a game being played by the user.
 */
public class Game extends QuinzicalGame implements Serializable {

    private static final long serialVersionUID = -7700892048792888475L;

    private HashMap<String, ArrayList<Question>> _questions;
    private int _score;
    private static Game _instance;
    private static int questionCount = 5;

    private Game() {
        _questions = new HashMap<String, ArrayList<Question>>();
        // Pick 5 categories at random
        ArrayList<String> categories = questionBank.getRandomCategories(5, false);

        // For each category select 5 random questions
        for (String category : categories) {
            ArrayList<Question> questions = questionBank.getRandomQuestions(category, questionCount, false);
            _questions.put(category, questions);
        }

        // Make sure the users score is 0
        _score = 0;
    }

    /**
     * Adds to the score for the current game
     */
    public void addScore(int score) {
        _score += score;
    }

    /**
     * Returns the user's current score
     */
    public int getScore() {
        return _score;
    }

    /**
     * Sets the current question in the game
     * 
     * @param category the category to select the question from
     * @param index    the index of the question in the game board
     */
    public void setCurrentQuestion(String category, int index) {
        setCurrentQuestion(_questions.get(category).get(index));
    }

    public static Game getInstance() {
        if (_instance == null) {
            // Attempt to read state from file
            _instance = IOManager.readGameState();
            if (_instance == null) {
                _instance = new Game();
                persist();
            }
        }
        return _instance;
    }

    /**
     * Starts a new game
     */
    @Override
    public void newGame() {
        System.out.println("Bruh");
        _instance = new Game();
    }

    /**
     * Ends the current game
     */
    public void endGame(){
        _instance = null;
        persist();
    }

    public ArrayList<String> getCategories() {
        return new ArrayList<String>(_questions.keySet());
    }

    public ArrayList<Question> getQuestionsByCategory(String category) {
        return new ArrayList<Question>(_questions.get(category));
    }

    /**
     * Saves the current game instance to file, or deletes any game data if the instance is null.
     */
    private static void persist() {
        if(_instance == null){
            IOManager.clearGameState();
        } else {
            IOManager.writeGameState();

        }
    }
}
