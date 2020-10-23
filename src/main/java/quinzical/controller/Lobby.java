package quinzical.controller;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import quinzical.model.MultiplayerGame;
import quinzical.model.Question;
import quinzical.util.Connect;
import quinzical.util.Router;

public class Lobby {
    @FXML
    private Label fxCode;
    @FXML
    private Button fxNextQuestion;

    private MultiplayerGame game;
    private Connect connect;

    public void initialize() {
        game = MultiplayerGame.getInstance();

        fxCode.setText(Integer.toString(game.getCode()));

        fxNextQuestion.setText(game.hasStarted() ? "Next question" : "Start game");
        // this has to be bound (timer from answer screen too)
        fxNextQuestion.setVisible(game.mayProgress());
        
        connect = Connect.getInstance();
        connect.onMessage("NEXT_QUESTION", e -> {
            System.out.println("half ayo");
            Question question = Question.fromJSONObject(e[0].toString());
            game.setCurrentQuestion(question);
            System.out.println("ayo mate!");
            Router.show(View.MULTIPLAYER_ANSWER_SCREEN);
            System.out.println("aha matey");
        });
        connect.onMessage("ROUND_OVER", e -> {
            game.setRoundOver(true);
            fxNextQuestion.setVisible(game.mayProgress());
        });

        connect.onMessage("GAME_OVER", e -> {
            System.out.println("ah yo man it's over");
        });

        connect.onMessage("LOBBY_JOINED", e -> {
            
        });
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
}
