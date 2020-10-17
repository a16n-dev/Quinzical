package quinzical.util;

import java.io.IOException;

import com.jfoenix.controls.JFXDialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DialogEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import quinzical.components.FrostPane;
import quinzical.controller.ConfirmController;
import quinzical.controller.View;

public class Modal {

    private static StackPane root;

    private static JFXDialog dialog;

    public static void init(StackPane pane) {
        root = pane;
    }

    public static void show(View view) {
        Region content = (Region) Router.loadFXML(view.getCenter());
        makeDialog(content, 600, 400);
    }

    public static void show(View view, int width, int height) {
        Region content = (Region) Router.loadFXML(view.getCenter());
        makeDialog(content, width, height);
    }

    private static void makeDialog(Region content, double width, double height){
        FrostPane frosted = new FrostPane(root, content, width, height);

        frosted.setMaxWidth(width);
        frosted.setMaxHeight(height);

        dialog = new JFXDialog();
        dialog.setContent(frosted);
        dialog.show(root);
    }

    public static void hide() {
        dialog.close();
    }

    public static void confirmation(String title, String message, EventHandler<ActionEvent> event) {
        FXMLLoader loader = Router.manualLoad(View.MODAL_CONFIRM.getCenter());

        try {
            Region content = loader.load();

            ConfirmController controller = loader.getController();
            if (controller != null) {
                controller.init(title, message, e -> {
                    hide();
                    event.handle(e);
                });
            } else {
                System.out.println("Bruh");
            }
            makeDialog(content, 500, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
