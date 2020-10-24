package quinzical.util;

import java.io.IOException;

import com.jfoenix.controls.JFXDialog;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import quinzical.controller.View;
import quinzical.controller.component.FrostPane;
import quinzical.controller.modal.Alert;
import quinzical.controller.modal.Confirm;

public class Modal {

    private static StackPane root;

    private static JFXDialog dialog;

    private static FrostPane frosted;

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

    public static void redirect(View view){
        Region content = (Region) Router.loadFXML(view.getCenter());
        frosted.setContent(content);
    }

    public static void show(View view, EventHandler<Event> event){
        Region content = (Region) Router.loadFXML(view.getCenter());
        JFXDialog d = makeDialog(content, 800, 500);
        d.setOnDialogClosed(event);

    }

    private static JFXDialog makeDialog(Region content, double width, double height){
        frosted = new FrostPane(root, content, width, height);

        frosted.setMaxWidth(width);
        frosted.setMaxHeight(height);

        dialog = new JFXDialog();
        dialog.setContent(frosted);
        dialog.show(root);

        return dialog;
    }

    public static void hide() {
        dialog.close();
    }

    public static void confirmation(String title, String message, EventHandler<ActionEvent> event) {
        FXMLLoader loader = Router.manualLoad(View.MODAL_CONFIRM.getCenter());

        try {
            Region content = loader.load();

            Confirm controller = loader.getController();
            if (controller != null) {
                controller.init(title, message, e -> {
                    hide();
                    event.handle(e);
                });
            }
            makeDialog(content, 500, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void alert(String title, String message) {
        FXMLLoader loader = Router.manualLoad(View.MODAL_ALERT.getCenter());

        try {
            Region content = loader.load();

            Alert controller = loader.getController();
            if (controller != null) {
                controller.init(title, message);
            }
            makeDialog(content, 500, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
