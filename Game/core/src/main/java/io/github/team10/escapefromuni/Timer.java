package io.github.team10.escapefromuni;

public class Timer {

    private float timeLeft;

    public Timer() {
        this.timeLeft = 300; // start at 300 seconds
    }

    public void update(float delta) {
        timeLeft -= delta;
        if (timeLeft < 0) {
            timeLeft = 0;
        }
    }

    public int getTimeSeconds() {
        return (int) timeLeft;
    }

    public boolean isFinished() {
        return timeLeft <= 0;
    }

    public void reset() {
        timeLeft = 300;
    }
}
