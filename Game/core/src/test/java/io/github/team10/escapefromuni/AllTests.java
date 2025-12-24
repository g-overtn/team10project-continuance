package io.github.team10.escapefromuni;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite that runs all unit tests for Escape From Uni.
 * All of the test classes are run in the CI/CD pipeline as a complete test suite. 
 * We have also created a HTML report outputting which of the tests have passed and which ones need work and some level of debugging to analyse what went wrong.
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({
    TimerTest.class,
    ScoreManagerTest.class,
    DoorDirectionTest.class,
    RoomTest.class,
    AudioManagerTest.class,
    DoorTest.class,
    EscapeGameTest.class,
    EventTest.class,
    EventGreggsTest.class,
    EventLongboiTest.class,
    EventTypeTest.class,
    GameOverScreenTest.class,
    GameScreenTest.class,
    RoomManagerTest.class,
    TutorialPageTest.class
})
public class AllTests {
    // This class remains empty.
}   