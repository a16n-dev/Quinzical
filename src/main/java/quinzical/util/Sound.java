package quinzical.util;

import java.io.Serializable;

import javafx.scene.media.AudioClip;
import quinzical.App;

/**
 * Handles the playing of audio outside of the text to speech functionality
 * 
 * @author Alexander Nicholson
 */
public class Sound implements Serializable{

    private static final long serialVersionUID = 3339525790584853512L;

    private static Sound sound;

    private double effectVolume;

    private static AudioClip ac;

    /**
     * Creates a new sound object
     */
    public Sound(){
        effectVolume = 1;
        ac = new AudioClip(App.class.getResource("sound/click.mp3").toString());
    }

    /**
     * Plays the click sound effect
     */
    public void playEffect(){
        ac.setVolume(effectVolume);
        ac.play();
    }

    /**
     * @return the existing Sound instance if it exists or a new one if it does not
     */
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

    /**
     * Set the sound effect volume
     * @param volume the new volume
     */
    public void setEffectVolume(double volume){
        effectVolume = volume;
        persist();
    }

    /**
     * @return the current value of the effect volume
     */
    public double getEffectVolume(){
        return effectVolume;
    }

    /**
     * Persists the Sound object to file
     */
    private static void persist() {
        if (sound == null) {
            IOManager.clearState(State.MUSIC);
        } else {
            IOManager.writeState(State.MUSIC, sound);
        }
    }
}
