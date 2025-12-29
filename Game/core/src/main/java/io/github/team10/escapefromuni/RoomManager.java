package io.github.team10.escapefromuni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Manages all {@link Room} and {@link Door} instances in the game.
 * * Responsible for initialising the pre-defined game map, handling transitions between rooms, drawing room and door
 * textures, and disposing of those textures when no longer needed.
 */
public class RoomManager {
    public EscapeGame game;

    public final Player player;
    public final ScoreManager scoreManager;
    public final Timer timer;
    public Room currentRoom;
    public Door[] doors = new Door[4];
    public final ObjectMap<String, Texture> roomTextures = new ObjectMap<>();
    public Texture[] indicatorTextures = new Texture[4];
    public Texture positiveIndicator;
    public Texture negativeIndicator;


    /**
     * Initialises a RoomManager.
     */
    public RoomManager(EscapeGame game, Player player, ScoreManager scoreManager, Timer timer)
    {
        this.game = game;
        this.player = player;
        this.scoreManager = scoreManager;
        this.timer = timer;
    }

    /**
     * Initialise the game map, which will be the same every time.
     * * Initialise 4 doors, create rooms and create room connections. Room textures are stored in a list for easy
     * access and disposal. Also loads the first room.
     */
    public void initialiseMap() {
        initialiseDoors();

        // Store room textures in list for easy access and disposal.
        roomTextures.put("room1", new Texture("Room1.png"));
        roomTextures.put("room2", new Texture("Room3.png"));
        roomTextures.put("room3", new Texture("Room4.png"));
        roomTextures.put("room4", new Texture("Room7.png"));
        roomTextures.put("room5", new Texture("Room6.png"));
        roomTextures.put("room6", new Texture("Room8.png"));
        roomTextures.put("room7", new Texture("Room5.png"));
        roomTextures.put("room8", new Texture("Room9.png"));
        roomTextures.put("room9", new Texture("Room10.png"));
        roomTextures.put("t", new Texture("RoomsTemp1.png"));

        // Initialise all the rooms
        Room room1 = new Room(roomTextures.get("room1")); // Start
        Room room2 = new Room(roomTextures.get("room2"));
        Room room3 = new Room(roomTextures.get("room3")); // Greggs
        Room room4 = new Room(roomTextures.get("room4"));
        Room room5 = new Room(roomTextures.get("room5")); // THE3
        Room room6 = new Room(roomTextures.get("room6"));
        Room room7 = new Room(roomTextures.get("room7")); // Longboi
        Room room8 = new Room(roomTextures.get("room8"));
        Room room9 = new Room(roomTextures.get("room9")); // Contains Exit

        // NEW initialising rooms
        // yes there are rooms missing (24, 26, 30, 31) but i cba to go through and change all the room numbers
        // this works fine
        Room room10 = new Room(roomTextures.get("room6")); // SYS2
        Room room11 = new Room(roomTextures.get("room8"));
        Room room12 = new Room(roomTextures.get("room7")); // Bob
        Room room13 = new Room(roomTextures.get("room3"));
        Room room14 = new Room(roomTextures.get("room9"));
        Room room15 = new Room(roomTextures.get("room7")); // Monster
        Room room16 = new Room(roomTextures.get("room5"));
        Room room17 = new Room(roomTextures.get("room8")); // CupNoodles
        Room room18 = new Room(roomTextures.get("room9")); // Inverse
        Room room19 = new Room(roomTextures.get("room6")); // Placement
        Room room20 = new Room(roomTextures.get("room9"));
        Room room21 = new Room(roomTextures.get("room5"));
        Room room22 = new Room(roomTextures.get("room3"));
        Room room23 = new Room(roomTextures.get("room1")); // Networking
        Room room25 = new Room(roomTextures.get("room7"));
        Room room27 = new Room(roomTextures.get("room8")); // Dominoes
        Room room28 = new Room(roomTextures.get("room3"));
        Room room29 = new Room(roomTextures.get("room6")); // ENG1

        // Exit room is not actually displayed - game ends as soon as player steps inside.
        Room exit = new Room(roomTextures.get("room1"), true);

        // Initialise connections - remember both ways.
        room1.addAdjacent(room22, DoorDirection.NORTH);
        room1.addAdjacent(room2, DoorDirection.EAST);
        room1.addAdjacent(room13, DoorDirection.SOUTH);
        room1.addAdjacent(room21, DoorDirection.WEST);

        room2.addAdjacent(room3, DoorDirection.NORTH);
        room2.addAdjacent(room4, DoorDirection.EAST);
        room2.addAdjacent(room6, DoorDirection.SOUTH);
        room2.addAdjacent(room1, DoorDirection.WEST);

        room3.addAdjacent(room2, DoorDirection.SOUTH);
        room3.addAdjacent(room22, DoorDirection.WEST);

        room4.addAdjacent(room5, DoorDirection.SOUTH);
        room4.addAdjacent(room2, DoorDirection.WEST);

        room5.addAdjacent(room4, DoorDirection.NORTH);
        room5.addAdjacent(room8, DoorDirection.EAST);
        room5.addAdjacent(room7, DoorDirection.SOUTH);
        room5.addAdjacent(room6, DoorDirection.WEST);

        room6.addAdjacent(room2, DoorDirection.NORTH);
        room6.addAdjacent(room5, DoorDirection.EAST);
        room6.addAdjacent(room13,  DoorDirection.WEST);

        room7.addAdjacent(room5, DoorDirection.NORTH);

        room8.addAdjacent(room9, DoorDirection.EAST);
        room8.addAdjacent(room5, DoorDirection.WEST);

        room9.addAdjacent(room10, DoorDirection.NORTH);
        room9.addAdjacent(exit, DoorDirection.EAST);
        room9.addAdjacent(room11, DoorDirection.SOUTH);
        room9.addAdjacent(room8, DoorDirection.WEST);

        room10.addAdjacent(room9, DoorDirection.SOUTH);

        room11.addAdjacent(room9, DoorDirection.NORTH);
        room11.addAdjacent(room12, DoorDirection.SOUTH);

        room12.addAdjacent(room11, DoorDirection.NORTH);

        room13.addAdjacent(room1,  DoorDirection.NORTH);
        room13.addAdjacent(room6,  DoorDirection.EAST);
        room13.addAdjacent(room14,  DoorDirection.SOUTH);
        room13.addAdjacent(room20,  DoorDirection.WEST);

        room14.addAdjacent(room13,  DoorDirection.NORTH);
        room14.addAdjacent(room15,  DoorDirection.SOUTH);
        room14.addAdjacent(room19,  DoorDirection.WEST);

        room15.addAdjacent(room14,  DoorDirection.NORTH);
        room15.addAdjacent(room16,  DoorDirection.SOUTH);

        room16.addAdjacent(room15,  DoorDirection.NORTH);
        room16.addAdjacent(room17,  DoorDirection.EAST);
        room16.addAdjacent(room18,  DoorDirection.WEST);

        room17.addAdjacent(room16,  DoorDirection.WEST);

        room18.addAdjacent(room16,  DoorDirection.EAST);

        room19.addAdjacent(room20,  DoorDirection.NORTH);
        room19.addAdjacent(room14,  DoorDirection.EAST);

        room20.addAdjacent(room21,  DoorDirection.NORTH);
        room20.addAdjacent(room13,  DoorDirection.EAST);
        room20.addAdjacent(room19,  DoorDirection.SOUTH);

        room21.addAdjacent(room23,  DoorDirection.NORTH);
        room21.addAdjacent(room1,  DoorDirection.EAST);
        room21.addAdjacent(room20,  DoorDirection.SOUTH);
        room21.addAdjacent(room29,  DoorDirection.WEST);

        room22.addAdjacent(room3,  DoorDirection.EAST);
        room22.addAdjacent(room1,  DoorDirection.SOUTH);
        room22.addAdjacent(room23,  DoorDirection.WEST);

        room23.addAdjacent(room25,  DoorDirection.NORTH);
        room23.addAdjacent(room22,  DoorDirection.EAST);
        room23.addAdjacent(room21,  DoorDirection.SOUTH);
        room23.addAdjacent(room28,  DoorDirection.WEST);

        room25.addAdjacent(room23,  DoorDirection.SOUTH);
        room25.addAdjacent(room27,  DoorDirection.WEST);

        room27.addAdjacent(room25,  DoorDirection.EAST);
        room27.addAdjacent(room28,  DoorDirection.SOUTH);

        room28.addAdjacent(room27,  DoorDirection.NORTH);
        room28.addAdjacent(room23,  DoorDirection.EAST);
        room28.addAdjacent(room29,  DoorDirection.SOUTH);

        room29.addAdjacent(room28,  DoorDirection.NORTH);
        room29.addAdjacent(room21,  DoorDirection.EAST);

        // Initialise Events
        room3.setEvent(new PositiveEvent(PositiveEventType.GREGGS, player, game, scoreManager));
        room5.setEvent(new NegativeEvent(NegativeEventType.THE3, player, game, scoreManager));
        room7.setEvent(new HiddenEvent(HiddenEventType.LONGBOI, player, game));
        room10.setEvent(new NegativeEvent(NegativeEventType.SYS2, player, game, scoreManager));
        room12.setEvent(new HiddenEvent(HiddenEventType.BOB, player, game));
        room15.setEvent(new PositiveEvent(PositiveEventType.MONSTER, player, game, scoreManager));
        room17.setEvent(new PositiveEvent(PositiveEventType.CUP_NOODLES, player, game, scoreManager));
        room18.setEvent(new HiddenEvent(HiddenEventType.INVERSE_CONTROLS, player, game));
        room19.setEvent(new NegativeEvent(NegativeEventType.JOB, player, game, scoreManager));
        room23.setEvent(new PositiveEvent(PositiveEventType.NETWORKING, player, game, scoreManager));
        room27.setEvent(new PositiveEvent(PositiveEventType.PIZZA, player, game, scoreManager));
        room29.setEvent(new NegativeEvent(NegativeEventType.ENG1, player, game, scoreManager));

        currentRoom = room1;
        updateDoors(currentRoom);

        positiveIndicator = new Texture("PositiveIndicator.png");
        negativeIndicator = new Texture("NegativeIndicator.png");

        updateEventIndicators();
    }

