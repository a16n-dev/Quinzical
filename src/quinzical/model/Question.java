package quinzical.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Question {
    private SimpleIntegerProperty value;
    private SimpleStringProperty question;
    private SimpleStringProperty answer;
    private boolean isAnswered = false;

    public Question(int value, String question, String answer) {
        this.value = new SimpleIntegerProperty(value);
        this.question = new SimpleStringProperty(question);
        this.answer = new SimpleStringProperty(answer);
    }

    // getters
    public int getValue() {
        return value.get();
    }
    public String getQuestion() {
        return question.get();
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
        //No matter if the answer was right or wrong the question is now been attempted
        isAnswered = true;
        
        if(input.toLowerCase().equals(answer.get().toLowerCase())) {
            return true;
        }
        return false;
    }
    /**
     * Save the question to a string. This is useful for saving the question to a file
     */
    public String toString() {
        return Integer.toString(value.get()) + "," + question.get() + "," + answer.get();
    }

    public boolean isAnswered(){
        return isAnswered;
    }
}
