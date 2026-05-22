package controller;

import model.Booking;
import service.BookingService;

import java.util.List;

public class BookingController {

    private final BookingService
            bookingService =
            new BookingService();

    public Booking createBooking(
            Booking booking) {

        try {

            return bookingService
                    .createBooking(booking);

        } catch (Exception e) {

            System.out.println(
                    "Booking Failed : "
                            + e.getMessage());

            return null;
        }
    }

    public List<Booking> getAllBookings() {

        return bookingService
                .getAllBookings();
    }

    public List<Booking> getActiveBookings() {

        return bookingService
                .getActiveBookings();
    }

    public void cancelBooking(Long bookingId) {

        try {

            bookingService
                    .cancelBooking(bookingId);

            System.out.println(
                    "Booking cancelled successfully");

        } catch (Exception e) {

            System.out.println(
                    "Cancellation Failed : "
                            + e.getMessage());
        }
    }

}
