package controller;

import model.Room;
import service.RoomService;

import java.util.List;

public class RoomController {

    private final RoomService
            roomService =
            new RoomService();

    public Room addRoom(Room room) {

        try {

            return roomService.addRoom(room);

        } catch (Exception e) {

            System.out.println(
                    "Room Creation Failed : "
                            + e.getMessage());

            return null;
        }
    }

    public List<Room> getAllRooms() {

        return roomService.getAllRooms();
    }

    public List<Room> getAvailableRooms() {

        return roomService.getAvailableRooms();
    }

    public void deleteRoom(Long roomId) {

        try {

            roomService.deleteRoom(roomId);

            System.out.println(
                    "Room deleted successfully");

        } catch (Exception e) {

            System.out.println(
                    "Delete Failed : "
                            + e.getMessage());
        }
    }
}
