package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;


/**
 * This class will deal with the 'main' game logic.
 * * For example, creating the player, calling initialiseMap() on the RoomManager.
 * Will handle rendering of game textures using the SpriteBatch stored in EscapeGame.
 */
public class GameScreen extends ScreenAdapter {
    final EscapeGame game;
    Player player;
    RoomManager roomManager;
    ScoreManager scoreManager;
    Timer timer;

    private final BitmapFont font;

    public boolean isPaused;

    public GameScreen(final EscapeGame game)
    {
        this.game = game;
        timer = new Timer();
        scoreManager = new ScoreManager();

        player = new Player(3f, 1f, 1f, game);
        roomManager = new RoomManager(game, player, scoreManager, timer);
        roomManager.initialiseMap();

        font = game.font;
        isPaused = false;
    }

    @Override
    public void render(float delta) {
    // Check for ESC key to pause
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
        isPaused = true;
        int currentTime = timer.getTimeSeconds();
        game.setScreen(new PauseMenu(game, this, currentTime));
        return;
    }

    // NEW
    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
        game.achievementManager.initAchievements();
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
        game.achievementManager.loadAchievements();
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
        game.achievementManager.saveAchievements();
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
        game.achievementManager.printAchievements();
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
        System.out.println(player.positive_events + " : " + player.negative_events + " : " + player.hidden_events + " : " + player.total_events);
    }

    if (!isPaused) {
        update(delta);
        CheckLose();
    }

    draw();
    }

    public void CheckLose()
    {
        if (timer.hasReached(300)) { // 300 seconds = 5 minutes
            game.achievementManager.check_ZERO_TIMER();
            game.setScreen(new GameOverScreen(game, false, timer, scoreManager));
        }
    }

    //timer resuming form pause menu
    public void resumeGame() {
        isPaused = false;
    }

    /**
     * Performs game logic each frame.
     * * Always called before drawing textures.
     * @param delta float representing the time since the last frame.
     */
    private void update(float delta)
    {
        player.update(delta);
        roomManager.update(delta);
        timer.update(delta);
    }

    /**
     * Draw textures to the screen each frame using the {@link EscapeGame}'s SpriteBatch.
     */
    private void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        // World Rendering
        roomManager.drawMap();
        player.draw();
        game.batch.end();

        // UI Rendering
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiCamera.combined);
        game.batch.begin();
        roomManager.drawEventUI();

        font.setColor(Color.BLACK);
        font.draw(game.batch, "Time: " + timer.getTimeLeftSeconds() + "s", 75f, 1000f);

        game.batch.end();
    }

    @Override
    public void dispose()
    {
        roomManager.dispose();
        player.dispose();
    }

    @Override public void show() {
        AudioManager.getInstance().playGameMusic();
        isPaused = false;
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
