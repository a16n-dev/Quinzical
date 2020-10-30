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

/**
 * The controller for the category select screens
 */
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
        for (Category c : categories) {
            Region content = CategoryListItem.create(c, disableSelection, selectedCategories);
            if (c.isUserCreated()) {
                fxUserCategoryContainer.getChildren().add(content);
            } else {
                fxBaseCategoryContainer.getChildren().add(content);

            }
        }
    }

    /**
     * 
     * @return the selected categories
     */
    public ObservableList<Category> getSelected() {
        return selectedCategories;
    }

    /**
     * 
     * @param b the truth value to set it to
     */
    public void setDisableSelection(boolean b) {
        disableSelection.set(b);
    }

    /**
     * 
     * @return whether selection is disabled
     */
    public BooleanProperty getDisableSelection() {
        return disableSelection;
    }

    /**
     * The action to perform on submission of the category select
     */
    @FXML
    public abstract void handleSubmit();

    /**
     * Clear the selection of categories
     */
    @FXML
    public void clearSelected() {
        selectedCategories.clear();
    }

}
