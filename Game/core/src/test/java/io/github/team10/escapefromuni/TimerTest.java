package io.github.team10.escapefromuni;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the Timer class.
 * Validates timing logic, countdown functionality, and state management.
 */
public class TimerTest extends HeadlessTestRunner {
    
    private Timer timer;
    
    @Before
    public void setUp() {
        timer = new Timer();
    }
    
    @Test
    public void TimerInit() {
        // Test that the timer is initialised correctly
        assertEquals("Initial time should be 0", 0, timer.getTimeSeconds());
        assertEquals("Initial time left should be 300 seconds", 300, timer.getTimeLeftSeconds());
        assertFalse("Timer should not be finished initially", timer.isFinished());
    }
    
    @Test
    public void TimerUpdate() {
        // Test updating the timer
        timer.update(1.0f);
        assertEquals("Time should increase by 1 second", 1, timer.getTimeSeconds());
        assertEquals("Time left should decrease by 1 second", 299, timer.getTimeLeftSeconds());
    }
    
    @Test
    public void TimerMultipleUpdates() {
        // Test multiple updates to the timer
        timer.update(5.5f);
        assertEquals("Time should be 5 seconds", 5, timer.getTimeSeconds());
        
        timer.update(10.3f);
        assertEquals("Time should be 15 seconds", 15, timer.getTimeSeconds());
        assertEquals("Time left should be 285 seconds", 284, timer.getTimeLeftSeconds());
    }
    
    @Test
    public void TimeTicking() {
        // Test the hasReached method
        assertFalse("Timer should not have reached 10 seconds", timer.hasReached(10));
        
        timer.update(10.0f);
        assertTrue("Timer should have reached 10 seconds", timer.hasReached(10));
        assertTrue("Timer should have reached 5 seconds", timer.hasReached(5));
    }
    
    @Test
    public void TimeDone() {
        // Test timer finishing after 300 seconds
        assertFalse("Timer should not be finished", timer.isFinished());
        
        timer.update(300.0f);
        assertTrue("Timer should be finished after 300 seconds", timer.isFinished());
        assertEquals("Time left should be 0", 0, timer.getTimeLeftSeconds());
    }
    
    @Test
    public void NegTime() {
        // Test that time left does not go negative
        timer.update(350.0f);
        assertTrue("Timer should be finished", timer.isFinished());
        assertEquals("Time left should not go negative", 0, timer.getTimeLeftSeconds());
    }
    
    @Test
    public void Reset() {
        // Test resetting the timer
        timer.update(50.0f);
        timer.reset();
        
        assertEquals("Time should be reset to 0", 0, timer.getTimeSeconds());
        assertEquals("Time left should be reset to 300", 300, timer.getTimeLeftSeconds());
        assertFalse("Timer should not be finished after reset", timer.isFinished());
    }
    
    @Test
    public void TimerPrecision() {
        // Test timer precision with fractional seconds
        timer.update(0.5f);
        timer.update(0.5f);
        assertEquals("Time should accumulate correctly", 1, timer.getTimeSeconds());
    }
}