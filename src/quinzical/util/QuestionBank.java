package quinzical.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import quinzical.model.Question;

/**
 * Helper class which serves as a link between the IOManager reading questions
 * from file and the controller class that wants to access specific questions
 * This class provides lists of questions and handles the 'random selection' of
 * questions
 */
public class QuestionBank {

    private HashMap<String, ArrayList<Question>> _questionBank;
    private Random _rand = new Random();

    private static QuestionBank instance;

    public static QuestionBank getInstance() {
        if (instance == null) {
            instance = new QuestionBank();
        }
        return instance;
    }

    // Private constructor
    private QuestionBank() {
        _questionBank = IOManager.loadQuestions(true);
    }

    /**
     * Gets a given amount of random questions from the specified category
     * 
     * @param categoryName    a name of a category - case sensitive. If the category
     *                        doesnt exist it will throw an error
     * @param amount          the number of questions to return.
     * @return a list of questions
     */
    public ArrayList<Question> getRandomQuestions(String categoryName, int amount, boolean allowDuplicates)
            throws IllegalArgumentException {
        // Get arraylist of questions

        // Check if category exists
        if (!_questionBank.containsKey(categoryName)) {
            throw new IllegalArgumentException("Category does not exist");
        }
        // Get the list of questions and create a copy of the array so the original is
        // not modified
        ArrayList<Question> questionList = new ArrayList<Question>(_questionBank.get(categoryName));

        ArrayList<Question> results = new ArrayList<Question>();

        for (int i = 0; i < amount; i++) {
            int difficulty = i + 1;
            // Get all questions with difficulty i
            List<Question> filteredQs = questionList.stream().filter(p -> p.getDifficulty() == difficulty)
                    .collect(Collectors.toList());

            int index = _rand.nextInt(filteredQs.size());
            // Get a random question and remove it from the list

            results.add(filteredQs.get(index));

        }

        return results;
    }

    /**
     * Gets a given amount of random questions from the specified category
     * 
     * @param categoryName    a name of a category - case sensitive. If the category
     *                        doesnt exist the method will throw an error
     * @param amount          the number of questions to return.
     * @param allowDuplicates specify if questions can be repeated or if all
     *                        questions must be unique
     * @return
     */
    public ArrayList<String> getRandomCategories(int amount, boolean allowDuplicates) throws IllegalArgumentException {
        // Get arraylist of categories

        // Get the list of categories and create a copy of the array so the original is
        // not modified
        ArrayList<String> categoryList = getCategories();

        // Throw an error if there are not enough unique questions to match the amount
        // specified
        if (categoryList.size() < amount && !allowDuplicates) {
            throw new IllegalArgumentException(categoryList + " only has " + categoryList.size() + " questions but "
                    + amount + " was requested. Either add more questions to this category or allow duplicates");
        }

        ArrayList<String> results = new ArrayList<String>();

        for (int i = 0; i < amount; i++) {
            int index = _rand.nextInt(categoryList.size());
            // Get a random question and remove it from the list

            if (allowDuplicates) {
                results.add(categoryList.get(index));
            } else {
                // Remove question from list so we dont get duplicates
                results.add(categoryList.remove(index));
            }
        }

        return results;
    }

    /**
     * Overload for getRandomCategories to allow it to only select categories with a
     * minimum number of questions
     */
    public ArrayList<String> getRandomCategories(int amount, boolean allowDuplicates, int minimumQuestions)
            throws IllegalArgumentException {
        // Get arraylist of categories

        // Get the list of categories and create a copy of the array so the original is
        // not modified
        List<String> categoryList = getCategories().stream()
                .filter(p -> _questionBank.get(p).size() >= minimumQuestions).collect(Collectors.toList());

        // Throw an error if there are not enough unique questions to match the amount
        // specified
        if (categoryList.size() < amount && !allowDuplicates) {
            throw new IllegalArgumentException(categoryList + " only has " + categoryList.size() + " questions but "
                    + amount + " was requested. Either add more questions to this category or allow duplicates");
        }

        ArrayList<String> results = new ArrayList<String>();

        for (int i = 0; i < amount; i++) {
            int index = _rand.nextInt(categoryList.size());
            // Get a random question and remove it from the list

            if (allowDuplicates) {
                results.add(categoryList.get(index));
            } else {
                // Remove question from list so we dont get duplicates
                results.add(categoryList.remove(index));
            }
        }

        return results;
    }

    /**
     * @return a list of all categories
     */
    public ArrayList<String> getCategories() {
        return new ArrayList<String>(_questionBank.keySet());
    }

    public ArrayList<Question> getQuestionsByCategory(String category){
        return _questionBank.get(category);
    }
}
