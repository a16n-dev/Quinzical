package quinzical.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import quinzical.model.Category;
import quinzical.model.Game;
import quinzical.util.QuestionBank;
import quinzical.util.Router;

//TODO: Make this class
public class CategorySelectGame extends CategorySelect {

    @FXML
    private HBox fxSelectedList;

    @FXML
    private Button fxSubmit;

    private final int SLOTS = 5;

    private ObservableList<Category> selected;

    public void initialize() {
        super.initialize();

        selected = getSelected();

        fxSubmit.disableProperty().bind(getDisabled().not());

        selected.addListener((ListChangeListener<Category>)(c -> {
            if(selected.size() == SLOTS){
                disableSelection(true);
            } else {
                disableSelection(false);
            }

            fxSelectedList.getChildren().clear();

            //Display arraylist
            for(Category category : selected){
                JFXButton button = new JFXButton(category.getName());
                button.setOnAction(v -> {
                    selected.remove(category);
                });
                fxSelectedList.getChildren().add(button);
            }
        }));
    }

    @Override
    public void handleSubmit() {
        Game.newGame(selected);
        Router.show(View.GAME_BOARD);
    }

    
    
}
