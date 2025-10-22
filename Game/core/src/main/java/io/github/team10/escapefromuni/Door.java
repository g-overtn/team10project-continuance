package io.github.team10.escapefromuni;

public class Door {
    public RoomManager roomManager;
    public DoorDirection direction;

    public boolean isActive;

    public void Interact() {
        // Will call changeRoom() on RoomManager
    }
}
