package quinzical.components;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import quinzical.model.Category;
import quinzical.util.Router;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class CategoryListItem {

    /**
     * The category the element represents
     */
    private Category category;

    /**
     * Boolean indicating if the category is part of the current selection
     */
    private boolean isSelected;

    /**
     * Boolean indicating if the element should be disabled and unable to added to
     * the selection
     */
    private BooleanProperty disabled;

    /**
     * A reference to the list of selected categories that listeners can be
     * attatched to
     */
    private ObservableList<Category> categories;

    /**
     * The different style classes that can be applied to the element to reflect its
     * state
     */
    private enum Style {
        DEFAULT("listItem"), SELECTED("listItemSelected"), DISABLED("listItemDisabled");

        private String className;

        private Style(String styleClass) {
            className = styleClass;
        }

        public String toString() {
            return className;
        }
    }

    @FXML
    private Label fxName;

    @FXML
    private Label fxCount;

    @FXML
    private AnchorPane fxBase;

    @FXML
    private AnchorPane fxLock;

    /**
     * Configures the controller to correspond to a specific category
     * 
     * @param categoryName       the category the element represents
     * @param isDisabled         observable value indicating if the element should
     *                           be disabled
     * @param selectedCategories a reference to the observable list of categories
     */
    private void config(Category categoryName, BooleanProperty isDisabled,
            ObservableList<Category> selectedCategories) {
        isSelected = false;
        categories = selectedCategories;
        disabled = isDisabled;
        category = categoryName;
        fxName.textProperty().set(category.getName());
        fxCount.textProperty().set(Integer.toString(category.getQuestions().size()));

        // Event listeners
        categories.addListener((ListChangeListener<Category>) (c -> {
            if (categories.indexOf(category) != -1) {
                isSelected = true;
                setStyle(Style.SELECTED);
            } else {
                isSelected = false;
                setStyle(Style.DEFAULT);
            }
        }));

        disabled.addListener(c -> {
            if (disabled.get() && !isSelected) {
                setStyle(Style.DISABLED);
            }
        });

        if (category.isLocked()) {
            fxLock.setVisible(true);
            setStyle(Style.DISABLED);
        } else {
            fxLock.setVisible(false);
        }
    }

    /**
     * On press should add the category to the selection unless it is disabled or
     * already selected
     */
    @FXML
    public void handleButtonPress() {
        if (!(disabled.get() || isSelected || category.isLocked())) {
            categories.add(category);
        } else if (isSelected) {
            categories.remove(category);
        }

    }

    /**
     * Sets the style of the element to the given class
     * 
     * @param style the style to apply
     */
    private void setStyle(Style style) {
        fxBase.getStyleClass().set(0, style.toString());
    }

    /**
     * Factory method to create a new instance of the javfx node and links it to the
     * specified category
     * 
     * @param category
     * @return A Region containing an javafx element that displays information about
     *         a given category
     */
    public static Region create(Category category, BooleanProperty isDisabled,
            ObservableList<Category> selectedCategories) {
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
