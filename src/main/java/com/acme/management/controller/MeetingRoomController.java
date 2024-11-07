package com.acme.management.controller;

import com.acme.management.controller.dto.MeetingRoomRequest;
import com.acme.management.controller.dto.MeetingRoomResponse;
import com.acme.management.repository.entity.MeetingRoom;
import com.acme.management.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/rooms/")
@RequiredArgsConstructor
public class MeetingRoomController {

    private final MeetingRoomService service;

    @GetMapping
    public ResponseEntity<List<MeetingRoomResponse>> getMeetingRooms() {
        return ResponseEntity.ok(service.getAllMeetingRooms());
    }

    @PostMapping
    public ResponseEntity<MeetingRoomResponse> createMeetingRoom(MeetingRoomRequest request) {
        return ResponseEntity.ok(service.createMeetingRoom(request));
    }

}
