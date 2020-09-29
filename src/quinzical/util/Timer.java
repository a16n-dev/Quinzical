package quinzical.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Labeled;
import javafx.util.Duration;

/**
 * Util module which is given a labeled JavaFX element and turns it into a timer display.
 */
public class Timer {
    private int maxTime;
    private int currentTime;
    private Labeled element;
    private Timeline timeline;

    public Timer(Labeled element, int maxTime) {
        this.element = element;
        this.maxTime = maxTime;
    }
    public Timer(Labeled element) {
        this.element = element;
        this.maxTime = 60;
    }

    /**
     * Restart the timer from the maxTime. Decrements the time value displayed on the labeled element
     * possessed by the timer every second until the timer has finished, at which the provided event
     * is executed
     * @param event The event to be executed once the timer finishes
     */
    public void startTimer(EventHandler<ActionEvent> event) {
        currentTime = maxTime;
        element.setText(String.valueOf(currentTime));
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            currentTime -= 1;
            element.setText(String.valueOf(currentTime));
        }));
        timeline.setCycleCount(maxTime);
        timeline.setOnFinished(event);
        timeline.play();
    }

    /**
     * Stop the timer and reset the position to the beginning
     */
    public void stopTimer() {
        timeline.stop();
    }
    /**
     * Pause the timer. Resuming is possible
     */
    public void pauseTimer() {
        timeline.pause();
    }
    /**
     * Resume the timer if it has been paused
     */
    public void resumeTimer() {
        timeline.play();
    }
    
    public int getTime() {
        return currentTime;
    }
}
