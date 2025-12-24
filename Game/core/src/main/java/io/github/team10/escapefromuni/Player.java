package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents the player character.
 *
 * Handles player rendering, movement and collision detection.
 */
public class Player {
    public Texture playerTexture;
    public Sprite playerSprite;
    public float speed;
    public EscapeGame game;
    private boolean movementEnabled;

    private final float EDGE_LIMIT = 1f;

    // EVENT TOTALS
    // types of events stored as vector2, (interacted_count, total_count)
    // spawning an event of that type: .y++
    // interacting with an event of that type: .x++
    public Vector2 positive_events = new Vector2(0, 0);
    public Vector2 negative_events = new Vector2(0, 0);
    public Vector2 hidden_events =  new Vector2(0, 0);
    public Vector2 total_events = new Vector2(0, 0);

    /**
     * Creates a new player instance.
     * @param speed The player's movement speed (world units per second).
     * @param playerWidth The width of the player in world units.
     * @param playerHeight The height of the player in world units.
     * @param game Reference to the main {@link EscapeGame} instance.
     */
    public Player(float speed, float playerWidth, float playerHeight, EscapeGame game)
    {
        this.speed = speed;
        this.game = game;
        playerTexture = new Texture("MalePlayer.png");
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(playerWidth, playerHeight);

        float centerX = game.viewport.getWorldWidth() / 2f;
        float centerY = game.viewport.getWorldHeight() / 2f;
        playerSprite.setCenter(centerX, centerY);
        this.movementEnabled = true;
    }

    /**
     * Called every frame to perform player logic.
     * @param delta The time in seconds since the last frame.
     */
    public void update(float delta)
    {
        if (movementEnabled)
        {
            move(delta);
        }
    }

    /**
     * Handles player movement and constrains movement to within screen edge boundaries.
     *
     * Uses arrow key input. Center of player remains at least {@link #EDGE_LIMIT} units from the world edges.
     * @param delta The time in seconds since the last frame.
     */
    private void move(float delta) {
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();
        float halfWidth = playerSprite.getWidth() / 2f;
        float halfHeight = playerSprite.getHeight() / 2f;
        float playerCenterX = playerSprite.getX() + halfWidth;
        float playerCenterY = playerSprite.getY() + halfHeight;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (playerCenterX < worldWidth - EDGE_LIMIT) {
                playerSprite.translateX(speed * delta);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (playerCenterX > EDGE_LIMIT) {
                playerSprite.translateX(-speed * delta);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (playerCenterY < worldHeight - EDGE_LIMIT) {
                playerSprite.translateY(speed * delta);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (playerCenterY > EDGE_LIMIT) {
                playerSprite.translateY(-speed * delta);
            }
        }
    }

    /**
     * Allows enabling or disabling movement for the player.
     * @param enabled boolean representing whether the player will be able to move.
     */
    public void enableMovement(boolean enabled)
    {
        this.movementEnabled = enabled;
    }

    /**
     * Draws the player sprite.
     */
    public void draw() {
        playerSprite.draw(game.batch);
    }

    /**
     * Checks whether the player has collided (overlaps) with another sprite.
     * @param objectSprite The other object's sprite.
     * @return boolean representing whether collision has occured.
     */
    public boolean checkCollision(Sprite objectSprite)
    {
        return playerSprite.getBoundingRectangle().overlaps(objectSprite.getBoundingRectangle());
    }

    /**
     * Dispose of player texture to free GPU memory.
     *
     * Should be called when the GameScreen is disposed.
     */
    public void dispose()
    {
        playerTexture.dispose();
    }

    /**
     * Set the position of the player sprite, updating from the bottom left corner.
     * @param x The x-coord of new position.
     * @param y The y-coord of new position.
     */
    public void setPosition(float x, float y)
    {
        playerSprite.setPosition(x, y);
    }

    /**
     * Sets the center position of the player sprite.
     * @param x The x-coord of the new position.
     * @param y The y-coord of the new position.
     */
    public void setCenter(float x, float y)
    {
        playerSprite.setCenter(x, y);
    }

    /**
     * Return the center position of the player sprite, as a Vector2.
     */
    public Vector2 getCenter()
    {
        float centerX = playerSprite.getX() + playerSprite.getWidth() / 2f;
        float centerY = playerSprite.getY() + playerSprite.getHeight() / 2f;
        return new Vector2(centerX, centerY);
    }

    /**
     * Increase the player's speed by a fixed amount.
     * @param speedIncrease the amount by which the speed will increase.
     */
    public void increaseSpeed(float speedIncrease)
    {
        speed += speedIncrease;
    }
}
