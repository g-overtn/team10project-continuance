package io.github.team10.escapefromuni;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the NegativeEventType enum.
 */
public class NegativeEventTypeTest {

    @Test
    public void NegativeEventTypes() {
        // check total enum count
        NegativeEventType[] types = NegativeEventType.values();
        assertEquals("NegativeEventType should have 4 values", 4,  types.length);
    }

    @Test
    public void EventTypeTHE3() {
        // THE3 enum value
        NegativeEventType type = NegativeEventType.THE3;
        assertNotNull("Should set up a THE3 event", type);
        assertEquals("THE3 event", NegativeEventType.THE3, type);
    }

    @Test
    public void EventTypeSYS2() {
        // SYS2 enum value
        NegativeEventType type = NegativeEventType.SYS2;
        assertNotNull("Should set up a SYS2 event", type);
        assertEquals("SYS2 event", NegativeEventType.SYS2, type);
    }

    @Test
    public void EventTypeENG1() {
        // ENG1 enum value
        NegativeEventType type = NegativeEventType.ENG1;
        assertNotNull("Should set up a ENG1 event", type);
        assertEquals("ENG1 event", NegativeEventType.ENG1, type);
    }

    @Test
    public void EventTypeJob() {
        // JOB enum value
        NegativeEventType type = NegativeEventType.JOB;
        assertNotNull("Should set up a JOB event", type);
        assertEquals("JOB event", NegativeEventType.JOB, type);
    }

    @Test
    public void enumVals() {
        // valueOf method for all enum values
        assertEquals(NegativeEventType.THE3, NegativeEventType.valueOf("THE3"));
        assertEquals(NegativeEventType.SYS2, NegativeEventType.valueOf("SYS2"));
        assertEquals(NegativeEventType.ENG1, NegativeEventType.valueOf("ENG1"));
        assertEquals(NegativeEventType.JOB, NegativeEventType.valueOf("JOB"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidNegativeEvent() {
        // Test that valueOf throws an exception for an invalid name
        NegativeEventType.valueOf("INVALID");
    }
}
