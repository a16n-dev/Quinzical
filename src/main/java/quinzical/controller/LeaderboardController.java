package quinzical.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import quinzical.model.Ranking;
import quinzical.model.User;

public class LeaderboardController {

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
		
		//TODO: Get data from server here

		initTable();

		// Show users own stats
		int score = User.getInstance().getTotalCoins();
		fxTotalScore.setText(Integer.toString(score));

		// Make API call

		// Load table into view

	}

	public void initTable() {
		fxTable.setItems(rankings);

		fxRankCol.setCellValueFactory(cellData -> cellData.getValue().getRank());
		fxScoreCol.setCellValueFactory(cellData -> cellData.getValue().getScore());
		fxNameCol.setCellValueFactory(cellData -> cellData.getValue().getName());
	}
}
