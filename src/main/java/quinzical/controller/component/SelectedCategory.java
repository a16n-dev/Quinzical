package quinzical.controller.component;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import quinzical.model.Category;
import quinzical.util.Router;

/**
 * Display a category that has been selected in the select categories screen
 * This class can also generate placeholders for slots where no category has
 * been selected
 * 
 * @author Alexander Nicholson
 */
public class SelectedCategory {

    @FXML
    private Label fxName;

    @FXML
    private AnchorPane fxBase;

    private Category category;

    private ObservableList<Category> categories;

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
        FXMLLoader loader = Router.manualLoad(Component.SELECTED_CATEGORY.getPath());
        try {
            Region content = (Region) loader.load();
            SelectedCategory controller = loader.getController();

            controller.config(category, selectedCategories);
            return content;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Factory method to create a template for an empty category slot as a
     * placeholder for a selected category
     * 
     * @return A Region containing an javafx element that displays a placeholder
     */
    public static Node createTemplate() {
        Node content = Router.loadFXML(Component.EMPTY_CATEGORY.getPath());
        return content;
    }
}
