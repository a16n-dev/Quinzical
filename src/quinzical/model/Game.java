package quinzical.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleIntegerProperty;
import quinzical.util.IOManager;
import quinzical.util.State;

/**
 * This model represents a game being played by the user.
 */
public class Game extends QuinzicalGame implements Serializable {

    private static final long serialVersionUID = -7700892048792888475L;

    private HashMap<String, ArrayList<Question>> _questions;

    private transient SimpleIntegerProperty _score;

    private static Game _instance;

    private static final int questionCount = 5;

    private String currentCategory;

    private Game() {
        _score = new SimpleIntegerProperty();
        _questions = new HashMap<String, ArrayList<Question>>();
        // Pick 5 categories at random
        ArrayList<String> categories = questionBank.getRandomCategories(5, false);

        // For each category select 5 random questions
        for (String category : categories) {
            ArrayList<Question> questions = questionBank.getRandomQuestions(category, questionCount, false);
            _questions.put(category, questions);
        }
    }

    /**
     * Adds to the score for the current game
     */
    public void addScore(int score) {
        System.out.println(score);
        _score.set(_score.intValue() + score);
        System.out.println(_score.get());
    }

    /**
     * Returns the user's current score
     */
    public SimpleIntegerProperty getScore() {
        return _score;
    }

    /**
     * Sets the current question in the game
     * 
     * @param category the category to select the question from
     * @param index    the index of the question in the game board
     */
    public void setCurrentQuestion(String category, int index) {
        Question q = _questions.get(category).get(index);
        currentCategory = category;
        setCurrentQuestion(q);
        q.setAnswered(true);
        persist();
        System.out.println("The number of remaining questions is " + getRemainingQuestions());
    }

    public static Game getInstance() {
        if (_instance == null) {
            // Attempt to read state from file
            _instance = IOManager.readState(State.GAME);
            if (_instance == null) {
                _instance = new Game();
            }
        }
        return _instance;
    }

    public static boolean isInProgress() {
        _instance = IOManager.readState(State.GAME);
        if (_instance != null) {
            return !(_instance.getRemainingQuestions() == 0);
        } else {
            return false;
        }
    }

    /**
     * Starts a new game
     */
    public static void newGame() {
        _instance = new Game();
    }

    /**
     * Ends the current game
     */
    public static void clearGame() {
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
     * @return the number of questions the user still has to answer in the current
     *         game
     */
    public int getRemainingQuestions() {
        int count = 0;
        for (Map.Entry<String, ArrayList<Question>> entry : _questions.entrySet()) {
            List<Question> rem = entry.getValue().stream().filter(p -> !p.isAnswered()).collect(Collectors.toList());
            count += rem.size();
        }
        return count;
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    /**
     * Saves the current game instance to file, or deletes any game data if the
     * instance is null.
     */
    private static void persist() {
        if (_instance == null) {
            IOManager.clearState(State.GAME);
        } else {
            IOManager.writeState(State.GAME, _instance);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        Integer score = _score.getValue();
        out.writeObject(score);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        Integer score = (Integer) in.readObject();
        _score = new SimpleIntegerProperty(score);
    }
}
