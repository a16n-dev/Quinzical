package quinzical.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import quinzical.model.PracticeGame;

public class PracticeScreenController {
	public VBox panel;

	private PracticeGame game;

	public void initialize() {
		game = PracticeGame.getInstance();
		var categories = game.getCategories();
		for (String category : categories) {
			Button button = new Button(category);
			button.setOnAction(event -> {
				game.setCurrentCategory(category);
				try {
					Parent PracticeScreenView = FXMLLoader.load(getClass().getResource("PracticeAnswerScreen.fxml"));
					Scene PracticeScreenScene = new Scene(PracticeScreenView, 700, 500);
					
					Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
					window.setScene(PracticeScreenScene);
					window.show(); 
				}
				catch (IOException e) {
					System.out.println(e);
				}
			});
			panel.getChildren().add(button);
		}
	}
}
