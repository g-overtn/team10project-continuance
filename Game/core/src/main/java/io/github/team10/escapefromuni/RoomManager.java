package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;

public class RoomManager {
    public EscapeGame game;

    private Room startingRoom;
    private Room currentRoom;
    private Door[] doors = new Door[4];

    public RoomManager(EscapeGame game)
    {
        this.game = game;
    }


    public void initialiseMap() {
        initialiseDoors();

        // Iniitalise all the rooms
        Room room1 = new Room(new Texture("RoomsTemp1.png"));
        Room room2 = new Room(new Texture("RoomsTemp2.png"));
        Room room3 = new Room(new Texture("RoomsTemp3.png"));

        // Initialise connections
        room1.addAdjacent(room2, DoorDirection.EAST);
        room2.addAdjacent(room1, DoorDirection.WEST);
        room2.addAdjacent(room3, DoorDirection.NORTH);
        room3.addAdjacent(room2, DoorDirection.SOUTH);


        startingRoom = room1;
        currentRoom = room1;
    }

    public void changeRoom(DoorDirection direction) {
        // Will unload current room and load next room
        // Will update which doors are visible. Note that only 4 door objects are used -
        // they can be made visible or invisible.
    }

    public void drawMap()
    {
        drawCurrentRoom();
        drawDoors();
    }

    private void drawCurrentRoom()
    {
        Texture roomTexture = currentRoom.getRoomTexture();
        float worldWidth = game.viewport.getWorldWidth();
		float worldHeight = game.viewport.getWorldHeight();
        game.batch.draw(roomTexture, 0, 0, worldWidth, worldHeight);
    }

    private void drawDoors()
    {
        for (Door door : doors)
        {
            door.draw();
        }
    }

    private void unloadCurrentRoom() {
        // Called from changeRoom()
    }

    private void initialiseDoors() {
        // Will create the four doors at the start of the game
        Door northDoor = new Door(this, DoorDirection.NORTH, 7.5f, 8f);
        Door eastDoor = new Door(this, DoorDirection.EAST, 15f, 4f);
        Door southDoor = new Door(this, DoorDirection.SOUTH, 7.5f, 0f);
        Door westDoor = new Door(this, DoorDirection.WEST, 0f, 4f);

        doors = new Door[]{northDoor, eastDoor, southDoor, westDoor};
    }

    private void updateDoors() {
        // Called from changeRoom()
        // Will update which doors are visible based on the new room
    }

    private void updateEventIndicators() {
        // Called from changeRoom()
        // Will update the event type indicators by the doors
    }

}
