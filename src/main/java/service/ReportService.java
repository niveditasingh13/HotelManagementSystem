package service;

import model.Booking;
import model.Payment;

import java.math.BigDecimal;
import java.util.List;

public class ReportService {

    private final BookingService
            bookingService =
            new BookingService();

    private final PaymentService
            paymentService =
            new PaymentService();

    public long getTotalBookings() {

        List<Booking> bookings =
                bookingService.getAllBookings();

        return bookings.size();
    }

    public BigDecimal getTotalRevenue() {

        List<Payment> payments =
                paymentService.getCompletedPayments();

        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO,
                        BigDecimal::add);
    }
}
