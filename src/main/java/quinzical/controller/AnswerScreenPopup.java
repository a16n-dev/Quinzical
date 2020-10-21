package quinzical.controller;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import quinzical.App;
import quinzical.App.GameState;
import quinzical.model.Game;
import quinzical.model.PracticeGame;
import quinzical.model.QuinzicalGame.Status;
import quinzical.util.ImageLoader;
import quinzical.util.Modal;

public class AnswerScreenPopup {
    
    @FXML
    private ImageView fxSymbol;

    @FXML
    private Label fxMessage;

    @FXML
    private Label fxAboveText;

    @FXML
    private Label fxStatusText;

    @FXML
    private Button fxButton;

    public void initialize(){

        //if user is in game mode
        if(App.getState() == GameState.GAME){
            fxButton.setText("NEXT QUESTION");

            Status status = Game.getInstance().getStatus();
            System.out.println(status);
                if(status == Status.SUCCESS){
                    //Check if question was correct
                    fxSymbol.setImage(ImageLoader.loadImage("images/feedback_correct.png"));
                    fxMessage.setText("CORRECT!");
                } else if (status == Status.FAILURE){
                    fxSymbol.setImage(ImageLoader.loadImage("images/feedback_incorrect.png"));
                    fxMessage.setText("INCORRECT");
                } else if (status == Status.OUT_OF_TIME){
                    fxSymbol.setImage(ImageLoader.loadImage("images/feedback_timeout.png"));
                    fxMessage.setText("OUT OF TIME!");
                }
        } else if (App.getState() == GameState.PRACTICE){
            Status status = PracticeGame.getInstance().getStatus();
        }

    }

    @FXML
    private void handleClick(){
        Modal.hide();
    }

}
