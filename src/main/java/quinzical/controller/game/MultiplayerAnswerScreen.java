package quinzical.controller.game;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
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
import quinzical.util.Timer;

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
        connect.emit("RESULT", createResultJSON(game.getCode(), game.getLocalScore(), Answer.CORRECT, fxInput.getText()));
        Router.show(View.LOBBY, false);
    }
    
    @Override
    void onWrongAnswer(Question question) {
        connect.emit("RESULT", createResultJSON(game.getCode(), game.getLocalScore(), Answer.INCORRECT, fxInput.getText()));
        game.setStatus(Status.FAILURE);
        showAlert(onFinished);
        Router.show(View.LOBBY, false);
    }
    
    @Override
    void forceWrongAnswer(Question question, boolean wasTimerExpire) {
        connect.emit("RESULT", createResultJSON(game.getCode(), game.getLocalScore(), Answer.INCORRECT, fxInput.getText()));
        game.setStatus(Status.FAILURE);
        showAlert(onFinished);
        Router.show(View.LOBBY, false);
    }

    private EventHandler<Event> onFinished = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            // Navigate to the 'reward screen' only if all questions are answered
        }
    };

    private static JSONObject createResultJSON(int code, int score, Answer status, String answer) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("code", code);
            obj.put("score", score);
            obj.put("status", status.toString());
            obj.put("answer", answer);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    void remove(int i) {

    }
}