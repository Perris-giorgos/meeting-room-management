package com.acme.management.controller;

import com.acme.management.controller.dto.MeetingRoomRequest;
import com.acme.management.controller.dto.MeetingRoomResponse;
import com.acme.management.service.MeetingRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/rooms")
@RequiredArgsConstructor
@Validated
public class MeetingRoomController {

    private final MeetingRoomService service;

    @GetMapping
    public ResponseEntity<List<MeetingRoomResponse>> getMeetingRooms() {
        return ResponseEntity.ok(service.getAllMeetingRooms());
    }

    @PostMapping
    public ResponseEntity<MeetingRoomResponse> createMeetingRoom(@Valid @RequestBody MeetingRoomRequest request) {
        return ResponseEntity.ok(service.createMeetingRoom(request));
    }

}
