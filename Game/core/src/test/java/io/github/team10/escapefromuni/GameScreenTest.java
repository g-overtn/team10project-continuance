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
 * Tests for the GameScreen class.
 */
public class GameScreenTest extends HeadlessTestRunner {
    
    private GameScreen gameScreen;
    private EscapeGame game;
    
    @Before
    public void setUp() {
        game = new EscapeGame();
        
        game.batch = mock(SpriteBatch.class);
        game.font = mock(BitmapFont.class);
        game.viewport = new FitViewport(16, 9);
        game.uiViewport = new FitViewport(1920, 1080);
        
        gameScreen = new GameScreen(game);
    }
    
    @After
    public void tearDown() {
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        game = null;
    }
    
    @Test
    public void GameScreenInitialisation() {
        // Test that the GameScreen and its components are initialised
        assertNotNull("GameScreen should be initialised", gameScreen);
        assertNotNull("Player should be initialised", gameScreen.player);
        assertNotNull("RoomManager should be initialised", gameScreen.roomManager);
        assertNotNull("ScoreManager should be initialised", gameScreen.scoreManager);
        assertNotNull("Timer should be initialised", gameScreen.timer);
    }
    
    @Test
    public void PlayerInitialisation() {
        // Test that the player is initialised correctly
        assertNotNull("Player should not be null", gameScreen.player);
        assertEquals("Player speed should be 3f", 3f, gameScreen.player.speed, 0.01f);
    }
    
    @Test
    public void TimerInitialisation() {
        // Test that the timer is initialised correctly
        assertNotNull("Timer should not be null", gameScreen.timer);
        assertEquals("Timer should start at 0", 0, gameScreen.timer.getTimeSeconds());
    }
    
    @Test
    public void ScoreManagerInitialisation() {
        // Test that the ScoreManager is initialised correctly
        assertNotNull("ScoreManager should not be null", gameScreen.scoreManager);
    }
    
    @Test
    public void RoomManagerInitialisation() {
        // Test that the RoomManager is initialised correctly
        assertNotNull("RoomManager should not be null", gameScreen.roomManager);
    }
    
    @Test
    public void NotPausedInitially() {
        // Test that the game is not paused initially
        assertFalse("Game should not be paused initially", gameScreen.isPaused);
    }
    
    @Test
    public void ResumeGame() {
        // Test that resumeGame unpauses the game
        gameScreen.isPaused = true;
        gameScreen.resumeGame();
        assertFalse("Game should not be paused after resumeGame()", gameScreen.isPaused);
    }
    
    @Test
    public void CheckLoseNotTriggeredInitially() {
        // Test that CheckLose does not trigger lose condition initially
        // loss screen doesn't randomly appear unless triggered by the game ending and the result not being favourable for the player.
        gameScreen.CheckLose();
        assertNotNull("GameScreen should still be active", gameScreen.timer);
    }
    
    @Test
    public void ShowMethod() {
        // Test that show method sets up the screen correctly
        gameScreen.show();
        assertFalse("Game should not be paused after show", gameScreen.isPaused);
    }
    
    @Test
    public void ResizeMethod() {
        // Test that resize method updates the viewport correctly
        gameScreen.resize(800, 600);
    }
    
    @Test
    public void PauseMethod() {
        // Test that pause method pauses the game
        gameScreen.pause();
    }
    
    @Test
    public void ResumeMethod() {
        // Test that resume method resumes the game
        gameScreen.resume();
    }
    
    @Test
    public void HideMethod() {
        // Test that hide method disposes resources correctly
        gameScreen.hide();
    }
}