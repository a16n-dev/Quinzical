package quinzical.util;

import javafx.scene.image.Image;
import quinzical.App;

public class ImageLoader {

    public static Image loadImage(String path) {     
            Image image = new Image(App.class.getResource(path).toString(), true);
            return image;
        }
    }

