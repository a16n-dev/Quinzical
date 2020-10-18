package quinzical.util;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import quinzical.App;

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

    /**
     * Sets the image size
     */
    private IntegerProperty size = new SimpleIntegerProperty(320);

    private boolean animated = false;

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
        fxBaseLayer = new ImageView();
        fxBaseLayer.fitWidthProperty().bind(size);
        fxBaseLayer.fitHeightProperty().bind(size);

        fxHatLayer = new ImageView();
        fxHatLayer.fitWidthProperty().bind(size);
        fxHatLayer.fitHeightProperty().bind(size);

        fxBodyLayer = new ImageView();
        fxBodyLayer.fitWidthProperty().bind(size);
        fxBodyLayer.fitHeightProperty().bind(size);
    }

    /**
     * Renders the avatar into the previously specified StackPane
     */
    public void render() {
        try {
            // fetch images
            // TODO put this in a background thread
            String ext = animated ? ".gif" : ".png";          

            fxBaseLayer.setImage(ImageLoader.loadImage(RESOURCE_PATH + "char_idle" + ext));
            fxBodyLayer.setImage(ImageLoader.loadImage(RESOURCE_PATH + "mustache_idle" + ext));
            fxHatLayer.setImage(ImageLoader.loadImage(RESOURCE_PATH + "beanie_idle" + ext));
            fxFrame.getChildren().setAll(fxBaseLayer, fxBodyLayer, fxHatLayer);
        } catch (Exception e) {
            System.out.println("Error, avatar frame not set");
        }
    }

    /**
     * Shorthand method for init() and render()
     * 
     * @param container the StackPane to display the avatar in
     */
    public void render(StackPane container) {
        init(container);
        render();
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
}
