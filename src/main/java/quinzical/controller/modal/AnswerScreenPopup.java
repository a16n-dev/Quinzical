package quinzical.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import quinzical.App;
import quinzical.App.GameState;
import quinzical.model.Game;
import quinzical.model.MultiplayerGame;
import quinzical.model.PracticeGame;
import quinzical.model.QuinzicalGame;
import quinzical.model.QuinzicalGame.Status;
import quinzical.util.ImageLoader;
import quinzical.util.Modal;

/**
 * Controller for the answer screen dialog that appears after a user submits
 * their answer to a question This informs the user if they were correct,
 * incorrect or if they ran out of time.
 * 
 * @author Alexander Nicholson, Peter Geodeke
 */
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

    /**
     * The method to run when the fxml is initialised
     */
    public void initialize() {
        fxButton.setText("NEXT QUESTION");

        // if user is in game mode
        Status status = Status.ANSWERING;
        QuinzicalGame game;
        if (App.getState() == GameState.GAME) {
            status = Game.getInstance().getStatus();
            game = Game.getInstance();

            if (Game.getInstance().getRemainingQuestions() == 0) {
                fxButton.setText("VIEW REWARDS");
            }

        } else if (App.getState() == GameState.PRACTICE) {

            status = PracticeGame.getInstance().getStatus();
            game = PracticeGame.getInstance();

        } else {

            status = MultiplayerGame.getInstance().getStatus();
            game = MultiplayerGame.getInstance();

        }

        if (status == Status.SUCCESS) {

            fxSymbol.setImage(ImageLoader.loadImage("images/feedback_correct.png"));
            fxMessage.setText("CORRECT!");

            if (App.getState() == GameState.GAME) {

                fxAboveText.setText("You earned");
                fxStatusText.setText(Game.getInstance().getLastScore() + " points");
            } else if (App.getState() == GameState.PRACTICE) {
                fxAboveText.setText("Current streak:");
                fxStatusText.setText(Integer.toString(PracticeGame.getInstance().getStreak()));
            } else if (App.getState() == GameState.MULTIPLAYER) {
                fxAboveText.setText("");
            }

        } else {

            if (status == Status.FAILURE) {
                fxSymbol.setImage(ImageLoader.loadImage("images/feedback_incorrect.png"));
                fxMessage.setText("INCORRECT");
            } else if (status == Status.OUT_OF_TIME) {
                fxSymbol.setImage(ImageLoader.loadImage("images/feedback_timeout.png"));
                fxMessage.setText("OUT OF TIME!");
            } else if (status == Status.SKIP) {
                fxSymbol.setImage(ImageLoader.loadImage("images/feedback_incorrect.png"));
                fxMessage.setText("SKIPPED");
            }

            fxAboveText.setText("The correct answer was");
            fxStatusText.setText(game.getCurrentQuestion().getAnswer());
        }

    }

    /**
     * Method to handle the user accepting the dialog
     */
    @FXML
    private void handleClick() {
        Modal.hide();
    }

}
