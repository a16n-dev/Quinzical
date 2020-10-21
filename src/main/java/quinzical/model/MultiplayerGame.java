package quinzical.model;

import java.util.ArrayList;

public class MultiplayerGame extends QuinzicalGame {
    private ArrayList<Member> members;
    private String host;
    
    private Integer code;
    private Member local;
    
    private static MultiplayerGame instance;

    private MultiplayerGame() {
        members = new ArrayList<Member>();
    }
    
    public static MultiplayerGame getInstance() {
        return instance;
    }
    
    public static void startGame(Integer code, boolean isHost) {
        instance = new MultiplayerGame();
        instance.code = code;
        instance.local = new Member(User.getInstance().getAvatar(), 0, isHost);
        instance.members.add(instance.local);
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
    public int getLocalScore() {
        return local.getScore();
    }
    public void adjustLocalScore(int score) {
        local.setScore(local.getScore() + score);
    }
}

class Member {
    private Avatar avatar;
    private int score;
    private boolean isHost;

    public Member(Avatar avatar, int score) {
        this.avatar = avatar;
        this.score = score;
        this.isHost = false;
    }
    public Member(Avatar avatar, int score, boolean isHost) {
        this(avatar, score);
        this.isHost = isHost;
    }

    public Avatar getAvatar() {
        return avatar;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public boolean isHost() {
        return isHost;
    }
}