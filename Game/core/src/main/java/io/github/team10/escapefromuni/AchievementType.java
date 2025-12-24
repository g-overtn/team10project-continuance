package io.github.team10.escapefromuni;

/**
 * AchievementType enum that represents all possible achievement types.
 * Includes information like the achievement name, description, and complete status.
 */
public enum AchievementType {

    /*
        WARNING: achievements are stored to and read from file with a '|' separating their type and complete status
        to not break it, don't include that in the enum type name pretty please :)
     */

    POSITIVE_EVENTS("Living the life", "Interact with all positive events."),
    NEGATIVE_EVENTS("Poor you...", "Interact with all negative events."),
    HIDDEN_EVENTS("Eagle eyes", "Interact with all hidden events."),
    ALL_EVENTS("I'm flattered...", "Interact with all events."),
    ZERO_TIMER("Are you still alive?", "Let the timer reach 0."),
    PASS("Library camper", "Pass the THE3 exam."),
    FAIL("Who's surprised?", "Fail the THE3 exam."),
    TEN_SECONDS("Please, touch grass", "Find the exit in under 10 seconds.");

    private final String name;
    private final String description;

    AchievementType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // getters and setters
    public String getName() { return name; }
    public String getDescription() { return description; }

}
