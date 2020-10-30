package quinzical.controller.menu;

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
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import quinzical.avatar.Cosmetic;
import quinzical.controller.component.GameButton;
import quinzical.controller.component.ShopListItem;
import quinzical.model.Avatar;
import quinzical.model.User;
import quinzical.model.Avatar.Slot;
import quinzical.util.AvatarFactory;
import quinzical.util.ImageLoader;
import quinzical.util.Modal;

/**
 * Controller class for the shop view. This handles allowing the user to preview
 * and purchase new cosmetic items for their avatar, using coins earned from
 * games. It also allows the user to apply cosmetics they have purchased.
 */
public class Shop {

    /**
     * See Shop.fxml for details on what each of these correspond to
     */
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
    private GameButton fxActionButton;

    @FXML
    private ImageView fxAccessorySlot;

    @FXML
    private ImageView fxHatSlot;

    @FXML
    private ImageView fxEyesSlot;

    @FXML
    private AnchorPane fxAccessoryEquipped;

    @FXML
    private AnchorPane fxHatEquipped;

    @FXML
    private AnchorPane fxEyesEquipped;

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

    /**
     * The avatarFactory responsible for rendering the avatar
     */
    private AvatarFactory avatarfactory;

    /**
     * Tracks if the user is previewing a cosmetic (ie they are viewing a cosmetic
     * they do not own)
     */
    private BooleanProperty isPreview;
    /**
     * Tracks what item the user currently has selected in the menu
     */
    private ObjectProperty<Cosmetic> selectedItem;

    /**
     * The number of coins the user has
     */

    private IntegerProperty coins;

    /**
     * This method runs when the screen is loaded and is responsible for all of the
     * page setup This includes loading all cosmetics into the shop view and making
     * sure the avatar is displaying the correct items
     */
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
            fxItemGridAll.getChildren().add(ShopListItem.create(c, selectedItem, ownedItems, e -> {
                selectedItem.set(c);
            }));
            fxItemGridOwned.getChildren().add(ShopListItem.create(c, selectedItem, ownedItems, e -> {
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
                        fxActionButton.setOnClick(e -> unequip(c));

                    } else {
                        // if not equipped
                        fxActionButton.setText("Equip " + c.getName());
                        fxActionButton.setOnClick(e -> equip(c));
                    }

                } else {
                    // if not owned
                    previewItem(c);
                    if (coins.get() >= c.getPrice()) {
                        // If user can afford
                        fxActionButton.setText("Purchase " + c.getName() + " for $" + c.getPrice());
                        fxActionButton.setDisable(false);
                        fxActionButton.setOnClick(e -> purchaseItem(c));

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

    /**
     * Equips the given cosmetic item onto the users avatar. This will be saved to
     * even once the user leaves the shop the item will still be equipped on the
     * avatar
     * 
     * @param item the cosmetic item to equip
     */
    public void equip(Cosmetic item) {
        switch (item.getSlot()) {
            case ACCESSORY:
                avatar.setAccessory(item);
                break;
            case HAT:
                avatar.setHat(item);
                break;
            case EYES:
                avatar.setEyes(item);
                break;
            default:
                break;
        }
        // Update display
        avatarfactory.set(avatar);
        User.getInstance().setAvatar(avatar);
        selectedItem.set(null);
        selectedItem.set(item);
        setEquipped();
    }

    /**
     * Unequips the specified cosmetic item. If the item specified is not currently
     * equipped on the avatar then nothing will happen.
     * 
     * @param item the cosmetic item to remove from the avtar
     */
    public void unequip(Cosmetic item) {
        switch (item.getSlot()) {
            case ACCESSORY:
                avatar.setAccessory(null);
                break;
            case HAT:
                avatar.setHat(null);
                break;
            case EYES:
                avatar.setEyes(null);
                break;
            default:
                break;
        }
        // Update display
        User.getInstance().setAvatar(avatar);
        selectedItem.set(null);
        setEquipped();
    }

    /**
     * Sets the avatar display to display only the cosmetics the avatar has equipped
     */
    private void setEquipped() {
        setViewEquippedItem(avatar.getHat(), fxHatSlot, fxHatEquipped);
        setViewEquippedItem(avatar.getAccessory(), fxAccessorySlot, fxAccessoryEquipped);
        setViewEquippedItem(avatar.getEyes(), fxEyesSlot, fxEyesEquipped);
    }

    /**
     * Helper method for @see setEquipped() This sets the equipped item into the
     * specified slot on the avatar. Note this does not equip the item, but rather
     * make sure the equipped item display is showing the correct items
     * 
     * @param item         the item to equip onto the avatar
     * @param slot         the slot to equip it in
     * @param equippedSlot the container element for the slot, which handles the
     *                     click events
     */
    private void setViewEquippedItem(Cosmetic item, ImageView slot, AnchorPane equippedSlot) {
        if (item != null) {
            slot.setImage(ImageLoader.loadImage("avatar/" + item.getIcon()));
            equippedSlot.setOnMouseClicked(e -> {
                selectedItem.set(item);
            });
            equippedSlot.getStyleClass().set(1, "equippedItem");
        } else {
            slot.setImage(null);
            equippedSlot.setOnMouseClicked(e -> {
            });
            equippedSlot.getStyleClass().set(1, "");
        }
    }

    /**
     * Hides all elements in the owned item list that are not owned by the player.
     */
    private void filterOwnedView() {
        fxItemGridOwned.getChildren().forEach(n -> {
            boolean visible = ownedItems.indexOf(n.getId()) != -1;
            n.setVisible(visible);
            n.setManaged(visible);
        });
        ;
    }

    /**
     * Resets the avatar to display the equipped cosmetics only
     */
    @FXML
    private void resetAvatar() {
        isPreview.set(false);
        avatarfactory.set(avatar);
        selectedItem.set(null);
    }

    /**
     * Allows the user to preview what an item will look like on their avatar.
     * 
     * @param item the cosmetic item to preview
     */
    private void previewItem(Cosmetic item) {
        isPreview.set(true);
        switch (item.getSlot()) {
            case ACCESSORY:
                avatarfactory.set(item, Slot.ACCESSORY);
                break;
            case HAT:
                avatarfactory.set(item, Slot.HAT);
                break;
            case EYES:
                avatarfactory.set(item, Slot.EYES);
                break;
            default:
                break;
        }
    }

    /**
     * Handler for allowing the user to purchase an item. It will first prompt the
     * user with a confirmation dialog asking them if they do want to buy the item.
     * If they answer yes the item will be equipped and added to their purchased
     * items and the correct amount of money will be removed from their balance
     * 
     * @param item
     */
    private void purchaseItem(Cosmetic item) {
        Modal.confirmation("Buy " + item.getName(),
                "Are you sure you want to purchase " + item.getName() + " for $" + item.getPrice() + "?", e -> {
                    coins.set(coins.get() - item.getPrice());
                    ownedItems.add(item.getId());
                    resetAvatar();
                    equip(item);
                });
    }

    /**
     * Helper function to retrieve a list of cosmetics and then sort them according
     * to price
     * 
     * @return an array list of all cosmetics, sorted by price
     */
    private ArrayList<Cosmetic> getItemList() {

        ArrayList<Cosmetic> items = new ArrayList<Cosmetic>();

        // Get hats
        items.addAll(Arrays.asList(Cosmetic.values()));

        // Sort item list by price
        items.sort((Cosmetic c1, Cosmetic c2) -> c1.getPrice() - (c2.getPrice()));

        return items;
    }
}
