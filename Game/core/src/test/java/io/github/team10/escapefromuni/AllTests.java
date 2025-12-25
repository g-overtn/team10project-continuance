package io.github.team10.escapefromuni;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// NO ONE GET RID OF IT!!!!!!


/**
 * Test suite that runs all unit tests for our project
 * These are all of our automated tests which get executed in the CI pipeline. 
 * We have also created a HTML report outputting which of the tests have passed and which ones need work and some level of debugging to analyse what went wrong.
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({
    AchievementManagerTest.class,
    AchievementTypeTest.class,
    AchievementTest.class,
    TimerTest.class,
    ScoreManagerTest.class,
    DoorDirectionTest.class,
    RoomTest.class,
    DoorTest.class,
    EscapeGameTest.class,
    EventTest.class,
    EventTypeTest.class,
    GameOverScreenTest.class,
    RoomManagerTest.class
})
public class AllTests {
    // DON'T DELETE THIS!!!!!
    // This class remains empty on purpose. This is needed because of what we called the file.
    // OTHERWISE THE TESTS WILL NOT RUN.
    // THIS SUITE CONTAINS ALL OF THE FILES WITH THE TEST CASES.
}

