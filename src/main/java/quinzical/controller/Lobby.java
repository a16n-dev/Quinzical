package quinzical.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import quinzical.model.MultiplayerGame;
import quinzical.model.Question;
import quinzical.avatar.Accessory;
import quinzical.avatar.Eyes;
import quinzical.avatar.Hat;
import quinzical.model.Avatar;
import quinzical.model.Member;
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
        // this has to be bound (timer from answer screen too)
        fxNextQuestion.visibleProperty().bind(game.mayProgress());
        System.out.println(game.mayProgress().getValue());
        System.out.println(game.isHost());

        connect = Connect.getInstance();
        connect.onMessage("NEXT_QUESTION", e -> {
            Question question = Question.fromJSONObject(e[0].toString());
            game.setCurrentQuestion(question);
            Router.show(View.MULTIPLAYER_ANSWER_SCREEN, false);
            game.start();
        });
        connect.onMessage("ROUND_OVER", e -> {
            game.setRoundOver(true);
            System.out.println("hmm");
            System.out.println(game.mayProgress().getValue());
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
            try {
                JSONObject obj = new JSONObject(args[0].toString());
                String username = obj.getString("username");
                int score = obj.getInt("score");

                setSubtitle(getUserIndex(username), "Answered: " + score);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        });
        connect.onMessage("GAME_OVER", e -> {
            System.out.println("ah yo man it's over");
        });

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
            if(game.hasStarted()){
                setSubtitle(i, "Score: " +  m.getScore());
            } else {
                setSubtitle(i, "");
            }
        }
    }

    private void renderSlot(int pos, Avatar avatar) {
        final int[] SIZE = { 220, 180, 140 };

        try {
            Field slotField = getClass().getDeclaredField("avatarSlot" + pos);

            StackPane container = (StackPane) slotField.get(this);

            // Map index to size
            int s = (int) Math.ceil((pos - 1.7) / 1.7);
            System.out.println(s);
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

    public void setSubtitle(int pos, String subtitleMessage) {
        try {
            Field subtitleField = getClass().getDeclaredField("avatarSubtitle" + pos);
            Label subTitle = (Label) subtitleField.get(this);

            subTitle.setText(subtitleMessage);
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setSpeechBubble(int pos, String text) {
        try {
            Field speechBubbleField = getClass().getDeclaredField("avatarSpeechBubble" + pos);
            Label speechBubble = (Label) speechBubbleField.get(this);

            speechBubble.setText(text);
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public int getUserIndex(String username) {
        for (int i = 0; i < game.getMembers().size(); i++) {
            Member m = game.getMembers().get(i);
            if (m.getUsername().equals(username)) {
                return i + 1;
            }
        }
        return -1;
    }
}
