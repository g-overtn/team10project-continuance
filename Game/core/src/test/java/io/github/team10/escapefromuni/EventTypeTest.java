package io.github.team10.escapefromuni;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the EventType enum.
 */
public class EventTypeTest {
    
    @Test
    public void EventTypes() {
        // Total number of enum values
        EventType[] types = EventType.values();
        assertEquals("EventType should have 4 values", 4, types.length);
    }
    
    @Test
    public void EventTypePos() {
        // POSITIVE enum value
        EventType type = EventType.POSITIVE;
        assertNotNull("Should set up a positive event", type);
        assertEquals("POSITIVE event", EventType.POSITIVE, type);
    }
    
    @Test
    public void EventTypeNeg() {
        // NEGATIVE enum value
        EventType type = EventType.NEGATIVE;
        assertNotNull("Should set up a negative event", type);
        assertEquals("NEGATIVE event", EventType.NEGATIVE, type);
    }
    
    @Test
    public void EventTypeHidden() {
        // HIDDEN enum value
        EventType type = EventType.HIDDEN;
        assertNotNull("Should set up hidden event", type);
        assertEquals("Even is of type hidden", EventType.HIDDEN, type);
    }
    
    @Test
    public void EventTypeNone() {
        // NONE enum value
        EventType type = EventType.NONE;
        assertNotNull("Should be a blank Event", type);
        assertEquals("Regular event", EventType.NONE, type);
    }
    
    @Test
    public void enumVals() {
        // valueOf method for all enum values
        assertEquals(EventType.POSITIVE, EventType.valueOf("POSITIVE"));
        assertEquals(EventType.NEGATIVE, EventType.valueOf("NEGATIVE"));
        assertEquals(EventType.HIDDEN, EventType.valueOf("HIDDEN"));
        assertEquals(EventType.NONE, EventType.valueOf("NONE"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void InvalidEvent() {
        // Test that valueOf throws an exception for an invalid name
        EventType.valueOf("INVALID");
    }
}