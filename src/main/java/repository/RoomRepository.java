package repository;

import model.Room;
import model.enums.RoomStatus;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends GenericRepository<Room, Long>{

    Optional<Room> findByRoomNumber(String roomNumber);

    List<Room> findByStatus(RoomStatus status);
}
