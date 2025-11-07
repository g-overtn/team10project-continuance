package io.github.team10.escapefromuni;

public class Timer {

    private float time;

    public Timer() {
        this.time = 0;
    }

    public void update(float delta) {
        time += delta;
    }

    public float getTime() {
        return time;
    }

    public int getTimeSeconds() {
        return (int) time;
    }

    public boolean hasReached(float seconds) {
        return time >= seconds;
    }

    public void reset() {
        time = 0;
    }
}
