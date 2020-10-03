package quinzical.util;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Modal {

    private static AnchorPane modalContainer;

    public static void show(){
        modalContainer.setVisible(true);
        modalContainer.setManaged(true);
    }

    public static void hide(){
        modalContainer.setVisible(false);
        modalContainer.setManaged(false);
    }

    public static void setModalContainer(AnchorPane container){
        modalContainer = container;
    }
}

