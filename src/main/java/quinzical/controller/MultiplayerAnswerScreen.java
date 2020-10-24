package quinzical.controller;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import javafx.event.Event;
import javafx.event.EventHandler;
import quinzical.model.MultiplayerGame;
import quinzical.model.Question;
import quinzical.model.QuinzicalGame.Status;
import quinzical.util.Connect;
import quinzical.util.Router;
import quinzical.util.Timer;

public class MultiplayerAnswerScreen extends BaseAnswerScreen {
    private Connect connect;
    private MultiplayerGame game;
    
    @Override
    void onLoad() {
        connect = Connect.getInstance();
        game = MultiplayerGame.getInstance();
        game.setStatus(Status.ANSWERING);
        game.setRoundOver(false);
        
        // connect.onMessage("ROUND_OVER", e -> {
        //     game.setRoundOver(true);
        // });
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
        connect.emit("RESULT", createResultJSON(game.getCode(), game.getLocalScore()));
        Router.show(View.LOBBY, false);
    }
    
    @Override
    void onWrongAnswer(Question question) {
        connect.emit("RESULT", createResultJSON(game.getCode(), game.getLocalScore()));
        game.setStatus(Status.FAILURE);
        showAlert(onFinished);
        Router.show(View.LOBBY, false);
    }
    
    @Override
    void forceWrongAnswer(Question question, boolean wasTimerExpire) {
        connect.emit("RESULT", createResultJSON(game.getCode(), game.getLocalScore()));
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

    private static JSONObject createResultJSON(int code, int score) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("code", code);
            obj.put("score", score);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}