package quinzical.util;

import javafx.scene.image.Image;
import quinzical.App;

/**
 * This is a utility class responsible for loading images
 * 
 * @author Alexander Nicholson
 */
public class ImageLoader {

    /**
     * @param path the path to the image to load
     * @return the loaded image
     */
    public static Image loadImage(String path) {     
            Image image = new Image(App.class.getResource(path).toString(), true);
            return image;
        }
    }

