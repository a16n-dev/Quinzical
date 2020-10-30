package quinzical.controller.game;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import quinzical.controller.View;
import quinzical.model.Answer;
import quinzical.model.MultiplayerGame;
import quinzical.model.Question;
import quinzical.model.QuinzicalGame.Status;
import quinzical.util.Connect;
import quinzical.util.Router;

public class MultiplayerAnswerScreen extends BaseAnswerScreen {
    @FXML
    private TextField fxInput;

    private Connect connect;
    private MultiplayerGame game;

    @Override
    void onLoad() {
        connect = Connect.getInstance();
        game = MultiplayerGame.getInstance();
        game.setStatus(Status.ANSWERING);
        game.setRoundOver(false);
    }

    @Override
    Question setQuestion() {
        return game.getCurrentQuestion();
    }

    @Override
    void onCorrectAnswer(Question question) {
        game.adjustLocalScore(question.getValue());
        game.setStatus(Status.SUCCESS);
        showAlert(onFinished);
        connect.emit("RESULT",
                createResultJSON(game.getCode(), game.getLocalScore(), Answer.CORRECT, fxInput.getText()));
        Router.show(View.LOBBY, false);
    }

    @Override
    void onWrongAnswer(Question question) {
        connect.emit("RESULT",
                createResultJSON(game.getCode(), game.getLocalScore(), Answer.INCORRECT, fxInput.getText()));
        game.setStatus(Status.FAILURE);
        showAlert(onFinished);
        Router.show(View.LOBBY, false);
    }

    @Override
    void forceWrongAnswer(Question question, boolean wasTimerExpire) {
        connect.emit("RESULT",
                createResultJSON(game.getCode(), game.getLocalScore(), Answer.INCORRECT, fxInput.getText()));
        game.setStatus(Status.FAILURE);
        showAlert(onFinished);
        Router.show(View.LOBBY, false);
    }

    /**
     * Event handler which triggers once the answer has been submitted and marked
     */
    private EventHandler<Event> onFinished = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {

        }
    };

    /**
     * Create a JSON object containing information about the user's answer which is
     * suitable for sending to the server.
     * 
     * @param code   the lobby code
     * @param score  the user's score
     * @param status the answer status of the last question
     * @param answer the string the user gave as their answer for the last question
     * @return
     */
    private static JSONObject createResultJSON(int code, int score, Answer status, String answer) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("code", code);
            obj.put("score", score);
            obj.put("status", status.toString());
            obj.put("answer", answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    void remove(int i) {

    }
}