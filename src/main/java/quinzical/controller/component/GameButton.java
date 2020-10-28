package quinzical.controller.component;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import quinzical.util.Sound;

/**
 * A custom button to use that plays a sound when pressed
 * This component has a custom property, onClick. whatever is passed to this
 * will become the onAction property of the button
 */
public class GameButton extends JFXButton {
    
    //The methods and fields here are setup so that onClick is a valid property, which javaFX uses reflection to determine
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

    public final ObjectProperty<EventHandler<ActionEvent>> onClickProperty() {
        return onClick;
    }

    public final void setOnClick(EventHandler<ActionEvent> value) {

        onActionProperty().set(e -> {
            Platform.runLater(() -> Sound.getInstance().playEffect("click"));
            value.handle(e);
        });
    }

    public final EventHandler<ActionEvent> getOnClick() {
        return onActionProperty().get();
    }
}