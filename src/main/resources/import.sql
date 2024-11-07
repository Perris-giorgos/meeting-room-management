INSERT INTO meeting_room (id, name, room_code, capacity) VALUES (1, 'Coyote Room', 'COYOTE', 10);
INSERT INTO meeting_room (id, name, room_code) VALUES (2, 'Beep-Beep Room', 'BEEP');
INSERT INTO meeting_room (id, name, room_code) VALUES (3, 'Wile E Room', 'WILE');
INSERT INTO meeting_room (id, name, room_code) VALUES (4, 'Road Runner Room', 'RUNNER');

INSERT INTO employee (id, email, name) VALUES (1, 'michael@acme.com', 'Michael');
INSERT INTO employee (id, email, name) VALUES (2, 'chuck@acme.com', 'Chuck');
INSERT INTO employee (id, email, name) VALUES (3, 'george@acme.com', 'George');
INSERT INTO employee (id, email, name) VALUES (4, 'dave@acme.com', 'Dave');

INSERT INTO room_booking (id, room_id, employee_id, booking_date, start_time, end_time) VALUES (1, 1, 1, '2024-11-07', '09:00:00', '11:00:00');

INSERT INTO room_booking (id, room_id, employee_id, booking_date, start_time, end_time) VALUES (2, 1, 2, '2024-11-07', '13:00:00', '15:00:00');

INSERT INTO room_booking (id, room_id, employee_id, booking_date, start_time, end_time) VALUES (3, 2, 3, '2025-11-07', '14:00:00', '16:00:00');

INSERT INTO room_booking (id, room_id, employee_id, booking_date, start_time, end_time) VALUES (4, 4, 4, '2024-11-08', '09:00:00', '10:00:00');