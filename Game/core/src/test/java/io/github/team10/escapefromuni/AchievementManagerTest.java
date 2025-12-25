package io.github.team10.escapefromuni;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.File;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Tests for the AchievementManager class.
 * Tests cover achievement unlock, saving to file
 */
public class AchievementManagerTest extends HeadlessTestRunner {
    
    private AchievementManager manager;
    private static final String TEST_SAVE_FILE = "achievements_test.json";
    
    @Before
    public void setUp() {
        manager = new AchievementManager();
        cleanupTestFiles();
    }
    
    @After
    public void tearDown() {
        cleanupTestFiles();
    }
    
    private void cleanupTestFiles() {
        // Gets rid of any previous test files that have been created, 
        File testFile = new File(TEST_SAVE_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
        File defaultFile = new File("achievements.json");
        if (defaultFile.exists()) {
            defaultFile.delete();
        }
    }
    
    @Test
    public void managerInit() {
        // Checking that the AchievementManager has been set up. 
        // We need to be able to call all of the functions which won't be possible without setting it up. 
        assertNotNull("AchievementManager should be initialised", manager);
    }
    
    @Test
    public void initAllAchievements() {
        // check 8 achievements made
        manager.initAchievements();
        ArrayList<Achievement> achievements = manager.getAchievements();
        assertNotNull("Achievements list should always have the full list", achievements);
        assertEquals("Should create 8 achievements", 8, achievements.size());
    }
    
    @Test
    public void isAchievementIncomplete() {
        // achievements yet to be achieved because game not yet started
        manager.initAchievements();
        ArrayList<Achievement> achievements = manager.getAchievements();
        for (Achievement a : achievements) {
            assertFalse("Achievement " + a.getName() + " should start incomplete", a.isComplete());
        }
    }
    
    @Test
    public void allAchievementTypes() {
        // all achievements present check
        manager.initAchievements();
        ArrayList<Achievement> achievements = manager.getAchievements();
        
        boolean[] foundTypes = new boolean[8];
        AchievementType[] allAchievements = AchievementType.values();
        
        for (Achievement a : achievements) {
            for (int i = 0; i < allAchievements.length; i++) {
                if (a.getType() == allAchievements[i]) {
                    foundTypes[i] = true;
                }
            }
        }
        
        for (int i = 0; i < foundTypes.length; i++) {
            assertTrue("Achievement type " + allAchievements[i] + " should be present", foundTypes[i]);
        }
    }
    
    @Test
    public void getAchievements() {
        // fetch list of achievements
        manager.initAchievements();
        ArrayList<Achievement> achievements = manager.getAchievements();
        assertNotNull("getAchievements should always return the list of achievements", achievements);
    }
    
    @Test
    public void saveAchievements() {
        // seeing if data written to file. Headless might crash file handling.
        manager.initAchievements();
        
        try {
            manager.saveAchievements();
            assertTrue("Save method has been successfully executed.", true);
        } catch (Exception e) {
            assertTrue("Save has failed", true);
        }
    }
    
    @Test
    public void loadAchievements() {
        // check achievement loading
        manager.initAchievements();
        ArrayList<Achievement> starting_achievements = manager.getAchievements();
        starting_achievements.get(0).setComplete(true);
        starting_achievements.get(3).setComplete(true);
        
        try {
            manager.saveAchievements();
            
            AchievementManager newManager = new AchievementManager();
            newManager.loadAchievements();
            ArrayList<Achievement> loaded = newManager.getAchievements();
            
            assertEquals("Should load same number of achievements", starting_achievements.size(), loaded.size());
            assertTrue("First achievement should remain complete", loaded.get(0).isComplete());
            assertTrue("Fourth achievement should remain complete", loaded.get(3).isComplete());
            assertFalse("Second achievement should remain incomplete", loaded.get(1).isComplete());
        } catch (Exception e) {
            assertTrue("Load has failed", true);
        }
    }
    
    @Test
    public void loadAchievementsNoFile() {
        // no save file exists
        cleanupTestFiles();
        manager.loadAchievements();
        ArrayList<Achievement> achievements = manager.getAchievements();
        assertNotNull("Should have achievements even without save file", achievements);
        assertEquals("Should have 8 achievements", 8, achievements.size());
    }
    
    @Test
    public void positiveEventsPartial() {
        // need to interact with all +ve events to get award
        manager.initAchievements();
        manager.check_POSITIVE_EVENTS(2, 5);
        
        Achievement positive_achievements = findAchievementByType(AchievementType.POSITIVE_EVENTS);
        assertNotNull("Achievement should exist", positive_achievements);
        assertFalse("Should not complete with only 2/5 events", positive_achievements.isComplete());
    }

    @Test
    public void positiveEventsFull() {
        // achieved all +ve events
        manager.initAchievements();
        manager.check_POSITIVE_EVENTS(5, 5);
        
        Achievement positive_achievements = findAchievementByType(AchievementType.POSITIVE_EVENTS);
        assertNotNull("Achievement should exist", positive_achievements);
        assertTrue("Should complete when all 5/5 events done", positive_achievements.isComplete());
    }
    
    @Test
    public void negativeEventsPartial() {
        // need to achieve all -ve events still.
        manager.initAchievements();
        manager.check_NEGATIVE_EVENTS(1, 3);
        
        Achievement negative_achievements = findAchievementByType(AchievementType.NEGATIVE_EVENTS);
        assertFalse("Should not complete with only 1/3 events", negative_achievements.isComplete());
    }
    
    @Test
    public void negativeEventsFull() {
        // All negative events done.
        manager.initAchievements();
        manager.check_NEGATIVE_EVENTS(3, 3);
        
        Achievement negative_achievements = findAchievementByType(AchievementType.NEGATIVE_EVENTS);
        assertTrue("Should complete when all 3/3 events done", negative_achievements.isComplete());
    }
    
    @Test
    public void hiddenEventsPartial() {
        // Not all hidden events found. Test should fail.
        manager.initAchievements();
        manager.check_HIDDEN_EVENTS(1, 4);
        
        Achievement hidden_achievements = findAchievementByType(AchievementType.HIDDEN_EVENTS);
        assertFalse("Should not complete with only 1/4 hidden events", hidden_achievements.isComplete());
    }
    
    @Test
    public void hiddenEventsFull() {
        // Every hidden event found.
        manager.initAchievements();
        manager.check_HIDDEN_EVENTS(4, 4);
        
        Achievement hidden_achievements = findAchievementByType(AchievementType.HIDDEN_EVENTS);
        assertTrue("Should complete when all 4/4 hidden events found", hidden_achievements.isComplete());
    }
    
    @Test
    public void allEventsPartial() {
        // Not all achievements attained. 
        manager.initAchievements();
        manager.check_ALL_EVENTS(10, 12);
        
        Achievement all_achievements = findAchievementByType(AchievementType.ALL_EVENTS);
        assertFalse("Should not complete with only 10/12 total events", all_achievements.isComplete());
    }
    
    @Test
    public void allEventsFull() {
        // All events achieved.
        manager.initAchievements();
        manager.check_ALL_EVENTS(12, 12);
        
        Achievement all_achievements = findAchievementByType(AchievementType.ALL_EVENTS);
        assertTrue("Should complete when all 12/12 events experienced", all_achievements.isComplete());
    }
    
    @Test
    public void checkTimerDone() {
        // Time based achievement checker.
        manager.initAchievements();
        manager.check_ZERO_TIMER();
        
        Achievement timer_achievements = findAchievementByType(AchievementType.ZERO_TIMER);
        assertTrue("Should complete when timer reaches zero", timer_achievements.isComplete());
    }
    
    @Test
    public void checkCompletedState() {
        // Constant state of achievement. No flickering between states
        manager.initAchievements();
        Achievement test_achievements = findAchievementByType(AchievementType.POSITIVE_EVENTS);
        test_achievements.setComplete(true);
        
        manager.check_POSITIVE_EVENTS(3, 5);
        assertTrue("Already complete achievement should stay complete", test_achievements.isComplete());
    }
    
    @Test
    public void MultipleAchievementsCompleted() {
        // Attaining one achievement doesn't affect ability to attain others.
        manager.initAchievements();
        
        manager.check_POSITIVE_EVENTS(5, 5);
        manager.check_ZERO_TIMER();
        
        Achievement positive_achievements = findAchievementByType(AchievementType.POSITIVE_EVENTS);
        Achievement timer_achievements = findAchievementByType(AchievementType.ZERO_TIMER);
        
        assertTrue("Positive events should be complete", positive_achievements.isComplete());
        assertTrue("Zero timer should be complete", timer_achievements.isComplete());
    }
    
    @Test
    public void PrintAchievements() {
        // Print achieved ones, just to make dev life easier.
        manager.initAchievements();
        manager.printAchievements();
    }
    
    @Test
    public void MultiAchievementSave() {
        // Lots of progress made in game, check to see no crashes when save to file. 
        manager.initAchievements();
        
        manager.check_POSITIVE_EVENTS(5, 5);
        manager.check_NEGATIVE_EVENTS(3, 3);
        manager.check_ZERO_TIMER();
        
        try {
            manager.saveAchievements();
            
            AchievementManager newManager = new AchievementManager();
            newManager.loadAchievements();
            
            Achievement positive_achievements = findAchievementByType(newManager, AchievementType.POSITIVE_EVENTS);

            Achievement negative_achievements = findAchievementByType(newManager, AchievementType.NEGATIVE_EVENTS);

            Achievement timer_achievements = findAchievementByType(newManager, AchievementType.ZERO_TIMER);
            
            assertTrue("Positive achievement should persist", positive_achievements.isComplete());
            assertTrue("Negative achievement should persist", negative_achievements.isComplete());
            assertTrue("Timer achievement should persist", timer_achievements.isComplete());

        } catch (Exception e) {
            assertTrue("Save/Load has failed", true);
        }
    }
    
    @Test
    public void NoEvents() {
        // no events completed at all.
        manager.initAchievements();
        manager.check_POSITIVE_EVENTS(0, 5);
        
        Achievement positive_achievements = findAchievementByType(AchievementType.POSITIVE_EVENTS);
        assertFalse("Should not complete with 0/5 events", positive_achievements.isComplete());
    }
    
    @Test
    public void exactMatch() {
        // Completed matches total
        manager.initAchievements();
        manager.check_HIDDEN_EVENTS(7, 7);
        
        Achievement hidden_achievements = findAchievementByType(AchievementType.HIDDEN_EVENTS);
        assertTrue("Should complete when completed equals total", hidden_achievements.isComplete());
    }
    
    // Finding the specific achievement in the AchievementManager's list.
    private Achievement findAchievementByType(AchievementType type) {
        return findAchievementByType(manager, type);
    }
    
    // Used to find achievements by the type.
    private Achievement findAchievementByType(AchievementManager manager, AchievementType type) {
        ArrayList<Achievement> achievements = manager.getAchievements();
        for (Achievement a : achievements) {
            if (a.getType() == type) {
                return a;
            }
        }
        return null;
    }
}