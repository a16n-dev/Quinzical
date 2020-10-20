package quinzical.util;

import java.io.Serializable;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import quinzical.App;

public class Sound implements Serializable{


    private static final long serialVersionUID = 3339525790584853512L;

    private static Sound sound;

    private double volume;

    private transient MediaPlayer mp;

    public Sound(){
        volume = 1;
    }

    public void playSound(String fileName){
        //Start background music
		Media media = new Media(App.class.getResource("sound/"+fileName+".mp3").toString());
        mp = new MediaPlayer(media);
        mp.setVolume(volume);
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
        volume = v;
        mp.setVolume(v);
        persist();
    }

    public double getVolume(){
        return volume;
    }

    private static void persist() {
        if (sound == null) {
            IOManager.clearState(State.MUSIC);
        } else {
            IOManager.writeState(State.MUSIC, sound);
        }
    }
}
