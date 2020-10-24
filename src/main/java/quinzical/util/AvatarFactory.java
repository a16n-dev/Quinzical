package quinzical.util;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import quinzical.avatar.Accessory;
import quinzical.avatar.Cosmetic;
import quinzical.avatar.Eyes;
import quinzical.avatar.Hat;
import quinzical.model.Avatar;
import quinzical.model.User;

/**
 * This class handles the user's avatar and is responsible for rendering it
 * correctly
 */
public class AvatarFactory {

    /**
     * Reference to where avatar files are stored
     */
    private final static String RESOURCE_PATH = "avatar/";

    /**
     * The stack pane to render the layers onto
     */
    private StackPane fxFrame;

    /**
     * Represents the base layer of the character
     */
    private ImageView fxBaseLayer;

    /**
     * Represents the accessories layer of the character
     */
    private ImageView fxBodyLayer;

    /**
     * Represents the hat layer of the character
     */
    private ImageView fxHatLayer;

    private ImageView fxEyesLayer;

    private Hat savedHat;

    private Accessory savedAccessory;

    private Eyes savedEyes;

    private Image fxBase = null;

    private Image fxBody = null;

    private Image fxHat = null;

    private Image fxEyes = null;

    /**
     * Sets the image size
     */
    private IntegerProperty size = new SimpleIntegerProperty(320);

    private String ext = ".png";

    /**
     * Constructor
     * 
     * @param container the StackPane to place the avatar in
     */
    public AvatarFactory(StackPane container) {
        init(container);
    }

    /**
     * Constructor overload to create an avatar of a certain size
     * 
     * @param container the StackPane to place the avatar in
     * @param scale     the size in pixels of the avatar
     */
    public AvatarFactory(StackPane container, int scale) {
        size.set(scale);
        init(container);
    }

    /**
     * Constructor
     * 
     * @param container the StackPane to place the avatar in
     * @param animated  if the avatar should be animated or not
     */
    public AvatarFactory(StackPane container, boolean animated) {
        ext = animated ? ".gif" : ".png";
        init(container);
    }

    /**
     * Constructor overload to create an avatar of a certain size
     * 
     * @param container the StackPane to place the avatar in
     * @param scale     the size in pixels of the avatar
     * @param animated  if the avatar should be animated or not
     */
    public AvatarFactory(StackPane container, int scale, boolean animated) {
        size.set(scale);
        ext = animated ? ".gif" : ".png";
        init(container);
    }

    /**
     * Initializes the avatar to load into the specified container but does not load
     * the avatar
     * 
     * @param container the StackPane to display the avatar in
     */
    public void init(StackPane container) {
        // override animation settings from user preferences
        if (User.getInstance().getAvatar().hasForceDisableAnimation()) {
            ext = ".png";
        }

        fxFrame = container;
        container.getChildren().setAll(new ImageView(), new ImageView(), new ImageView(), new ImageView());

        fxBaseLayer = new ImageView();
        fxBaseLayer.fitWidthProperty().bind(size);
        fxBaseLayer.fitHeightProperty().bind(size);

        fxHatLayer = new ImageView();
        fxHatLayer.fitWidthProperty().bind(size);
        fxHatLayer.fitHeightProperty().bind(size);

        fxBodyLayer = new ImageView();
        fxBodyLayer.fitWidthProperty().bind(size);
        fxBodyLayer.fitHeightProperty().bind(size);

        fxEyesLayer = new ImageView();
        fxEyesLayer.fitWidthProperty().bind(size);
        fxEyesLayer.fitHeightProperty().bind(size);

        fxFrame.getChildren().setAll(fxBaseLayer, fxHatLayer, fxBodyLayer, fxEyesLayer);

        fxBase = ImageLoader.loadImage(RESOURCE_PATH + "char_idle" + ext);
        fxBaseLayer.setImage(fxBase);
    }

    /**
     * Sets the size of the avatar to the specified size, in pixels. The default
     * value is 320.
     * 
     * @param scale
     */
    public void setScale(int scale) {
        size.set(scale);
    }

    public void set(Hat hat) {
        if (! (hat == savedHat)) {
            fxHat = setSlot(hat);
            savedHat = hat;
            fxHatLayer.setImage(fxHat);
        }
    }

    public void set(Accessory accessory) {
        if (! (accessory == savedAccessory)) {
            fxBody = setSlot(accessory);
            savedAccessory = accessory;
            fxBodyLayer.setImage(fxBody);
        }
    }

    public void set(Eyes eyes) {
        if (!(eyes == savedEyes)) {
            fxEyes = setSlot(eyes);
            savedEyes = eyes;
            fxEyesLayer.setImage(fxEyes);
        }
    }

    private Image setSlot(Cosmetic item) {
        Image slot = null;

        if (item != null) {
            slot = ImageLoader.loadImage(RESOURCE_PATH + item.getFile() + "_idle" + ext);
        }

        return slot;
    }

    public void set(Avatar avatar) {
        set(avatar.getHat());
        set(avatar.getAccessory());
        set(avatar.getEyes());
    }
}
