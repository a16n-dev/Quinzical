package quinzical.util;

import com.jfoenix.controls.JFXDialog;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import quinzical.controller.Views;

public class Modal {

    private static StackPane root;
    
    private static JFXDialog dialog;

    public static void init(StackPane pane) {
    	root = pane;
    }
    
    public static void show(Views view) {
    	Region content = (Region) Router.loadFXML(view.getCenter());
        dialog = new JFXDialog();
        dialog.setContent(content);
        dialog.show(root);
    }
    
    public static void hide() {
    	dialog.close();
    }
}
