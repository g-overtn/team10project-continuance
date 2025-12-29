package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class PositiveEvent extends Event implements Disposable {

    private final PositiveEventType type;
    private final ScoreManager scoreManager;

    private final Texture texture;
    public Sprite sprite;

    private boolean used = false;

    /**
     * Creates a new positive event.
     */
    public PositiveEvent(PositiveEventType type, Player player, EscapeGame game, ScoreManager scoreManager) {
        super(EventType.POSITIVE, player, game);
        this.scoreManager =  scoreManager;
        this.type = type;
        texture = new Texture(type.getTexturePath());
    }

    @Override
    public void startEvent() {
        if (eventFinished) return;
        super.startEvent();

        sprite = new Sprite(texture);
        sprite.setSize(3f, 2f);
        sprite.setPosition(6.5f, 3.5f);
        AudioManager.getInstance().playEventSound(EventType.POSITIVE);
    }

    @Override
    public void endEvent() {
        if (!eventFinished && used) {
            eventFinished = true;
            texture.dispose();
        }
    }

    @Override
    public void update(float delta) {
        if (!used) {
            float playerDist = getPlayerDist();
            if (playerDist < 1f) {
                used = true;
                type.doThing(player, game, scoreManager);
            }
        }
    }

    /**
     * Calculates the distance between the center of the player and the sprite.
     * @return the distance between the player and the sprite.
     */
    private float getPlayerDist()
    {
        Vector2 playerPos = player.getCenter();
        float spriteX = sprite.getX() + sprite.getWidth() / 2f;
        float spriteY = sprite.getY() + sprite.getHeight() / 2f;
        Vector2 greggsCenter = new Vector2(spriteX, spriteY);
        return greggsCenter.dst(playerPos);
    }

    @Override
    public void draw() {
        if (!used) { sprite.draw(game.batch); }
    }

    @Override
    public void drawUI() {}

    @Override
    public void dispose() {
        if (texture != null) { texture.dispose(); }
    }

    // getters and setters

    /**
     * Used for testing purposes.
     * @return event type.
     */
    public PositiveEventType getType() {
        return type;
    }

    /**
     * Used for testing purposes.
     * @return used value.
     */
    public boolean getUsed() {
        return used;
    }

    /**
     * Used for testing purposes.
     * @return scoreManager object.
     */
    public ScoreManager getScoreManager() {
        return scoreManager;
    }
}
