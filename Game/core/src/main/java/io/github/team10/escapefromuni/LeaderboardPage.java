package io.github.team10.escapefromuni;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.lang.reflect.Field;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class LeaderboardPage implements Screen {
    
    private final EscapeGame game;
    private final Screen previousScreen;
    private Texture backgroundImage;
    private Texture buttonTexture;
    private BitmapFont font;
    private GlyphLayout layout;
    
    // UI elements
    private Rectangle backButton;
    private Rectangle score1Label;
    private Rectangle score2Label;
    private Rectangle score3Label;
    private Rectangle score4Label;
    private Rectangle score5Label; 
    
    // States
    private boolean backHovered;
    
    public LeaderboardPage(EscapeGame game, Screen previousScreen) {
        this.game = game;
        this.previousScreen = previousScreen;
    }

    @Override
    public void show() {
        // Load textures
        backgroundImage = new Texture(Gdx.files.internal("Settings_Background.png"));
        buttonTexture = new Texture(Gdx.files.internal("ButtonBG.png"));
        
        font = game.font;
        layout = new GlyphLayout();
        
        // Initialize UI
        float screenWidth = game.uiViewport.getWorldWidth();
        float screenHeight = game.uiViewport.getWorldHeight();
        float centerX = screenWidth / 2f;
        
        // Back button
        float buttonWidth = 400f;
        float buttonHeight = 80f;
        backButton = new Rectangle(centerX - buttonWidth / 2f, 200f, buttonWidth, buttonHeight);

        // Score tiles
        score1Label = new Rectangle(centerX - buttonWidth / 2f, 700f, buttonWidth, buttonHeight);
        score2Label = new Rectangle(centerX - buttonWidth / 2f, 625f, buttonWidth, buttonHeight);
        score3Label = new Rectangle(centerX - buttonWidth / 2f, 550f, buttonWidth, buttonHeight);
        score4Label = new Rectangle(centerX - buttonWidth / 2f, 475f, buttonWidth, buttonHeight);
        score5Label = new Rectangle(centerX - buttonWidth / 2f, 400f, buttonWidth, buttonHeight);

        AudioManager.getInstance().playMenuMusic();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            onBack();
        }
        
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        backHovered = isButtonHovered(backButton);
        
        if (isButtonClicked(backButton)) {
            onBack();
        }
        
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
        buttonTexture.dispose();
    }

    private boolean isButtonClicked(Rectangle button) {
        // click detector
        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            game.uiViewport.unproject(touchPos);

            if (button.contains(touchPos.x, touchPos.y)) {
                // play click audio
                AudioManager.getInstance().playClickSound();
                return true;
            }
        }
        return false;
    }

    private boolean isButtonHovered(Rectangle button) {
        // detect mouse hover in UI coordinates
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        game.uiViewport.unproject(mousePos);
        return button.contains(mousePos.x, mousePos.y);
    }

    public void onBack() {
        game.setScreen(previousScreen);
        dispose();
    }

    public void display() {
         
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();
        
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiCamera.combined);
        game.batch.begin();
        
        // Screen title
        String title = "LEADERBOARD";
        layout.setText(font, title);
        float titleX = (game.uiViewport.getWorldWidth() - layout.width) / 2f;
        float titleY = game.uiViewport.getWorldHeight() - 150f;
        font.setColor(Color.WHITE);
        font.draw(game.batch, layout, titleX, titleY);
        
        // Draw back button
        drawButton(backButton, "Go Back", backHovered);
        
        // Read scores from save file and set text on score labels
        try {
            BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"));
            
            for (int i=1; i<=5; i++) {

                // formats the record
                String temp = "Failed to read scores.";

                try {
                    temp = reader.readLine().replace(",", ":   ");
                } catch (Exception e) {
                    System.err.println("Couldn't interpret the score files!");
                    e.printStackTrace();
                }

                // gets the scorelabel corresponding to i
                Field f = this.getClass().getDeclaredField(("score"+i+"Label"));
                f.setAccessible(true);

                drawTile((Rectangle) f.get(this), temp);
            }

            
            reader.close();

        } catch (FileNotFoundException errorNoFile) {
            System.err.println("Failed to locate scores!");
            errorNoFile.printStackTrace();

        } catch (IOException errorIO) {
            System.err.println("Failed to read scores!");
            errorIO.printStackTrace();

        } catch (NoSuchFieldException errorNoField) {
            System.err.println("Failed to locate the score label fields in LeaderboardPage.");
            errorNoField.printStackTrace();
        
        } catch (IllegalAccessException errorNoAccess) {
            System.err.println("Couldn't access the score labels!");
            errorNoAccess.printStackTrace();
        }
        
        game.batch.end();        
    }

    private void drawButton(Rectangle button, String text, boolean hovered) {
        // Draw button background texture
        if (hovered) {
            game.batch.setColor(1f, 1f, 0.5f, 1f);
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

    private void drawTile(Rectangle tile, String text) {
        
        // Draw button text
        game.batch.setColor(Color.WHITE);
        layout.setText(font, text);
        float textX = tile.x + (tile.width - layout.width) / 2f;
        float textY = tile.y + (tile.height + layout.height) / 2f;
        
        font.setColor(Color.WHITE);
        font.draw(game.batch, layout, textX, textY);
    }
}
