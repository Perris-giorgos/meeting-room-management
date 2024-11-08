-- MeetingRoom table
CREATE TABLE meeting_room (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    room_code VARCHAR(255) NOT NULL,
    capacity INT
);

CREATE UNIQUE INDEX unique_code_index ON meeting_room (room_code);

-- Employee table
CREATE TABLE employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX unique_email_index ON employee (email);

-- RoomBooking table
CREATE TABLE room_booking (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    room_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    booking_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    FOREIGN KEY (room_id) REFERENCES meeting_room(id),
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE INDEX booking_date_index ON room_booking (booking_date);

