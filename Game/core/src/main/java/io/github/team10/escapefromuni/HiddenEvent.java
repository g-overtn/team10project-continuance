package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class HiddenEvent extends Event implements Disposable {

    private final HiddenEventType type;
    public boolean hidden = true;
    private final Texture hidden_texture;
    private final Texture texture;

    public Sprite sprite;

    private final Texture speech_panel_texture;
    private final Sprite speech_panel_sprite;

    /**
     * Creates a new hidden event.
     */
    public HiddenEvent(HiddenEventType type, Player player, EscapeGame game) {
        super(EventType.HIDDEN, player, game);
        this.type = type;
        texture = new Texture(type.getTexturePath());
        hidden_texture = new Texture(type.getHiddenTexturePath());
        speech_panel_texture = new Texture("UIWideBottomPanel.png");
        speech_panel_sprite = new Sprite(speech_panel_texture);
        speech_panel_sprite.setSize(1200f, 240f);
    }

    @Override
    public void startEvent() {
        if (eventFinished) return;
        super.startEvent();

        sprite = new Sprite(hidden_texture);
        sprite.setSize(2f, 2f);
        sprite.setPosition(8f, 3f);
        AudioManager.getInstance().playEventSound(EventType.HIDDEN);
    }

    @Override
    public void endEvent() {
        if (!eventFinished && !hidden){
            eventFinished = true;

            // Dispose called here manually in logic, but we also need a public dispose method for tests/cleanup
            texture.dispose();
            hidden_texture.dispose();
        }
    }

    @Override
    public void update(float delta) {
        // Check every frame if player is close enough to reveal.
        if (hidden) {
            float playerDist = getPlayerDist();
            if (playerDist < 3f) {
                hidden = false;
                sprite = new Sprite(texture);
                sprite.setSize(2f, 2f);
                sprite.setPosition(8f, 3f);
                type.doThing(player, game);
            }
        }
    }

    /**
     * Calculates the distance between the center of the player and the sprite.
     * @return The distance between the player and the sprite.
     */
    private float getPlayerDist()
    {
        Vector2 playerPos = player.getCenter();
        float spriteX = sprite.getX() + sprite.getWidth() / 2f;
        float spriteY = sprite.getY() + sprite.getHeight() / 2f;
        Vector2 longboiCenter = new Vector2(spriteX, spriteY);
        return longboiCenter.dst(playerPos);
    }

    @Override
    public void draw() {
        if (eventFinished) return;
        sprite.draw(game.batch);
    }

    @Override
    public void drawUI() {
        if (eventFinished) return;

        if (!hidden)
        {
            float uiWidth = game.uiViewport.getWorldWidth();

            float panelY = 150f;
            float panelX = uiWidth / 2f;
            speech_panel_sprite.setCenter(panelX, panelY);
            speech_panel_sprite.draw(game.batch);

            String message = type.getMessage();

            GlyphLayout layout = new GlyphLayout(game.font, message);
            float textWidth = layout.width;
            float textHeight = layout.height;
            float textX = (uiWidth - textWidth) / 2f;
            float textY = panelY + textHeight / 2f;

            game.font.setColor(Color.BLACK);
            game.font.draw(game.batch, layout, textX, textY);
        }
    }

    @Override
    public void dispose() {
        if (texture != null) texture.dispose();
        if (hidden_texture != null) hidden_texture.dispose();
        if (speech_panel_texture != null) speech_panel_texture.dispose();
    }

    // getters and setters

    /**
     * Used for testing purposes.
     * @return event type.
     */
    public HiddenEventType getType() {
        return type;
    }
}
