package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

/**
 * AchievementManager class that handles the loading, saving and updating of game achievements.
 */
public class AchievementManager {

    private final ArrayList<Achievement> achievements = new ArrayList<>();

    /**
     * Initialises all achievements, setting their complete status to false.
     * Can also be used to reset all achievements back to incomplete.
     */
    public void initAchievements() {
        System.out.println("Initialising Achievements...");
        if (!achievements.isEmpty()) {
            System.out.println("Achievements are already initialised!");
        } else {
            for (AchievementType type : AchievementType.values()) {
                achievements.add(new Achievement(type, false));
            }
        }
    }

    /**
     * Loads achievements from memory.
     */
    public void loadAchievements() {
        System.out.println("Loading Achievements...");
        if (!achievements.isEmpty()) {
            System.out.println("Achievements are already loaded!");
        } else {
            try {
                FileHandle file = Gdx.files.external("achievements.json");
                String[] text = file.readString().split("\n");
                for (String substring : text) {
                    String[] values = substring.split("\\|");
                    achievements.add(new Achievement(AchievementType.valueOf(values[0]), Boolean.parseBoolean(values[1])));
                }
            } catch (Exception e) { // if 'achievements.json' not found i.e. first time playing
                System.out.println(e.getMessage());
                initAchievements();
            }
        }
    }

    /**
     * Stores achievements into memory, overwriting and data already existing in the file.
     */
    public void saveAchievements() {
        System.out.println("Saving Achievements...");
        StringBuilder text = new  StringBuilder();
        for (Achievement a : achievements) {
            text.append(a).append("\n");
        }

        FileHandle file = Gdx.files.external("achievements.json");
        file.writeString(text.toString(), false);
    }

    /**
     * Prints all achievements to terminal, primarily used for debugging.
     */
    public void printAchievements() {
        System.out.println("Printing Achievements...");
        if  (achievements.isEmpty()) {
            System.out.println("There are no achievements.");
        } else {
            StringBuilder output = new StringBuilder();
            for (Achievement a : achievements) {
                output.append(a).append("\n");
            }
            System.out.println(output);
        }
    }

    // check-TYPE_NAME methods
    // probably a much easier and more modular way to do this, but we don't have that many achievements so who really cares :)

    public void check_POSITIVE_EVENTS(float count, float total) { // achievements[0]
        if (count >= total) { achievements.get(0).setComplete(true); }
    }

    public void check_NEGATIVE_EVENTS(float count, float total) { // achievements[1]
        if (count >= total) { achievements.get(1).setComplete(true); }
    }

    public void check_HIDDEN_EVENTS(float count, float total) { // achievements[2]
        if (count >= total) { achievements.get(2).setComplete(true); }
    }

    public void check_ALL_EVENTS(float count, float total) { // achievements[3]
        if (count >= total) { achievements.get(3).setComplete(true); }
    }

    public void check_ZERO_TIMER() { // achievements[4]
        achievements.get(4).setComplete(true);
    }

    public void check_PASS() { // achievements[5]
        achievements.get(5).setComplete(true);
    }

    public void check_FAIL() { // achievements[6]
        achievements.get(6).setComplete(true);
    }

    public void check_TEN_SECONDS() { // achievements[7]
        achievements.get(7).setComplete(true);
    }

    // getters and setters
    public ArrayList<Achievement> getAchievements() { return achievements; }
}
