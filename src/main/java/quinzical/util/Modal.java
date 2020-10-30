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

/**
 * Class responsible for creating dialog boxes to display over top of the
 * content.
 *
 * @author Alexander Nicholson, Peter Geodeke
 */
public class Modal {

    private static StackPane root;

    private static JFXDialog dialog;

    private static FrostPane frosted;

    /**
     * Initialises the modal class
     * 
     * @param pane the root pane of the display to place the modal window into
     */
    public static void init(StackPane pane) {
        root = pane;
    }

    /**
     * Show a modal dialog to the user
     * 
     * @param view the specified view that should be shown
     */
    public static void show(View view) {
        Region content = (Region) ViewLoader.loadFXML(view.getCenter());
        makeDialog(content, 600, 400);
    }

    /**
     * Show a modal dialog to the user
     * 
     * @param view   the specified view that should be shown
     * @param width  the width of the dialog box
     * @param height the height of the dialog box
     */
    public static void show(View view, int width, int height) {
        Region content = (Region) ViewLoader.loadFXML(view.getCenter());
        makeDialog(content, width, height);
    }

    /**
     * Changes the view of the existing modal dialog
     * 
     * @param view the new view to switch to
     */
    public static void redirect(View view) {
        Region content = (Region) ViewLoader.loadFXML(view.getCenter());
        frosted.setContent(content);
    }

    /**
     * Show a modal dialog, and trigger an event when the user closes it
     * 
     * @param view  the view to show in the dialog
     * @param event the handler to run once the modal is closed
     */
    public static void show(View view, EventHandler<Event> event) {
        Region content = (Region) ViewLoader.loadFXML(view.getCenter());
        JFXDialog d = makeDialog(content, 800, 500);
        d.setOnDialogClosed(event);

    }

    /**
     * Helper function to create a dialog of specific dimensions
     * 
     * @param content the content to place in the dialog box
     * @param width   the width of the dialog box
     * @param height  the height of the dialog box
     * @return
     */
    private static JFXDialog makeDialog(Region content, double width, double height) {
        frosted = new FrostPane(root, content, width, height);

        frosted.setMaxWidth(width);
        frosted.setMaxHeight(height);

        dialog = new JFXDialog();
        dialog.setContent(frosted);
        dialog.show(root);

        return dialog;
    }

    /**
     * Hides the modal
     */
    public static void hide() {
        dialog.close();
    }

    /**
     * Shows a confirmation dialog to the user
     * 
     * @param title   the title to display in the dialog
     * @param message the message to display, asking the user to confirm
     * @param event   the event handler to run when the user confirms the action
     */
    public static void confirmation(String title, String message, EventHandler<ActionEvent> event) {
        FXMLLoader loader = ViewLoader.manualLoad(View.MODAL_CONFIRM.getCenter());

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

    /**
     * Show an alert to the user
     * 
     * @param title   the title to display in the dialog
     * @param message the alert message to display to the user
     */
    public static void alert(String title, String message) {
        FXMLLoader loader = ViewLoader.manualLoad(View.MODAL_ALERT.getCenter());

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
