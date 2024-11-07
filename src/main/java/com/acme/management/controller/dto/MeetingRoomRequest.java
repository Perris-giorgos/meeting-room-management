package com.acme.management.controller.dto;

import jakarta.validation.constraints.NotNull;

public record MeetingRoomRequest(
        @NotNull
        String name,
        Integer capacity
) {
}
