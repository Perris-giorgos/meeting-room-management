package com.acme.management.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capacity")
    private int capacity;

}
