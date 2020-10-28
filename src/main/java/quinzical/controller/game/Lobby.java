package quinzical.controller.game;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import quinzical.controller.View;
import quinzical.model.Answer;
import quinzical.model.Avatar;
import quinzical.model.Member;
import quinzical.model.MultiplayerGame;
import quinzical.model.Question;
import quinzical.util.AvatarFactory;
import quinzical.util.Connect;
import quinzical.util.Modal;
import quinzical.util.Router;

public class Lobby {
    @FXML
    private Label fxCode;
    @FXML
    private Button fxNextQuestion;

    @FXML
    private StackPane avatarSlot1;
    @FXML
    private StackPane avatarSlot2;
    @FXML
    private StackPane avatarSlot3;
    @FXML
    private StackPane avatarSlot4;
    @FXML
    private StackPane avatarSlot5;

    @FXML
    private Label avatarTitle1;
    @FXML
    private Label avatarTitle2;
    @FXML
    private Label avatarTitle3;
    @FXML
    private Label avatarTitle4;
    @FXML
    private Label avatarTitle5;

    @FXML
    private Label avatarSubtitle1;
    @FXML
    private Label avatarSubtitle2;
    @FXML
    private Label avatarSubtitle3;
    @FXML
    private Label avatarSubtitle4;
    @FXML
    private Label avatarSubtitle5;

    @FXML
    private Label avatarSpeechBubble1;
    @FXML
    private Label avatarSpeechBubble2;
    @FXML
    private Label avatarSpeechBubble3;
    @FXML
    private Label avatarSpeechBubble4;
    @FXML
    private Label avatarSpeechBubble5;

    private MultiplayerGame game;
    private Connect connect;

    public void initialize() {
        game = MultiplayerGame.getInstance();

        fxCode.setText(Integer.toString(game.getCode()));

        fxNextQuestion.setText(game.hasStarted() ? "Next question" : "Start game");
        fxNextQuestion.visibleProperty().bind(game.mayProgress());

        connect = Connect.getInstance();
        connect.onMessage("NEXT_QUESTION", args -> {
            try {
                JSONObject obj = new JSONObject(args[0].toString());
                Question question = Question.fromJSONObject(obj.getString("question"));
                JSONArray membersRaw = obj.getJSONArray("members");
            
                ArrayList<Member> members = new ArrayList<Member>();
                for (int i = 0; i < membersRaw.length(); i++) {
                    members.add(Member.fromJSONObject(membersRaw.getString(i)));
                }
                MultiplayerGame.getInstance().updateMembers(members);
    
                game.setCurrentQuestion(question);
                Router.show(View.MULTIPLAYER_ANSWER_SCREEN, false);
                game.start();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        });
        connect.onMessage("ROUND_OVER", e -> {
            game.setRoundOver(true);
        });
        connect.onMessage("LOBBY_JOINED", args -> {
            processLobbyUpdate(args);
        });
        connect.onMessage("LOBBY_LEFT", args -> {
            processLobbyUpdate(args);
        });
        connect.onMessage("LOBBY_CLOSED", args -> {
            Router.show(View.MAIN_MENU);
            Modal.alert("Lobby closed", "The lobby has been closed by the host.");
        });
        connect.onMessage("SCORE_UPDATE", args -> {
            System.out.println("updating the score of a user");
            try {
                JSONObject obj = new JSONObject(args[0].toString());
                String username = obj.getString("username");
                int score = obj.getInt("score");
                Answer status = Answer.valueOf(obj.getString("status"));
                String answer = obj.getString("answer");

                setUserAnswerStatus(username, score, status, answer);
                System.out.println("the score right now is: " + score);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        });
        connect.onMessage("GAME_OVER", e -> {
            System.out.println("ah yo man it's over");
        });

        bindAnswerDisplays(game.getMembers());
        renderAvatars(game.getMembers());
    }

    public void processLobbyUpdate(Object... args) {
        try {
            JSONObject obj = new JSONObject(args[0].toString());
            JSONArray membersRaw = obj.getJSONArray("members");
            
            ArrayList<Member> members = new ArrayList<Member>();
            for (int i = 0; i < membersRaw.length(); i++) {
                members.add(Member.fromJSONObject(membersRaw.getString(i)));
            }
            MultiplayerGame.getInstance().updateMembers(members);
            Router.show(View.LOBBY, false);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onNextQuestion() {
        JSONObject json = new JSONObject();
        try {
            json.put("code", game.getCode());
        } catch (JSONException err) {
            err.printStackTrace();
        }
        connect.emit("NEXT_QUESTION", json);
    }

    public void renderAvatars(List<Member> players) {
        // Sort stuff

        // Render
        for (int i = 1; i <= players.size(); i++) {
            Member m = players.get(i - 1);

            renderSlot(i, m.getAvatar());
            setTitle(i, m.getUsername());
        }
    }

    private void renderSlot(int pos, Avatar avatar) {
        final int[] SIZE = { 220, 180, 140 };

        try {
            Field slotField = getClass().getDeclaredField("avatarSlot" + pos);

            StackPane container = (StackPane) slotField.get(this);

            // Map index to size
            int s = (int) Math.ceil((pos - 1.7) / 1.7);
            AvatarFactory slot = new AvatarFactory(container, SIZE[s], false);

            slot.set(avatar);

        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setTitle(int pos, String titleMessage) {
        try {
            Field titleField = getClass().getDeclaredField("avatarTitle" + pos);
            Label title = (Label) titleField.get(this);

            title.setText(titleMessage);
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void bindAnswerDisplays(List<Member> members) {
        try {
            for (int i = 1; i <= members.size(); i++) {
                Member member = members.get(i - 1);
                Label bubble = (Label) getClass().getDeclaredField("avatarSpeechBubble" + i).get(this);
                bubble.textProperty().bind(member.getAnswer());
    
                Label subtitle = (Label) getClass().getDeclaredField("avatarSubtitle" + i).get(this);
                subtitle.textProperty().bind(Bindings.concat(new SimpleStringProperty("Score: "), member.getScore().asString()));
            }
        }
        catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setUserAnswerStatus(String username, int score, Answer status, String answer) {
        for (Member member : game.getMembers()) {
            if (member.getUsername().equals(username)) {
                member.setScore(score);
                member.setStatus(status);
                member.setAnswer(answer);
                return;
            }
        }
    }
}