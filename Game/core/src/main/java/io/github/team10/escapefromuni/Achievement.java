package io.github.team10.escapefromuni;

/**
 * Achievement class used for storing information about a single achievement.
 */
public class Achievement {

    private final AchievementType type;
    private boolean complete;

    Achievement(AchievementType type, boolean complete) {
        this.type = type;
        this.complete = complete;
    }

    @Override
    public String toString() {
        return getType() + "|" + isComplete();
    }

    // getters and setters
    public AchievementType getType() { return type; }
    public String getName() { return type.getName(); }
    public String getDescription() { return type.getDescription(); }
    public boolean isComplete() { return complete; }

    public void setComplete(boolean complete) { this.complete = complete; }
}
