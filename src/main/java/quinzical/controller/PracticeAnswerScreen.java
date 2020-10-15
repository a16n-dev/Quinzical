package quinzical.controller;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import quinzical.model.PracticeGame;
import quinzical.model.Question;
import quinzical.util.Router;
import quinzical.util.TTS;

public class PracticeAnswerScreen extends BaseAnswerScreen {
    @FXML
    private Label fxFeedback;
    @FXML
    private Label fxAttemptCount;
    
    private PracticeGame game;

    @Override
    void onLoad() {
        game = PracticeGame.getInstance();
        fxAttemptCount.textProperty().bind(Bindings.convert(game.getRemainingAttempts()));
        fxAttemptCount.setVisible(true);
    }

    @Override
    Question setQuestion() {
        game.setRandomQuestion();
        return game.getCurrentQuestion();
    }

    @Override
    void onCorrectAnswer(Question question) {
        TTS.getInstance().speak("correct");
        showAlert("Congratulations", "You answered correctly.", question.getAnswer(), onFinished);
    }

    @Override
    void onWrongAnswer(Question question) {
        TTS.getInstance().speak("Incorrect");

        int timesAttempted = game.getAttempts();
        if (timesAttempted == 0) {
            fxFeedback.setText("Incorrect");
        }
        if (timesAttempted == 1) {
            TTS.getInstance().speak("The first character is: " + question.getAnswer().charAt(0));
            fxFeedback.setText("The first character is: " + question.getAnswer().charAt(0));
        }
        else if (timesAttempted == 2) {
            TTS.getInstance().speak("The correct answer was: " + question.getAnswer());
            showAlert("Oops!", "Answer was: " + question.getAnswer(), question.getHint(), onFinished);
            return;
        }
        game.addAttempt();
        fxFeedback.setVisible(true);
    }

    private EventHandler<DialogEvent> onFinished = new EventHandler<DialogEvent>() {
        @Override
        public void handle(DialogEvent event) {
            Router.show(Views.ANSWER_SCREEN, new PracticeAnswerScreen());
        }
    };
}
