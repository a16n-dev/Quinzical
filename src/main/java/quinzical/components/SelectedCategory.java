package quinzical.components;

import javafx.scene.Node;
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

public class SelectedCategory {

    /**
     * The category the element represents
     */
    private Category category;

    /**
     * A reference to the list of selected categories that listeners can be
     * attatched to
     */
    private ObservableList<Category> categories;

    @FXML
    private Label fxName;

    @FXML
    private AnchorPane fxBase;

    /**
     * Configures the controller to correspond to a specific category
     * 
     * @param categoryName       the category the element represents
     * @param isDisabled         observable value indicating if the element should
     *                           be disabled
     * @param selectedCategories a reference to the observable list of categories
     */
    private void config(Category categoryName, ObservableList<Category> selectedCategories) {
        categories = selectedCategories;
        category = categoryName;
        fxName.textProperty().set(category.getName());
    }

    /**
     * On press should add the category to the selection unless it is disabled or
     * already selected
     */
    @FXML
    public void handleButtonPress() {
        categories.remove(category);
    }

    /**
     * Factory method to create a new instance of the javfx node and links it to the
     * specified category
     * 
     * @param category
     * @return A Region containing an javafx element that displays information about
     *         a given category
     */
    public static Region create(Category category, ObservableList<Category> selectedCategories) {
        FXMLLoader loader = Router.manualLoad("components/SelectedCategory.fxml");
        try {
            Region content = (Region) loader.load();
            SelectedCategory controller = loader.getController();

            controller.config(category, selectedCategories);
            return content;
        } catch (IOException e) {
            return null;
        }
    }

    public static Node createTemplate() {
        Node content = Router.loadFXML("components/EmptyCategory.fxml");
        return content;
    }
}
