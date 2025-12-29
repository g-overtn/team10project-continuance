package io.github.team10.escapefromuni;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the HiddenEventType enum.
 */
public class HiddenEventTypeTest {

    @Test
    public void HiddenEventTypes() {
        // check total enum count
        HiddenEventType[] types = HiddenEventType.values();
        assertEquals("HiddenEventType should have 3 values", 3,  types.length);
    }

    @Test
    public void EventTypeLongboi() {
        // LONGBOI enum value
        HiddenEventType type = HiddenEventType.LONGBOI;
        assertNotNull("Should set up a LONGBOI event", type);
        assertEquals("LONGBOI event", HiddenEventType.LONGBOI, type);
    }

    @Test
    public void EventTypeBob() {
        // BOB enum value
        HiddenEventType type = HiddenEventType.BOB;
        assertNotNull("Should set up a BOB event", type);
        assertEquals("BOB event", HiddenEventType.BOB, type);
    }

    @Test
    public void EventTypeInverseControls() {
        // INVERSE_CONTROLS enum value
        HiddenEventType type = HiddenEventType.INVERSE_CONTROLS;
        assertNotNull("Should set up a INVERSE_CONTROLS event", type);
        assertEquals("INVERSE_CONTROLS event", HiddenEventType.INVERSE_CONTROLS, type);
    }

    @Test
    public void enumVals() {
        // valueOf method for all enum values
        assertEquals(HiddenEventType.LONGBOI, HiddenEventType.valueOf("LONGBOI"));
        assertEquals(HiddenEventType.BOB, HiddenEventType.valueOf("BOB"));
        assertEquals(HiddenEventType.INVERSE_CONTROLS, HiddenEventType.valueOf("INVERSE_CONTROLS"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidHiddenEvent() {
        // Test that valueOf throws an exception for an invalid name
        HiddenEventType.valueOf("INVALID");
    }
}
