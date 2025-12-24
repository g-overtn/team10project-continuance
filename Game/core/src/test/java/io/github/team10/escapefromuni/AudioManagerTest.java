package io.github.team10.escapefromuni;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Tests for the AudioManager class.
 * We are checking all of the functionlaity for the Audio Manager class and ensuring that everything works as expected.
 * Using Mocking to simulate audio playback in a headless environment.
 */
public class AudioManagerTest extends HeadlessTestRunner {
    
    private AudioManager audioManager;
    
    @Before
    public void setUp() {
        // Create the mock Gdx audio and files for testing out audio loading and playback.
        Gdx.audio = Mockito.mock(Audio.class);
        Gdx.files = Mockito.mock(Files.class);
        
        // Create mock file handles
        FileHandle mockFileHandle = Mockito.mock(FileHandle.class);
        Mockito.when(Gdx.files.internal(any(String.class))).thenReturn(mockFileHandle);
        
        // Create mock music and sound objects
        Music mockMusic = Mockito.mock(Music.class);
        Sound mockSound = Mockito.mock(Sound.class);
        
        Mockito.when(Gdx.audio.newMusic(any(FileHandle.class))).thenReturn(mockMusic);
        Mockito.when(Gdx.audio.newSound(any(FileHandle.class))).thenReturn(mockSound);
        
        audioManager = AudioManager.getInstance();
    }
    
    
    @Test
    public void StartingVolume() {
        // Check that the initial volume is within valid range
        // The volume needs to be between 0 and 1. Otherwise, implementation needs to get sorted out.
        float volume = audioManager.getVolume();
        assertTrue("Initial volume should be between 0 and 1", volume >= 0.0f && volume <= 1.0f);
    }
    
    @Test
    public void SettingVolume() {
        // Test setting volume to a valid value
        // Seeing behaviour when we change the volume to something other than the default
        audioManager.setVolume(0.7f);
        assertEquals("Volume should be set to 0.7", 0.7f, audioManager.getVolume(), 0.01f);
    }
    
    @Test
    public void MinVol() {
        // Test setting volume to minimum
        // testing the lower edge to see what happens when we try and move volume to 0.
        audioManager.setVolume(0.0f);
        assertEquals("Volume should be 0", 0.0f, audioManager.getVolume(), 0.01f);
    }
    
    @Test
    public void MaxVol() {
        // Test setting volume to maximum
        // Seeing what happens when we set volume to 1
        audioManager.setVolume(1.0f);
        assertEquals("Volume should be 1", 1.0f, audioManager.getVolume(), 0.01f);
    }
    
    @Test
    public void NegativeVol() {
        // Test setting volume below minimum
        // Lower edge case - we are testing behaviour when we try and move the volume to sub-zero.
        // It should stay at 0. 
        audioManager.setVolume(-0.5f);
        assertTrue("Volume should be clamped to 0", audioManager.getVolume() >= 0.0f);
    }
    
    @Test
    public void TooHighVol() {
        // Test setting volume above maximum
        // Seeing what happens when we attempt to move volume >1.
        audioManager.setVolume(1.5f);
        assertTrue("Volume should be clamped to 1", audioManager.getVolume() <= 1.0f);
    }
    
    @Test
    public void PosSound() {
        // Test playing a positive event sound
        audioManager.playEventSound(EventType.POSITIVE);
        assertTrue(true);
    }
    
    @Test
    public void NegSound() {
        // Test playing a negative event sound
        audioManager.playEventSound(EventType.NEGATIVE);
        assertTrue(true);
    }
    
    @Test
    public void HiddenSound() {
        // Test playing a hidden event sound
        audioManager.playEventSound(EventType.HIDDEN);
        assertTrue(true);
    }
    
    @Test
    public void NoSound() {
        // Test playing a none event sound
        audioManager.playEventSound(EventType.NONE);
        assertTrue(true);
    }
}