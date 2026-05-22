package service;

import model.Booking;
import model.Room;
import model.enums.BookingStatus;
import model.enums.RoomStatus;
import repository.BookingRepository;
import repository.RoomRepository;
import repository.jpa.BookingRepositoryImpl;
import repository.jpa.RoomRepositoryImpl;

import java.util.List;

public class BookingService {

    private final BookingRepository
            bookingRepository =
            new BookingRepositoryImpl();

    private final RoomRepository
            roomRepository =
            new RoomRepositoryImpl();

    public synchronized Booking createBooking(
            Booking booking) {

        boolean alreadyBooked =
                bookingRepository
                        .existsOverlappingBooking(
                                booking.getRoom().getRoomId(),
                                booking.getCheckInDate(),
                                booking.getCheckOutDate());
        if (alreadyBooked) {

            throw new RuntimeException(
                    "Room already booked");
        }

        booking.setStatus(
                BookingStatus.CONFIRMED);

        Room room = booking.getRoom();

        room.setStatus(RoomStatus.OCCUPIED);

        roomRepository.update(room);

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();
    }

    public List<Booking> getActiveBookings() {

        return bookingRepository
                .findActiveBookings();
    }

    public void cancelBooking(Long bookingId) {

        Booking booking =
                bookingRepository
                        .findById(bookingId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Booking not found"));

        booking.setStatus(
                BookingStatus.CANCELLED);

        Room room = booking.getRoom();

        room.setStatus(RoomStatus.AVAILABLE);

        roomRepository.update(room);

        bookingRepository.update(booking);
    }
}

