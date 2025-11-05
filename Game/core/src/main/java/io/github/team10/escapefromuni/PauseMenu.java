package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Pause menu displayed during gameplay.
 * 
 * Triggered by ESC key. Shows paused timer and menu options:
 * Resume, Exit.
 */
public class PauseMenu implements Screen {
    
    private final EscapeGame game;
    private final GameScreen gameScreen;
    private BitmapFont font;
    private GlyphLayout layout;
    private int pausedTime;
    private Texture backgroundImage;
    
    // Button rectangles
    private Rectangle resumeButton;
    private Rectangle exitButton;
    
    // Button states
    private boolean resumeHovered;
    private boolean exitHovered;
    
    /**
     * Creates a new PauseMenu instance.
     * @param game Reference to the main {@link EscapeGame} instance.
     * @param gameScreen The GameScreen to resume to.
     * @param pausedTime The time (in seconds) when the game was paused.
     */
    public PauseMenu(EscapeGame game, GameScreen gameScreen, int pausedTime) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.pausedTime = pausedTime;
    }
    
    @Override
    public void show() {
        // Load background image
        backgroundImage = new Texture(Gdx.files.internal("pausemenu_background.png"));
        
        // Use the game's existing font
        font = game.font;
        layout = new GlyphLayout();
        
        // Initialize button rectangles (centered on screen, positioned much lower)
        float buttonWidth = 600f;
        float buttonHeight = 100f;
        float screenWidth = game.uiViewport.getWorldWidth();
        float screenHeight = game.uiViewport.getWorldHeight();
        float centerX = screenWidth / 2f;
        
        // Positioned much lower - closer to bottom half of screen
        resumeButton = new Rectangle(centerX - buttonWidth / 2f, 450f, buttonWidth, buttonHeight);
        exitButton = new Rectangle(centerX - buttonWidth / 2f, 300f, buttonWidth, buttonHeight);
    }
    
    /**
     * Displays the pause menu UI.
     */
    public void display() {
        // Draw background image
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();
        
        // Draw UI elements
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiCamera.combined);
        game.batch.begin();
        
        // Draw custom text - positioned lower
        String title = "You pulled out 'Exceptional Circumstances' card on dean";
        layout.setText(font, title);
        float titleX = (game.uiViewport.getWorldWidth() - layout.width) / 2f;
        float titleY = 750f; // Much lower
        font.setColor(Color.WHITE);
        font.draw(game.batch, layout, titleX, titleY);
        
        // Draw timer - also lower
        showPausedTimer(pausedTime);
        int minutes = pausedTime / 60;
        int seconds = pausedTime % 60;
        String timeText = String.format("Time: %02d:%02d", minutes, seconds);
        layout.setText(font, timeText);
        float timeX = (game.uiViewport.getWorldWidth() - layout.width) / 2f;
        float timeY = 650f; // Lower
        font.draw(game.batch, layout, timeX, timeY);
        
        // Draw buttons
        drawButton(resumeButton, "Resume", resumeHovered);
        drawButton(exitButton, "Exit to Menu", exitHovered);
        
        game.batch.end();
    }
    
    /**
     * Draws a button with text.
     */
    private void drawButton(Rectangle button, String text, boolean hovered) {
        // Draw button text
        layout.setText(font, text);
        float textX = button.x + (button.width - layout.width) / 2f;
        float textY = button.y + (button.height + layout.height) / 2f;
        
        // Change color based on hover state
        font.setColor(hovered ? Color.YELLOW : Color.WHITE);
        font.draw(game.batch, layout, textX, textY);
    }
    
    /**
     * Checks if a button is clicked.
     */
    private boolean isButtonClicked(Rectangle button) {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.uiCamera.unproject(touchPos);
            return button.contains(touchPos.x, touchPos.y);
        }
        return false;
    }
    
    /**
     * Checks if mouse is hovering over a button.
     */
    private boolean isButtonHovered(Rectangle button) {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.uiCamera.unproject(mousePos);
        return button.contains(mousePos.x, mousePos.y);
    }
    
    /**
     * Updates the paused timer display.
     * @param time The time in seconds when the game was paused.
     */
    public void showPausedTimer(int time) {
        this.pausedTime = time;
    }
    
    /**
     * Handles Resume button click.
     * Returns to gameplay.
     */
    public void onResume() {
        System.out.println("Resuming game...");
        game.setScreen(gameScreen);
        dispose();
    }
    
    /**
     * Handles Exit button click.
     * Returns to main menu and disposes GameScreen.
     */
    public void onExit() {
        System.out.println("Returning to main menu...");
        gameScreen.dispose();
        game.setScreen(new MainMenu(game));
        dispose();
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.8f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Update hover states
        resumeHovered = isButtonHovered(resumeButton);
        exitHovered = isButtonHovered(exitButton);
        
        // Check for button clicks
        if (isButtonClicked(resumeButton)) {
            onResume();
        } else if (isButtonClicked(exitButton)) {
            onExit();
        }
        
        // Display the menu
        display();
    }
    
    @Override
    public void resize(int width, int height) {
        game.uiViewport.update(width, height, true);
    }
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
    
    @Override
    public void hide() {}
    
    @Override
    public void dispose() {
        backgroundImage.dispose();
        // Don't dispose font - it's owned by EscapeGame
    }
}