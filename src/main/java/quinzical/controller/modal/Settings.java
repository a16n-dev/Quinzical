package quinzical.controller.modal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import quinzical.controller.View;
import quinzical.model.User;
import quinzical.util.IOManager;
import quinzical.util.Modal;
import quinzical.util.Router;
import quinzical.util.Sound;
import quinzical.util.State;
import quinzical.util.TTS;

/**
 * Controller for the settings modal. This allows the user to change various
 * settings such as speech volume, speed as well as animation
 * 
 * @author Alexander Nicholson
 */
public class Settings {

    @FXML
    private Slider fxSliderVolume;

    @FXML
    private Slider fxSliderSpeed;

    @FXML
    private Label fxLabelSpeed;

    @FXML
    private Label fxLabelVolume;

    @FXML
    private Slider fxMusicVolume;

    @FXML
    private Label fxMusicLabel;

    @FXML
    private Slider fxEffectVolume;

    @FXML
    private Label fxEffectLabel;

    @FXML
    private ToggleButton fxToggleAnimation;

    /**
     * The method to run when the fxml is initialised
     */
    public void initialize() {
        fxSliderVolume.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                TTS.getInstance().setVolume(newValue.intValue());
                fxLabelVolume.setText(newValue.intValue() + "%");
            }
        });

        fxSliderSpeed.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                TTS.getInstance().setSpeed((int) (newValue.intValue() * 1.6));
                fxLabelSpeed.setText(newValue.intValue() + "%");
            }
        });

        fxEffectVolume.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Sound.getInstance().setEffectVolume(newValue.intValue() / 100.0);
                fxEffectLabel.setText(Math.round((newValue.intValue())) + "%");
            }
        });

        fxToggleAnimation.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                User.getInstance().setforceDisableAnimation(!newValue);
            }
        });

        fxSliderSpeed.setValue(TTS.getInstance().getSpeed() / 1.6);
        fxSliderVolume.setValue(TTS.getInstance().getVolume());
        fxEffectVolume.setValue(Sound.getInstance().getEffectVolume() * 100);
        fxToggleAnimation.setSelected(!User.getInstance().hasForceDisableAnimation());
    }

    /**
     * Speaks a test message to the user so they can judge the new volume settings
     */
    @FXML
    private void speakTest() {
        TTS.getInstance().clearQueue();
        TTS.getInstance().speak("Test");
    }

    /**
     * Handler for when the user wants to close the dialog
     */
    @FXML
    private void handleClose() {
        Modal.hide();
    }

    /**
     * Handler for when the user presses the reset progress button
     */
    @FXML
    private void handleResetProgress() {
        String alertContent = (User.getInstance().getInternationalUnlocked()) ? " and lock the international questions."
                : ".";

        // Promt the user for confirmation first
        Modal.confirmation("Reset Progress",
                "Are you sure you want to reset your progress? This will remove all your rewards" + alertContent, e -> {

                    IOManager.clearState(State.USER);
                    IOManager.clearState(State.GAME);
                    Router.show(View.MAIN_MENU);
                });
    }

}
