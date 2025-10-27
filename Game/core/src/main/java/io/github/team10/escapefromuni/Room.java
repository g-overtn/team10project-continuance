package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;

public class Room {
    public RoomManager roomManager;
    public Event event;
    public Room[] adjacentRooms = new Room[4];

    private Texture roomTexture;

    public Room(Texture roomTexture)
    {
        this.roomTexture = roomTexture;
    }

    public Texture getRoomTexture()
    {
        return roomTexture;
    }

    public void addAdjacent(Room adjacentRoom, DoorDirection direction)
    {
        if (direction == DoorDirection.NORTH) adjacentRooms[0] = adjacentRoom;
        if (direction == DoorDirection.EAST) adjacentRooms[1] = adjacentRoom;
        if (direction == DoorDirection.SOUTH) adjacentRooms[2] = adjacentRoom;
        if (direction == DoorDirection.WEST) adjacentRooms[3] = adjacentRoom;
    }

    /* 
    public EventType getEventType() {
        // Return the event type of event
    }

    
    public void start() {
        // Actions to perform when room is started i.e. play event
    }*/
}
