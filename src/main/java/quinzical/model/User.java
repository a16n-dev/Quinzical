package quinzical.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import quinzical.util.IOManager;
import quinzical.util.QuestionBank;
import quinzical.util.State;
import quinzical.util.UserConnect;

/**
 * The user model is reponsible for keeping track of all information related
 * to a single user. This includes the users total score, owned items, and
 * also some preferences such as 
 */
public class User implements Serializable {

    private static final long serialVersionUID = -4964840915298617866L;

    private static User user;

    private HashMap<Reward, Integer> _rewards = new HashMap<Reward, Integer>();

    private HashMap<String, List<String>> unattemptedQuestions;

    private int numberAttemptedCategories;

    private boolean internationalUnlocked;

    private int coins;

    private int totalCoins;

    private Avatar avatar;

    private ArrayList<String> ownedItems;

    private Number prefWidth;

    private Number prefHeight;

    private String name;

    /**
     * This is the token that indicates if the user is logged in or not
     */
    private String token;

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

        avatar = new Avatar();

        prefWidth = 1000;
        prefHeight = 700;

        ownedItems = new ArrayList<String>();

        // Populate unattempted questions
        QuestionBank questionBank = QuestionBank.getInstance();
        ArrayList<Category> categories = questionBank.getCategories();

        unattemptedQuestions = new HashMap<String, List<String>>();

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
     * @return a copy of the users rewards
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

    /**
     * Removes the specified question from the list of unattempted questions for the user
     * @param category the category to remove the question from
     * @param id the id of the specific question to remove
     */
    public void attemptQuestion(String category, String id) {
        unattemptedQuestions.get(category).remove(id);

        if (unattemptedQuestions.get(category).isEmpty()) {
            numberAttemptedCategories++;
        }

        if (numberAttemptedCategories >= 2) {
            setInternational(true);
        }
    }

     /**
      * Adds the given amount of coins to the users coins
      * @param amount the number of coins to add
      * @return the number of coins the user has
      */
    public int addCoins(int amount) {
        coins += amount;
        totalCoins += amount;

        //send to server
        UserConnect.updateUserScore(totalCoins, coins);

        //persist on local file
        persist();

        return coins;

    }

    /**
     * 
     * 
     * @return the number of coins the user has
     */

     /**
      * Sets the users coins to the given amount
      * @param amount the amount of coins to set as the users coins
      * @return the amount of coins the user has
      */
    public int setCoins(int amount) {
        coins = amount;

        persist();

        return coins;
    }

    /**
     * Returns how many coins the user currently has
     * 
     * @return the number of coins the user currently has
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Returns the users total coins earned
     * 
     * @return the total number of coins the user has earned
     */
    public int getTotalCoins() {
        return totalCoins;
    }

    /**
     * Sets the users total coints to the specified amount
     * @param coins the total number of coins to set for the user
     */
    public void setTotalCoins(int coins) {
        totalCoins = coins;
    }

    /**
     * @return the avatar for the user, which can be modified
     */
    public Avatar getAvatar() {
        return avatar;
    }

    /**
     * Sets the users avatar to the specified avatar object
     * @param a an avatar
     */
    public void setAvatar(Avatar a) {
        avatar = a;
        persist();
    }

    /**
     * Forces animation to be off for this user
     * 
     * @param b if true animation will be disabled
     */
    public void setforceDisableAnimation(boolean b) {
        avatar.setforceDisableAnimation(b);
        persist();
    }

    /**
     * 
     * @return true if the user has animation disabled
     */
    public boolean hasForceDisableAnimation() {
        return avatar.hasForceDisableAnimation();
    }

    /**
     * @return a reference to the list of items the user owns, represented by their
     *         item ids
     */
    public ArrayList<String> getOwnedItems() {
        return ownedItems;
    }

    /**
     * Sets the list of items the user owns
     * 
     * @param items a list of item ids the user owns
     */
    public void setOwnedItems(List<String> items) {
        ownedItems = new ArrayList<String>(items);
        persist();
    }

    /**
     * Writes the user instance to file
     */
    private static void persist() {
        if (user == null) {
            IOManager.clearState(State.USER);
        } else {
            IOManager.writeState(State.USER, user);
        }
    }

    public void setPrefWidth(Number w){
        prefWidth = w;
        persist();
    }

    public Number getPrefWidth(){
        return prefWidth;
    }

    public void setPrefHeight(Number h){
        prefHeight = h;
        persist();
    }

    public Number getPrefHeight(){
        return prefHeight;
    }

    public void setToken(String authToken){
        token = authToken;
        persist();
    }

    public String getToken(){
        return token;
    }

    public void setName(String username){
        name = username;
        persist();
    }

    public String getName(){
        return name;
    }

}
