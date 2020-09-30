package quinzical.model;

/**
 * A class representing a practice game being played by the user
 */
public class Practice extends QuinzicalGame{
    private String _category;

    private int _currentQuestionAttempts;

    /**
     * Picks a random question from the selected category
     */
    public void getRandomQuestion(){
        //Pick a random question
        setCurrentQuestion(questionBank.getRandomQuestions(_category, 1, false).get(0));
        //As question has changed also reset attempts
        _currentQuestionAttempts = 0;
    }

    /**
     * @return the number of attempts for the current question
     */
    public int getAttempts(){
        return _currentQuestionAttempts;
    }

    /**
     * Increases the number of attempts for the current question by 1 and then returns the total number of attempts
     */
    public int addAttempt(){
        _currentQuestionAttempts += 1;
        return _currentQuestionAttempts;
    }

    @Override
    public void newGame() {
        // TODO Auto-generated method stub

    }
}
