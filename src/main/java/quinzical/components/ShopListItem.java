package quinzical.components;

import java.io.IOException;
import java.util.List;
import java.util.Observable;

import com.jfoenix.controls.JFXBadge;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import quinzical.avatar.Cosmetic;
import quinzical.model.Category;
import quinzical.util.ImageLoader;
import quinzical.util.Router;

public class ShopListItem {
    
    @FXML
    private ImageView fxImage;

    @FXML
    private Label fxText;

    @FXML
    private Pane fxBase;

    @FXML
    private Label fxPrice;

    private enum Style {
        DEFAULT("shopListItem"),
        SELECTED("shopListItemSelected"),
        OWNED("shopListItemOwned"),
        ;

        private String className;

        private Style(String styleClass){
            className = styleClass;
        }

        public String toString(){
            return className;
        }
    }


    private void config(Cosmetic item, EventHandler<MouseEvent> e, ObjectProperty<Cosmetic> selectedItem, ObservableList<String> ownedItems){
        fxText.setText(item.getName());
        fxBase.setOnMousePressed(e);
        fxBase.setId(item.getId());

        fxPrice.setText("$" + item.getPrice());

        fxImage.setImage(ImageLoader.loadImage("avatar/" + item.getIcon()));

        selectedItem.addListener(i -> {
            if(selectedItem.get() != null && selectedItem.get().equals(item)){
                setStyle(Style.SELECTED);
            } else {
                setStyle(Style.DEFAULT);
            }
        });

        ownedItems.addListener((ListChangeListener<String>)(c -> {
            setBadge(ownedItems, item);
        }));

        setBadge(ownedItems, item);
    }

    private void setBadge(List<String> owned, Cosmetic item){
        if(owned.indexOf(item.getId()) != -1){
            //if owned
            fxBase.setStyle("-fx-background-color: rgba(0,0,0,0.5)");
            fxImage.setOpacity(1);
            fxText.setOpacity(1);
            fxPrice.setVisible(false);
        } else {
            //if not owned
            fxImage.setOpacity(0.4);
            fxText.setOpacity(0.4);
            fxPrice.setVisible(true);
        }
    }

    private void setStyle(Style style){
        fxBase.getStyleClass().set(0,style.toString());
    }

    public static Region create(Cosmetic item, ObjectProperty<Cosmetic> selectedItem, ObservableList<String> ownedItems, EventHandler<MouseEvent> event) {
        FXMLLoader loader = Router.manualLoad("components/ShopListItem.fxml");
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
