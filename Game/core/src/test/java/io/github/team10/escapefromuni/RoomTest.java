package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;

/**
 * Tests for the Room class.
 * Validates room connections, event management, and exit functionality.
 */
public class RoomTest extends HeadlessTestRunner {
    
    private Room room;
    private Texture mockTexture;
    
    @Before
    public void setUp() {
        mockTexture = Mockito.mock(Texture.class);
        room = new Room(mockTexture);
    }
    
    @Test
    public void RoomInitialisation() {
        // Test that the room is initialised correctly
        assertNotNull("Room should be initialised", room);
        assertEquals("Room texture should match", mockTexture, room.getRoomTexture());
        assertFalse("Room should not be exit by default", room.getExit());
        assertEquals("Room should have no event by default", EventType.NONE, room.getEventType());
    }
    
    @Test
    public void ExitRoom() {
        // Test that the room can be marked as an exit
        Room exitRoom = new Room(mockTexture, true);
        assertTrue("Room should be marked as exit", exitRoom.getExit());
    }
    
    @Test
    public void AddAdjacentRoom() {
        // Test adding adjacent rooms
        // checking nearby rooms and seeing if it detects it as visitable, 
        Room northRoom = new Room(mockTexture);
        room.addAdjacent(northRoom, DoorDirection.NORTH);
        
        assertEquals("North room should be adjacent", northRoom, room.getAdjacent(DoorDirection.NORTH));
        assertNull("East room should be null", room.getAdjacent(DoorDirection.EAST));
    }
    
    @Test
    public void AddAllDirections() {
        // Test adding adjacent rooms in all directions to ensure they can be added in all directions relative to the current room, of course. 
        Room northRoom = new Room(mockTexture);
        Room eastRoom = new Room(mockTexture);
        Room southRoom = new Room(mockTexture);
        Room westRoom = new Room(mockTexture);
        
        room.addAdjacent(northRoom, DoorDirection.NORTH);
        room.addAdjacent(eastRoom, DoorDirection.EAST);
        room.addAdjacent(southRoom, DoorDirection.SOUTH);
        room.addAdjacent(westRoom, DoorDirection.WEST);
        
        assertEquals("North room should be adjacent", northRoom, room.getAdjacent(DoorDirection.NORTH));
        assertEquals("East room should be adjacent", eastRoom, room.getAdjacent(DoorDirection.EAST));
        assertEquals("South room should be adjacent", southRoom, room.getAdjacent(DoorDirection.SOUTH));
        assertEquals("West room should be adjacent", westRoom, room.getAdjacent(DoorDirection.WEST));
    }
    
    @Test
    public void GetAllAdjacent() {
        // Test retrieving all adjacent rooms
        Room northRoom = new Room(mockTexture);
        Room eastRoom = new Room(mockTexture);
        
        room.addAdjacent(northRoom, DoorDirection.NORTH);
        room.addAdjacent(eastRoom, DoorDirection.EAST);
        
        Room[] adjacent = room.getAllAdjacent();
        assertEquals("Should have 4 slots", 4, adjacent.length);
        assertEquals("North room should be at index 0", northRoom, adjacent[0]);
        assertEquals("East room should be at index 1", eastRoom, adjacent[1]);
        assertNull("South room should be null", adjacent[2]);
        assertNull("West room should be null", adjacent[3]);
    }
    
    @Test
    public void SetAndGetEvent() {
        // Test setting and getting an event
        // checking out our event class.
        Player mockPlayer = Mockito.mock(Player.class);
        EscapeGame mockGame = Mockito.mock(EscapeGame.class);

        Event realEvent = new Event(EventType.POSITIVE, mockPlayer, mockGame) {
            @Override public void startEvent() {}
            @Override public void endEvent() {}
            @Override public void update(float delta) {}
            @Override public void draw() {}
            @Override public void drawUI() {}
        };
        
        room.setEvent(realEvent);
        
        assertEquals("Event should be set", realEvent, room.getEvent());
        assertEquals("Event type should be POSITIVE", EventType.POSITIVE, room.getEventType());
    }
    
    @Test
    public void NoEvent() {
        // Test that room has no event by default
        // when intially created, it doesn't have any event or anything. 
        // Just all the same standard variables
        assertNull("Event should be null by default", room.getEvent());
        assertEquals("Event type should be NONE", EventType.NONE, room.getEventType());
    }
}