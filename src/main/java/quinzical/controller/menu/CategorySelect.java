package quinzical.controller.menu;

import java.util.ArrayList;

import com.jfoenix.controls.JFXMasonryPane;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import quinzical.controller.component.CategoryListItem;
import quinzical.controller.component.GameButton;
import quinzical.model.Category;
import quinzical.util.QuestionBank;

public abstract class CategorySelect {

    @FXML
    private JFXMasonryPane fxBaseCategoryContainer;

    @FXML
    private JFXMasonryPane fxUserCategoryContainer;

    @FXML
    private GameButton fxClearSelection;

    private ObservableList<Category> selectedCategories;

    private BooleanProperty disableSelection;

    public void initialize() {
        disableSelection = new SimpleBooleanProperty();
        selectedCategories = FXCollections.observableArrayList();

        fxClearSelection.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            return selectedCategories.size() == 0;
        }, selectedCategories));

        // Retrieve list of all categories as an arrayList
        ArrayList<Category> categories = QuestionBank.getInstance().getCategories();
        for(Category c : categories){
            Region content = CategoryListItem.create(c, disableSelection, selectedCategories);
            if(c.isUserCreated()){
                fxUserCategoryContainer.getChildren().add(content);
            } else {
                fxBaseCategoryContainer.getChildren().add(content);

            }
        }
    }

    public ObservableList<Category> getSelected(){
        return selectedCategories;
    }

    public void disableSelection(boolean b){
        disableSelection.set(b);
    }

    public BooleanProperty getDisabled(){
        return disableSelection;
    }

    @FXML
    public abstract void handleSubmit();

    @FXML
    public void clearSelected(){
        selectedCategories.clear();
    }

}
