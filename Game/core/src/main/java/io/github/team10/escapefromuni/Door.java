package io.github.team10.escapefromuni;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents a door used to connect rooms.
 * 
 * Each {@code Door} is has a direction and is managed by the {@link RoomManager}.
 * The door can be active or inactive indicating whether it is visible and can be used.  
 */
public class Door {
    public RoomManager roomManager;
    public DoorDirection direction;
    public boolean isActive;
    public Texture doorTexture;
    public Sprite doorSprite;

    /**
     * Creates a new Door instance.
     * 
     * The door is always active initially (but this may be changed by the roomManager).
     * @param roomManager Manages the door.
     * @param direction Direction of the door in relation to the center of the room.
     * @param x The x-coord of the bottom left corner of the door.
     * @param y The y-coord of the bottom left corner of the door.
     */
    public Door(RoomManager roomManager, DoorDirection direction, float x, float y)
    {
        this.roomManager = roomManager;
        this.direction = direction;
        doorTexture = new Texture("DoorNew.png");
        doorSprite = new Sprite(doorTexture);
        doorSprite.setSize(1f, 1f);
        doorSprite.setPosition(x, y);
        isActive = true;
    }

    public void draw()
    /**Draws the door if it is active.
     */
    {
        if (isActive)
        {
            doorSprite.draw(roomManager.game.batch);    
        }
    }

    public void setActive(boolean isActive)
    /**Sets the active state of the door.
     * @param isActive true if the door should be active (visible and usable), false otherwise.
     */

    {
        this.isActive = isActive;
    }

    public boolean getActive()
    /**Returns whether the door is active.
     * @return true if the door is active, false otherwise.
     */
    {
        return isActive;
    }

    public void dispose()
    /**Disposes of the door texture to free GPU memory.
     * Should be called when the RoomManager is disposed.
     */
    {
        doorTexture.dispose();
    }
}
