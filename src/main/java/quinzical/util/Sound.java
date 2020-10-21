package quinzical.util;

import java.io.Serializable;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import quinzical.App;

public class Sound implements Serializable{


    private static final long serialVersionUID = 3339525790584853512L;

    private static Sound sound;

    private double musicVolume;

    private double effectVolume;

    private transient MediaPlayer mp;

    private AudioClip ac = new AudioClip(App.class.getResource("sound/click.mp3").toString());

    public Sound(){
        musicVolume = 1;
        effectVolume = 1;
    }

    public void playEffect(String fileName){
        ac.setVolume(effectVolume);
        ac.play();
    }

    public void playSound(String fileName){
        //Start background music
		Media media = new Media(App.class.getResource("sound/"+fileName+".mp3").toString());
        mp = new MediaPlayer(media);
        mp.setVolume(musicVolume);
        mp.play();
    }

    public static Sound getInstance() {
        if (sound == null) {
            // Attempt to read state from file
            sound = IOManager.readState(State.MUSIC);
            if (sound == null) {
                sound = new Sound();
                persist();
            }
        }
        return sound;
    }

    public void setVolume(double v){
        musicVolume = v;
        mp.setVolume(v);
        persist();
    }

    public double getVolume(){
        return musicVolume;
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
