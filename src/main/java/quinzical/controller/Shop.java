package quinzical.controller;

import java.util.ArrayList;
import java.util.Arrays;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import quinzical.avatar.Accessory;
import quinzical.avatar.Cosmetic;
import quinzical.avatar.Hat;
import quinzical.components.ShopListItem;
import quinzical.model.Avatar;
import quinzical.model.User;
import quinzical.util.AvatarFactory;
import quinzical.util.ImageLoader;
import quinzical.util.Modal;

public class Shop {

    // FXML node references
    @FXML
    private JFXMasonryPane fxItemGridAll;

    @FXML
    private JFXMasonryPane fxItemGridOwned;

    @FXML
    private Label fxCoinsDisplay;

    @FXML
    private StackPane fxAvatarDisplay;

    @FXML
    private JFXButton fxResetButton;

    @FXML
    private JFXButton fxActionButton;

    @FXML
    private ImageView fxAccessorySlot;

    @FXML
    private ImageView fxHatSlot;

    @FXML
    private AnchorPane fxAccessoryEquipped;

    @FXML
    private AnchorPane fxHatEquipped;

    /**
     * Stores a reference to the users avatar
     */
    private Avatar avatar;

    /**
     * Stores a reference to the list of items the user owns, represented by their
     * item ids
     */

    private ObservableList<String> ownedItems;

    /**
     * Stores a list of all purchasable items
     */
    private ArrayList<Cosmetic> itemList;

    private AvatarFactory avatarfactory;

    private BooleanProperty isPreview;

    private ObjectProperty<Cosmetic> selectedItem;

    /**
     * The number of coins the user has
     */

    private IntegerProperty coins;

    public void initialize() {
        // Get data
        avatar = User.getInstance().getAvatar();

        itemList = getItemList();
        ownedItems = FXCollections.observableArrayList(User.getInstance().getOwnedItems());
        // Link coins to user instance field
        coins = new SimpleIntegerProperty(User.getInstance().getCoins());

        // Initialise boolean to track preview mode
        isPreview = new SimpleBooleanProperty(false);
        fxResetButton.visibleProperty().bind(isPreview);

        // Keep track of selected item
        selectedItem = new SimpleObjectProperty<Cosmetic>();

        // When observable value changes update user instance
        coins.addListener(e -> {
            User.getInstance().setCoins(coins.get());
        });

        // Populate grid with items
        for (Cosmetic c : itemList) {
            // Check if user owns item

            // Region item = ShopListItem.create(c, selectedItem, e -> {
            //     selectedItem.set(c);
            // });

            fxItemGridAll.getChildren().add(ShopListItem.create(c, selectedItem, e -> {
                selectedItem.set(c);
            }));
            fxItemGridOwned.getChildren().add(ShopListItem.create(c, selectedItem, e -> {
                selectedItem.set(c);
            }));
        }
        filterOwnedView();

        // Display the users current avatar
        avatarfactory = new AvatarFactory(fxAvatarDisplay, 250);
        avatarfactory.set(avatar);

        // Display the currently equipped cosmetics

        // Bind coins to label
        fxCoinsDisplay.textProperty().bind(Bindings.convert(coins));

        // Listen for when user selects an item

        fxActionButton.setVisible(false);
        selectedItem.addListener(i -> {
            Cosmetic c = selectedItem.get();
            avatarfactory.set(avatar);
            isPreview.set(false);
            // Check if user owns the item
            if (c == null) {
                fxActionButton.setVisible(false);
            } else {
                fxActionButton.setVisible(true);
                if (ownedItems.indexOf(c.getId()) != -1) {
                    // if owned
                    fxActionButton.setDisable(false);
                    if (avatar.isEquipped(c)) {
                        // if equipped
                        fxActionButton.setText("Unequip " + c.getName());
                        fxActionButton.setOnAction(e -> unequip(c));

                    } else {
                        // if not equipped
                        fxActionButton.setText("Equip " + c.getName());
                        fxActionButton.setOnAction(e -> equip(c));
                    }

                } else {
                    // if not owned
                    previewItem(c);
                    if (coins.get() >= c.getPrice()) {
                        // If user can afford
                        fxActionButton.setText("Purchase " + c.getName() + " for $" + c.getPrice());
                        fxActionButton.setDisable(false);
                        fxActionButton.setOnAction(e -> purchaseItem(c));

                    } else {
                        // If user is poor
                        fxActionButton.setText("Not enough coins to buy");
                        fxActionButton.setDisable(true);
                    }
                }
            }

        });

        ownedItems.addListener((ListChangeListener<String>) (e -> {
            User.getInstance().setOwnedItems(ownedItems);
            filterOwnedView();
        }));

        setEquipped();
    }

