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
        mockRoomManager = mock(RoomManager.class);
        mockGame = mock(EscapeGame.class);
        mockRoomManager.game = mockGame;
        
        door = new Door(mockRoomManager, DoorDirection.NORTH, 5f, 5f);
    }
    
    @After
    public void tearDown() {
        if (door != null) {
            door.dispose();
        }
    }
    
    @Test
    public void DoorInit() {
        // check attributes set up. 
        assertNotNull("Door should be initialised", door);
        assertEquals("Door direction should be NORTH", DoorDirection.NORTH, door.direction);
        assertTrue("Door should be active initially", door.isActive);
        assertNotNull("Door texture should exist", door.doorTexture);
        assertNotNull("Door sprite should exist", door.doorSprite);
    }
    
    @Test
    public void DoorPos() {
        // Door positioning on screen
        assertEquals("Door X position should be 5f", 5f, door.doorSprite.getX(), 0.01f);
        assertEquals("Door Y position should be 5f", 5f, door.doorSprite.getY(), 0.01f);
    }
    
    @Test
    public void DoorSize() {
        // door size.
        assertEquals("Door width should be 1f", 1f, door.doorSprite.getWidth(), 0.01f);
        assertEquals("Door height should be 1f", 1f, door.doorSprite.getHeight(), 0.01f);
    }
    
    @Test
    public void setActive() {
        // Change door state
        door.setActive(false);
        assertFalse("Door should be inactive after setActive(false)", door.isActive);
        
        door.setActive(true);
        assertTrue("Door should be active after setActive(true)", door.isActive);
    }
    
    @Test
    public void getActive() {
        // get door state
        assertTrue("getActive should return true initially", door.getActive());
        
        door.setActive(false);
        assertFalse("getActive should return false after setActive(false)", door.getActive());
    }
    
    @Test
    public void DoorDirections() {
        // enum check + door in all directions
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
    public void RoomManagerRef() {
        // link between manager and door classes
        assertSame("Door should store reference to RoomManager", mockRoomManager, door.roomManager);
    }
}