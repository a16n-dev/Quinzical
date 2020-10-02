package quinzical.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
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
import quinzical.util.TTS;

public class PracticeAnswerScreenController {
    private PracticeGame game;
    private Question question;
    public Button button;
    public Label labelHint;
    public Label feedback;
    public TextField input;
    public VBox parent;
    public HBox answerContainer;
    public Label header;

    private int attemptNumber = 1;
	
	public void initialize() {
        game = PracticeGame.getInstance();
        game.setRandomQuestion();
        question = game.getCurrentQuestion();

        labelHint.setText(question.getHint());
        TTS.getInstance().speak(question.getHint());
	}

	public void onSubmit(ActionEvent event) throws IOException {
		
		/* Prompt user to try again after first and second incorrect attempts, 
		   show first letter hint on third attempt, or show clue and answer 
           after third incorrect attempt. */        
        boolean correct = question.checkAnswer(input.getText());
        
        if (attemptNumber == 3 || correct) {
            parent.getChildren().remove(button);
            parent.getChildren().remove(input);
            parent.getChildren().remove(labelHint);
            answerContainer.getChildren().clear();
            answerContainer.getChildren().add(new Label(question.getAnswer()));

            Button next = new Button("Another question.");
            next.setOnAction(event2 -> {
                try {
                    Parent PracticeScreenView = FXMLLoader.load(getClass().getResource("PracticeAnswerScreen.fxml"));
                    Scene PracticeScreenScene = new Scene(PracticeScreenView, 700, 500);
                    
                    Stage window = (Stage)((Node)event2.getSource()).getScene().getWindow();
                    window.setScene(PracticeScreenScene);
                    window.show(); 
                }
                catch (IOException e) {
                    System.out.println(e);
                }
            });
            parent.getChildren().add(next);

            Button back = new Button("Return to menu.");
            back.setOnAction(event2 -> {
                try {
                    Parent PracticeScreenView = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                    Scene PracticeScreenScene = new Scene(PracticeScreenView, 700, 500);
                    
                    Stage window = (Stage)((Node)event2.getSource()).getScene().getWindow();
                    window.setScene(PracticeScreenScene);
                    window.show(); 
                }
                catch (IOException e) {
                    System.out.println(e);
                }
            });
            parent.getChildren().add(back);
        }

        if (correct) {
            TTS.getInstance().speak("correct");
            header.setText("Correct");
            return;
        }

        TTS.getInstance().speak("The correct answer was " + question.getHint());
        if (attemptNumber == 3) {
            header.setText("The correct answer was:");
        }
        feedback.setText(correct ? "Correct" : "Incorrect." + (attemptNumber == 2 ? "The first character is: " + question.getAnswer().charAt(0) : ""));
        attemptNumber ++;
    }
}
