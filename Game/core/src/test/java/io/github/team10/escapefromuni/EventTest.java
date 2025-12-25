package io.github.team10.escapefromuni;

import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the Event abstract class.
 * This set of tests check that different event types (POSITIVE, NEGATIVE, HIDDEN) are correctly initialised and that events properly store references to the player and game objects.
 */
public class EventTest extends HeadlessTestRunner {
    
    private Player mockPlayer;
    private EscapeGame mockGame;
    private AchievementManager mockAchievementManager;
    
    @Before
    public void setUp() {
        mockPlayer = mock(Player.class);
        mockGame = mock(EscapeGame.class);
        mockAchievementManager = mock(AchievementManager.class);
        
        mockPlayer.positive_events = new Vector2(0, 0);
        mockPlayer.negative_events = new Vector2(0, 0);
        mockPlayer.hidden_events = new Vector2(0, 0);
        mockPlayer.total_events = new Vector2(0, 0);
    }
    
    @Test
    public void positiveEvent() {
        // EventGreggs initialised as POSITIVE
        EventGreggs positiveEvent = new EventGreggs(mockPlayer, mockGame);
        assertEquals("Event type should be POSITIVE", EventType.POSITIVE, positiveEvent.type);
        positiveEvent.dispose();
    }
    
    @Test
    public void negativeEvent() {
        // EventTHE3 initialised as NEGATIVE
        ScoreManager mockScoreManager = mock(ScoreManager.class);
        EventTHE3 negativeEvent = new EventTHE3(mockPlayer, mockGame, mockScoreManager);
        assertEquals("Event type should be NEGATIVE", EventType.NEGATIVE, negativeEvent.type);
    }
    
    @Test
    public void HiddenEvent() {
        // Longboi initialised as hidden
        EventLongboi hiddenEvent = new EventLongboi(mockPlayer, mockGame);
        assertEquals("Event type should be HIDDEN", EventType.HIDDEN, hiddenEvent.type);
        hiddenEvent.dispose();
    }
    
    @Test
    public void storePlayerRef() {
        // events store a reference to player object.
        EventGreggs event = new EventGreggs(mockPlayer, mockGame);
        assertSame("Event should store player reference", mockPlayer, event.player);
        event.dispose();
    }
    
    @Test
    public void storeGameRef() {
        // events store a reference to game object.
        EventGreggs event = new EventGreggs(mockPlayer, mockGame);
        assertSame("Event should store game reference", mockGame, event.game);
        event.dispose();
    }
}