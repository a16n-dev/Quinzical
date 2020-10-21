package quinzical.controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import quinzical.App;
import quinzical.App.GameState;
import quinzical.components.GameButton;
import quinzical.model.Answer;
import quinzical.model.Game;
import quinzical.model.PracticeGame;
import quinzical.model.Question;
import quinzical.model.User;
import quinzical.model.QuinzicalGame.Status;
import quinzical.util.AvatarFactory;
import quinzical.util.Macron;
import quinzical.util.Modal;
import quinzical.util.TTS;
import quinzical.util.Timer;

public abstract class BaseAnswerScreen {
    private Question question;
    private Timer timer;
    private boolean isSubmitted;

    private EventHandler<Event> onFinished;

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

    public void initialize() {

        isSubmitted = false;

        fxSubmit.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            return fxInput.textProperty().get().length() == 0;
        }, fxInput.textProperty()));

        // Show avatar
        AvatarFactory avatar = new AvatarFactory(avatarContainer);
        avatar.set(User.getInstance().getAvatar());

        onLoad();
        question = setQuestion();

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

        // timer, category name, value text, random question for practise, attempt count
        // for practise
    }

    abstract void onLoad();

    abstract Question setQuestion();

    private boolean hasTypoed = false;

    private void submit() {

        String userAnswer = fxInput.getText();
        Answer answer = question.checkAnswer(userAnswer);

        if (answer == Answer.CORRECT) {
            onCorrectAnswer(question);
        } else if (answer == Answer.INCORRECT) {
            onWrongAnswer(question);
        } else {
            if (hasTypoed) {
                onWrongAnswer(question);
            } else {
                hasTypoed = true;
                TTS.getInstance().speak("Typo");
                fxFeedback.setVisible(true);
                fxFeedback.setText("Typo");
            }
        }
    }

    public void showAlert(EventHandler<Event> event) {
        Modal.show(View.MODAL_ANSWER_FEEDBACK, event);
        timer.stop();
    }

    abstract void onCorrectAnswer(Question question);

    abstract void onWrongAnswer(Question question);

    abstract void forceWrongAnswer(Question question, boolean wasTimerExpire);

    private String capitalise(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    @FXML
    public void onSubmit(ActionEvent event) {
        if(App.getState() == GameState.GAME){
            if(fxInput.textProperty().get().length() == 0){
                return;
            }
            if(isSubmitted){
                Modal.hide();
                return;
            }
            isSubmitted = true;
        } else if (App.getState() == GameState.PRACTICE){
            if(fxInput.textProperty().get().length() == 0){
                return;
            }
            if(isSubmitted){
                Modal.hide();
                return;
            }
            if(PracticeGame.getInstance().getRemainingAttempts().get() == 1){
                isSubmitted = true;
            }
        }

        
        submit();
    }

    @FXML
    public void onUnsure() {
        forceWrongAnswer(question, false);
    }

    @FXML
    public void repeatClue() {
        TTS.getInstance().speak(question.getHint());
    }
}
