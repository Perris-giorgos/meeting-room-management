package com.acme.management.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingResponse(
        Long bookingId,
        String roomName,
        String roomCode,
        String employeeEmail,
        LocalDate date,
        LocalTime timeFrom,
        LocalTime timeTo
) {
}
