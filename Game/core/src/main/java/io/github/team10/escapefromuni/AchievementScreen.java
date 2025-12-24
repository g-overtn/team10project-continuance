package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.util.ArrayList;

public class AchievementScreen implements Screen {

    private final EscapeGame game;
    private Texture backgroundImage;
    private BitmapFont font;
    private GlyphLayout layout;

    // screen sizes
    float screenHeight;

    ArrayList<Achievement> achievements;

    public AchievementScreen(EscapeGame game) {
        this.game = game;
        this.game.achievementManager.loadAchievements();
        achievements = this.game.achievementManager.getAchievements();

        // alignment; To be Fixed
        float screenWidth = game.uiViewport.getWorldWidth();

        font = new BitmapFont();
        layout = new GlyphLayout();
    }

    @Override
    public void show() {
        // backgrounds
        backgroundImage = new Texture(Gdx.files.internal("Settings_Background.png"));

        font = game.font;
        layout = new GlyphLayout();

    }

    // Draws the achievement menu UI
    public void display() {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();

        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiCamera.combined);
        game.batch.begin();

        // Achievement title
        String title = "        ACHIEVEMENTS\n( PRESS ESC FOR MENU )";
        layout.setText(font, title);
        float titleX = (game.uiViewport.getWorldWidth() - layout.width) / 2f;
        float titleY = game.uiViewport.getWorldHeight() - 100f;
        font.setColor(Color.WHITE);
        font.draw(game.batch, layout, titleX, titleY);

        // displaying achievements
        font.setColor(Color.WHITE);
        display_achievements();

        game.batch.end();
    }

    /**
     * Displays the name, description and completion status of each loaded achievement.
     */
    public void display_achievements() {
        for (int i = 0; i < achievements.size(); i++) {
            Achievement a = achievements.get(i);
            font.setColor(Color.RED); // assume incomplete
            if (a.isComplete()) { font.setColor(Color.GREEN); } // change to green if complete
            layout.setText(font, a.getName() + " -> " + a.getDescription());
            font.draw(game.batch, layout, 50f, (screenHeight / 2f) + 850f - (i * 50f));

        }
        font.setColor(Color.WHITE); // reset back to white once loop is finished
    }

    /**
     * Handles ESC key press.
     * Returns to main menu.
     */
    public void onEscPress() {
        game.setScreen(new MainMenu(game));
        dispose();
    }

    @Override
    public void render(float v) {
        // Check for ESC key press
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            onEscPress();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw everything
        display();
    }

    @Override
    public void resize(int i, int i1) {

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
    }
}
