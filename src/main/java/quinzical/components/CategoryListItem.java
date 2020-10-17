package quinzical.components;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import quinzical.controller.View;
import quinzical.model.Category;
import quinzical.model.PracticeGame;
import quinzical.util.Router;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class CategoryListItem {

    private Category category;

    private boolean isSelected;

    private BooleanProperty disabled;

    private ObservableList<Category> categories;

    private enum Style {
        DEFAULT("listItem"),
        SELECTED("listItemSelected"),
        DISABLED("listItemDisabled");

        private String className;

        private Style(String styleClass){
            className = styleClass;
        }

        public String toString(){
            return className;
        }
    }

    @FXML
    private Label fxName;

    @FXML
    private Label fxCount;

    @FXML
    private AnchorPane fxBase;

    private void config(Category categoryName, BooleanProperty isDisabled, ObservableList<Category> selectedCategories) {
        isSelected = false;
        categories = selectedCategories;
        disabled = isDisabled;
        category = categoryName;
        fxName.textProperty().set(category.getName());
        fxCount.textProperty().set(Integer.toString(category.getQuestions().size()));

        //Event listeners
        categories.addListener((ListChangeListener<Category>)(c -> {
            if(categories.indexOf(category) != -1){
                isSelected = true;
                setStyle(Style.SELECTED);
            } else {
                isSelected = false;
                setStyle(Style.DEFAULT);
            }
        }));

        disabled.addListener(c -> {
            if(disabled.get() && !isSelected){
                setStyle(Style.DISABLED);
            }
        });
    }

    @FXML
    public void handleButtonPress() {
        if(!(disabled.get() || isSelected)){
            categories.add(category);
        }
        
    }

    private void setStyle(Style style){
        fxBase.getStyleClass().set(0,style.toString());
    }

    /**
     * Factory method to create a new instance of the javfx node and links it to the specified category
     * @param category
     * @return A Region containing an javafx element that displays information about a given category
     */
    public static Region create(Category category, BooleanProperty isDisabled, ObservableList<Category> selectedCategories) {
        FXMLLoader loader = Router.manualLoad("components/CategoryListItem.fxml");
        try {
            Region content = (Region) loader.load();
            CategoryListItem controller = loader.getController();

            controller.config(category, isDisabled, selectedCategories);
            return content;
        } catch (IOException e) {
            return null;
        }
    }
}
