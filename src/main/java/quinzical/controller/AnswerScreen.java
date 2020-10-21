package quinzical.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import quinzical.model.Game;
import quinzical.model.Question;
import quinzical.model.QuinzicalGame.Status;
import quinzical.util.Router;
import quinzical.util.TTS;

public class AnswerScreen extends BaseAnswerScreen {
    @FXML
    private Label fxCategoryName;
    @FXML
    private Label fxValueText;
    @FXML
    private HBox fxBarPractice;

    private Game game;

    @Override
    void onLoad() {
        game = Game.getInstance();
        
        game.setStatus(Status.ANSWERING);

        fxCategoryName.setText(game.getCurrentCategory());
        fxValueText.setText(Integer.toString(game.getCurrentQuestion().getValue()));

        fxBarPractice.setVisible(false);
        fxBarPractice.setManaged(false);
    }

    @Override
    Question setQuestion() {
        return game.getCurrentQuestion();
    }

    @Override
    void onCorrectAnswer(Question question) {
        TTS.getInstance().speak("That is correct");
        game.addScore(question.getValue());
        game.setStatus(Status.SUCCESS);
        showAlert(onFinished);
    }

    @Override
    void onWrongAnswer(Question question) {
        TTS.getInstance().speak("That is incorrect");
        TTS.getInstance().speak("The correct answer was " + question.getAnswer());
        game.setStatus(Status.FAILURE);
        showAlert(onFinished);
    }

    @Override
    void forceWrongAnswer(Question question, boolean wasTimerExpire) {
        TTS.getInstance().speak("The correct answer was " + question.getAnswer());
        if (wasTimerExpire) {
            game.setStatus(Status.OUT_OF_TIME);
        } else {
            game.setStatus(Status.SKIP);
        }
        showAlert(onFinished);
    }

    private EventHandler<Event> onFinished = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            // Navigate to the 'reward screen' only if all questions are answered
            if (game.getRemainingQuestions() == 0) {
                Router.show(View.REWARD_SCREEN);
                game.setStatus(Status.REWARD);
            } else {
                Router.show(View.GAME_BOARD, false);
                game.setStatus(Status.GAME_BOARD);
            }
        }
    };
}
