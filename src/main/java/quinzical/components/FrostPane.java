package quinzical.components;

import javafx.beans.property.ReadOnlyDoubleProperty;
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
 * Adapted from:
 * https://stackoverflow.com/questions/22622034/frosted-glass-effect-in-javafx
 * This generates a pane which blurs the background behind the specified
 * content.
 */
public class FrostPane extends StackPane {

    private static final double BLUR_AMOUNT = 50;

    private static final Effect frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 3);

    private double setWidth;

    private double setHeight;

    private Image frostImage;

    private ImageView frost;

    private ReadOnlyDoubleProperty vw;
    private ReadOnlyDoubleProperty vh;

    public FrostPane(Node background, Region content, double width, double height) {

        Stage stage = (Stage) background.getScene().getWindow();

        getChildren().addAll(new Pane(), new Pane(), new Pane());

        vw = stage.widthProperty();
        vh = stage.heightProperty();

        setWidth = width;
        setHeight = height;

        snapshot(background);

        setContent(content);
        renderFrost();
        renderFiller();

        vw.addListener(c -> {
            positionFrost();
        });

        vh.addListener(c -> {
            positionFrost();
        });
    }

    /**
     * Generates an image of the node tree contained in background
     * 
     * @param background
     */
    private void snapshot(Node background) {
        frostImage = background.snapshot(new SnapshotParameters(), null);
    }

    public void setContent(Region content) {
        getChildren().set(2, content);

        Rectangle clipShape = new Rectangle(0, 0, setWidth, setHeight);
        clipShape.setArcWidth(5);
        clipShape.setArcHeight(5);
        setClip(clipShape);
    }

    private void renderFrost() {

        frost = new ImageView(frostImage);
        frost.fitWidthProperty().bind(vw);
        frost.fitHeightProperty().bind(vh);

        Pane frostPane = new Pane(frost);
        frostPane.maxWidth(setWidth);
        frostPane.maxHeight(setHeight);
        frostPane.setEffect(frostEffect);

        getChildren().set(1, frostPane);
        positionFrost();
    }

    private void positionFrost() {
        Translate translate = new Translate();

        translate.setX(-((vw.get() - setWidth) / 2));
        translate.setY(-((vh.get() - setHeight) / 2));

        frost.getTransforms().setAll(translate);
    }

    private void renderFiller() {
        Rectangle filler = new Rectangle(0, 0, setWidth, setHeight);
        filler.setFill(Color.rgb(0, 0, 0));
        getChildren().set(0, filler);
    }
}
