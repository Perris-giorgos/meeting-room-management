# meeting-room-management
# General Info
This microservice is offering an API that lets the user manage meeting rooms and book slots for meetings.
Meeting rooms can be books only for registered employee emails. 
This API has the following functionalities:
1. View the booked slots for each meeting room for a specific day using GET/rest/bookings?roomCode=?&date=?
2. Book a new slot for a meeting room using POST/rest/bookings
3. Cancel an already booked meeting in the future using DELETE/rest/bookings/{id}
4. All the available meeting rooms can be retrieved through GET/rest/rooms, 
to help the client get the required details about the rooms and be able to make the booking. 
5. Add new rooms in the database using POST/rest/rooms

# Clarifications
## Database 
In this project h2 in memory database has been used in order to achieve the fastest and easiest run process for testing purposes. 
In this way the reviewer does not need any external tooling or any installed Database locally. 
Running the application will initialize all the data included in the file src/main/resources/import.sql. 
Database accessible through browser in http://localhost:8080/h2-console.
    url: jdbc:h2:mem:testdb
    username: sa
    password: pass

## Swagger
For Api documentation openapi swagger is used. The page can be accessed by http://localhost:8080/swagger-ui/index.html#/ .
All the API can be found there and execute call to the service using manual requests. 
