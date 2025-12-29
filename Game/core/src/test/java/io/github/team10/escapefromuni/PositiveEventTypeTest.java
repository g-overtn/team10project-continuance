package io.github.team10.escapefromuni;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the PositiveEventType enum.
 */
public class PositiveEventTypeTest {

    @Test
    public void PositiveEventTypes() {
        // check total enum count
        PositiveEventType[] types = PositiveEventType.values();
        assertEquals("PositiveEventType should have 5 values", 5,  types.length);
    }

    @Test
    public void EventTypeGreggs() {
        // GREGGS enum value
        PositiveEventType type = PositiveEventType.GREGGS;
        assertNotNull("Should set up a GREGGS event", type);
        assertEquals("GREGGS event", PositiveEventType.GREGGS, type);
    }

    @Test
    public void EventTypeMonster() {
        // MONSTER enum value
        PositiveEventType type = PositiveEventType.MONSTER;
        assertNotNull("Should set up a MONSTER event", type);
        assertEquals("MONSTER event", PositiveEventType.MONSTER, type);
    }

    @Test
    public void EventTypeCupNoodles() {
        // CUP_NOODLES enum value
        PositiveEventType type = PositiveEventType.CUP_NOODLES;
        assertNotNull("Should set up a CUP_NOODLES event", type);
        assertEquals("CUP_NOODLES event", PositiveEventType.CUP_NOODLES, type);
    }

    @Test
    public void EventTypeNetworking() {
        // NETWORKING enum value
        PositiveEventType type = PositiveEventType.NETWORKING;
        assertNotNull("Should set up a NETWORKING event", type);
        assertEquals("NETWORKING event", PositiveEventType.NETWORKING, type);
    }

    @Test
    public void EventTypePizza() {
        // PIZZA enum value
        PositiveEventType type = PositiveEventType.PIZZA;
        assertNotNull("Should set up a PIZZA event", type);
        assertEquals("PIZZA event", PositiveEventType.PIZZA, type);
    }

    @Test
    public void enumVals() {
        // valueOf method for all enum values
        assertEquals(PositiveEventType.GREGGS, PositiveEventType.valueOf("GREGGS"));
        assertEquals(PositiveEventType.MONSTER, PositiveEventType.valueOf("MONSTER"));
        assertEquals(PositiveEventType.CUP_NOODLES, PositiveEventType.valueOf("CUP_NOODLES"));
        assertEquals(PositiveEventType.NETWORKING, PositiveEventType.valueOf("NETWORKING"));
        assertEquals(PositiveEventType.PIZZA, PositiveEventType.valueOf("PIZZA"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidPositiveEvent() {
        // Test that valueOf throws an exception for an invalid name
        PositiveEventType.valueOf("INVALID");
    }
}
