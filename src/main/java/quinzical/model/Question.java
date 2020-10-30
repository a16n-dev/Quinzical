package quinzical.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

import org.apache.xmlbeans.impl.common.Levenshtein;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Question model representing a question in the game
 */
public class Question implements Serializable {

    private static final long serialVersionUID = -1945949962117279881L;

    private String id;

    private transient SimpleIntegerProperty difficulty;
    private transient SimpleStringProperty question;
    private transient SimpleStringProperty questionPrefix;
    private transient SimpleStringProperty answer;
    
    private boolean isAnswered = false;

    private String answerStatus = "Didnt Answer";

    /**
     * Constructor without id
     * @param difficulty
     * @param question
     * @param questionPrefix
     * @param answer
     */
    public Question(int difficulty, String question, String questionPrefix, String answer) {
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.question = new SimpleStringProperty(question);
        this.questionPrefix = new SimpleStringProperty(questionPrefix);
        this.answer = new SimpleStringProperty(answer);
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Constructor with id
     * @param id
     * @param difficulty
     * @param question
     * @param questionPrefix
     * @param answer
     */
    public Question(String id, int difficulty, String question, String questionPrefix, String answer) {
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.question = new SimpleStringProperty(question);
        this.questionPrefix = new SimpleStringProperty(questionPrefix);
        this.answer = new SimpleStringProperty(answer);
        this.id = id;
    }

    /**
     * 
     * @param q
     */
    public Question(Question q) {
        this.difficulty = q.difficulty;
        this.question = q.question;
        this.questionPrefix = q.questionPrefix;
        this.answer = q.answer;
    }

    /**
     * Constructor for empty question
     */
    public Question() {
        this.difficulty = new SimpleIntegerProperty();
        this.question = new SimpleStringProperty();
        this.questionPrefix = new SimpleStringProperty();
        this.answer = new SimpleStringProperty();
        this.id = UUID.randomUUID().toString();
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

    public boolean isAnswered() {
        return isAnswered;
    }

    /**
     * Function to check whether the user's answer is correct
     * 
     * @param input The user's answer
     * @return Whether the answer was correct
     */
    public Answer checkAnswer(String input) {
        String[] possibleAnswers = answer.get().split("/");

        for (String answer : possibleAnswers) {
            int distance = Levenshtein.distance(sanitise(input), sanitise(answer));

            if (distance == 0) {
                answerStatus = "Correct";
                return Answer.CORRECT;
            }
            else if (distance < 3) {
                return Answer.TYPO;
            }
        }
        answerStatus = "Incorrect";
        return Answer.INCORRECT;
    }

    private String sanitise(String str) {
        // str = Normalizer.normalize(str, Normalizer.Form.NFKD);
        return str.toLowerCase().replaceAll("\\s+", "");
    }

    /**
     * Sets the answered state of the question
     */
    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public String getAnswerStatus(){
        return answerStatus;
    }

    /**
     * Save the question to a string. This is useful for saving the question to a
     * file
     */
    public String toString() {
        return String.join("|", new String[] { id, Integer.toString(difficulty.get()), question.get(), questionPrefix.get(),
                answer.get() 
        });
    }

    /**
     * Write the question to a file
     * @param out the output stream to write it to
     * @throws IOException
     */
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

    /**
     * Read a question from a file
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    @Override
    public boolean equals(Object o){
        if (this == o) {  
            return true;  
        } 
        if (o instanceof Question) {
            Question q = (Question) o;

            if(this.id.equals(q.id)){
                return true;
            }

        }
        return false;
    }

    /**
     * 
     * @return the id of the question
     */
    public String getId(){
        return id;
    }

    /**
     * 
     * @return the question as a JSONObject
     */
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("difficulty", difficulty.getValue());
            obj.put("question", question.getValue());
            obj.put("questionPrefix", questionPrefix.getValue());
            obj.put("answer", answer.getValue());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * Static method to create a question object from a JSONObject representing a question
     * @param raw the JSONObject as a string
     * @return the question object
     */
    public static Question fromJSONObject(String raw) {
        try {
            // if this raises an error, there is a good chance that you need to convert the argument to a string before passing
            JSONObject obj = new JSONObject(raw);
            return new Question(obj.getInt("difficulty"), obj.getString("question"), obj.getString("questionPrefix"), obj.getString("answer"));
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
