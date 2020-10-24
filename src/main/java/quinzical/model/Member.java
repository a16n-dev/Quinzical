package quinzical.model;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Member {
    private Avatar avatar;
    private SimpleIntegerProperty score;
    private boolean isHost;
    private String username;

    private Answer status;
    private SimpleStringProperty answer;

    public Member(Avatar avatar, int score, String username) {
        this.avatar = avatar;
        this.score = new SimpleIntegerProperty(score);
        this.username = username;
        this.isHost = false;
        this.status = Answer.ANSWERING;
        this.answer = new SimpleStringProperty("");
    }
    public Member(Avatar avatar, int score, String username, boolean isHost) {
        this(avatar, score, username);
        this.isHost = isHost;
    }
    public Member(Avatar avatar, int score, String username, boolean isHost, Answer status, String answer) {
        this(avatar, score, username, isHost);
        this.status = status;
        this.answer.setValue(answer);
    }

    public Avatar getAvatar() {
        return avatar;
    }
    public SimpleIntegerProperty getScore() {
        return score;
    }
    public String getUsername() {
        return username;
    }
    public void setScore(int score) {
        this.score.setValue(score);
    }
    public boolean isHost() {
        return isHost;
    }
    public Answer getStatus() {
        return status;
    }
    public void setStatus(Answer status) {
        this.status = status;
    }
    public SimpleStringProperty getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer.setValue(answer);
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("avatar", avatar.toJSONObject());
            obj.put("score", score.getValue());
            obj.put("isHost", isHost);
            obj.put("username", username == null ? "No name" : username);
            obj.put("status", status.toString());
            obj.put("answer", answer.getValue());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Member fromJSONObject(String raw) {
        try {
            // if this raises an error, there is a good chance that you need to convert the argument to a string before passing
            JSONObject obj = new JSONObject(raw);
            
            return new Member(
                Avatar.fromJSONObject(obj.getString("avatar")),
                obj.getInt("score"),
                obj.getString("username"),
                obj.getBoolean("isHost"),
                Answer.valueOf(obj.getString("status")),
                obj.getString("answer")
            );
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}