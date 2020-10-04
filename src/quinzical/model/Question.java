package quinzical.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.Normalizer;

public class Question implements Serializable {

    private static final long serialVersionUID = -1945949962117279881L;
    private transient SimpleIntegerProperty difficulty;
    private transient SimpleStringProperty question;
    private transient SimpleStringProperty questionPrefix;
    private transient SimpleStringProperty answer;
    private boolean isAnswered = false;

    public Question(int difficulty, String question, String questionPrefix, String answer) {
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.question = new SimpleStringProperty(question);
        this.questionPrefix = new SimpleStringProperty(questionPrefix);
        this.answer = new SimpleStringProperty(answer);
    }

    public Question(Question q){
        this.difficulty = q.difficulty;
        this.question = q.question;
        this.questionPrefix = q.questionPrefix;
        this.answer = q.answer;
    }

    // getters
    public int getValue() {
        return difficulty.get() * 100;
    }
    public String getHint() {
        return question.get();
    }
    public String getPrefix() {
        return questionPrefix.get();
    }
    public String getAnswer() {
        return answer.get();
    }
    public int getDifficulty() {
        return difficulty.get();
    }
    public boolean isAnswered(){
        return isAnswered;
    }
    /**
     * Function to check whether the user's answer is correct
     * @param input The user's answer
     * @return Whether the answer was correct
     */
    public boolean checkAnswer(String input) {
        String[] possibleAnswers = answer.get().split("/");

        for (String answer : possibleAnswers) {
            if (sanitise(input).equals(sanitise(answer))) {
                return true;
            }
        }
        return false;
    }

    private String sanitise(String str) {
        // str = Normalizer.normalize(str, Normalizer.Form.NFKD);
        return str.toLowerCase().replaceAll("\\s+", "");
    }

    /**
     * Sets the answered state of the question
     */
    public void setAnswered(boolean answered){
        isAnswered = answered;
    }
    /**
     * Save the question to a string. This is useful for saving the question to a file
     */
    public String toString() {
        return String.join("|", new String[] {Integer.toString(difficulty.get()), question.get(), questionPrefix.get(), answer.get()});
    }

    //Serializable methods for writing and reading from file 
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        Integer difficultyS = difficulty.getValue();
        out.writeObject(difficultyS);

        String questionS = question.getValue();
        out.writeObject(questionS);

        String questionPrefixS = questionPrefix.getValue();
        out.writeObject(questionPrefixS);

        String answerS = answer.getValue();
        out.writeObject(answerS);

    };

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        Integer difficultyS = (Integer) in.readObject();
        difficulty = new SimpleIntegerProperty(difficultyS);

        String questionS = (String) in.readObject();
        question = new SimpleStringProperty(questionS);

        String questionPrefixS = (String) in.readObject();
        questionPrefix = new SimpleStringProperty(questionPrefixS);

        String answerS = (String) in.readObject();
        answer = new SimpleStringProperty(answerS);
    };
}
