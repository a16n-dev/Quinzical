package quinzical.util;

import java.io.Serializable;

import javafx.scene.media.AudioClip;
import quinzical.App;

public class Sound implements Serializable{


    private static final long serialVersionUID = 3339525790584853512L;

    private static Sound sound;

    private double effectVolume;

    private static AudioClip ac;

    public Sound(){
        effectVolume = 1;
        ac = new AudioClip(App.class.getResource("sound/click.mp3").toString());
    }

    public void playEffect(String fileName){
        ac.setVolume(effectVolume);
        ac.play();
    }

    public static Sound getInstance() {
        if (sound == null) {
            // Attempt to read state from file
            sound = IOManager.readState(State.MUSIC);
            
            if (sound == null) {
                sound = new Sound();
                persist();
            } else {
                ac = new AudioClip(App.class.getResource("sound/click.mp3").toString());
            }
        }
        return sound;
    }

    public void setEffectVolume(double v){
        effectVolume = v;
        persist();
    }

    public double getEffectVolume(){
        return effectVolume;
    }

    private static void persist() {
        if (sound == null) {
            IOManager.clearState(State.MUSIC);
        } else {
            IOManager.writeState(State.MUSIC, sound);
        }
    }
}
