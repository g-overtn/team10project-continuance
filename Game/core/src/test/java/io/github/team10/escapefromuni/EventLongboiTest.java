package io.github.team10.escapefromuni;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the EventLongboi class.
 */
public class EventLongboiTest extends HeadlessTestRunner {
    // basic setup of a potential scenario in the game so we can test functionality.
    private EventLongboi event;
    private Player mockPlayer;
    private EscapeGame mockGame;
    private FitViewport mockUIViewport;
    
    @Before
    public void setUp() {
        mockPlayer = mock(Player.class);
        mockGame = mock(EscapeGame.class);
        mockUIViewport = mock(FitViewport.class);
        
        when(mockUIViewport.getWorldWidth()).thenReturn(1920f);
        when(mockUIViewport.getWorldHeight()).thenReturn(1080f);
        mockGame.uiViewport = mockUIViewport;
        
        event = new EventLongboi(mockPlayer, mockGame);
    }
    
    @After
    // Wipe the slate clean once the tests are done.
    public void tearDown() {
        if (event != null && event.eventFinished) {
            event.dispose();
        }
    }
    
    @Test
    public void EventInitialisation() {
        // Test that the event is initialised correctly
        // Ensure the event is hidden ti begin with at least.
        assertNotNull("Event should be initialised", event);
        assertEquals("Event type should be HIDDEN", EventType.HIDDEN, event.type);
        assertFalse("Event should not be finished initially", event.eventFinished);
    }
    
    @Test
    public void StartEvent() {
        // Test that the event starts correctly
        // height and width check.
        event.startEvent();
        assertNotNull("Longboi sprite should be created after startEvent", event.longboiSprite);
        assertEquals("Sprite width should be 1f", 1f, event.longboiSprite.getWidth(), 0.01f);
        assertEquals("Sprite height should be 2f", 2f, event.longboiSprite.getHeight(), 0.01f);
    }
    
    @Test
    public void StartEventWhenFinished() {
        // Test that starting the event when already finished does nothing
        event.eventFinished = true;
        event.startEvent();
        assertNull("Longboi sprite should not be created if event already finished", event.longboiSprite);
    }
    
    @Test
    public void SpritePosition() {
        // Test that the Longboi sprite is positioned correctly
        event.startEvent();
        assertEquals("Sprite X position should be 8f", 8f, event.longboiSprite.getX(), 0.01f);
        assertEquals("Sprite Y position should be 3f", 3f, event.longboiSprite.getY(), 0.01f);
    }
    
    @Test
    public void HiddenInitially() {
        // Testing the hidden event functionality as per the brief
        // make sure it stays hidden till exposure.
        event.startEvent();
        assertTrue("Event should be hidden initially", event.hidden);
    }
    
    @Test
    public void UpdateWithPlayerFarAway() {
        // Testing to see if the hidden event gets triggered even when the player is far away. 
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(0f, 0f));
        
        event.update(0.016f);
        
        assertTrue("Event should remain hidden when player is far away", event.hidden);
    }
    
    @Test
    public void UpdateWithPlayerClose() {
        // Test that updating the event with the player close reveals it
        event.startEvent();
        // Position player close to Longboi (8f, 3f position, so center around 8.5f, 4f)
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8.5f, 5.5f));
        
        event.update(0.016f);
        
        assertFalse("Event should be revealed when player is close", event.hidden);
    }
    
    @Test
    public void RevealOnlyOnce() {
        // Test that the reveal only happens once even with multiple updates
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8.5f, 5.5f));
        
        event.update(0.016f);
        assertFalse("Event should be revealed", event.hidden);
        
        event.update(0.016f);
        assertFalse("Event should stay revealed", event.hidden);
    }
    
    @Test
    public void EndEventWhenRevealed() {
        // Test that ending the event after reveal marks it as finished
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8.5f, 5.5f));
        event.update(0.016f);
        event.endEvent();
        assertTrue("Event should be finished after being revealed and ended", event.eventFinished);
    }
    
    @Test
    public void EndEventWhenNotRevealed() {
        // Test that ending the event without reveal does not mark it as finished
        event.startEvent();
        event.endEvent();
        assertFalse("Event should not be finished if not revealed", event.eventFinished);
    }
}