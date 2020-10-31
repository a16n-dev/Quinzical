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

/**
 * Controller for the lobby of the multiplayer game. This class contains most of
 * the networking of the multiplayer game mode.
 */
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

        // on next question, refresh the members and then go to the question screen
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
            } catch (JSONException e) {
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
        // update the score of a member who has answered the question
        connect.onMessage("SCORE_UPDATE", args -> {
            try {
                JSONObject obj = new JSONObject(args[0].toString());
                String username = obj.getString("username");
                int score = obj.getInt("score");
                Answer status = Answer.valueOf(obj.getString("status"));
                String answer = obj.getString("answer");

                setUserAnswerStatus(username, score, status, answer);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        connect.onMessage("GAME_OVER", e -> {
        });
        // make sure displays are bound so that the elements reflect the scores of the
        // members
        bindAnswerDisplays(game.getMembers());
        renderAvatars(game.getMembers());
    }

    /**
     * Take a list of members as JSON and update the display of the lobby to use the
     * new list of members. This is necessary because the backend will often change
     * details about the list of members and return it as a list.
     * 
     * @param args
     */
    public void processLobbyUpdate(Object... args) {
        try {
            JSONObject obj = new JSONObject(args[0].toString());
            JSONArray membersRaw = obj.getJSONArray("members");

            ArrayList<Member> members = new ArrayList<Member>();
            for (int i = 0; i < membersRaw.length(); i++) {
                members.add(Member.fromJSONObject(membersRaw.getString(i)));
            }
            MultiplayerGame.getInstance().updateMembers(members);
            Router.show(View.LOBBY, false); // reload screen
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * When the user - must be host - clicks to go to the next question, broadcast
     * this
     */
    public void onNextQuestion() {
        JSONObject json = new JSONObject();
        try {
            json.put("code", game.getCode());
        } catch (JSONException err) {
            err.printStackTrace();
        }
        connect.emit("NEXT_QUESTION", json);
    }

    /**
     * Render the avatars of the players in the lobby
     * 
     * @param players the players
     */
    public void renderAvatars(List<Member> players) {
        for (int i = 1; i <= players.size(); i++) {
            Member m = players.get(i - 1);

            renderSlot(i, m.getAvatar());
            setTitle(i, m.getUsername());
        }
    }

    /**
     * Render a specific avatar within the lobby
     * 
     * @param pos    the position in the array
     * @param avatar the avatar to set to the location on the board
     */
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

    /**
     * Set the title of a given avatar slot. This is supposed to represnt the given
     * user's username.
     * 
     * @param pos          the position of the slot
     * @param titleMessage the username of the user in the slot
     */
    private void setTitle(int pos, String titleMessage) {
        try {
            Field titleField = getClass().getDeclaredField("avatarTitle" + pos);
            Label title = (Label) titleField.get(this);

            title.setText(titleMessage);
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bind the various elements of the avatar slots to JavaFX beans so that they
     * will update when the server returns information about the answers of the
     * different users.
     * 
     * @param members the members of the game
     */
    public void bindAnswerDisplays(List<Member> members) {
        try {
            for (int i = 1; i <= members.size(); i++) {
                Member member = members.get(i - 1);
                Label bubble = (Label) getClass().getDeclaredField("avatarSpeechBubble" + i).get(this);
                bubble.textProperty().bind(member.getAnswer());
                // don't show answer bubbles before we have answered a question
                if (game.hasStarted()) {
                    bubble.setVisible(true);
                }

                Label subtitle = (Label) getClass().getDeclaredField("avatarSubtitle" + i).get(this);
                subtitle.textProperty()
                        .bind(Bindings.concat(new SimpleStringProperty("Score: "), member.getScore().asString()));
            }
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the answer status of a given user to a value returned from the server.
     * Sets using the bean interface so that the display updates.
     * 
     * @param username the user's username
     * @param score    the new score of the user
     * @param status   the new answer status of the user (e.g. correct)
     * @param answer   the answer the user typed
     */
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
