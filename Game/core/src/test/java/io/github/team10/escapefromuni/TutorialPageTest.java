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
 * Tests for the TutorialPage class.
 */
public class TutorialPageTest extends HeadlessTestRunner {
    
    private TutorialPage tutorialPage;
    private EscapeGame game;
    
    @Before
    public void setUp() {
        game = new EscapeGame();
        
        // Mock graphics components
        game.batch = mock(SpriteBatch.class);
        game.font = mock(BitmapFont.class);
        game.viewport = new FitViewport(16, 9);
        game.uiViewport = new FitViewport(1920, 1080);
        
        tutorialPage = new TutorialPage(game);
    }
    
    @After
    public void tearDown() {
        if (tutorialPage != null) {
            tutorialPage.dispose();
        }
        game = null;
    }
    
    @Test
    public void TutorialPageInitialisation() {
        // Test that the TutorialPage and its components are initialised
        assertNotNull("TutorialPage should be initialised", tutorialPage);
    }
    
    @Test
    public void ShowMethod() {
        // Test that the show method loads the tutorial image
        tutorialPage.show();
        assertNotNull("Tutorial image should be loaded after show", tutorialPage.tutorialImage);
    }
    
    @Test
    public void ResizeMethod() {
        // Test the resize method
        tutorialPage.show();
        tutorialPage.resize(800, 600);
    }
    
    @Test
    public void PauseMethod() {
        // Test the pause method
        tutorialPage.pause();
    }
    
    @Test
    public void ResumeMethod() {
        // Test the resume method
        tutorialPage.resume();
    }
    
    @Test
    public void HideMethod() {
        // Test the hide method
        tutorialPage.hide();
    }
    
    @Test
    public void DisposeMethod() {
        // Test the dispose method
        tutorialPage.show();
        tutorialPage.dispose();
    }
}
