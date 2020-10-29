package quinzical.controller.component;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import quinzical.util.Sound;

/**
 * A custom button to use that plays a sound when pressed This component has a
 * custom property, onClick. Whatever is passed to this will become the onAction
 * property of the button
 * 
 * @author Alexander Nicholson
 */
public class GameButton extends JFXButton {

    // The methods and fields here are setup so that onClick is a valid property,
    // which javaFX uses reflection to determine
    private ObjectProperty<EventHandler<ActionEvent>> onClick = new ObjectPropertyBase<EventHandler<ActionEvent>>() {
        @Override
        protected void invalidated() {
            setEventHandler(ActionEvent.ACTION, get());
        }

        @Override
        public Object getBean() {
            return GameButton.this;
        }

        @Override
        public String getName() {
            return "onClick";
        }
    };

    /**
     * @return the onClickProperty for the GameButton
     */
    public final ObjectProperty<EventHandler<ActionEvent>> onClickProperty() {
        return onClick;
    }

    /**
     * Sets the onclick handler to the specified EventHandler
     * @param value the EventHanlder to handle the event when the user click the button
     */
    public final void setOnClick(EventHandler<ActionEvent> value) {

        onActionProperty().set(e -> {
            Platform.runLater(() -> Sound.getInstance().playEffect("click"));
            value.handle(e);
        });
    }

    /**
     * @return the EventHandler for the onClick event
     */
    public final EventHandler<ActionEvent> getOnClick() {
        return onActionProperty().get();
    }
}
