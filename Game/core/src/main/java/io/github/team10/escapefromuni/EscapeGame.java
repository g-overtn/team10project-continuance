package io.github.team10.escapefromuni;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Class to represent the game. Instantiated by the Launcher.
 * 
 * Similar to the Drop class in https://libgdx.com/wiki/start/simple-game-extended.
 * Loads the initial Screen. Has a SpriteBatch batch, to be used by each screen to render the game.
 */
public class EscapeGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(16, 9);

        // Scale font to viewport
        font.setUseIntegerPositions(false);
		font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
		super.render();
	}

    @Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
