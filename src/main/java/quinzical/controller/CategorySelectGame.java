package quinzical.controller;

import com.jfoenix.controls.JFXButton;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import quinzical.components.SelectedCategory;
import quinzical.model.Category;
import quinzical.model.Game;
import quinzical.util.Router;

public class CategorySelectGame extends CategorySelect {

    @FXML
    private GridPane fxSelected;

    @FXML
    private Button fxSubmit;

    private final int SLOTS = 5;

    private ObservableList<Category> selected;

    public void initialize() {
        super.initialize();

        selected = getSelected();

        fxSubmit.disableProperty().bind(getDisabled().not());

        for(int i = selected.size(); i < 5; i++){
            fxSelected.add(SelectedCategory.createTemplate(), i, 0);
        }
        
        selected.addListener((ListChangeListener<Category>)(c -> {
            if(selected.size() == SLOTS){
                disableSelection(true);
            } else {
                disableSelection(false);
            }

            fxSelected.getChildren().clear();

            //Display arraylist
            for(int i = 0; i < selected.size(); i++){
                Category category = selected.get(i);
                Region content = SelectedCategory.create(category, selected);
                fxSelected.add(content, i, 0);
            }

            for(int i = selected.size(); i < 5; i++){
                fxSelected.add(SelectedCategory.createTemplate(), i, 0);
            }
        }));
    }

    @Override
    public void handleSubmit() {
        Game.newGame(selected);
        Router.show(View.GAME_BOARD);
    }

    
    
}
