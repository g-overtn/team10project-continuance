package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the GameOverScreen class.
 * Tests the game over screen functionality for both win and lose conditions.
 * We test both win and lose screens to ensure they behave correctly in either outcome.
 */
public class GameOverScreenTest extends HeadlessTestRunner {
    
    private GameOverScreen winScreen;
    private GameOverScreen loseScreen;
    private EscapeGame mockGame;
    private Timer mockTimer;
    private ScoreManager mockScoreManager;
    private BitmapFont mockFont;
    private FitViewport mockUIViewport;
    private OrthographicCamera mockUICamera;
    
    @Before
    public void setUp() {
        mockGame = mock(EscapeGame.class);
        mockTimer = mock(Timer.class);
        mockScoreManager = mock(ScoreManager.class);
        mockFont = mock(BitmapFont.class);
        mockUIViewport = mock(FitViewport.class);
        mockUICamera = mock(OrthographicCamera.class);
        mockGame.achievementManager = new AchievementManager();
        mockGame.achievementManager.initAchievements();
        


        when(mockUIViewport.getWorldWidth()).thenReturn(1920f);
        when(mockUIViewport.getWorldHeight()).thenReturn(1080f);
        mockGame.font = mockFont;
        mockGame.uiViewport = mockUIViewport;
        mockGame.uiCamera = mockUICamera;
        mockGame.batch = mock(SpriteBatch.class);
        
        when(mockTimer.getTimeSeconds()).thenReturn(120);
        when(mockTimer.getTimeLeftSeconds()).thenReturn(180);
        when(mockScoreManager.CalculateFinalScore(anyInt())).thenReturn(1500);
        
        winScreen = new GameOverScreen(mockGame, true, mockTimer, mockScoreManager);
        loseScreen = new GameOverScreen(mockGame, false, mockTimer, mockScoreManager);
    }
    
    @After
    public void tearDown() {
        if (winScreen != null) {
            winScreen.dispose();
        }
        if (loseScreen != null) {
            loseScreen.dispose();
        }
    }
    
    @Test
    public void WinScreenInit() {
        // Win screen setup
        assertNotNull("Win screen should be initialised", winScreen);
    }
    
    @Test
    public void LoseScreenInit() {
        // Lose screen setup.
        assertNotNull("Lose screen should be initialised", loseScreen);
    }
    
    @Test
    public void Show() {
        // show() test
        winScreen.show();
        loseScreen.show();
    }
    
    @Test
    public void Resize() {
        // resize windows
        winScreen.resize(800, 600);
        loseScreen.resize(800, 600);
    }
    
    @Test
    public void Pause() {
        // pause() method
        winScreen.pause();
        loseScreen.pause();
    }
    
    @Test
    public void Resume() {
        // resume() method
        winScreen.resume();
        loseScreen.resume();
    }
    
    @Test
    public void Hide() {
        // hide() method
        winScreen.hide();
        loseScreen.hide();
    }
}