package io.github.team10.escapefromuni;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the Event abstract class.
 * We're going to have to create implementations of this class to test it rigorously. 
 * An attempt has been made to test it using the existing Event child classes. 
 * We call the child objects and check that their EventType is as expected.
 */
public class EventTest extends HeadlessTestRunner {
    
    private Player mockPlayer;
    private EscapeGame mockGame;
    
    @Before
    public void setUp() {
        mockPlayer = mock(Player.class);
        mockGame = mock(EscapeGame.class);
    }
    
    @Test
    public void EventTypePositive() {
        // Test that EventGreggs has type POSITIVE.
        EventGreggs positiveEvent = new EventGreggs(mockPlayer, mockGame);
        assertEquals("Event type should be POSITIVE", EventType.POSITIVE, positiveEvent.type);
    }
    
    @Test
    public void EventTypeNegative() {
        // Test that EventTHE3 has type NEGATIVE (it's an exam)
        ScoreManager mockScoreManager = mock(ScoreManager.class);
        EventTHE3 negativeEvent = new EventTHE3(mockPlayer, mockGame, mockScoreManager);
        assertEquals("Event type should be NEGATIVE", EventType.NEGATIVE, negativeEvent.type);
    }
    
    @Test
    public void EventTypeHidden() {
        // Test that EventLongboi has type HIDDEN. This is one of the hidden events asked to be implemented. 
        EventLongboi hiddenEvent = new EventLongboi(mockPlayer, mockGame);
        assertEquals("Event type should be HIDDEN", EventType.HIDDEN, hiddenEvent.type);
    }
    
    @Test
    public void EventStoresPlayerReference() {
        // Test that the event stores the player reference correctly
        EventGreggs event = new EventGreggs(mockPlayer, mockGame);
        assertSame("Event should store player reference", mockPlayer, event.player);
    }
    
    @Test
    public void EventStoresGameReference() {
        // Test that the event stores the game reference correctly
        EventGreggs event = new EventGreggs(mockPlayer, mockGame);
        assertSame("Event should store game reference", mockGame, event.game);
    }
}