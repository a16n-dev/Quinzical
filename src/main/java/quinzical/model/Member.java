package quinzical.model;

public class Member {
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
