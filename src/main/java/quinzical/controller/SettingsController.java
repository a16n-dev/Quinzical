package quinzical.controller;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import quinzical.util.TTS;

public class SettingsController {
  public Slider fxSliderVolume;
  public Slider fxSliderSpeed;
  public Label fxLabelSpeed;
  public Label fxLabelVolume;
	
    public void initialize() {
    	//Initialise modal
    	
        fxSliderSpeed.setValue(TTS.getInstance().getSpeed());
        fxSliderVolume.setValue(TTS.getInstance().getVolume());
    }
    
    public void handleSpeechVolumeSliderChange(ObservableValue<Number> ovn, Number before, Number after) {
        TTS.getInstance().setVolume(after.intValue());
        fxLabelVolume.setText(after.intValue() + "%");
    }

    public void handleSpeechSpeedSliderChange(ObservableValue<Number> ovn, Number before, Number after) {
        TTS.getInstance().setSpeed(after.intValue());
        fxLabelSpeed.setText(Math.round(((float)after.intValue() / 160) * 100) + "%");
    }
    
    public void speakTest() {
        TTS.getInstance().clearQueue();
        TTS.getInstance().speak("Test");
    }
}

