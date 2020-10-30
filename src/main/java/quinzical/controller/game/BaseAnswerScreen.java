package quinzical.controller.game;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import quinzical.App;
import quinzical.App.GameState;
import quinzical.controller.View;
import quinzical.controller.component.GameButton;
import quinzical.model.Answer;
import quinzical.model.PracticeGame;
import quinzical.model.Question;
import quinzical.model.User;
import quinzical.util.AvatarFactory;
import quinzical.util.Macron;
import quinzical.util.Modal;
import quinzical.util.TTS;
import quinzical.util.Timer;

/**
 * A base class to act as the controller for the answer screen
 */
public abstract class BaseAnswerScreen {

    @FXML
    private Label fxHint;
    @FXML
    private Label fxPrefix;
    @FXML
    private Label fxFeedback;
    @FXML
    private TextField fxInput;
    @FXML
    private Label fxMacronLetter;
    @FXML
    private VBox fxMacronPopup;
    @FXML
    private ProgressBar fxProgressRight;
    @FXML
    private ProgressBar fxProgressLeft;
    @FXML
    private Label fxProgressLabel;
    @FXML
    private GameButton fxSubmit;
    @FXML
    private StackPane avatarContainer;

    private boolean hasTypoed;

    private Question question;

    private Timer timer;

    private boolean isSubmitted;

    public void initialize() {

        isSubmitted = false;
        hasTypoed = false;

        // Show avatar
        AvatarFactory avatar = new AvatarFactory(avatarContainer);
        avatar.set(User.getInstance().getAvatar());

        onLoad();
        question = setQuestion();

        fxSubmit.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            return fxInput.textProperty().get().length() == 0;
        }, fxInput.textProperty()));

        fxHint.setText(capitalise(question.getHint()));
        fxPrefix.setText(capitalise(question.getPrefix()));

        TTS.getInstance().speak(question.getHint());
        Macron.getInstance().bind(fxInput, fxMacronLetter, fxMacronPopup);

        timer = Timer.getInstance();
        timer.set(fxProgressLabel, fxProgressLeft, fxProgressRight, 30);
        timer.start(e -> {
            forceWrongAnswer(question, true);
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fxInput.requestFocus();
            }
        });
    }

    /**
     * The JavaFX initialize method is already defined in this class, so the onLoad
     * method is provided for subclasses to use instead.
     */
    abstract void onLoad();

    /**
     * Set the question of the answer screen
     * @return the question
     */
    abstract Question setQuestion();

    /**
     * Method to be implemented by subclass to handle the user entering the correct
     * answer
     * 
     * @param question the question that was answered correctly
     */
    abstract void onCorrectAnswer(Question question);

    /**
     * Method to be implemented by subclass to handle the user entering the
     * incorrect answer
     * 
     * @param question the question that was answered incorrectly
     */
    abstract void onWrongAnswer(Question question);

    /**
     * Method to be implemented by subclass to handle when the user is forced to be
     * wrong, either by the timer running out or by selecting dont know
     * 
     * @param question       the question
     * @param wasTimerExpire if the forced answer was due to the timer running out
     */
    abstract void forceWrongAnswer(Question question, boolean wasTimerExpire);

    /**
     * Shows an alert giving the user feedback on how they did for a question
     * 
     * @param event the event to be run once the user dismisses the alert
     */
    public void showAlert(EventHandler<Event> event) {
        Modal.show(View.MODAL_ANSWER_FEEDBACK, event);
        timer.stop();
    }

    /**
     * Captialises the first letter of a given string
     * 
     * @param s the string to captialise
     * @return the string with the first letter captialised
     */
    private String capitalise(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * Handle the user submitting their answer
     */
    @FXML
    public void onSubmit() {

        if (fxInput.textProperty().get().length() == 0) {
            // If input is empty do nothing
            return;
        } else if (isSubmitted) {
            // if answer has already been submitted then hide modal and move on
            Modal.hide();
            return;
        }

        // if user is in practice mode, only mark question as submitted if user is
        // submitting their 3rd attempt
        if (App.getState() == GameState.PRACTICE) {
            if (PracticeGame.getInstance().getRemainingAttempts().get() == 1) {
                isSubmitted = true;
            }
        } else {
            isSubmitted = true;
        }

        String userAnswer = fxInput.getText();
        Answer answer = question.checkAnswer(userAnswer);

        if (answer == Answer.CORRECT) {
            onCorrectAnswer(question);
        } else if (answer == Answer.INCORRECT) {
            onWrongAnswer(question);
        } else if (answer == Answer.TYPO) {
            if (hasTypoed) {
                onWrongAnswer(question);
            }
            else {
                isSubmitted = false;
                hasTypoed = true;
                TTS.getInstance().speak("Typo");
                fxFeedback.setVisible(true);
                fxFeedback.setText("Typo");
            }
        }
    }

    /**
     * Handle the user pressing don't know
     */
    @FXML
    public void onUnsure() {
        forceWrongAnswer(question, false);
    }

    /**
     * Repeat the spoken clue to the user
     */
    @FXML
    public void repeatClue() {
        TTS.getInstance().speak(question.getHint());
    }
}
