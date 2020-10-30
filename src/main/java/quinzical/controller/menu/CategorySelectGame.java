package quinzical.controller.menu;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import quinzical.controller.View;
import quinzical.controller.component.SelectedCategory;
import quinzical.model.Category;
import quinzical.model.Game;
import quinzical.util.QuestionBank;
import quinzical.util.Router;

/**
 * The category select screen for the regular game module
 */
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

        fxSubmit.disableProperty().bind(getDisableSelection().not());

        for(int i = selected.size(); i < 5; i++){
            fxSelected.add(SelectedCategory.createTemplate(), i, 0);
        }
        
        selected.addListener((ListChangeListener<Category>)(c -> {
            if(selected.size() == SLOTS){
                setDisableSelection(true);
            } else {
                setDisableSelection(false);
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

    @FXML
    public void selectRandom(){
        selected.setAll(QuestionBank.getInstance().getRandomCategories(5, false));
    }
}
