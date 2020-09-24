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

    private void speakNext() {
        Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
                if(processQueue.peek() != null) {
                    var builder = processQueue.poll();
        
                    try {
                        Process p = builder.start();
                        try {
                            p.waitFor();
                            speakNext();
                        }
                        catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
				return null;
			}
        };
        new Thread(task).start();
    }

    public void speak(String text) {
        boolean alreadyRunning = processQueue.peek() != null;

        ProcessBuilder builder = new ProcessBuilder("espeak", "-x", text, "-a", Integer.toString(volume));
        processQueue.add(builder);
        if (!alreadyRunning) speakNext();
    }

    public void clearQueue() {
        processQueue.clear();
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
    public int getVolume() {
        return volume;
    }
}