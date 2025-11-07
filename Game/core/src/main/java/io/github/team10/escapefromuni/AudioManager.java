package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Singleton AudioManager for handling all game audio.
 * 
 * Manages background music for menus and gameplay, and sound effects
 * for events and button clicks.
 */
public class AudioManager {
    private static AudioManager instance;
    
    // Music tracks (looping background music)
    private Music menuMusic;
    private Music gameMusic;
    
    // Sound effects (short clips)
    private Sound clickSound;
    private Sound negativeEventSound;
    private Sound positiveEventSound;
    private Sound hiddenEventSound;
    
    // Current state tracking
    private Music currentMusic;
    
    /**
     * Private constructor for singleton pattern.
     */
    private AudioManager() {
        loadAudio();
    }
    
    /**
     * Gets the singleton instance of AudioManager.
     * @return The AudioManager instance
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
    
    /**
     * Loads all audio assets.
     */
    private void loadAudio() {
        try {
            // Load music tracks (use Music for long, looping audio)
            menuMusic = Gdx.audio.newMusic(Gdx.files.internal("MenuMusic.mp3"));
            gameMusic = Gdx.audio.newMusic(Gdx.files.internal("GameMusic.mp3"));
            
            // Set looping for background music
            menuMusic.setLooping(true);
            gameMusic.setLooping(true);
            
            // Load sound effects (use Sound for short audio clips)
            clickSound = Gdx.audio.newSound(Gdx.files.internal("Click.mp3"));
            negativeEventSound = Gdx.audio.newSound(Gdx.files.internal("NegMus.mp3"));
            positiveEventSound = Gdx.audio.newSound(Gdx.files.internal("PosMus.mp3"));
            hiddenEventSound = Gdx.audio.newSound(Gdx.files.internal("HidMus.mp3"));
            
        } catch (Exception e) {
            Gdx.app.error("AudioManager", "Error loading audio files: " + e.getMessage());
        }
    }
    
    /**
     * Plays menu music in a loop.
     */
    public void playMenuMusic() {
        if (currentMusic != menuMusic) {
            stopCurrentMusic();
            currentMusic = menuMusic;
            menuMusic.play();
        }
    }
    
    /**
     * Plays game music in a loop.
     */
    public void playGameMusic() {
        if (currentMusic != gameMusic) {
            stopCurrentMusic();
            currentMusic = gameMusic;
            gameMusic.play();
        }
    }
    
    /**
     * Plays event sound effect based on event type.
     * The sound plays once and game music continues in the background.
     * @param eventType The type of event (NEGATIVE, POSITIVE, or HIDDEN)
     */
    public void playEventSound(EventType eventType) {
        Sound eventSound = null;
        
        switch (eventType) {
            case NEGATIVE:
                eventSound = negativeEventSound;
                break;
            case POSITIVE:
                eventSound = positiveEventSound;
                break;
            case HIDDEN:
                eventSound = hiddenEventSound;
                break;
        }
        
        if (eventSound != null) {
            eventSound.play(1.0f); // Play at full volume
        }
    }
    
    /**
     * Plays the button click sound effect.
     */
    public void playClickSound() {
        if (clickSound != null) {
            clickSound.play(1.0f); // Play at full volume
        }
    }
    
    /**
     * Stops the currently playing music.
     */
    private void stopCurrentMusic() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
        }
    }
    
    /**
     * Pauses the currently playing music.
     */
    public void pauseMusic() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.pause();
        }
    }
    
    /**
     * Resumes the currently paused music.
     */
    public void resumeMusic() {
        if (currentMusic != null && !currentMusic.isPlaying()) {
            currentMusic.play();
        }
    }
    
    /**
     * Sets the volume for all music tracks.
     * @param volume Volume level (0.0f to 1.0f)
     */
    public void setMusicVolume(float volume) {
        if (menuMusic != null) menuMusic.setVolume(volume);
        if (gameMusic != null) gameMusic.setVolume(volume);
    }
    
    /**
     * Sets the volume for all sound effects.
     * @param volume Volume level (0.0f to 1.0f)
     */
    public void setSoundVolume(float volume) {
        }
    
    /**
     * Disposes all audio resources.
     * Call this when the game is closing.
     */
    public void dispose() {
        // Dispose music
        if (menuMusic != null) menuMusic.dispose();
        if (gameMusic != null) gameMusic.dispose();
        
        // Dispose sound effects
        if (clickSound != null) clickSound.dispose();
        if (negativeEventSound != null) negativeEventSound.dispose();
        if (positiveEventSound != null) positiveEventSound.dispose();
        if (hiddenEventSound != null) hiddenEventSound.dispose();
    }
}