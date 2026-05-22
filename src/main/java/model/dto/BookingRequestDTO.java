package model.dto;

import java.time.LocalDate;

public record BookingRequestDTO(
        Long customerId,
        Long roomId,
        LocalDate checkIn,
        LocalDate checkOut
) {}

