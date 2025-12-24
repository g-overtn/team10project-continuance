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
 * Uses mocking to avoid OpenGL shader compilation issues in headless environment.
 */
public class EscapeGameTest extends HeadlessTestRunner {
    
    private EscapeGame game;
    
    @Before
    public void setUp() {
        game = new EscapeGame();
        
        // We create mock objects for all of the graphic components to enable headless testing and really see if everything is in order and initialised as expected. 

        game.batch = mock(SpriteBatch.class);
        
        game.font = mock(BitmapFont.class);
        
        game.viewport = new FitViewport(16, 9);
        game.uiViewport = new FitViewport(1920, 1080);
        
        if (game.uiCamera == null) {
            game.uiCamera = mock(OrthographicCamera.class);
        }
    }
    
    @After
    public void tearDown() {
        game = null;
    }
    
    @Test
    public void GameInitialisation() {
        // Test that the game and its components are initialized
        // Checking all of our objects are active ahead of the testing procedure beginning
        assertNotNull("Game should be initialised", game);
        assertNotNull("SpriteBatch should be initialised", game.batch);
        assertNotNull("Font should be initialised", game.font);
        assertNotNull("Viewport should be initialised", game.viewport);
        assertNotNull("UI Viewport should be initialised", game.uiViewport);
    }
    
    @Test
    public void ViewportDimensions() {
        // Test that the window dimensions are as expected
        assertEquals("Viewport width should be 16", 16f, game.viewport.getWorldWidth(), 0.01f);
        assertEquals("Viewport height should be 9", 9f, game.viewport.getWorldHeight(), 0.01f);
    }
    
    @Test
    public void UIViewportDimensions() {
        // Test that the UI viewport dimensions are as expected
        assertEquals("UI Viewport width should be 1920", 1920f, game.uiViewport.getWorldWidth(), 0.01f);
        assertEquals("UI Viewport height should be 1080", 1080f, game.uiViewport.getWorldHeight(), 0.01f);
    }
    
    @Test
    public void BatchNotNull() {
        // Test that the SpriteBatch exists
        assertNotNull("Batch should not be null", game.batch);
    }
    
    @Test
    public void FontNotNull() {
        // Test that the font exists
        assertNotNull("Font should not be null", game.font);
    }
    
    @Test
    public void ViewportType() {
        // Test that the viewports are of type FitViewport
        assertTrue("Viewport should be FitViewport", game.viewport instanceof FitViewport);
        assertTrue("UI Viewport should be FitViewport", game.uiViewport instanceof FitViewport);
    }
    
    @Test
    public void Resize() {
        // Test the resize method
        game.resize(800, 600);
        assertNotNull("Viewport should still exist after resize", game.viewport);
        assertNotNull("UI Viewport should still exist after resize", game.uiViewport);
    }
}