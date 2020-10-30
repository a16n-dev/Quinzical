package quinzical.model;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a category of questions, which can either be set or user created.
 */
public class Category {

    /**
     * A list of questions in the category
     */
    private HashMap<String, Question> questions;

    /**
     * Represents if this category is created by the user or is a default category
     */
    private boolean userCreated;

    /**
     * Represents if this category is locked. If it is locked the user cant select
     * it until it is unlocked
     */
    private boolean locked;

    /**
     * The name of the category
     */
    private String name;

    /**
     * Constructor to call when the user wants to create a category. Any categories
     * created this way will be marked as user created.
     * 
     * @param categoryName
     */
    public Category(String categoryName) {
        name = categoryName;
        questions = new HashMap<String, Question>();
        userCreated = true;
        locked = false;
    }

    /**
     * Constructor to be called when questions are being loaded in (not user
     * created)
     * 
     * @param categoryName
     * @param isLocked
     */
    public Category(String categoryName, boolean isUserCreated) {
        name = categoryName;
        questions = new HashMap<String, Question>();
        userCreated = false;
        locked = false;
        userCreated = isUserCreated;
    }

    /**
     * Set the locked state of the category
     * 
     * @param b
     */
    public void setLocked(boolean b) {
        locked = b;
    }

    /**
     * 
     * @return whether the category is locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * 
     * @return whether it is user created or core
     */
    public boolean isUserCreated() {
        return userCreated;
    }

    /**
     * 
     * @return the name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the category
     * 
     * @param categoryName
     */
    public void setName(String categoryName) {
        name = categoryName;
    }

    /**
     * 
     * @return get the questions of the category
     */
    public List<Question> getQuestions() {
        return questions.values().stream().collect(Collectors.toList());
    }

    /**
     * Add a question to the category
     * 
     * @param q the question to add
     */
    public void addQuestion(Question q) {
        questions.put(q.getId(), q);
    }

    /**
     * Add a list of questions to the category
     * 
     * @param qList the list of questions
     */
    public void addQuestion(List<Question> qList) {
        for (Question q : qList) {
            questions.put(q.getId(), q);
        }
    }

    /**
     * Get a question from the list by id
     * 
     * @param id the id
     * @return the question
     */
    public Question getQuestionById(String id) {
        return questions.get(id);
    }

    /**
     * 
     * @return the ids of the questions
     */
    public List<String> getQuestionIds() {
        return questions.keySet().stream().collect(Collectors.toList());
    }

    /**
     * Remove a question from the category by id
     * 
     * @param id the id of the question to remove
     */
    public void removeQuestion(String id) {
        questions.remove(id);
    }

}
