package quinzical.controller;

import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import quinzical.model.Reward;
import quinzical.model.User;

public class TrophyCaseController {
    @FXML
    public Label diamond;
    @FXML
    public Label platinum;
    @FXML
    public Label gold;
    @FXML
    public Label silver;
    @FXML
    public Label bronze;

    public void initialize() {
        HashMap<Reward, Integer> rewards = User.getInstance().getRewards();
        System.out.println(rewards);
        diamond.setText(Integer.toString(rewards.get(Reward.Diamond)));
        platinum.setText(Integer.toString(rewards.get(Reward.Platinum)));
        gold.setText(Integer.toString(rewards.get(Reward.Gold)));
        silver.setText(Integer.toString(rewards.get(Reward.Silver)));
        bronze.setText(Integer.toString(rewards.get(Reward.Bronze)));
    }
}
