package quinzical.util;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import quinzical.App;
import quinzical.avatar.Accessory;
import quinzical.avatar.Eyes;
import quinzical.avatar.Hat;
import quinzical.model.Avatar;

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

    private Image fxBase;

    private Image fxBody;

    private Image fxHat;

    private Image fxEyes;

    private Hat savedHat;

    private Accessory savedAccessory;

    private Eyes savedEyes;

    /**
     * Sets the image size
     */
    private IntegerProperty size = new SimpleIntegerProperty(320);

    private boolean animated = false;

    private String ext = ".png";

    /**
     * Constructor
     * @param container the StackPane to place the avatar in
     */
    public AvatarFactory(StackPane container){
        init(container);
    }

    /**
     * Constructor overload to create an avatar of a certain size
     * @param container the StackPane to place the avatar in
     * @param scale the size in pixels of the avatar
     */
    public AvatarFactory(StackPane container, int scale){
        size.set(scale);
        init(container);
    }

    /**
     * Initializes the avatar to load into the specified container but does not load
     * the avatar
     * 
     * @param container the StackPane to display the avatar in
     */
    public void init(StackPane container) {
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

        fxBaseLayer.setImage(ImageLoader.loadImage(RESOURCE_PATH + "char_idle" + ext));
        fxFrame.getChildren().set(0, fxBaseLayer);
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

    public void set(Hat hat){
        if(hat != savedHat){
            if(hat != null){
                fxHatLayer.setImage(ImageLoader.loadImage(RESOURCE_PATH + hat.getFile() + "_idle" + ext));
                
            } else if (hat == null){
                fxHatLayer.setImage(null);
            }
            savedHat = hat;
            fxFrame.getChildren().set(3, fxHatLayer);
        }

    }

    public void set(Accessory accessory){
        if(accessory != savedAccessory){
            if(accessory != null){
                fxBodyLayer.setImage(ImageLoader.loadImage(RESOURCE_PATH + accessory.getFile() + "_idle" + ext));
                
            } else if (accessory == null){
                fxBodyLayer.setImage(null);
            }
            savedAccessory = accessory;
            fxFrame.getChildren().set(1, fxBodyLayer);
        }
    }

    public void set(Eyes eyes){
        if(eyes != savedEyes){
            if(eyes != null){
                fxEyesLayer.setImage(ImageLoader.loadImage(RESOURCE_PATH + eyes.getFile() + "_idle" + ext));
                
            } else if (eyes == null){
                fxEyesLayer.setImage(null);
            }
            savedEyes = eyes;
            fxFrame.getChildren().set(2, fxEyesLayer);
        }

    }

    public void set(Avatar avatar){
        set(avatar.getHat());
        set(avatar.getAccessory());
        set(avatar.getEyes());
    }
}
