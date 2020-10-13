package quinzical.controller;

import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import quinzical.model.Reward;
import quinzical.model.User;

public class TrophyCaseController {
    @FXML
    private Label fxDiamond;
    @FXML
    private Label fxPlatinum;
    @FXML
    private Label fxGold;
    @FXML
    private Label fxSilver;
    @FXML
    private Label fxBronze;

    public void initialize() {
        HashMap<Reward, Integer> rewards = User.getInstance().getRewards();
        fxDiamond.setText(Integer.toString(rewards.get(Reward.Diamond)));
        fxPlatinum.setText(Integer.toString(rewards.get(Reward.Platinum)));
        fxGold.setText(Integer.toString(rewards.get(Reward.Gold)));
        fxSilver.setText(Integer.toString(rewards.get(Reward.Silver)));
        fxBronze.setText(Integer.toString(rewards.get(Reward.Bronze)));
    }
}
