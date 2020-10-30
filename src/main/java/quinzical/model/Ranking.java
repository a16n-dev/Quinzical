package quinzical.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class represnting a user's ranking on the leaderboard
 */
public class Ranking {
	private StringProperty rank;
	
	private StringProperty score;
	
	private StringProperty name;
	
	/**
	 * Constructor
	 * @param userRank
	 * @param userScore
	 * @param userName
	 */
	public Ranking(int userRank, int userScore, String userName) {
		rank = new SimpleStringProperty(Integer.toString(userRank));
		score = new SimpleStringProperty(Integer.toString(userScore));
		name = new SimpleStringProperty(userName);
	}
	
	/**
	 * 
	 * @return the user's rank
	 */
	public StringProperty getRank() {
		return rank;
	}
	/**
	 * 
	 * @return the user's score
	 */
	public StringProperty getScore() {
		return score;
	}
	/**
	 * 
	 * @return the user's name
	 */
	public StringProperty getName() {
		return name;
	}
}
