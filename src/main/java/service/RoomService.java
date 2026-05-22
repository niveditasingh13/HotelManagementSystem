package service;

import model.Room;
import model.enums.RoomStatus;
import repository.RoomRepository;
import repository.jpa.RoomRepositoryImpl;

import java.util.List;

public class RoomService {

    private final RoomRepository
            roomRepository =
            new RoomRepositoryImpl();

    public Room addRoom(Room room) {

        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {

        return roomRepository.findAll();
    }

    public List<Room> getAvailableRooms() {

        return roomRepository
                .findByStatus(RoomStatus.AVAILABLE);
    }

    public Room updateRoom(Room room) {

        return roomRepository.update(room);
    }

    public void deleteRoom(Long roomId) {

        roomRepository.delete(roomId);
    }
}
