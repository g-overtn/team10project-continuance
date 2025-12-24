package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the Door class.
 */
public class DoorTest extends HeadlessTestRunner {
    
    private Door door;
    private RoomManager mockRoomManager;
    private EscapeGame mockGame;
    
    @Before
    public void setUp() {
        // Mock RoomManager and EscapeGame for testing all of the functionality of the door.
        mockRoomManager = mock(RoomManager.class);
        mockGame = mock(EscapeGame.class);
        mockRoomManager.game = mockGame;
        
        door = new Door(mockRoomManager, DoorDirection.NORTH, 5f, 5f);
    }
    
    @After
    public void tearDown() {
        // Delete the door object after the test and clean slate.
        if (door != null) {
            door.dispose();
        }
    }
    
    @Test
    public void DoorInitialisation() {
        // Test that the door is initialised correctly
        // We are checking all of the attributes to ebsure that they have the correct starting values.
        assertNotNull("Door should be initialised", door);
        assertEquals("Door direction should be NORTH", DoorDirection.NORTH, door.direction);
        assertTrue("Door should be active initially", door.isActive);
        assertNotNull("Door texture should not be null", door.doorTexture);
        assertNotNull("Door sprite should not be null", door.doorSprite);
    }
    
    @Test
    public void DoorPosition() {
        // Test that the door is positioned correctly on the map.
        assertEquals("Door X position should be 5f", 5f, door.doorSprite.getX(), 0.01f);
        assertEquals("Door Y position should be 5f", 5f, door.doorSprite.getY(), 0.01f);
    }
    
    @Test
    public void DoorSize() {
        // Test that the door has correct size (height and width)
        assertEquals("Door width should be 1f", 1f, door.doorSprite.getWidth(), 0.01f);
        assertEquals("Door height should be 1f", 1f, door.doorSprite.getHeight(), 0.01f);
    }
    
    @Test
    public void SetActive() {
        // Test setting the door active/inactive
        // We test the open and close functionality.
        door.setActive(false);
        assertFalse("Door should be inactive after setActive(false)", door.isActive);
        
        door.setActive(true);
        assertTrue("Door should be active after setActive(true)", door.isActive);
    }
    
    @Test
    public void GetActive() {
        // Test getting the door active state
        // Testing both possible states to ensure everything is in order.
        assertTrue("getActive should return true initially", door.getActive());
        
        door.setActive(false);
        assertFalse("getActive should return false after setActive(false)", door.getActive());
    }
    
    @Test
    public void DoorDirections() {
        // Making sure we can make doors for different directions
        Door northDoor = new Door(mockRoomManager, DoorDirection.NORTH, 0f, 0f);
        Door eastDoor = new Door(mockRoomManager, DoorDirection.EAST, 0f, 0f);
        Door southDoor = new Door(mockRoomManager, DoorDirection.SOUTH, 0f, 0f);
        Door westDoor = new Door(mockRoomManager, DoorDirection.WEST, 0f, 0f);
        
        assertEquals(DoorDirection.NORTH, northDoor.direction);
        assertEquals(DoorDirection.EAST, eastDoor.direction);
        assertEquals(DoorDirection.SOUTH, southDoor.direction);
        assertEquals(DoorDirection.WEST, westDoor.direction);
        
        northDoor.dispose();
        eastDoor.dispose();
        southDoor.dispose();
        westDoor.dispose();
    }
    
    @Test
    public void RoomManagerReference() {
        // Test that the door has a reference to the RoomManager
        assertSame("Door should store reference to RoomManager", mockRoomManager, door.roomManager);
    }
}