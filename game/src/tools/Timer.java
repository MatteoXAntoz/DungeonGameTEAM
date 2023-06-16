package tools;

/** The Timer class represents a simple countdown timer. */
public class Timer {

    int durationInFrames;
    int currentDurationInFrames;

    /** Reduces the current duration by 1 frame, ensuring it does not go below 0. */
    public void reduceCounter() {
        currentDurationInFrames = Math.max(0, --currentDurationInFrames);
    }

    /** Activates the counter by setting the current duration to the initial duration. */
    public void activateCounter() {
        currentDurationInFrames = durationInFrames;
    }

    /**
     * Checks if the timer is currently counting down.
     *
     * @return true if the timer is still counting down, false otherwise.
     */
    public boolean isCounting() {
        return currentDurationInFrames > 0;
    }

    /**
     * Sets the duration of the timer in seconds.
     *
     * @param coolDownInSeconds the duration in seconds
     */
    public void setDuration(float coolDownInSeconds) {
        this.durationInFrames = (int) (coolDownInSeconds * Constants.FRAME_RATE);
        this.currentDurationInFrames = 0;
    }
}
