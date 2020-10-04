package quinzical.controller;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import quinzical.model.PracticeGame;
import quinzical.model.Question;
import quinzical.util.Macron;
import quinzical.util.Router;
import quinzical.util.TTS;

public class PracticeAnswerScreenController {
    private PracticeGame game;
    private Question question;
    // public Button button;
    // public Button repeatHint;
    public Label hint;
    public Label hintLetter;
    public TextField input;
    public VBox wrapper;
    public Label labelPrefix;
    // public HBox answerContainer;
    // public Label header;

    @FXML
    public Label hintText;

    @FXML
    public Label AttemptCount;

    public void initialize() {
        game = PracticeGame.getInstance();
        game.setRandomQuestion();
        question = game.getCurrentQuestion();

        hintText.setText(question.getHint());
        AttemptCount.textProperty().bind(Bindings.convert(game.getRemainingAttempts()));
        labelPrefix.setText(question.getPrefix().substring(0, 1).toUpperCase() + question.getPrefix().substring(1));
        TTS.getInstance().speak(question.getHint());
        Macron.getInstance().bindToTextField(input, wrapper);
    }

    public void onSubmit(ActionEvent event) throws IOException {

        /*
         * Prompt user to try again after first and second incorrect attempts, show
         * first letter hint on third attempt, or show clue and answer after third
         * incorrect attempt.
         */
        boolean correct = question.checkAnswer(input.getText());

        if (correct) {
            TTS.getInstance().speak("correct");
            handleAnswer("Congratulations", "You answered correctly.", question.getAnswer());
            return;
        }

        TTS.getInstance().speak(question.getHint());
        hint.setText(correct ? "Correct"
                : "Incorrect."
                        + (game.getAttempts() == 1 ? "The first character is: " + question.getAnswer().charAt(0) : ""));
        game.addAttempt();
        if (game.getAttempts() == 3) {
            handleAnswer("Oops!", "Answer was: " + question.getAnswer(), question.getHint());
        }
    }

    private void handleAnswer(String title, String header, String content) {
        // Alert box to inform user of score and whether answer was correct
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

        Router.show(Views.PRACTICE_ANSWER_SCREEN);
    }

    public void onUnsure() {
        handleAnswer("Oops.", "Answer was: " + question.getAnswer(), question.getHint());
    }

    @FXML
    public void repeatClue() {
        TTS.getInstance().speak(question.getHint());
    }
}
