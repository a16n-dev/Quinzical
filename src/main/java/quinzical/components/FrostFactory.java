package quinzical.components;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * Adapted from: https://stackoverflow.com/questions/22622034/frosted-glass-effect-in-javafx
 */
public class FrostFactory {

    private static final double BLUR_AMOUNT = 50;

    private static final Effect frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 3);


    public static StackPane freeze(Node background, Region content, double width, double height) {
        Image frostImage = background.snapshot(
                new SnapshotParameters(),
                null
        );

        Stage stage = (Stage)background.getScene().getWindow();
        
        double vw = stage.getWidth();
        double vh = stage.getHeight();

        Translate translate = new Translate(); 

        translate.setX(-((vw - width)/2)); 
        translate.setY(-((vh - height)/2)); 

        
        ImageView frost = new ImageView(frostImage);
        frost.getTransforms().addAll(translate);
       
        Rectangle filler = new Rectangle(0, 0, width, height);
        filler.setFill(Color.rgb(0,0,0));

        Pane frostPane = new Pane(frost);
        frostPane.maxWidth(width);
        frostPane.maxHeight(height);
        frostPane.setEffect(frostEffect);

        StackPane frostView = new StackPane(
                filler,
                frostPane,
                content
        );

        Rectangle clipShape = new Rectangle(0, 0, width, height);
        clipShape.setArcWidth(5);
        clipShape.setArcHeight(5);
        frostView.setClip(clipShape);

        // clipShape.yProperty().bind(y);

        return frostView;
    }
}