    /**
     * Will change the current room, given the direction of the new room in relation to the current room.
     * * Updates the active doors, and repositions the player to the appropriate entrance location in the new room.
     * @param direction The direction of the new room.
     */
    public void changeRoom(DoorDirection direction) {
        // Will unload current room and load next room
        // Will update which doors are visible. Note that only 4 door objects are used -
        // they can be made visible or invisible.



        if (currentRoom.getEventType() != EventType.NONE)
        {
            currentRoom.getEvent().endEvent();
        }

        Room newRoom = currentRoom.getAdjacent(direction);
        updateDoors(newRoom);

        currentRoom = newRoom;
        updatePlayerPosition(direction);
        updateEventIndicators();

        if (newRoom.getEventType() != EventType.NONE)
        {
            newRoom.getEvent().startEvent();
        }

        if (newRoom.getExit())
        {
            game.setScreen(new GameOverScreen(game, true, timer, scoreManager));

            // achievement
            if (timer.getTimeSeconds() <= 10) { game.achievementManager.check_TEN_SECONDS(); }
        }
    }

    /**
     * Updates the player's position when changing rooms, to prevent the player colliding with the door the next frame.
     */
    private void updatePlayerPosition(DoorDirection direction)
    {
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();
        if(direction == DoorDirection.NORTH) player.setCenter(worldWidth / 2, 2);
        if(direction == DoorDirection.EAST) player.setCenter(2f, worldHeight / 2);
        if(direction == DoorDirection.SOUTH) player.setCenter(worldWidth / 2, worldHeight - 2);
        if(direction == DoorDirection.WEST) player.setCenter(worldWidth - 2, worldHeight / 2);
    }

