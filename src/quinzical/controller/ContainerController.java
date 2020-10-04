package quinzical.controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import quinzical.util.Modal;
import quinzical.util.TTS;

public class ContainerController {
    public Slider sliderVolume;
    public Slider sliderSpeed;
    public Label labelSpeed;
    public Label labelVolume;

    @FXML
    public void hideModal(){
        Modal.hide();
    }

    public void initialize() {
		sliderSpeed.setValue(TTS.getInstance().getSpeed());
		sliderVolume.setValue(TTS.getInstance().getVolume());
	}

    public void handleSpeechVolumeSliderChange(ObservableValue<Number> ovn, Number before, Number after) {
        TTS.getInstance().setVolume(after.intValue());
        labelVolume.setText(after.intValue() + "%");
	}

	public void handleSpeechSpeedSliderChange(ObservableValue<Number> ovn, Number before, Number after) {
        TTS.getInstance().setSpeed(after.intValue());
        labelSpeed.setText(after.intValue() + " WPS");
    }

    public void speakTest() {
        TTS.getInstance().clearQueue();
        TTS.getInstance().speak("Test");
    }
}
