package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the EscapeGame class.
 */
public class EscapeGameTest extends HeadlessTestRunner {
    
    private EscapeGame game;
    
    @Before
    public void setUp() {
        game = new EscapeGame();
        game.batch = mock(SpriteBatch.class);
        game.font = mock(BitmapFont.class);
        game.viewport = new FitViewport(16, 9);
        game.uiViewport = new FitViewport(1920, 1080);
        game.uiCamera = mock(OrthographicCamera.class);
    }
    
    @After
    public void tearDown() {
        game = null;
    }
    
    @Test
    public void gameInit(){
        // Attributes set up check
        assertNotNull("Game should be initialised", game);
        assertNotNull("SpriteBatch should be initialised", game.batch);
        assertNotNull("Font should be initialised", game.font);
        assertNotNull("Viewport should be initialised", game.viewport);
        assertNotNull("UI Viewport should be initialised", game.uiViewport);
    }
    
    @Test
    public void viewportDims() {
        // screen dimensions checks
        assertEquals("Viewport width should be 16", 16f, game.viewport.getWorldWidth(), 0.01f);
        assertEquals("Viewport height should be 9", 9f, game.viewport.getWorldHeight(), 0.01f);
        assertEquals("UI Viewport width should be 1920", 1920f, game.uiViewport.getWorldWidth(), 0.01f);
        assertEquals("UI Viewport height should be 1080", 1080f, game.uiViewport.getWorldHeight(), 0.01f);
    }
    
    
    @Test
    public void Resize() {
        // Window suze change
        game.resize(800, 600);
        assertNotNull("Viewport should still exist after resize", game.viewport);
        assertNotNull("UI Viewport should still exist after resize", game.uiViewport);
    }
}