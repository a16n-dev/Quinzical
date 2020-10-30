package quinzical.controller.menu;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import quinzical.model.Ranking;
import quinzical.model.User;
import quinzical.util.UserConnect;

/**
 * Controller for the leaderboard screen
 */
public class Leaderboard {

	@FXML
	public Label fxTotalScore;

	@FXML
	public Label fxRanking;

	@FXML
	public TableView<Ranking> fxTable;

	@FXML
	public TableColumn<Ranking, String> fxRankCol;

	@FXML
	public TableColumn<Ranking, String> fxScoreCol;

	@FXML
	public TableColumn<Ranking, String> fxNameCol;

	private ObservableList<Ranking> rankings;

	public void initialize() {
		rankings = FXCollections.observableArrayList();
		rankings.add(new Ranking(1, 0, "Loading Leaderboard..."));
		initTable();

		// Show users own stats
		int score = User.getInstance().getTotalCoins();
		fxTotalScore.setText(Integer.toString(score));

		// Make API call to get other users places
		UserConnect.getLeaderboardData((List<Ranking> l) -> {
			if (l != null) {
				rankings.setAll(l);
			}
			return null;

		}, (Integer rank) -> {
			if (rank != null) {
				fxRanking.setText(rank.toString());
			}
			return null;
		});

	}

	/**
	 * Initialise the table
	 */
	public void initTable() {
		fxTable.setItems(rankings);

		fxRankCol.setCellValueFactory(cellData -> cellData.getValue().getRank());
		fxScoreCol.setCellValueFactory(cellData -> cellData.getValue().getScore());
		fxNameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
	}
}
