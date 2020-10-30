package quinzical.model;

import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * MultiplayerGame class
 */
public class MultiplayerGame extends QuinzicalGame {
    private ArrayList<Member> members;
    private boolean isHost;

    private Integer code;
    private Member local;
    private boolean hasStarted;
    private boolean roundOver;
    private SimpleBooleanProperty mayProgress;

    private static MultiplayerGame instance;

    /**
     * Singleton constructor
     */
    private MultiplayerGame() {
        members = new ArrayList<Member>();
        hasStarted = false;
        roundOver = true;
    }

    /**
     * 
     * @return the singleton instance
     */
    public static MultiplayerGame getInstance() {
        return instance;
    }

    /**
     * Initialise the game.
     * 
     * @param code the public code of the game. Should be set to the value provided
     *             by the backend.
     * @param user your current logged in user
     */
    public static void startGame(Integer code, Member user) {
        instance = new MultiplayerGame();
        instance.code = code;
        instance.isHost = user.isHost();
        instance.local = user;
        instance.members.add(instance.local);
        instance.mayProgress = new SimpleBooleanProperty(instance.roundOver && instance.isHost);
    }

    /**
     * Update the members list of the lobby
     * 
     * @param members
     */
    public void updateMembers(ArrayList<Member> members) {
        this.members = members;
    }

    /**
     * 
     * @return the members of the lobby
     */
    public ArrayList<Member> getMembers() {
        return members;
    }

    /**
     * Set the public code of the multiplayer game
     * 
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Get the public code of the multiplayer game.
     * 
     * @return
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 
     * @return whether the logged in user is the host of the game
     */
    public boolean isHost() {
        return isHost;
    }

    /**
     * 
     * @return the logged in user's score in the game
     */
    public int getLocalScore() {
        return local.getScore().getValue();
    }

    /**
     * Set the score of the logged in user
     * 
     * @param score
     */
    public void adjustLocalScore(int score) {
        local.setScore(local.getScore().getValue() + score);
    }

    /**
     * 
     * @return whether the game has started
     */
    public boolean hasStarted() {
        return hasStarted;
    }

    /**
     * Start the game
     */
    public void start() {
        hasStarted = true;
    }

    /**
     * Set the current question
     * 
     * @param question the new question
     */
    public void setCurrentQuestion(Question question) {
        super.setCurrentQuestion(question);
    }

    /**
     * 
     * @param roundOver the value to set to
     */
    public void setRoundOver(boolean roundOver) {
        this.roundOver = roundOver;
        mayProgress.setValue(roundOver && isHost);
    }

    /**
     * Get whether the user may progress the state of the game. This is only true if
     * the user is the host and all of the users have answered the current question.
     * 
     * @return
     */
    public SimpleBooleanProperty mayProgress() {
        return mayProgress;
    }
}
