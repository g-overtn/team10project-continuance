package io.github.team10.escapefromuni;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the ScoreManager class.
 */
public class ScoreManagerTest extends HeadlessTestRunner {
    
    private ScoreManager scoreManager;
    
    @Before
    public void setUp() {
        scoreManager = new ScoreManager();
    }
    
    @Test
    public void InitialScore() {
        // Just making sure that the attributes are set up right. 
        assertEquals("Initial score should be 0", 0, scoreManager.getScore());
    }
    
    @Test
    public void increaseScore() {
        // Test increasing the score
        scoreManager.increaseScore(100);
        assertEquals("Score should be 100", 100, scoreManager.getScore());
    }
    
    @Test
    public void multipleIncreases() {
        // Test multiple score increases
        scoreManager.increaseScore(50);
        scoreManager.increaseScore(75);
        assertEquals("Score should be 125", 125, scoreManager.getScore());
    }
    
    @Test
    public void Reset() {
        // Test resetting the score
        scoreManager.increaseScore(500);
        scoreManager.reset();
        assertEquals("Score should be 0 after reset", 0, scoreManager.getScore());
    }
    
    @Test
    public void CalculateFinalScore() {
        // Test final score calculation with time bonus
        scoreManager.increaseScore(1000);
        int finalScore = scoreManager.CalculateFinalScore(60);
        assertEquals("Final score should be 4000 (1000 + 60*50)", 4000, finalScore);
    }
    
    @Test
    public void calculateFinalScoreNoTime() {
        // Test final score calculation with no time left
        scoreManager.increaseScore(500);
        int finalScore = scoreManager.CalculateFinalScore(0);
        assertEquals("Final score should be 500", 500, finalScore);
    }
}