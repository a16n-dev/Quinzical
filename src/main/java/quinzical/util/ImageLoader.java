package quinzical.util;

import java.util.HashMap;
import javafx.scene.image.Image;
import quinzical.App;

public class ImageLoader {

    private static HashMap<String, Image> imageCache = new HashMap<String, Image>();

    public static Image loadImage(String path) {
        String ext = path.substring(path.lastIndexOf("."));
        if(ext.equals(".gif") && false){
            if(imageCache.get(path) == null){
                Image image = new Image(App.class.getResource(path).toString(), true);
                imageCache.put(path, image);
            }
            return imageCache.get(path);
        } else {
            Image image = new Image(App.class.getResource(path).toString(), true);
            return image;
        }

    }
}
