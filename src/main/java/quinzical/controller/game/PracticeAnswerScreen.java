package quinzical.controller.game;

import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import quinzical.controller.View;
import quinzical.model.PracticeGame;
import quinzical.model.Question;
import quinzical.model.QuinzicalGame.Status;
import quinzical.util.Router;
import quinzical.util.TTS;

public class PracticeAnswerScreen extends BaseAnswerScreen {
    @FXML
    private Label fxFeedback;
    @FXML
    private Label fxAttemptCount;

    @FXML
    private AnchorPane fxBarGame;
    
    private PracticeGame game;

    @Override
    void onLoad() {
        game = PracticeGame.getInstance();

        game.setStatus(Status.ANSWERING);

        fxAttemptCount.textProperty().bind(Bindings.convert(game.getRemainingAttempts()));

        fxBarGame.setVisible(false);
        fxBarGame.setManaged(false);
    }

    @Override
    Question setQuestion() {
        game.setRandomQuestion();
        return game.getCurrentQuestion();
    }

    @Override
    void onCorrectAnswer(Question question) {
        TTS.getInstance().speak("correct");
        game.addStreak();
        game.setStatus(Status.SUCCESS);
        showAlert(onFinished);
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
            game.clearStreak();
            game.setStatus(Status.FAILURE);
            showAlert(onFinished);

            return;
        }
        game.addAttempt();
        fxFeedback.setVisible(true);
    }

    @Override
    void forceWrongAnswer(Question question, boolean wasTimerExpire)  {
        if(wasTimerExpire){
            game.setStatus(Status.OUT_OF_TIME);
        } else {
            game.setStatus(Status.SKIP);
        }
        showAlert(onFinished);
    }

    private EventHandler<Event> onFinished = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            Router.show(View.PRACTICE_ANSWER_SCREEN, false);
        }
    };
}
