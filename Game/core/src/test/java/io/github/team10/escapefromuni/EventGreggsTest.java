package io.github.team10.escapefromuni;

import com.badlogic.gdx.math.Vector2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the EventGreggs class.
 */
public class EventGreggsTest extends HeadlessTestRunner {
    
    private EventGreggs event;
    private Player mockPlayer;
    private EscapeGame mockGame;
    
    @Before
    public void setUp() {
        // setting up an instance of the game.
        mockPlayer = mock(Player.class);
        mockGame = mock(EscapeGame.class);
        event = new EventGreggs(mockPlayer, mockGame);
    }
    
    @After
    public void tearDown() {
        // tests complete so we can clean slate
        if (event != null && event.eventFinished) {
            event.dispose();
        }
    }
    
    @Test
    public void EventInitialisation() {
        // Test that the event is initialised correctly
        assertNotNull("Event should be initialised", event);
        assertEquals("Event type should be POSITIVE", EventType.POSITIVE, event.type);
        assertFalse("Event should not be finished initially", event.eventFinished);
    }
    
    @Test
    public void StartEvent() {
        // Test that the event starts correctly
        // checking the attributes of the class
        event.startEvent();
        assertNotNull("Greggs sprite should be created after startEvent", event.greggsSprite);
        assertEquals("Sprite width should be 3f", 3f, event.greggsSprite.getWidth(), 0.01f);
        assertEquals("Sprite height should be 2f", 2f, event.greggsSprite.getHeight(), 0.01f);
    }
    
    @Test
    public void StartEventWhenFinished() {
        // Test that starting the event when already finished does nothing
        // We are ensuring the event only happens once.
        event.eventFinished = true;
        event.startEvent();
        assertNull("Greggs sprite should not be created if event already finished", event.greggsSprite);
    }
    
    @Test
    public void SpritePosition() {
        // Testing for Greggs sprite positioning
        event.startEvent();
        assertEquals("Sprite X position should be 6.5f", 6.5f, event.greggsSprite.getX(), 0.01f);
        assertEquals("Sprite Y position should be 3.5f", 3.5f, event.greggsSprite.getY(), 0.01f);
    }
    
    @Test
    public void UpdateWithPlayerFarAway() {
        // Testing pickup when player is far away
        // the player is far away so there's no need for speed increase.
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(0f, 0f));
        
        event.update(0.016f);
        
        verify(mockPlayer, never()).increaseSpeed(anyFloat());
    }
    
    @Test
    public void UpdateWithPlayerClose() {
        // Test that updating the event with the player close triggers pickup
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8f, 4.5f));
        
        event.update(0.016f);
        
        //Considering how close the player is, increaseSpeed only needs to be called once, but can be called multiple times if necessary. 
        verify(mockPlayer, times(1)).increaseSpeed(2f);
    }
    
    @Test
    public void PickupOnlyOnce() {
        // Test that the pickup only happens once even with multiple updates
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8f, 4.5f));
        
        event.update(0.016f);
        event.update(0.016f);
        event.update(0.016f);
        
        // Should only be called once despite multiple updates
        verify(mockPlayer, times(1)).increaseSpeed(2f);
    }
    
    @Test
    public void EndEventNotUsed() {
        // Test that ending the event without pickup does not mark it as finished
        // It's not been used so it's active
        event.startEvent();
        event.endEvent();
        
        assertFalse("Event should not be finished if not used", event.eventFinished);
    }
    
    @Test
    public void EndEventUsed() {
        // Test that ending the event after pickup marks it as finished
        // It has been used so we can now move on to the other
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8f, 4.5f));
        event.update(0.016f);
        
        event.endEvent();
        assertTrue("Event should be finished after being used and ended", event.eventFinished);
    }
}