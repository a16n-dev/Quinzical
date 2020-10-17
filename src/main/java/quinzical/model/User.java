package quinzical.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import quinzical.util.AvatarFactory;
import quinzical.util.IOManager;
import quinzical.util.QuestionBank;
import quinzical.util.State;

/**
 * The user model is responsible for keeping track of the users rewards. Should
 * user be singleton? might be multiple users? In this case the 'rewards' are in
 * the form of strings, but this could be extended to its own class in the
 * future
 */
public class User implements Serializable {

    private static final long serialVersionUID = -4964840915298617866L;

    private static User user;

    private HashMap<Reward, Integer> _rewards = new HashMap<Reward, Integer>();
    
    private HashMap<String, List<String>> unattemptedQuestions;
    
    private int numberAttemptedCategories;
    
    private boolean internationalUnlocked;

    private transient AvatarFactory avatar;

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

    private User() {
    	internationalUnlocked = false;
    	numberAttemptedCategories = 0;
    	
    	// Store the ids of unattempted questions.
    	QuestionBank questionBank = QuestionBank.getInstance();
    	ArrayList<Category> categories = questionBank.getCategories();    	
    	for (Category category : categories) {
			unattemptedQuestions.put(category.getName(), category.getQuestionIds());
		} 	
    	
        _rewards.put(Reward.Diamond, 0);
        _rewards.put(Reward.Platinum, 0);
        _rewards.put(Reward.Gold, 0);
        _rewards.put(Reward.Silver, 0);
        _rewards.put(Reward.Bronze, 0);  
    }

    /**
     * Getter for user rewards
     * 
     * @return a copy of the users rewards, so that encapsulation is not broken
     */
    public HashMap<Reward, Integer> getRewards() {
        return new HashMap<Reward, Integer>(_rewards);
    }

    /**
     * Adds a new reward for the user
     */
    public Reward addReward(int score) {
        Reward reward;

        if (score == 7500) {
            reward = Reward.Diamond;
        } else if (score >= 6000) {
            reward = Reward.Platinum;
        } else if (score >= 4500) {
            reward = Reward.Gold;
        } else if (score >= 3000) {
            reward = Reward.Silver;
        } else {
            reward = Reward.Bronze;

        }
        _rewards.compute(reward, (k, v) -> (v == null) ? 1 : v + 1);
        persist();

        return reward;
    }

    /**
     * Removes all of the users obtained rewards. This could be used if the user
     * decides they want to reset their overall progress
     */
    public void clearRewards() {
        _rewards = new HashMap<Reward, Integer>();
    }
 
    public boolean getInternationalUnlocked() {
    	return internationalUnlocked;
    }
    
    /**
     * Sets the locking status of the international questions section.
     */
    public void setInternational(boolean unlock) {
    	internationalUnlocked = unlock;
    }
    
    public void attemptQuestion(String category, String id) {
    	unattemptedQuestions.get(category).remove(id);
    	
    	if (unattemptedQuestions.get(category).isEmpty()) {
    		numberAttemptedCategories ++;
    	}
    	
    	if (numberAttemptedCategories >= 2) {
    		setInternational(true);
    	}
    }

    private static void persist() {
        if (user == null) {
            IOManager.clearState(State.USER);
        } else {
            IOManager.writeState(State.USER, user);
        }
    }

}
