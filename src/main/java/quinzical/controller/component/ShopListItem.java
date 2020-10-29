package quinzical.controller.component;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import quinzical.avatar.Cosmetic;
import quinzical.util.ImageLoader;
import quinzical.util.Router;

/**
 * Component to display a cosmetic item in the list of items in the shop. The display changes based on if the cosmetic is owned or selected
 * 
 * @author Alexander Nicholson
 */
public class ShopListItem {

    @FXML
    private ImageView fxImage;

    @FXML
    private Label fxText;

    @FXML
    private Pane fxBase;

    @FXML
    private Label fxPrice;

    /**
     * The different style classes that can be applied to the element to reflect its
     * state
     */
    private enum Style {
        DEFAULT("shopListItem"), SELECTED("shopListItemSelected"), OWNED("shopListItemOwned"),;

        private String className;

        private Style(String styleClass) {
            className = styleClass;
        }

        public String toString() {
            return className;
        }
    }

    /**
     * Configures the node with the given data
     * 
     * @param item         the cosmetic item to display
     * @param e            the eventhandler for clicking the item
     * @param selectedItem the item the user currently has selected in the shop
     * @param ownedItems   the list of all items owned by the user
     */
    private void config(Cosmetic item, EventHandler<MouseEvent> event, ObjectProperty<Cosmetic> selectedItem,
            ObservableList<String> ownedItems) {
        fxText.setText(item.getName());
        fxBase.setOnMousePressed(event);
        fxBase.setId(item.getId());

        fxPrice.setText("$" + item.getPrice());

        fxImage.setImage(ImageLoader.loadImage("avatar/" + item.getIcon()));

        selectedItem.addListener(i -> {
            if (selectedItem.get() != null && selectedItem.get().equals(item)) {
                setStyle(Style.SELECTED);
            } else {
                setStyle(Style.DEFAULT);
            }
        });

        ownedItems.addListener((ListChangeListener<String>) (c -> {
            setDisplay(ownedItems, item);
        }));

        setDisplay(ownedItems, item);
    }

    /**
     * Sets the display of the item based on if it is owned or not
     * @param owned a list of owned items 
     * @param item the item for this slot
     */
    private void setDisplay(List<String> owned, Cosmetic item) {
        if (owned.indexOf(item.getId()) != -1) {
            // if owned
            fxBase.setStyle("-fx-background-color: rgba(0,0,0,0.5)");
            fxImage.setOpacity(1);
            fxText.setOpacity(1);
            fxPrice.setVisible(false);
        } else {
            // if not owned
            fxImage.setOpacity(0.4);
            fxText.setOpacity(0.4);
            fxPrice.setVisible(true);
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
     * Static factory method to generate a new intance of GameBoardItem.
     * 
     * @param item         the cosmetic item to display
     * @param e            the eventhandler for clicking the item
     * @param selectedItem the item the user currently has selected in the shop
     * @param ownedItems   the list of all items owned by the user
     */
    public static Region create(Cosmetic item, ObjectProperty<Cosmetic> selectedItem, ObservableList<String> ownedItems,
            EventHandler<MouseEvent> event) {
        FXMLLoader loader = Router.manualLoad(Component.SHOP_LIST_ITEM.getPath());
        try {
            Region content = (Region) loader.load();
            ShopListItem controller = loader.getController();

            controller.config(item, event, selectedItem, ownedItems);
            return content;
        } catch (IOException e) {
            return null;
        }
    }

}
