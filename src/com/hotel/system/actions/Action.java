package com.hotel.system.actions;

import com.hotel.system.rooms.Room;
import com.hotel.system.rooms.RoomManager;

import java.util.List;

public abstract class Action {
    protected RoomManager roomManager;

    public Action(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    public void viewRooms() {
        System.out.println("\nAvailable Rooms:");
        List<Room> rooms = roomManager.getRooms();

        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }
}
