package quinzical.util;

import com.jfoenix.controls.JFXDialog;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import quinzical.controller.Views;

public class Modal {

    private static StackPane root;

    public static void init(StackPane pane) {
    	root = pane;
    }
    
    public static void show(Views view) {
    	Region content = (Region) Router.loadFXML(view.getCenter());
        JFXDialog dialog = new JFXDialog();
        dialog.setContent(content);
        dialog.show(root);
    }
    
}
