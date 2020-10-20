package quinzical.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import quinzical.model.User;
import quinzical.util.Modal;
import quinzical.util.Sound;
import quinzical.util.TTS;

public class SettingsController {
  public Slider fxSliderVolume;
  public Slider fxSliderSpeed;
  public Label fxLabelSpeed;
  public Label fxLabelVolume;

  public Slider fxMusicVolume;
  public Label fxMusicLabel;

  public ToggleButton fxToggleAnimation;
	
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
                TTS.getInstance().setSpeed(newValue.intValue());
                fxLabelSpeed.setText(Math.round(((float)newValue.intValue() / 160) * 100) + "%");
            }
        });

        fxMusicVolume.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Sound.getInstance().setVolume(newValue.intValue()/100.0);
                fxMusicLabel.setText(Math.round((newValue.intValue())) + "%");
            }
        });

        fxToggleAnimation.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                User.getInstance().setforceDisableAnimation(!newValue);
            }
        });

        fxSliderSpeed.setValue(TTS.getInstance().getSpeed());
        fxSliderVolume.setValue(TTS.getInstance().getVolume());
        fxMusicVolume.setValue(Sound.getInstance().getVolume() * 100);
        fxToggleAnimation.setSelected(!User.getInstance().hasForceDisableAnimation());
    }

    public void speakTest() {
        TTS.getInstance().clearQueue();
        TTS.getInstance().speak("Test");
    }

    public void handleClose(){
        Modal.hide();

    }
}

