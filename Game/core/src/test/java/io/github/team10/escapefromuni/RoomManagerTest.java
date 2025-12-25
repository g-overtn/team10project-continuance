package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the RoomManager class.
 */
public class RoomManagerTest extends HeadlessTestRunner {
    
    private RoomManager roomManager;
    private EscapeGame game;
    private Player player;
    private ScoreManager scoreManager;
    private Timer timer;
    
    @Before
    public void setUp() {

        game = new EscapeGame();
        game.batch = mock(SpriteBatch.class);
        game.font = mock(BitmapFont.class);
        game.viewport = new FitViewport(16, 9);
        game.uiViewport = new FitViewport(1920, 1080);
        
        player = new Player(3f, 1f, 1f, game);
        scoreManager = new ScoreManager();
        timer = new Timer();
        
        roomManager = new RoomManager(game, player, scoreManager, timer);
        roomManager.initialiseMap();
    }
    
    @After
    public void tearDown() {
        if (roomManager != null) {
            roomManager.dispose();
        }
        if (player != null) {
            player.dispose();
        }
        game = null;
    }
    
    @Test
    public void RoomManagerInit() {
        // RoomManager setup right
        assertNotNull("RoomManager should be initialised", roomManager);
        assertNotNull("Current room should be set", roomManager.currentRoom);
    }
    
    @Test
    public void doorsInitialised() {
        // doors are initialised.
        assertNotNull("Doors array should be initialised", roomManager.doors);
        assertEquals("Should have 4 doors", 4, roomManager.doors.length);
        
        for (Door door : roomManager.doors) {
            assertNotNull("Each door should be initialised", door);
        }
    }
    
    @Test
    public void doorDirections() {
        // Test that doors have correct directions
        assertEquals("First door should be NORTH", DoorDirection.NORTH, roomManager.doors[0].direction);
        assertEquals("Second door should be EAST", DoorDirection.EAST, roomManager.doors[1].direction);
        assertEquals("Third door should be SOUTH", DoorDirection.SOUTH, roomManager.doors[2].direction);
        assertEquals("Fourth door should be WEST", DoorDirection.WEST, roomManager.doors[3].direction);
    }
    
    @Test
    public void initialRoomSetup() {
        // initial room is set up correctly
        assertNotNull("Current room should exist", roomManager.currentRoom);
        assertNotNull("Current room texture should exist", roomManager.currentRoom.getRoomTexture());
    }
    
    @Test
    public void TexturesLoading() {
        // room textures are loaded correctly
        assertNotNull("Room textures map should be initialised", roomManager.roomTextures);
        assertTrue("Room textures should contain room1", roomManager.roomTextures.containsKey("room1"));
        assertTrue("Room textures should contain room2", roomManager.roomTextures.containsKey("room2"));
        assertTrue("Room textures should contain room3", roomManager.roomTextures.containsKey("room3"));
    }
    

    
    @Test
    public void Update() {
        // update method runs with whatever event needs to appear
        roomManager.update(0.016f);
    }
    
    @Test
    public void NoCollisionRoom() {
        // Test that checkDoorCollision does not change room when no collision occurs
        player.setCenter(8f, 4.5f);
        
        Room initialRoom = roomManager.currentRoom;
        roomManager.checkDoorCollision();
        
        assertSame("Room should not change without collision", initialRoom, roomManager.currentRoom);
    }
}
