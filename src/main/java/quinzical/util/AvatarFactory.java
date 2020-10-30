package quinzical.util;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import quinzical.avatar.Cosmetic;
import quinzical.model.Avatar;
import quinzical.model.User;
import quinzical.model.Avatar.Slot;

/**
 * This class handles the user's avatar and is responsible for rendering it
 * correctly
 * 
 * @author Alexander Nicholson
 */
public class AvatarFactory {

    /**
     * Reference to where avatar files are stored
     */
    private final static String RESOURCE_PATH = "avatar/";


    private StackPane fxFrame;

    private ImageView fxBaseLayer;

    private ImageView fxBodyLayer;

    private ImageView fxHatLayer;

    private ImageView fxEyesLayer;

    private Cosmetic savedHat;

    private Cosmetic savedAccessory;

    private Cosmetic savedEyes;

    private Image fxBase;

    private Image fxBody;

    private Image fxHat;

    private Image fxEyes;

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

         fxBase = null;
         fxBody = null;
         fxHat = null;
         fxEyes = null;

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

    public void set(Cosmetic item, Slot slot) {
        switch (slot) {
            case ACCESSORY:
                if (!(item == savedAccessory)) {
                    fxBody = setSlot(item);
                    savedAccessory = item;
                    fxBodyLayer.setImage(fxBody);
                }
                break;
            case EYES:
                if (!(item == savedEyes)) {
                    fxEyes = setSlot(item);
                    savedEyes = item;
                    fxEyesLayer.setImage(fxEyes);
                }
                break;
            case HAT:
                if (!(item == savedHat)) {
                    fxHat = setSlot(item);
                    savedHat = item;
                    fxHatLayer.setImage(fxHat);
                }
                break;
            default:
                break;

        }

    }

    /**
     * Loads the specified cosmetic item image, as either a png or gif if it should be animated
     * @param item the cosmetic item to load
     * @return a Image of the cosmetic item
     */
    private Image setSlot(Cosmetic item) {
        Image slot = null;

        if (item != null) {
            slot = ImageLoader.loadImage(RESOURCE_PATH + item.getFile() + "_idle" + ext);
        }

        return slot;
    }

    /**
     * Sets the avatar for this class to render
     * @param avatar the avatar to render
     */
    public void set(Avatar avatar) {
        set(avatar.getHat(), Slot.HAT);
        set(avatar.getAccessory(), Slot.ACCESSORY);
        set(avatar.getEyes(), Slot.EYES);
    }
}
