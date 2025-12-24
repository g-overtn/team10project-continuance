package io.github.team10.escapefromuni;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the EventType enum.
 */
public class EventTypeTest {
    
    @Test
    public void EventTypeValues() {
        // Test the number of enum values
        EventType[] types = EventType.values();
        assertEquals("EventType should have 4 values", 4, types.length);
    }
    
    @Test
    public void EventTypePositive() {
        // Test the POSITIVE enum value
        EventType type = EventType.POSITIVE;
        assertNotNull("POSITIVE should not be null", type);
        assertEquals("POSITIVE should equal itself", EventType.POSITIVE, type);
    }
    
    @Test
    public void EventTypeNegative() {
        // Test the NEGATIVE enum value
        EventType type = EventType.NEGATIVE;
        assertNotNull("NEGATIVE should not be null", type);
        assertEquals("NEGATIVE should equal itself", EventType.NEGATIVE, type);
    }
    
    @Test
    public void EventTypeHidden() {
        // Test the HIDDEN enum value
        EventType type = EventType.HIDDEN;
        assertNotNull("HIDDEN should not be null", type);
        assertEquals("HIDDEN should equal itself", EventType.HIDDEN, type);
    }
    
    @Test
    public void EventTypeNone() {
        // Test the NONE enum value
        EventType type = EventType.NONE;
        assertNotNull("NONE should not be null", type);
        assertEquals("NONE should equal itself", EventType.NONE, type);
    }
    
    @Test
    public void ValueOf() {
        // Test the valueOf method for all enum values
        assertEquals(EventType.POSITIVE, EventType.valueOf("POSITIVE"));
        assertEquals(EventType.NEGATIVE, EventType.valueOf("NEGATIVE"));
        assertEquals(EventType.HIDDEN, EventType.valueOf("HIDDEN"));
        assertEquals(EventType.NONE, EventType.valueOf("NONE"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ValueOfInvalid() {
        // Test that valueOf throws an exception for an invalid name
        // Making sure only valid enums are registered.
        EventType.valueOf("INVALID");
    }
}