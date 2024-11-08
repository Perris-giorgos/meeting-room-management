package com.acme.management.controller;

import com.acme.management.controller.dto.BookingRequest;
import com.acme.management.controller.dto.BookingResponse;
import com.acme.management.service.RoomBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest/bookings")
@Validated
public class RoomBookingController {

    private final RoomBookingService bookingService;

    public RoomBookingController(RoomBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    @Operation(description = "returns all the bookings of a room a specific day.")
    @Parameter(name = "roomCode", example = "COYOTE")
    @Parameter(name = "date", example = "2024-11-07")
    public ResponseEntity<List<BookingResponse>> getBookings(
            @RequestParam String roomCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<BookingResponse> bookings = bookingService.getRoomBookingsByDate(roomCode, date);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PostMapping
    @Operation(description = "creates or updates room booking, include booking id only for existing books")
    public ResponseEntity<BookingResponse> postEvent(@Valid @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.saveOrUpdateBooking(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "cancel an existing booking")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
