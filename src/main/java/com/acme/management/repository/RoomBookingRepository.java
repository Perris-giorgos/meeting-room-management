package com.acme.management.repository;

import com.acme.management.repository.entity.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {

    @Query(value = "select b from RoomBooking b where b.room.roomCode= :code and b.date= :day")
    List<RoomBooking> findByRoomAndDate(@Param("code") String code,
                                        @Param("day") LocalDate day);


    @Query("SELECT b FROM RoomBooking b WHERE b.room.roomCode = :code AND b.date = :day " +
            "AND ((:timeFrom <= b.endTime AND :timeTo >= b.startTime))")
    List<RoomBooking> findOverlappingBookings(
            @Param("code") String code,
            @Param("day") LocalDate day,
            @Param("timeFrom")LocalTime timeFrom,
            @Param("timeTo")LocalTime timeTo
    );
}
