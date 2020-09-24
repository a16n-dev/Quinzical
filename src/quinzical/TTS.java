package quinzical;

import java.io.IOException;
import java.util.ArrayDeque;

import javafx.concurrent.Task;

public class TTS {
    private ArrayDeque<ProcessBuilder> processQueue;

    // singleton code
    private static TTS tts;
    private int volume;

    private TTS() {
        processQueue = new ArrayDeque<ProcessBuilder>();
        volume = 100;
    }

    public static TTS getInstance() {
        if (tts == null) {
            tts = new TTS();
        }
        return tts;
    }
}