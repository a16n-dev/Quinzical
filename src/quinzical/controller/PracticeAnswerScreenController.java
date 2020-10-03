package quinzical.controller;

import java.io.IOException;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import quinzical.model.PracticeGame;
import quinzical.model.Question;
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
    // public VBox parent;
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
        TTS.getInstance().speak(question.getHint());
	}

	public void onSubmit(ActionEvent event) throws IOException {
		
		/* Prompt user to try again after first and second incorrect attempts, 
		   show first letter hint on third attempt, or show clue and answer 
           after third incorrect attempt. */        
        boolean correct = question.checkAnswer(input.getText());
        
        // if (game.getAttempts() == 3 || correct) {
        //     parent.getChildren().remove(button);
        //     parent.getChildren().remove(input);
        //     parent.getChildren().remove(labelHint);
        //     parent.getChildren().remove(repeatHint);
        //     answerContainer.getChildren().clear();
        //     answerContainer.getChildren().add(new Label(question.getAnswer()));

        //     Button next = new Button("Another question.");
        //     next.setOnAction(event2 -> {
        //         Router.show(Views.PRACTICE_ANSWER_SCREEN);
        //     });
        //     parent.getChildren().add(next);

        //     Button back = new Button("Return to menu.");
        //     back.setOnAction(event2 -> {
        //         Router.show(Views.MAIN_MENU);
        //     });
        //     parent.getChildren().add(back);
        // }

        if (correct) {
            TTS.getInstance().speak("correct");
            // header.setText("Correct");
            return;
        }

        TTS.getInstance().speak(question.getHint());
        if (game.getAttempts() == 3) {
            // header.setText("The correct answer was:");
        }
        hint.setText(correct ? "Correct" : "Incorrect." + (game.getAttempts() == 1 ? "The first character is: " + question.getAnswer().charAt(0) : ""));
        game.addAttempt();
    }

    @FXML
	public void repeatClue(){
		TTS.getInstance().speak(question.getHint());
    }
    
    @FXML
    public void onUnsure(){}
}
