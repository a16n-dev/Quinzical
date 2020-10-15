package quinzical.controller;

import org.apache.xmlbeans.impl.common.Levenshtein;

import quinzical.util.Router;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import quinzical.model.QuinzicalGame;
import quinzical.model.Answer;
import quinzical.model.Question;
import quinzical.util.Macron;
import quinzical.util.TTS;
import quinzical.util.Timer;

public abstract class BaseAnswerScreen {
    private Question question;
    private Timer timer;
    
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
    private VBox fxMacronContainer;

    @FXML
	private ProgressBar fxProgressRight;
	@FXML
	private ProgressBar fxProgressLeft;
	@FXML
	private Label fxProgressLabel;

    public void initialize() {
        onLoad();
        question = setQuestion();
        
        fxHint.setText(capitalise(question.getHint()));
        fxPrefix.setText(capitalise(question.getPrefix()));
        
        TTS.getInstance().speak(question.getHint());
        Macron.getInstance().bindToTextField(fxInput, fxMacronLetter, fxMacronContainer);
        
        timer = Timer.getInstance();
        timer.set(fxProgressLabel, fxProgressLeft, fxProgressRight, 30);
		timer.start(e -> {
            submit();
        });
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fxInput.requestFocus();
            }
        });

        // timer, category name, value text, random question for practise, attempt count for practise
    }

    abstract void onLoad();
    abstract Question setQuestion();
    
    private boolean hasTypoed = false;
    private void submit() {
        String userAnswer = fxInput.getText();
        Answer answer = question.checkAnswer(userAnswer);

        if (answer == Answer.CORRECT) {
            onCorrectAnswer(question);
        }
        else if (answer == Answer.INCORRECT) {
            onWrongAnswer(question);
        }
        else {
            if (hasTypoed) {
                onWrongAnswer(question);
            }
            else {
                hasTypoed = true;
                TTS.getInstance().speak("Typo");
                fxFeedback.setVisible(true);
                fxFeedback.setText("Typo");
            }
        }
    }

    public void showAlert(String title, String header, String content, EventHandler<DialogEvent> event) {
        Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
        alert.setOnHidden(event);
        timer.stop();
    }

    abstract void onCorrectAnswer(Question question);
    abstract void onWrongAnswer(Question question);
    
    private String capitalise(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    @FXML
    public void onSubmit(ActionEvent event) { submit(); }

    @FXML
    public void onUnsure() {
        hasTypoed = true; // make sure that it doesn't count as a typo
        submit();
    }

    @FXML
	public void repeatClue() {
		TTS.getInstance().speak(question.getHint());
	}   
}
