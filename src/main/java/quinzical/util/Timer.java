package quinzical.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

/**
 * Util module which is given a Label JavaFX element and turns it into a timer
 * display.
 */
public class Timer {
    private float tickPeriod = 0.01f;
    private int maxTime;
    private float currentTime;
    private Label fxElement;
    private ProgressBar fxProgressLeft;
    private ProgressBar fxProgressRight;
    private Timeline timeline;

    public static Timer instance;

    /**
     * Private constructor
     */
    private Timer() { }

    /**
     * Get the singleton instance of the class
     * @return instance
     */
    public static Timer getInstance() {
        if (instance == null) {
            instance = new Timer();
        }
        return instance;
    }

    /**
     * Provide the timer with the elements it needs to update the display and the
     * maxTime to count down from.
     * 
     * @param fxElement       the element to display the time (in seconds) on
     * @param fxProgressLeft  the left half of the timer progress bar
     * @param fxProgressRight the right half of the timer progress bar
     * @param maxTime         the time to count down from
     */
    public void set(Label fxElement, ProgressBar fxProgressLeft, ProgressBar fxProgressRight, int maxTime) {
        instance.fxElement = fxElement;
        instance.fxProgressLeft = fxProgressLeft;
        instance.fxProgressRight = fxProgressRight;
        instance.maxTime = maxTime;
    }

    /**
     * Restart the timer from the maxTime. Decrements the time value displayed on
     * the Label element possessed by the timer every second until the timer has
     * finished, at which the provided event is executed.
     * 
     * JavaFX does not support the decrease of a progress bar from both ends, so two
     * progress bars are used to mock this behaviour.
     * 
     * @param event The event to be executed once the timer finishes
     */
    public void start(EventHandler<ActionEvent> event) {
        currentTime = maxTime;
        fxElement.setText(String.valueOf(currentTime));
        timeline = new Timeline(new KeyFrame(Duration.seconds(tickPeriod), e -> {
            currentTime -= tickPeriod;
            fxElement.setText(String.valueOf((int) Math.ceil(currentTime)));
            double prog = currentTime / maxTime;
            double shift = 0.07;
            fxProgressLeft.setProgress((1 - shift) * prog + shift);
            fxProgressRight.setProgress((1 - shift) * prog + shift);
        }));
        timeline.setCycleCount((int) (maxTime / tickPeriod));
        timeline.setOnFinished(event);
        timeline.play();
    }

    /**
     * Stop the timer and reset the position to the beginning
     */
    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Pause the timer. Resuming is possible
     */
    public void pause() {
        timeline.pause();
    }

    /**
     * Resume the timer if it has been paused
     */
    public void resume() {
        timeline.play();
    }

    /**
     * 
     * @return the current time of the timer
     */
    public float getTime() {
        return currentTime;
    }

    /**
     * 
     * @return the time which has elapsed on the current timer cycle
     */
    public float getElapsed() {
        return maxTime - currentTime;
    }
}
