package com.acme.management.service;

import com.acme.management.controller.dto.BookingRequest;
import com.acme.management.controller.dto.BookingResponse;
import com.acme.management.repository.EmployeeRepository;
import com.acme.management.repository.MeetingRoomRepository;
import com.acme.management.repository.RoomBookingRepository;
import com.acme.management.repository.entity.Employee;
import com.acme.management.repository.entity.MeetingRoom;
import com.acme.management.repository.entity.RoomBooking;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomBookingService {

    private final RoomBookingRepository bookingRepository;
    private final MeetingRoomRepository roomRepository;
    private final EmployeeRepository employeeRepository;

    public List<BookingResponse> getRoomBookingsByDate(String roomCode, LocalDate day) {

        return bookingRepository.findByRoomAndDate(roomCode, day)
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingResponse saveOrUpdateBooking(BookingRequest request) {
        ZonedDateTime dateFrom = request.dateFrom().atZone(ZoneOffset.UTC);
        ZonedDateTime dateTo = request.dateTo().atZone(ZoneOffset.UTC);
        validateRequest(request.roomCode(), dateFrom, dateTo, request.bookingId());
        RoomBooking booking = findExistingOrCreateBooking(request.bookingId());

        MeetingRoom room = roomRepository.findMeetingRoomByRoomCode(request.roomCode())
                .orElseThrow(() -> new IllegalArgumentException("Room " + request.roomCode() + " not found"));
        Employee employee = employeeRepository.findEmployeeByEmail(request.employeeEmail())
                .orElseThrow(() -> new IllegalArgumentException("""
                        Employee with email %s not found""".formatted(request.employeeEmail())));

        booking.setRoom(room);
        booking.setEmployee(employee);
        booking.setDate(request.dateFrom().atZone(ZoneOffset.UTC).toLocalDate());
        booking.setStartTime(request.dateFrom().atZone(ZoneOffset.UTC).toLocalTime());
        booking.setEndTime(request.dateTo().atZone(ZoneOffset.UTC).toLocalTime());

        bookingRepository.save(booking);

        return convert(booking);
    }

    public void cancelBooking(Long bookingId) {
        RoomBooking roomBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NoSuchElementException("Booking not found with id " + bookingId));

        if (roomBooking.getDate().isBefore(LocalDate.now())
                || (roomBooking.getDate().isEqual(LocalDate.now())
                && roomBooking.getStartTime().isBefore(LocalTime.now()))) {
            throw new IllegalArgumentException("Cannot cancel booking with id " + bookingId + " anymore");
        }

        bookingRepository.delete(roomBooking);
    }

    private void validateRequest(String roomCode, ZonedDateTime dateFrom, ZonedDateTime dateTo, Long bookingId) {
        if ( dateFrom.isAfter(dateTo) ) {

            throw new IllegalArgumentException("End date must be after start date");
        }
        Duration duration = Duration.between(dateFrom.toLocalTime(), dateTo.toLocalTime());
        boolean isBookingDurationValid = duration.toHours() >= 1 && duration.toMinutes() % 60 == 0;
        if ( !isBookingDurationValid ) {
            throw new IllegalArgumentException("Bookings slots should at least 1 hour or consecutive multiples of 1 hour");
        }
        var overlappingBooks =
                bookingRepository.findOverlappingBookings(roomCode, dateFrom.toLocalDate(), dateFrom.toLocalTime(), dateTo.toLocalTime());
        if ( !overlappingBooks.isEmpty() && !overlappingBooks.stream().allMatch(book -> book.getId().equals(bookingId)) ) {
            throw new IllegalArgumentException("Booking request overlaps with existing bookings for the same room");
        }
    }

    private BookingResponse convert(RoomBooking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getRoom().getName(),
                booking.getRoom().getRoomCode(),
                booking.getEmployee().getEmail(),
                booking.getDate(),
                booking.getStartTime(),
                booking.getEndTime()
        );
    }

    private RoomBooking findExistingOrCreateBooking(Long id) {
        return id != null ? bookingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Booking with Id " + id + " not found"))
                : new RoomBooking();

    }
}
