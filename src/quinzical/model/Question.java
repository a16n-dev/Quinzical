package quinzical.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Question {
    private SimpleIntegerProperty difficulty;
    private SimpleStringProperty question;
    private SimpleStringProperty questionPrefix;
    private SimpleStringProperty answer;

    public Question(int difficulty, String question, String questionPrefix, String answer) {
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.question = new SimpleStringProperty(question);
        this.questionPrefix = new SimpleStringProperty(questionPrefix);
        this.answer = new SimpleStringProperty(answer);
    }

    // getters
    public int getValue() {
        return difficulty.get() * 100;
    }
    public String getHint() {
        return question.get();
    }
    public String questionPrefix() {
        return questionPrefix.get();
    }
    public String getAnswer() {
        return answer.get();
    }
    /**
     * Function to check whether the user's answer is correct
     * @param input The user's answer
     * @return Whether the answer was correct
     */
    public boolean checkAnswer(String input) {
        if(input.toLowerCase().equals(answer.get().toLowerCase())) {
            return true;
        }
        return false;
    }
    /**
     * Save the question to a string. This is useful for saving the question to a file
     */
    public String toString() {
        return String.join("|", new String[] {Integer.toString(difficulty.get()), question.get(), questionPrefix.get(), answer.get()});
    }
}
