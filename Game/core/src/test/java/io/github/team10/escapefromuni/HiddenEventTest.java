package io.github.team10.escapefromuni;

import com.badlogic.gdx.math.Vector2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;

/**
 * Tests for the HiddenEvent class.
 * HiddenEvent is tested with the HiddenEventType.LONGBOI value.
 */
public class HiddenEventTest extends HeadlessTestRunner {

    private HiddenEvent event;
    private Player mockPlayer;
    private EscapeGame mockGame;

    @Before
    public void setUp() {
        mockGame = mock(EscapeGame.class);
        mockPlayer = mock(Player.class);
        mockPlayer.positive_events = new Vector2(0, 0);
        mockPlayer.negative_events = new Vector2(0, 0);
        mockPlayer.hidden_events = new Vector2(0, 0);
        mockPlayer.total_events = new Vector2(0, 0);

        event = new HiddenEvent(HiddenEventType.LONGBOI, mockPlayer, mockGame);
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
        assertTrue("Event should be hidden", event.hidden);
        assertFalse("Event should not be finished", event.eventFinished);
    }

    @Test
    public void startEvent() {
        // event sprite should be initialised with the correct values
        event.startEvent();
        assertNotNull("Sprite should not be null", event.sprite);
        assertEquals("Sprite width should be 2f", 2f, event.sprite.getWidth(), 0.01f);
        assertEquals("Sprite height should be 2f", 2f, event.sprite.getHeight(), 0.01f);
        assertEquals("Sprite x position should be 8f", 8f, event.sprite.getX(), 0.01f);
        assertEquals("Sprite y position should be 3f",  3f, event.sprite.getY(), 0.01f);
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
    public void UpdateWithPlayerClose() {
        // test that updating the event with the player close reveals it
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8.5f, 5.5f));
        event.update(0.016f);
        assertFalse("Event should be revealed when player is close", event.hidden);
    }

    @Test
    public void RevealOnlyOnce() {
        // event should only carry out reveal logic once
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8.5f, 5.5f));
        event.update(0.016f);
        assertFalse("Event should be revealed", event.hidden);
        event.update(0.016f);
        assertFalse("Event should stay revealed", event.hidden);
    }

    @Test
    public void EndEventWhenRevealed() {
        // test that ending the event after reveal marks it as finished
        event.startEvent();
        when(mockPlayer.getCenter()).thenReturn(new Vector2(8.5f, 5.5f));
        event.update(0.016f);
        event.endEvent();
        assertTrue("Event should be finished after being revealed and ended", event.eventFinished);
    }

    @Test
    public void EndEventWhenNotRevealed() {
        // test that ending the event without reveal does not mark it as finished
        event.startEvent();
        event.endEvent();
        assertFalse("Event should not be finished if not revealed", event.eventFinished);
    }

    @Test
    public void storeReferences() {
        // events store a reference to player, escapegame and scoremanager
        assertSame("Event should store Player reference", mockPlayer, event.player);
        assertSame("Event should store EscapeGame reference", mockGame, event.game);
    }
}
