INSERT INTO meeting_room (id, name, room_code, capacity) VALUES (91, 'Coyote Room', 'COYOTE', 10);
INSERT INTO meeting_room (id, name, room_code) VALUES (92, 'Beep-Beep Room', 'BEEP');
INSERT INTO meeting_room (id, name, room_code) VALUES (93, 'Wile E Room', 'WILE');
INSERT INTO meeting_room (id, name, room_code) VALUES (94, 'Road Runner Room', 'RUNNER');

INSERT INTO employee (id, email, name) VALUES (91, 'michael@acme.com', 'Michael');
INSERT INTO employee (id, email, name) VALUES (92, 'chuck@acme.com', 'Chuck');
INSERT INTO employee (id, email, name) VALUES (93, 'george@acme.com', 'George');
INSERT INTO employee (id, email, name) VALUES (94, 'dave@acme.com', 'Dave');

INSERT INTO room_booking (id, room_id, employee_id, booking_date, start_time, end_time) VALUES (91, 91, 91, '2024-12-07', '09:00:00', '11:00:00');

INSERT INTO room_booking (id, room_id, employee_id, booking_date, start_time, end_time) VALUES (92, 91, 92, '2024-12-07', '13:00:00', '15:00:00');

INSERT INTO room_booking (id, room_id, employee_id, booking_date, start_time, end_time) VALUES (93, 92, 93, '2025-12-07', '14:00:00', '16:00:00');

INSERT INTO room_booking (id, room_id, employee_id, booking_date, start_time, end_time) VALUES (94, 94, 94, '2024-12-08', '09:00:00', '10:00:00');

INSERT INTO room_booking (id, room_id, employee_id, booking_date, start_time, end_time) VALUES (95, 94, 94, '2024-10-08', '09:00:00', '10:00:00');