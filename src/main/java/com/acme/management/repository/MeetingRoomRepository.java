package com.acme.management.repository;

import com.acme.management.repository.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long>  {

    Optional<MeetingRoom> findMeetingRoomByRoomCode(String code);
}
