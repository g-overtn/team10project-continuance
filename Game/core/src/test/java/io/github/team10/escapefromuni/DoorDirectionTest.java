package io.github.team10.escapefromuni;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the DoorDirection enum.
 */
public class DoorDirectionTest extends HeadlessTestRunner {
    
    @Test
    public void EnumVals() {
        // Test that all enum values exist
        assertEquals("NORTH should exist", DoorDirection.NORTH, DoorDirection.valueOf("NORTH"));
        assertEquals("EAST should exist", DoorDirection.EAST, DoorDirection.valueOf("EAST"));
        assertEquals("SOUTH should exist", DoorDirection.SOUTH, DoorDirection.valueOf("SOUTH"));
        assertEquals("WEST should exist", DoorDirection.WEST, DoorDirection.valueOf("WEST"));
    }
    
    @Test
    public void EnumNums() {
        // Test the number of enum values, which should be 4.
        assertEquals("Should have 4 directions", 4, DoorDirection.values().length);
    }
}