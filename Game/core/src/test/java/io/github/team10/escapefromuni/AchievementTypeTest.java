package io.github.team10.escapefromuni;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the AchievementType enum.
 * This set of tests checks that all achievement types and enums are correctly set up.

 */
public class AchievementTypeTest extends HeadlessTestRunner {
    
    @Test
    public void EnumValues() {
        // Checks 8 achievement types.
        AchievementType[] types = AchievementType.values();
        assertEquals("AchievementType should have 8 values", 8, types.length);
    }
    
    @Test
    public void PositiveEventsExists() {
        // +ve event interaction award exists.
        AchievementType type = AchievementType.POSITIVE_EVENTS;
        assertNotNull("POSITIVE_EVENTS should exist", type);
        assertEquals("POSITIVE_EVENTS should equal itself", AchievementType.POSITIVE_EVENTS, type);
    }
    
    @Test
    public void NegativeEventsExists() {
        // -ve event interation award exists.
        AchievementType type = AchievementType.NEGATIVE_EVENTS;
        assertNotNull("NEGATIVE_EVENTS should exist", type);
        assertEquals("NEGATIVE_EVENTS should equal itself", AchievementType.NEGATIVE_EVENTS, type);
    }
    
    @Test
    public void HiddenEventsExists() {
        // hidden event interation award exists.
        AchievementType type = AchievementType.HIDDEN_EVENTS;
        assertNotNull("HIDDEN_EVENTS should exist", type);
        assertEquals("HIDDEN_EVENTS should equal itself", AchievementType.HIDDEN_EVENTS, type);
    }
    
    @Test
    public void AllEventsExists() {
        // all events done award exists.
        AchievementType type = AchievementType.ALL_EVENTS;
        assertNotNull("ALL_EVENTS should exist", type);
        assertEquals("ALL_EVENTS should equal itself", AchievementType.ALL_EVENTS, type);
    }
    
    @Test
    public void ZeroTimerExists() {
        // No time award exists
        AchievementType type = AchievementType.ZERO_TIMER;
        assertNotNull("ZERO_TIMER should exist", type);
        assertEquals("ZERO_TIMER should equal itself", AchievementType.ZERO_TIMER, type);
    }
    
    @Test
    public void PassExists() {
        // Exam pass award
        AchievementType type = AchievementType.PASS;
        assertNotNull("PASS should exist", type);
        assertEquals("PASS should equal itself", AchievementType.PASS, type);
    }
    
    @Test
    public void FailExists() {
        // Exam failure award
        AchievementType type = AchievementType.FAIL;
        assertNotNull("FAIL should exist", type);
        assertEquals("FAIL should equal itself", AchievementType.FAIL, type);
    }
    
    @Test
    public void TenSecondsExists() {
        // 10 seconds award exists
        AchievementType type = AchievementType.TEN_SECONDS;
        assertNotNull("TEN_SECONDS should exist", type);
        assertEquals("TEN_SECONDS should equal itself", AchievementType.TEN_SECONDS, type);
    }
    
    @Test
    public void ValPositiveEvents() {
        // Enum convert for +ve
        assertEquals(AchievementType.POSITIVE_EVENTS, AchievementType.valueOf("POSITIVE_EVENTS"));
    }
    
    @Test
    public void ValNegativeEvents() {
        // Enum convert for -ve
        assertEquals(AchievementType.NEGATIVE_EVENTS, AchievementType.valueOf("NEGATIVE_EVENTS"));
    }
    
    @Test
    public void ValHiddenEvents() {
        // enum convert for hidden
        assertEquals(AchievementType.HIDDEN_EVENTS, AchievementType.valueOf("HIDDEN_EVENTS"));
    }
    
    @Test
    public void ValAllEvents() {
        // enum convert for all
        assertEquals(AchievementType.ALL_EVENTS, AchievementType.valueOf("ALL_EVENTS"));
    }
    
    @Test
    public void ValZeroTimer() {
        // enum convert for no time
        assertEquals(AchievementType.ZERO_TIMER, AchievementType.valueOf("ZERO_TIMER"));
    }
    
    @Test
    public void ValPass() {
        // enum convert for exam passed
        assertEquals(AchievementType.PASS, AchievementType.valueOf("PASS"));
    }
    
    @Test
    public void ValFail() {
        // Convert enum for failure.
        assertEquals(AchievementType.FAIL, AchievementType.valueOf("FAIL"));
    }
    
    @Test
    public void ValTenSeconds() {
        // 10s conversion.
        assertEquals(AchievementType.TEN_SECONDS, AchievementType.valueOf("TEN_SECONDS"));
    }

    
    @Test
    public void getNamePositiveEvents() {
        // +ve achievement title
        assertEquals("Living the life", AchievementType.POSITIVE_EVENTS.getName());
    }
    
    @Test
    public void getNameNegativeEvents() {
        // -ve achievement title
        assertEquals("Poor you...", AchievementType.NEGATIVE_EVENTS.getName());
    }
    
    @Test
    public void getNameHiddenEvents() {
        // Hidden achievement title
        assertEquals("Eagle eyes", AchievementType.HIDDEN_EVENTS.getName());
    }
    
    @Test
    public void getNameAllEvents() {
        // All Events title
        assertEquals("I'm flattered...", AchievementType.ALL_EVENTS.getName());
    }
    
    @Test
    public void getNameZeroTimer() {
        // No time display
        assertEquals("Are you still alive?", AchievementType.ZERO_TIMER.getName());
    }
    
    @Test
    public void getNamePass() {
        // Exam pass award title
        assertEquals("Library camper", AchievementType.PASS.getName());
    }
    
    @Test
    public void getNameFail() {
        // Exam fail achievement title
        assertEquals("Who's surprised?", AchievementType.FAIL.getName());
    }
    
    @Test
    public void getNameTenSeconds() {
        // 10s display title
        assertEquals("Please, touch grass", AchievementType.TEN_SECONDS.getName());
    }
    
    @Test
    public void getDescriptionPositiveEvents() {
        // +ve event desc
        assertEquals("Interact with all positive events.", AchievementType.POSITIVE_EVENTS.getDescription());
    }

    @Test
    public void getDescNegativeEvents() {
    // -ve event desc
        assertEquals("Interact with all negative events.", AchievementType.NEGATIVE_EVENTS.getDescription());
    }

    @Test
    public void getDescHiddenEvents() {
    // Hidden event desc
        assertEquals("Interact with all hidden events.", AchievementType.HIDDEN_EVENTS.getDescription());
    }

    @Test
    public void getDescAllEvents() {
        // All events desc
        assertEquals("Interact with all events.", AchievementType.ALL_EVENTS.getDescription());
    }

    @Test
    public void getDescZeroTimer() {
        // No time desc
        assertEquals("Let the timer reach 0.", AchievementType.ZERO_TIMER.getDescription());
    }

    @Test
    public void getDescPass() {
        // Test passed desc
        assertEquals("Pass the THE3 exam.", AchievementType.PASS.getDescription());
    }

    @Test
    public void getDescFail() {
        // Failed exam desc
        assertEquals("Fail the THE3 exam.", AchievementType.FAIL.getDescription());
    }

    @Test
    public void getDescTenS() {
        // 10 seconds desc
        assertEquals("Find the exit in under 10 seconds.", AchievementType.TEN_SECONDS.getDescription());
    }
}