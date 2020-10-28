package quinzical.controller.modal;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import quinzical.model.User;
import quinzical.util.Modal;
import quinzical.util.Sound;
import quinzical.util.TTS;

public class Settings {
  public Slider fxSliderVolume;
  public Slider fxSliderSpeed;
  public Label fxLabelSpeed;
  public Label fxLabelVolume;

  public Slider fxMusicVolume;
  public Label fxMusicLabel;

  public Slider fxEffectVolume;
  public Label fxEffectLabel;

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
                TTS.getInstance().setSpeed((int)(newValue.intValue() * 1.6));
                fxLabelSpeed.setText(newValue.intValue() + "%");
            }
        });

        fxMusicVolume.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Sound.getInstance().setVolume(newValue.intValue()/100.0);
                fxMusicLabel.setText(Math.round((newValue.intValue())) + "%");
            }
        });

        fxEffectVolume.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Sound.getInstance().setEffectVolume(newValue.intValue()/100.0);
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
        fxMusicVolume.setValue(Sound.getInstance().getVolume() * 100);
        fxEffectVolume.setValue(Sound.getInstance().getEffectVolume() * 100);
        fxToggleAnimation.setSelected(!User.getInstance().hasForceDisableAnimation());
    }

    public void speakTest() {
        TTS.getInstance().clearQueue();
        TTS.getInstance().speak("Test");
    }

    public void handleClose(){
        Modal.hide();
    }

    @FXML
	public void handleResetProgress() {
		String alertContent = (User.getInstance().getInternationalUnlocked()) ? " and lock the international questions." : ".";

        Modal.confirmation("Reset Progress", "Are you sure you want to reset your progress? This will remove all your rewards" + alertContent, e -> {

			User.getInstance().clearRewards();
			User.getInstance().setInternational(false);
        });
    }				
	
}
