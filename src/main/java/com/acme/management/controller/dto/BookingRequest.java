package com.acme.management.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record BookingRequest(

        Long bookingId,
        @NotNull(message = "Room code must not be null")
        String roomCode,
        @NotNull(message = "Employee email must not be null")
        @Email(message = "Email should be valid")
        String employeeEmail,
        @NotNull(message = "Date must not be null")
        @FutureOrPresent(message = "Date from must be in the present or future")
        Instant dateFrom,
        @NotNull(message = "Date must not be null")
        @Future(message = "Date to must be in the future")
        Instant dateTo
) {
}
