package quinzical.model;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class representing a member of a multiplayer game
 */
public class Member {
    private Avatar avatar;
    private SimpleIntegerProperty score;
    private boolean isHost;
    private String username;

    private Answer status;
    private SimpleStringProperty answer;

    /**
     * Constructor for non-hosts
     * 
     * @param avatar   the user's avatar
     * @param score    the user's current score in the game
     * @param username the user's username
     */
    public Member(Avatar avatar, int score, String username) {
        this.avatar = avatar;
        this.score = new SimpleIntegerProperty(score);
        this.username = username;
        this.isHost = false;
        this.status = Answer.ANSWERING;
        this.answer = new SimpleStringProperty("");
    }

    /**
     * Constructor to assign the user as a host
     * 
     * @param avatar
     * @param score
     * @param username
     * @param isHost
     */
    public Member(Avatar avatar, int score, String username, boolean isHost) {
        this(avatar, score, username);
        this.isHost = isHost;
    }

    /**
     * Constructor for users who are already in the game (e.g. someone who joins
     * last)
     * 
     * @param avatar
     * @param score
     * @param username
     * @param isHost
     * @param status
     * @param answer
     */
    public Member(Avatar avatar, int score, String username, boolean isHost, Answer status, String answer) {
        this(avatar, score, username, isHost);
        this.status = status;
        this.answer.setValue(answer);
    }

    /**
     * 
     * @return the member's avatar
     */
    public Avatar getAvatar() {
        return avatar;
    }

    /**
     * 
     * @return the user's score
     */
    public SimpleIntegerProperty getScore() {
        return score;
    }

    /**
     * 
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the user's score
     * 
     * @param score
     */
    public void setScore(int score) {
        this.score.setValue(score);
    }

    /**
     * 
     * @return whether the user is the host
     */
    public boolean isHost() {
        return isHost;
    }

    /**
     * 
     * @return the answer status of the user
     */
    public Answer getStatus() {
        return status;
    }

    /**
     * Set the answer status of the user
     * 
     * @param status
     */
    public void setStatus(Answer status) {
        this.status = status;
    }

    /**
     * 
     * @return the user's answer
     */
    public SimpleStringProperty getAnswer() {
        return answer;
    }

    /**
     * 
     * @param answer the user's answer
     */
    public void setAnswer(String answer) {
        this.answer.setValue(answer);
    }

    /**
     * 
     * @return the member as a JSONObject
     */
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("avatar", avatar.toJSONObject());
            obj.put("score", score.getValue());
            obj.put("isHost", isHost);
            obj.put("username", username == null ? "No name" : username);
            obj.put("status", status.toString());
            obj.put("answer", answer.getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * Static method to create a Member object from a JSONObject represnting a
     * member
     * 
     * @param raw the JSONObject as a string
     * @return the member object
     */
    public static Member fromJSONObject(String raw) {
        try {
            // if this raises an error, there is a good chance that you need to convert the
            // argument to a string before passing
            JSONObject obj = new JSONObject(raw);

            return new Member(Avatar.fromJSONObject(obj.getString("avatar")), obj.getInt("score"),
                    obj.getString("username"), obj.getBoolean("isHost"), Answer.valueOf(obj.getString("status")),
                    obj.getString("answer"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}