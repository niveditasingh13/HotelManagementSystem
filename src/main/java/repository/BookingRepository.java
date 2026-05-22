package repository;

import model.Booking;
import model.Customer;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends GenericRepository<Booking, Long>{

    List<Booking> findBookingsByCustomer(Customer customer);

    List<Booking> findActiveBookings();

    boolean existsOverlappingBooking(
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut
    );
}