    public void equip(Cosmetic item) {
        switch (item.getSlot()) {
            case ACCESSORY:
                avatar.setAccessory((Accessory) item);
                break;
            case HAT:
                avatar.setHat((Hat) item);
                break;
            default:
                break;
        }
        // Update display
        avatarfactory.set(avatar);
        selectedItem.set(null);
        selectedItem.set(item);
        setEquipped();
    }

    public void unequip(Cosmetic item) {
        switch (item.getSlot()) {
            case ACCESSORY:
                avatar.setAccessory(null);
                break;
            case HAT:
                avatar.setHat(null);
                break;
            default:
                break;
        }
        // Update display
        selectedItem.set(null);
        setEquipped();
    }

    private void setEquipped() {
        if (avatar.getHat() != null) {
            fxHatSlot.setImage(ImageLoader.loadImage("avatar/" + avatar.getHat().getIcon()));
            fxHatEquipped.setOnMouseClicked(e->{selectedItem.set(avatar.getHat());});

            fxHatEquipped.getStyleClass().set(1,"equippedItem");
        } else {
            fxHatSlot.setImage(null);
            fxHatEquipped.setOnMouseClicked(e->{});
            fxHatEquipped.getStyleClass().set(1,"");
        }
        if (avatar.getAccessory() != null) {
            fxAccessorySlot.setImage(ImageLoader.loadImage("avatar/" + avatar.getAccessory().getIcon()));
            fxAccessoryEquipped.setOnMouseClicked(e->{selectedItem.set(avatar.getAccessory());});
            fxAccessoryEquipped.getStyleClass().set(1,"equippedItem");
        } else {
            fxAccessorySlot.setImage(null);
            fxAccessoryEquipped.setOnMouseClicked(e->{});
            fxAccessoryEquipped.getStyleClass().set(1,"");
        }
        ;
    }

    private void filterOwnedView(){
        fxItemGridOwned.getChildren().forEach( n ->{
            n.setVisible(ownedItems.indexOf(n.getId()) != -1);
        });;
    }

    @FXML
    private void resetAvatar() {
        isPreview.set(false);
        avatarfactory.set(avatar);
        selectedItem.set(null);
    }

    private void previewItem(Cosmetic item) {
        isPreview.set(true);
        switch (item.getSlot()) {
            case ACCESSORY:
                avatarfactory.set((Accessory) item);
                break;
            case HAT:
                avatarfactory.set((Hat) item);
                break;
            default:
                break;
        }
        // avatarfactory.render();
    }

    private void purchaseItem(Cosmetic item) {
        Modal.confirmation("Buy " + item.getName(),
                "Are you sure you want to purchase " + item.getName() + " for $" + item.getPrice() + "?", e -> {
                    coins.set(coins.get() - item.getPrice());
                    ownedItems.add(item.getId());
                    resetAvatar();
                    equip(item);
                });
    }

    private ArrayList<Cosmetic> getItemList() {

        ArrayList<Cosmetic> items = new ArrayList<Cosmetic>();

        // Get hats
        items.addAll(Arrays.asList(Hat.values()));

        // Get accessories
        items.addAll(Arrays.asList(Accessory.values()));

        // Sort item list by price
        items.sort((Cosmetic c1, Cosmetic c2) -> c1.getPrice() - (c2.getPrice()));

        return items;
    }
}