    /**
     * Draw the current room and it's active doors.
     */
    public void drawMap()
    {
        drawCurrentRoom();
        drawCurrentRoomEvent();
        drawDoors();
        drawIndicators();
    }

    private void drawCurrentRoom()
    {
        Texture roomTexture = currentRoom.getRoomTexture();
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();
        game.batch.draw(roomTexture, 0, 0, worldWidth, worldHeight);
    }

    private void drawCurrentRoomEvent()
    {
        if (currentRoom.getEventType() != EventType.NONE)
        {
            currentRoom.getEvent().draw();
        }
    }

    private void drawDoors()
    {
        for (Door door : doors)
        {
            door.draw();
        }
    }

    /**
     * Will draw event indicator textures, if required, by each door.
     */
    private void drawIndicators()
    {
        if (indicatorTextures[0] != null)
        {
            game.batch.draw(indicatorTextures[0], 7.5f, 8f, 1f, 1f);
        }
        if (indicatorTextures[1] != null)
        {
            game.batch.draw(indicatorTextures[1], 15f, 4f, 1f, 1f);
        }
        if (indicatorTextures[2] != null)
        {
            game.batch.draw(indicatorTextures[2], 7.5f, 0f, 1f, 1f);
        }
        if (indicatorTextures[3] != null)
        {
            game.batch.draw(indicatorTextures[3], 0f, 4f, 1f, 1f);
        }
    }

