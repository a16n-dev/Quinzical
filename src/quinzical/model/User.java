package quinzical.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private List<String> _rewards = new ArrayList<String>();

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

    /**
     * Getter for user rewards
     * @return a copy of the users rewards, so that encapsulation is not broken
     */
    public List<String> getRewards() {
        return new ArrayList<String>(_rewards);
    }

    /**
     * Adds a new reward for the user
     * @param reward a string representing the reward
     */
    public void addReward(String reward) {
        _rewards.add(reward);
        persist();
    }

    /**
     * Removes all of the users obtained rewards. This could be used if
     * the user decides they want to reset their overall progress
     */
    public void clearRewards(){
        _rewards = new ArrayList<String>();
    }

    private static void persist(){
        if(user == null){
            IOManager.clearState(State.USER);
        } else {
            IOManager.writeState(State.USER, user);
        }
    }


}
