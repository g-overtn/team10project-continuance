package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;

/**
 * Tests for the Room class.
 * This test suite validates room creation, connections between rooms, and event management.
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
    public void roomInit() {
        // a room is properly initialised with default values.
        assertNotNull("Room should be initialised", room);
        assertEquals("Room texture should match", mockTexture, room.getRoomTexture());
        assertFalse("Room should not be exit by default", room.getExit());
        assertEquals("Room should have no event by default", EventType.NONE, room.getEventType());
    }

    @Test
    public void exitRoom() {
        // room can be marked as an exit room.
        // Exit rooms trigger the win condition when the player enters them.
        Room exitRoom = new Room(mockTexture, true);
        assertTrue("Room should be marked as exit", exitRoom.getExit());
    }

    @Test
    public void AddAdjacent() {
        // we can connect one room to another in a specific direction.
        // We add a room to the north and verify it's accessible, while other directions remain null.
        Room northRoom = new Room(mockTexture);
        room.addAdjacent(northRoom, DoorDirection.NORTH);

        assertEquals("North room should be adjacent", northRoom, room.getAdjacent(DoorDirection.NORTH));
        assertNull("East room shouldn't exist", room.getAdjacent(DoorDirection.EAST));
    }

    @Test
    public void allDirections() {
        // we can connect rooms in all four directions.
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
    public void getAdjacent() {
        // The array should always have 4 slots (NORTH, EAST, SOUTH, WEST), with null for unconnected directions.
        Room northRoom = new Room(mockTexture);
        Room eastRoom = new Room(mockTexture);

        room.addAdjacent(northRoom, DoorDirection.NORTH);
        room.addAdjacent(eastRoom, DoorDirection.EAST);

        Room[] adjacent = room.getAllAdjacent();
        assertEquals("Should have 4 slots", 4, adjacent.length);
        assertEquals("North room should be at index 0", northRoom, adjacent[0]);
        assertEquals("East room should be at index 1", eastRoom, adjacent[1]);
        assertNull("South room should not exist", adjacent[2]);
        assertNull("West room should not exist", adjacent[3]);
    }

    @Test
    public void setAndGetEvent() {
        // set and fetch event test

        Player mockPlayer = Mockito.mock(Player.class);
        EscapeGame mockGame = Mockito.mock(EscapeGame.class);
        ScoreManager mockScoreManager = Mockito.mock(ScoreManager.class);

        mockPlayer.positive_events = new Vector2(0, 0);
        mockPlayer.negative_events = new Vector2(0, 0);
        mockPlayer.hidden_events = new Vector2(0, 0);
        mockPlayer.total_events = new Vector2(0, 0);

        PositiveEvent posEvent = new PositiveEvent(PositiveEventType.GREGGS, mockPlayer, mockGame, mockScoreManager);
        NegativeEvent negEvent = new NegativeEvent(NegativeEventType.THE3, mockPlayer, mockGame, mockScoreManager);
        HiddenEvent hidEvent = new HiddenEvent(HiddenEventType.LONGBOI,  mockPlayer, mockGame);

        room.setEvent(posEvent);
        assertEquals("Event should be set", posEvent, room.getEvent());
        assertEquals("Event type should be POSITIVE", EventType.POSITIVE, room.getEventType());
        posEvent.dispose();

        room.setEvent(negEvent);
        assertEquals("Event should be set", negEvent, room.getEvent());
        assertEquals("Event type should be NEGATIVE", EventType.NEGATIVE, room.getEventType());
        posEvent.dispose();

        room.setEvent(hidEvent);
        assertEquals("Event should be set", hidEvent, room.getEvent());
        assertEquals("Event type should be HIDDEN", EventType.HIDDEN, room.getEventType());
        posEvent.dispose();

    }

    @Test
    public void NoEvent() {
        // Verifies the room default state, which should have no event.
        assertNull("Event should be blank by defult", room.getEvent());
        assertEquals("Event type should be NONE", EventType.NONE, room.getEventType());
    }
}
