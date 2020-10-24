package quinzical.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Member {
    private Avatar avatar;
    private int score;
    private boolean isHost;
    private String username;

    private Answer status;
    private String answer;

    public Member(Avatar avatar, int score, String username) {
        this.avatar = avatar;
        this.score = score;
        this.username = username;
        this.isHost = false;
    }
    public Member(Avatar avatar, int score, String username, boolean isHost) {
        this(avatar, score, username);
        this.isHost = isHost;
    }
    public Member(Avatar avatar, int score, String username, boolean isHost, Answer status, String answer) {
        this(avatar, score, username, isHost);
        this.status = status;
        this.answer = answer;
    }

    public Avatar getAvatar() {
        return avatar;
    }
    public int getScore() {
        return score;
    }
    public String getUsername() {
        return username;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public boolean isHost() {
        return isHost;
    }
    public Answer getStatus() {
        return status;
    }
    public String getAnswer() {
        return answer;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("avatar", avatar.toJSONObject());
            obj.put("score", score);
            obj.put("isHost", isHost);
            obj.put("username", username == null ? "No name" : username);
            obj.put("status", status.toString());
            obj.put("answer", answer);
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