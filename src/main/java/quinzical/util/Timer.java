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

    public Timer(Label fxElement, ProgressBar fxProgressLeft, ProgressBar fxProgressRight, int maxTime) {
        this.fxElement = fxElement;
        this.fxProgressLeft = fxProgressLeft;
        this.fxProgressRight = fxProgressRight;
        this.maxTime = maxTime;
    }

    /**
     * Restart the timer from the maxTime. Decrements the time value displayed on
     * the Label element possessed by the timer every second until the timer has
     * finished, at which the provided event is executed
     * 
     * @param event The event to be executed once the timer finishes
     */
    public void startTimer(EventHandler<ActionEvent> event) {
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

    public float getTime() {
        return currentTime;
    }
}
