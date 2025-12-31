package io.github.team10.escapefromuni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
 * Start Game, Tutorial,Exit.
 */
public class MainMenu implements Screen {

    private final EscapeGame game;
    private Texture backgroundImage;
    private Texture buttonTexture;
    private BitmapFont font;
    private GlyphLayout layout;

    // buttons
    private Rectangle startButton;
    private Rectangle tutorialButton;
    private Rectangle settingsButton;
    private Rectangle exitButton;
    private Rectangle leaderboardButton;

    // hover states for buttons
    private boolean startHovered;
    private boolean tutorialHovered;
    private boolean settingsHovered;
    private boolean exitHovered;
    private boolean leaderboardHovered;

    public MainMenu(EscapeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        // backgrounds
        backgroundImage = new Texture(Gdx.files.internal("mainmenu_background.png"));
        buttonTexture = new Texture(Gdx.files.internal("ButtonBG.png"));

        font = game.font;
        layout = new GlyphLayout();

        // button sizes
        float buttonWidth = 400f;
        float buttonHeight = 80f;

        // alignment; To be Fixed
        float screenWidth = game.uiViewport.getWorldWidth();
        float screenHeight = game.uiViewport.getWorldHeight();
        float centerX = screenWidth / 2f;

        // main menu button positions
        startButton = new Rectangle(centerX - buttonWidth / 2f, screenHeight / 2f + 150f, buttonWidth, buttonHeight);
        tutorialButton = new Rectangle(centerX - buttonWidth / 2f, screenHeight / 2f + 50f, buttonWidth, buttonHeight);
        settingsButton = new Rectangle(centerX - buttonWidth / 2f, screenHeight / 2f - 50f, buttonWidth, buttonHeight);
        exitButton = new Rectangle(centerX - buttonWidth / 2f, screenHeight / 2f - 350f, buttonWidth, buttonHeight);
        leaderboardButton = new Rectangle(centerX-buttonWidth/ 2f, screenHeight / 2f - 150, buttonWidth, buttonHeight);
        
        //menu music 
        AudioManager.getInstance().playMenuMusic();

    }

    // Draws the main menu UI
    public void display() {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();

        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiCamera.combined);
        game.batch.begin();

        // draw all main menu buttons
        drawButton(startButton, "Start Game", startHovered);
        drawButton(tutorialButton, "Tutorial", tutorialHovered);
        drawButton(settingsButton, "Settings", settingsHovered);
        drawButton(exitButton, "Exit", exitHovered);
        drawButton(leaderboardButton, "Leaderboard", leaderboardHovered);

        game.batch.end();
    }

    //the buttons
    private void drawButton(Rectangle button, String text, boolean hovered) {

        if (hovered) {
            game.batch.setColor(1f, 1f, 0.5f, 1f);
        } else {
            game.batch.setColor(Color.WHITE);
        }

        //button bg,size etc
        game.batch.draw(buttonTexture, button.x, button.y, button.width, button.height);
        game.batch.setColor(Color.WHITE);

        layout.setText(font, text);
        float textX = button.x + (button.width - layout.width) / 2f;
        float textY = button.y + (button.height + layout.height) / 2f;

        font.setColor(Color.WHITE);
        font.draw(game.batch, layout, textX, textY);
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

    public void onStartGame() {
        // switch to main gameplay
        System.out.println("Starting game...");
        game.setScreen(new GameScreen(game));
        dispose();
    }

    public void onTutorial() {
        // open tutorial page
        System.out.println("Opening tutorial...");
        game.setScreen(new TutorialPage(game));
        dispose();
    }

    public void onSettings() {
        // open settings page
        System.out.println("Opening settings...");
        game.setScreen(new SettingsPage(game, this));
        dispose();
    }

    public void onExit() {
        // quit game
        System.out.println("Exiting game...");
        Gdx.app.exit();
    }

    public void onLeaderBoard() {
        // open the leaderboard screen
        System.out.println("Opening leaderboard...");
        game.setScreen(new LeaderboardPage(game,this));
        dispose();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        startHovered = isButtonHovered(startButton);
        tutorialHovered = isButtonHovered(tutorialButton);
        settingsHovered = isButtonHovered(settingsButton);
        exitHovered = isButtonHovered(exitButton);
        leaderboardHovered = isButtonHovered(leaderboardButton);

        if (isButtonClicked(startButton)) {
            onStartGame();
        } else if (isButtonClicked(tutorialButton)) {
            onTutorial();
        } else if (isButtonClicked(settingsButton)) {
            onSettings();
        } else if (isButtonClicked(exitButton)) {
            onExit();
        } else if (isButtonClicked(leaderboardButton)) {
            onLeaderBoard();
        }

        // draw everything
        display();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
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

    // public void writeScores(int newScore) {

    //     try {
            
    //         BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"));

    //         // contains data from old scoreboard to be used in comparison
    //         ArrayList<String> oldRecords = new ArrayList<String>();
    //         ArrayList<Integer> oldScores = new ArrayList<Integer>();

    //         for (int i=0; i<5; i++) {
    //             //entire record keeping CSV format
    //             String temp = reader.readLine();
    //             oldRecords.add(temp);

    //             //grabs just the score  
    //             try {
    //                 Integer tempInt = Integer.parseInt(temp.split(",")[1]);
    //                 oldScores.add(tempInt); 

    //             } catch (Exception e) {
    //                 System.err.println("Couldn't interpret scores file!");
    //                 e.printStackTrace();
    //             }
    //         }

    //         reader.close();

    //         BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt"));

    //         Boolean placed = false;
    //         for (int i=0; i<5; i++) {
    //             if (oldScores.get(i) <= newScore && placed == false) {
    //                 System.out.println(("Score was greater than score at position"+(i+1)));
    //                 writer.write(("!!!,"+newScore+"\n"));
    //                 writer.write((oldRecords.get(i)+"\n"));
    //                 placed = true;
    //             } else {
    //                 writer.write((oldRecords.get(i)+"\n"));
    //             }
    //         }

    //         System.out.println(oldScores);
    //         System.out.println(oldRecords);

    //         writer.close();

    //     } catch (IOException e) {
    //         System.err.println("Failed to overwrite scores!");
    //         e.printStackTrace();
    //     }
    // }

}
