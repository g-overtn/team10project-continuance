package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Main menu screen displayed on game launch with menu options:
 * Start Game, Tutorial, Exit.
 */
public class MainMenu implements Screen {

    private final EscapeGame game;
    private Texture backgroundImage;
    private Texture buttonTexture;
    private BitmapFont font;
    private GlyphLayout layout;

    // Button rectangles
    private Rectangle startButton;
    private Rectangle tutorialButton;
    private Rectangle exitButton;

    // Button states
    private boolean startHovered;
    private boolean tutorialHovered;
    private boolean exitHovered;

    /**
     * Creates a new MainMenu instance.
     * 
     * @param game Reference to the main {@link EscapeGame} instance.
     */
    public MainMenu(EscapeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Load background image
        backgroundImage = new Texture(Gdx.files.internal("mainmenu_background.png"));

        // Load button background texture
        buttonTexture = new Texture(Gdx.files.internal("ButtonBG.png"));

        // Use the game's existing font
        font = game.font;
        layout = new GlyphLayout();

        // Initialize button rectangles (centered on screen)
        float buttonWidth = 400f;
        float buttonHeight = 80f;
        float screenWidth = game.uiViewport.getWorldWidth();
        float screenHeight = game.uiViewport.getWorldHeight();
        float centerX = screenWidth / 2f;

        startButton = new Rectangle(centerX - buttonWidth / 2f, screenHeight / 2f + 100f, buttonWidth, buttonHeight);
        tutorialButton = new Rectangle(centerX - buttonWidth / 2f, screenHeight / 2f, buttonWidth, buttonHeight);
        exitButton = new Rectangle(centerX - buttonWidth / 2f, screenHeight / 2f - 100f, buttonWidth, buttonHeight);
    }

    /**
     * Displays the main menu UI with background and buttons.
     */
    public void display() {
        // Draw background
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();

        // Draw UI elements
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiCamera.combined);
        game.batch.begin();

        // Draw Start Game button
        drawButton(startButton, "Start Game", startHovered);

        // Draw Tutorial button
        drawButton(tutorialButton, "Tutorial", tutorialHovered);

        // Draw Exit button
        drawButton(exitButton, "Exit", exitHovered);

        game.batch.end();
    }

    /**
     * Draws a button with background texture and text.
     */
    private void drawButton(Rectangle button, String text, boolean hovered) {
        // Draw button background texture
        if (hovered) {
            game.batch.setColor(1f, 1f, 0.5f, 1f); // Yellowish tint when hovered
        } else {
            game.batch.setColor(Color.WHITE);
        }
        game.batch.draw(buttonTexture, button.x, button.y, button.width, button.height);
        game.batch.setColor(Color.WHITE);

        // Draw button text
        layout.setText(font, text);
        float textX = button.x + (button.width - layout.width) / 2f;
        float textY = button.y + (button.height + layout.height) / 2f;

        font.setColor(Color.WHITE);
        font.draw(game.batch, layout, textX, textY);
    }

    /**
     * Checks if a button is clicked.
     */
    private boolean isButtonClicked(Rectangle button) {
        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            game.uiViewport.unproject(touchPos);
            return button.contains(touchPos.x, touchPos.y);
        }
        return false;
    }

    /**
     * Checks if mouse is hovering over a button.
     */
    private boolean isButtonHovered(Rectangle button) {
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        game.uiViewport.unproject(mousePos);
        return button.contains(mousePos.x, mousePos.y);
    }

    /**
     * Handles Start Game button click.
     * Transitions to GameScreen to begin gameplay.
     */
    public void onStartGame() {
        System.out.println("Starting game...");
        game.setScreen(new GameScreen(game));
        dispose();
    }

    /**
     * Handles Tutorial button click.
     * Navigates to TutorialPage.
     */
    public void onTutorial() {
        System.out.println("Opening tutorial...");
        game.setScreen(new TutorialPage(game));
        dispose();
    }

    /**
     * Handles Exit button click.
     * Closes the game application.
     */
    public void onExit() {
        System.out.println("Exiting game...");
        Gdx.app.exit();
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update hover states
        startHovered = isButtonHovered(startButton);
        tutorialHovered = isButtonHovered(tutorialButton);
        exitHovered = isButtonHovered(exitButton);

        // Check for button clicks
        if (isButtonClicked(startButton)) {
            onStartGame();
        } else if (isButtonClicked(tutorialButton)) {
            onTutorial();
        } else if (isButtonClicked(exitButton)) {
            onExit();
        }

        // Display the menu
        display();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
        game.uiViewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
        buttonTexture.dispose();
    }
}