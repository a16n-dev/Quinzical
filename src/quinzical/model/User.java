package quinzical.model;

import java.io.Serializable;
import java.util.HashMap;

import quinzical.util.IOManager;
import quinzical.util.State;

/**
 * The user model is responsible for keeping track of the users rewards.
 * Should user be singleton? might be multiple users?
 * In this case the 'rewards' are in the form of strings, but this could be extended to its own class in the future
 */
public class User implements Serializable{

    private static final long serialVersionUID = -4964840915298617866L;

    private static User user;

    private HashMap<Reward, Integer> _rewards = new HashMap<Reward, Integer>();

    private Number prefWidth;

    private Number prefHeight;

    /**
     * @return the instance of the user class
     */
    public static User getInstance() {
        if (user == null) {
            // Attempt to read state from file
            user = IOManager.readState(State.USER);
            if (user == null) {
                user = new User();
                persist();
            }
        }
        return user;
    }

    private User(){
        prefWidth = 1000;
        prefHeight = 800;
    }

    /**
     * Getter for user rewards
     * @return a copy of the users rewards, so that encapsulation is not broken
     */
    public HashMap<Reward, Integer> getRewards() {
        return new HashMap<Reward, Integer>(_rewards);
    }

    /**
     * Adds a new reward for the user
     * @param reward a string representing the reward
     */
    public Reward addReward(int score) {
    	Reward reward;
    	
    	if (score == 7500) {
    		reward = Reward.Diamond;
    		_rewards.compute(reward, (k, v) -> (v == null) ? 1 : v + 1);
    	} else if (score >= 6000) {
			reward = Reward.Platinum;
			_rewards.compute(reward, (k, v) -> (v == null) ? 1 : v + 1);
		} else if (score >= 4500) {
			reward = Reward.Gold;
			_rewards.compute(reward, (k, v) -> (v == null) ? 1 : v + 1);
		} else if (score >= 3000) {
			reward = Reward.Silver;
			_rewards.compute(reward, (k, v) -> (v == null) ? 1 : v + 1);
		} else {
			reward = Reward.Bronze;
			_rewards.compute(reward, (k, v) -> (v == null) ? 1 : v + 1);
		}

        persist();
        
        return reward;
    }

    /**
     * Removes all of the users obtained rewards. This could be used if
     * the user decides they want to reset their overall progress
     */
    public void clearRewards(){
        _rewards = new HashMap<Reward, Integer>();
    }

    private static void persist(){
        if(user == null){
            IOManager.clearState(State.USER);
        } else {
            IOManager.writeState(State.USER, user);
        }
    }

    public int getPrefWidth(){
        return prefWidth.intValue();
    }

    public int getPrefHeight(){
        return prefHeight.intValue();
    }

    public void setPrefWidth(Number width){
        prefWidth = width;
        persist();
    }

    public void setPrefHeight(Number height){
        prefWidth = height;
        persist();
    }

}
