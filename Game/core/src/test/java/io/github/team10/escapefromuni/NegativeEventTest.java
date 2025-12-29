package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the NegativeEvent class.
 * NegativeEvent is tested with the NegativeEventType.THE3 value.
 */
public class NegativeEventTest extends HeadlessTestRunner {

    private NegativeEvent event;
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

        event = new NegativeEvent(NegativeEventType.THE3, mockPlayer, mockGame, manager);
        event.game.achievementManager = mock(AchievementManager.class);
        event.game.viewport = new FitViewport(16, 9);
        event.game.uiViewport = new FitViewport(1920, 1080);
    }

    @After
    public void tearDown() {
        for (Sprite s : event.getSprites()) { s.getTexture().dispose(); }
        if (mockPlayer != null) { mockPlayer.dispose(); }
        if (mockGame != null) { mockGame.dispose(); }
    }

    @Test
    public void initialise() {
        // event should be initialised properly
        event.startEvent();
        assertNotNull("Event should not be null", event);
        assertFalse("Event should not be answered", event.getAnswered());
        assertFalse("Event should not be finished", event.eventFinished);
        assertNotNull("Question text should not be null", event.getQuestionText());
        assertEquals("feedbackText should be empty", "", event.getFeedbackText());
    }


    @Test
    public void initialiseUI() {
        // event sprites should be initialised with the correct values
        // only tests sizes and locations depends on screen size
        event.startEvent();
        Sprite[] sprites = event.getSprites();
        for (Sprite sprite : sprites) {
            assertNotNull("Sprite should not be null", sprite);
        }
        Sprite title = sprites[0];
        Sprite question =  sprites[1];
        Sprite leftButton =  sprites[2];
        Sprite rightButton =  sprites[3];

        assertEquals("titlePanelSprite width should be 480f", 480f, title.getWidth(), 0.01f);
        assertEquals("titlePanelSprite height should be 120f", 120f, title.getHeight(), 0.01f);
        assertEquals("questionPanelSprite width should be 1200", 1200f, question.getWidth(), 0.01f);
        assertEquals("questionPanelSprite height should be 360", 360f, question.getHeight(), 0.01f);
        assertEquals("leftButtonSprite width should be 600", 600f, leftButton.getWidth(), 0.01f);
        assertEquals("leftButtonSprite height should be 240", 240f, leftButton.getHeight(), 0.01f);
        assertEquals("rightButtonSprite width should be 600", 600f, rightButton.getWidth(), 0.01f);
        assertEquals("rightButtonSprite height should be 240", 240f, rightButton.getHeight(), 0.01f);

    }

    @Test
    public void endEvent() {
        // event should be finished
        event.endEvent();
        assertTrue("Event should be finished", event.eventFinished);
    }

    @Test
    public void storeReferences() {
        // events store a reference to player, escapegame and scoremanager
        assertSame("Event should store Player reference", mockPlayer, event.player);
        assertSame("Event should store EscapeGame reference", mockGame, event.game);
        assertSame("Event should store ScoreManager reference", manager, event.getScoreManager());
    }
}
