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
import quinzical.util.Timer;

/**
 * This model represents a game being played by the user.
 */
public class Game extends QuinzicalGame implements Serializable {

    private static final long serialVersionUID = -7700892048792888475L;

    private HashMap<String, ArrayList<Question>> questions;

    private transient SimpleIntegerProperty score;

    private static Game instance;

    private static final int questionCount = 5;

    private String currentCategory;

    private transient int lastScore;

    private Game(List<Category> categories) {
        score = new SimpleIntegerProperty();
        questions = new HashMap<String, ArrayList<Question>>();

        // For each category select 5 random questions
        for (Category category : categories) {
            ArrayList<Question> questionList = questionBank.getRandomQuestions(category, questionCount, false);
            questions.put(category.getName(), questionList);
        }
    }

    /**
     * Adds to the score for the current game
     * @param amount the amount to add to the score
     */
    public void addScore(int amount) {

        float time = Timer.getInstance().getTime();

        float multiplier = Math.min(time / 24, 1);

        lastScore = Math.round(amount * multiplier);

        score.set(score.intValue() + lastScore);
    }

    public int getLastScore(){
        return lastScore;
    }

    /**
     * Returns the user's current score
     */
    public SimpleIntegerProperty getScore() {
        return score;
    }

    /**
     * Sets the current question in the game
     * 
     * @param category the category to select the question from
     * @param index    the index of the question in the game board
     */
    public void setCurrentQuestion(String category, int index) {
        Question q = questions.get(category).get(index);
        currentCategory = category;
        setCurrentQuestion(q);
        q.setAnswered(true);
        persist();
    }

    public static Game getInstance() {
        if (instance == null) {
            // Attempt to read state from file
            instance = IOManager.readState(State.GAME);
            if (instance == null) {
                return null;
            }
        }
        return instance;
    }

    public static boolean isInProgress() {
        instance = IOManager.readState(State.GAME);
        if (instance != null) {
            return !(instance.getRemainingQuestions() == 0);
        } else {
            return false;
        }
    }

    /**
     * Starts a new game
     */
    public static void newGame(List<Category> categories) {
        instance = new Game(categories);
    }

    /**
     * Ends the current game
     */
    public static void clearGame() {
        instance = null;
        persist();
    }

    public ArrayList<String> getCategories() {
        return new ArrayList<String>(questions.keySet());
    }

    public ArrayList<Question> getQuestionsByCategory(String category) {
        return new ArrayList<Question>(questions.get(category));
    }

    /**
     * @return the number of questions the user still has to answer in the current
     *         game
     */
    public int getRemainingQuestions() {
        int count = 0;
        for (Map.Entry<String, ArrayList<Question>> entry : questions.entrySet()) {
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
        if (instance == null) {
            IOManager.clearState(State.GAME);
        } else {
            IOManager.writeState(State.GAME, instance);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        Integer newScore = score.getValue();
        out.writeObject(newScore);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        Integer newScore = (Integer) in.readObject();
        score = new SimpleIntegerProperty(newScore);
    }
}
