package quinzical.util;

import java.io.IOException;
import java.util.ArrayDeque;

import javafx.concurrent.Task;

public class TTS {
    private ArrayDeque<ProcessBuilder> processQueue;

    // singleton code
    private static TTS tts;
    private int volume;
    private int speed;

    private TTS() {
        processQueue = new ArrayDeque<ProcessBuilder>();
        volume = 100;
        speed = 160;
    }

    public static TTS getInstance() {
        if (tts == null) {
            tts = new TTS();
        }
        return tts;
    }

    /**
     * Internal method used by the public speak function to handle the actual speaking
     * of messages. The message which is spoken is the first in the queue. Speaking of
     * messages runs in a separate thread. When a message has finished being spoken the
     * message queue is checked to see whether there is another message which must be spoken.
     * 
     * Does not speak if there are no messages in the queue. Messages must be added to the
     * queue with the speak method.
     */
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

    /**
     * Add a message to the queue of messages to be spoken by espeak. If this is the first
     * message within the queue, then the message will be immediately spoken. If it is not the
     * first message, then the message will be spoken once the messages earlier in the queue have
     * been spoken.
     * 
     * Calling this function does not guarantee that the message will be spoken; clearing the queue
     * may remove the message before it is spoken.
     * @param text
     */
    public void speak(String text) {
        boolean alreadyRunning = processQueue.peek() != null;

        ProcessBuilder builder = new ProcessBuilder("espeak", "-x", text, "-a", Integer.toString(volume), "-s", Integer.toString(speed));
        processQueue.add(builder);
        if (!alreadyRunning) speakNext();
    }

    /**
     * Method to cancel all current TTS messages which were previously requested to be voiced, but have
     * not yet completed.
     */
    public void clearQueue() {
        processQueue.clear();
    }

    // getters and setters
    public void setVolume(int volume) {
        this.volume = volume;
    }
    public int getVolume() {
        return volume;
    }

    public void setSpeed(int speed) {
        if (speed < 80) {
            this.speed = 80;
        }
        else if (speed > 160) {
            this.speed = 160;
        }
        else {
            this.speed = speed;
        }
    }
    public int getSpeed() {
        return speed;
    }

}