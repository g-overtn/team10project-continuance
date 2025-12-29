package io.github.team10.escapefromuni;

import com.badlogic.gdx.math.Vector2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the PositiveEvent class.
 * PositiveEvent is tested with the PositiveEventType.GREGGS value.
 */
public class PositiveEventTest extends HeadlessTestRunner {

    private PositiveEvent event;
    private Player mockPlayer;
    private EscapeGame mockGame;
    private ScoreManager manager;

    @Before
    public void setUp() {
        mockGame = mock(EscapeGame.class);
        manager = new ScoreManager();
        mockPlayer = mock(Player.class);
        mockPlayer.positive_events = new Vector2(0, 0);
        mockPlayer.negative_events = new Vector2(0, 0);
        mockPlayer.hidden_events = new Vector2(0, 0);
        mockPlayer.total_events = new Vector2(0, 0);

        event = new PositiveEvent(PositiveEventType.GREGGS, mockPlayer, mockGame, manager);
        event.game.achievementManager = mock(AchievementManager.class);
    }

    @After
    public void tearDown() {
        if (event != null) { event.dispose(); }
        if (mockPlayer != null) { mockPlayer.dispose(); }
        if (mockGame != null) { mockGame.dispose(); }
    }

    @Test
    public void initialise() {
        // event should be initialised properly
        assertNotNull("Event should not be null", event);
        assertFalse("Event should not be used", event.getUsed());
        assertFalse("Event should not be finished", event.eventFinished);
    }

    @Test
    public void startEvent() {
        // event sprite should be initialised with the correct values
        event.startEvent();
        assertNotNull("Sprite should not be null", event.sprite);
        assertEquals("Sprite width should be 3f", 3f, event.sprite.getWidth(), 0.01f);
        assertEquals("Sprite height should be 2f", 2f, event.sprite.getHeight(), 0.01f);
        assertEquals("Sprite x position should be 6.5f", 6.5f, event.sprite.getX(), 0.01f);
        assertEquals("Sprite y position should be 3.5f",  3.5f, event.sprite.getY(), 0.01f);
    }

    @Test
    public void updateWhenPlayerFar() {
        // event should not trigger pickup logic when player is outside of bounds
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(0, 0));
        event.update(0.016f);
        verify(mockPlayer, never()).increaseSpeed(anyFloat());
    }

    @Test
    public void UpdateWhenPlayerClose() {
        // event should trigger pickup logic once player is within bounds
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8f, 4.5f));
        event.update(0.016f);
        verify(mockPlayer, times(1)).increaseSpeed(2f);
    }

    @Test
    public void PickupOnlyOnce() {
        // event should only carry out pickup logic once
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8f, 4.5f));
        event.update(0.016f);
        event.update(0.016f);
        event.update(0.016f);
        verify(mockPlayer, times(1)).increaseSpeed(2f);
    }

    @Test
    public void EndEventNotUsed() {
        // event should not be able to finish before it has been used
        event.startEvent();
        event.endEvent();
        assertFalse("Event should not be finished if not used", event.eventFinished);
    }

    @Test
    public void EndEventUsed() {
        // event should be able to be finished once it has been used
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8f, 4.5f));
        event.update(0.016f);
        event.endEvent();
        assertTrue("Event should be finished after being used and ended", event.eventFinished);
    }

    @Test
    public void storeReferences() {
        // events store a reference to player, escapegame and scoremanager
        assertSame("Event should store Player reference", mockPlayer, event.player);
        assertSame("Event should store EscapeGame reference", mockGame, event.game);
        assertSame("Event should store ScoreManager reference", manager, event.getScoreManager());
    }

}
