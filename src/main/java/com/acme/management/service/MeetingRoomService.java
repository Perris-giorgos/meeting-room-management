package com.acme.management.service;


import com.acme.management.controller.dto.MeetingRoomRequest;
import com.acme.management.controller.dto.MeetingRoomResponse;
import com.acme.management.repository.MeetingRoomRepository;
import com.acme.management.repository.entity.MeetingRoom;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingRoomService {

    private final MeetingRoomRepository roomRepository;


    public MeetingRoomService(MeetingRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<MeetingRoomResponse> getAllMeetingRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public MeetingRoomResponse createMeetingRoom(MeetingRoomRequest request) {
        MeetingRoom room =
                MeetingRoom.builder()
                        .name(request.name())
                        .roomCode(request.name().toUpperCase())
                        .capacity(request.capacity())
                        .build();
        MeetingRoom savedRoom = roomRepository.save(room);

        return new MeetingRoomResponse(savedRoom.getName(), savedRoom.getRoomCode(), request.capacity());
    }

    private MeetingRoomResponse convert(MeetingRoom room) {
        return new MeetingRoomResponse(room.getName(), room.getRoomCode(), room.getCapacity());
    }
}