    /**
     * Draw the UI associated with any active events.
     */
    public void drawEventUI()
    {
        if (currentRoom.getEventType() != EventType.NONE)
        {
            currentRoom.getEvent().drawUI();
        }
    }

    private void initialiseDoors() {
        // Will create the four doors at the start of the game.
        // Assumes a viewport size of width 16 and height 9.
        Door northDoor = new Door(this, DoorDirection.NORTH, 7.5f, 8f);
        Door eastDoor = new Door(this, DoorDirection.EAST, 15f, 4f);
        Door southDoor = new Door(this, DoorDirection.SOUTH, 7.5f, 0f);
        Door westDoor = new Door(this, DoorDirection.WEST, 0f, 4f);

        doors = new Door[]{northDoor, eastDoor, southDoor, westDoor};
    }

    /**
     * Will update which doors are visible based on the new room
     */
    private void updateDoors(Room newRoom) {
        Room[] allAdjacent = newRoom.getAllAdjacent();
        for (int i = 0; i < 4; i++)
        {
            doors[i].setActive(allAdjacent[i] != null);
        }
    }

    /**
     * Update the event type indicators by the doors.
     */
    private void updateEventIndicators() {
        Room[] rooms = currentRoom.getAllAdjacent();
        for (int i = 0; i < 4; i++)
        {
            indicatorTextures[i] = null;

            // Check Room actually exists before trying to access event type.
            if (rooms[i] == null) continue;

            if (rooms[i].getEventType() == EventType.POSITIVE)
            {
                indicatorTextures[i] = positiveIndicator;
            }
            else if (rooms[i].getEventType() == EventType.NEGATIVE)
            {
                indicatorTextures[i] = negativeIndicator;
            }
        }
    }

    public void update(float delta)
    {
        checkDoorCollision();
        if (currentRoom.getEventType() != EventType.NONE)
        {
            currentRoom.getEvent().update(delta);
        }
    }

    /**
     * Check whether the player is colliding with any of the doors.
     */
    public void checkDoorCollision()
    {
        for (Door door : doors)
        {
            if (door.isActive && player.checkCollision(door.doorSprite))
            {
                changeRoom(door.direction);
                return;
            }
        }
    }

    /**
     * Dispose of all room and door textures.
     */
    public void dispose()
    {
        for (Texture t : roomTextures.values()) {
            t.dispose();
        }

        for (Door door : doors)
        {
            door.dispose();
        }

        positiveIndicator.dispose();
        negativeIndicator.dispose();
    }
}
