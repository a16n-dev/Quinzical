package quinzical.model;

import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;

public class MultiplayerGame extends QuinzicalGame {
    private ArrayList<Member> members;
    private boolean isHost;
    
    private Integer code;
    private Member local;
    private boolean hasStarted;
    private boolean roundOver;
    private SimpleBooleanProperty mayProgress;
    
    private static MultiplayerGame instance;

    private MultiplayerGame() {
        members = new ArrayList<Member>();
        hasStarted = false;
        roundOver = true;
    }
    
    public static MultiplayerGame getInstance() {
        return instance;
    }
    
    public static void startGame(Integer code, Member user) {
        instance = new MultiplayerGame();
        instance.code = code;
        instance.isHost = user.isHost();
        instance.local = user;
        instance.members.add(instance.local);
        instance.mayProgress = new SimpleBooleanProperty(instance.roundOver && instance.isHost);
    }

    public void updateMembers(ArrayList<Member> members) {
        this.members = members;
    }
    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }
    public boolean isHost() {
        return isHost;
    }
    public int getLocalScore() {
        return local.getScore();
    }
    public void adjustLocalScore(int score) {
        local.setScore(local.getScore() + score);
    }
    public boolean hasStarted() {
        return hasStarted;
    }
    public void start() {
        hasStarted = true;
    }

    public void setCurrentQuestion(Question question) {
        super.setCurrentQuestion(question);
    }
    public void setRoundOver(boolean roundOver) {
        this.roundOver = roundOver;
        mayProgress.setValue(roundOver && isHost);
    }
    public SimpleBooleanProperty mayProgress() {
        return mayProgress;
    }
}

