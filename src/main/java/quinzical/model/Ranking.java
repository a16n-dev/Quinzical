package quinzical.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ranking {
	private StringProperty rank;
	
	private StringProperty score;
	
	private StringProperty name;
	
	public Ranking(int userRank, int userScore, String userName) {
		rank = new SimpleStringProperty(Integer.toString(userRank));
		score = new SimpleStringProperty(Integer.toString(userScore));
		name = new SimpleStringProperty(userName);
	}
	
	public StringProperty getRank() {
		return rank;
	}
	
	public StringProperty getScore() {
		return score;
	}
	
	public StringProperty getName() {
		return name;
	}
}
