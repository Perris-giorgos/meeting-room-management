package com.acme.management.it;

import com.acme.management.AbstractBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;


import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomBookingControllerTest extends AbstractBaseTest {

    @Test
    void shouldGetExistingBookings() throws Exception {
        String expectedResponse = """
                [
                   {
                      "bookingId":91,
                      "roomName":"Coyote Room",
                      "roomCode":"COYOTE",
                      "employeeEmail":"michael@acme.com",
                      "date":"2024-12-07",
                      "timeFrom":"09:00:00",
                      "timeTo":"11:00:00"
                   },
                   {
                      "bookingId":92,
                      "roomName":"Coyote Room",
                      "roomCode":"COYOTE",
                      "employeeEmail":"chuck@acme.com",
                      "date":"2024-12-07",
                      "timeFrom":"13:00:00",
                      "timeTo":"15:00:00"
                   }
               ]
                """;
        mockMvc.perform(get("/rest/bookings?roomCode=COYOTE&date=2024-12-07"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse, true));
    }

    @Test
    void noBookingShouldMatchTheCriteria() throws Exception {
        String expectedResponse = "[]";
        mockMvc.perform(get("/rest/bookings?roomCode=COYOTE&date=2024-10-07"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse, true));
    }

    @Test
    void shouldCreateAnewBooking() throws Exception {
        String bookingRequest = """
                {
                    "roomCode": "COYOTE",
                    "employeeEmail": "george@acme.com",
                    "dateFrom": "2024-12-08T10:00:00.000Z",
                    "dateTo": "2024-12-08T11:00:00.000Z"
                }
                """;
        String expectedResponse = """
                   {
                      "bookingId":1,
                      "roomName":"Coyote Room",
                      "roomCode":"COYOTE",
                      "employeeEmail":"george@acme.com",
                      "date":"2024-12-08",
                      "timeFrom":"10:00:00",
                      "timeTo":"11:00:00"
                   }
                """;
        mockMvc.perform(post("/rest/bookings")
                        .contentType(APPLICATION_JSON)
                        .content(bookingRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse, true));
    }

    @Test
    void testInvalidDates() throws Exception {
        // date to before date from
        String bookingRequest = """
                {
                    "roomCode": "COYOTE",
                    "employeeEmail": "george@acme.com",
                    "dateFrom": "2024-12-09T10:00:00.000Z",
                    "dateTo": "2024-12-09T09:00:00.000Z"
                }
                """;
        String expectedResponse = """
                   {
                      "type":"about:blank",
                      "title":"Bad Request",
                      "status":400,
                      "detail":"End date must be after start date",
                      "instance":"/rest/bookings"
                   }
                """.stripIndent();
        mockMvc.perform(post("/rest/bookings")
                        .contentType(APPLICATION_JSON)
                        .content(bookingRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse, true));
    }


    @Test
    void testInvalidDuration() throws Exception {
        // date to before date from
        String bookingRequest = """
                {
                    "roomCode": "COYOTE",
                    "employeeEmail": "george@acme.com",
                    "dateFrom": "2024-12-09T09:00:00.000Z",
                    "dateTo": "2024-12-09T09:03:00.000Z"
                }
                """;
        String expectedResponse = """
                   {
                      "type":"about:blank",
                      "title":"Bad Request",
                      "status":400,
                      "detail":"Bookings slots should at least 1 hour or consecutive multiples of 1 hour",
                      "instance":"/rest/bookings"
                   }
                """.stripIndent();
        mockMvc.perform(post("/rest/bookings")
                        .contentType(APPLICATION_JSON)
                        .content(bookingRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse, true));

        String invalidDurationRequest = """
                {
                    "roomCode": "COYOTE",
                    "employeeEmail": "george@acme.com",
                    "dateFrom": "2024-12-09T09:00:00.000Z",
                    "dateTo": "2024-12-09T11:03:00.000Z"
                }
                """;
        mockMvc.perform(post("/rest/bookings")
                        .contentType(APPLICATION_JSON)
                        .content(invalidDurationRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse, true));
    }

    @Test
    void shouldUpdateExistingBooking() throws Exception {
        String bookingRequest = """
                {
                    "bookingId": 91,
                    "roomCode": "COYOTE",
                    "employeeEmail": "george@acme.com",
                    "dateFrom": "2024-12-07T10:00:00.000Z",
                    "dateTo": "2024-12-07T11:00:00.000Z"
                }
                """;
        String expectedResponse = """
                   {
                      "bookingId":91,
                      "roomName":"Coyote Room",
                      "roomCode":"COYOTE",
                      "employeeEmail":"george@acme.com",
                      "date":"2024-12-07",
                      "timeFrom":"10:00:00",
                      "timeTo":"11:00:00"
                   }
                """;
        mockMvc.perform(post("/rest/bookings")
                        .contentType(APPLICATION_JSON)
                        .content(bookingRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse, true));

        String bookingRequestRevert = """
                {
                    "bookingId": 91,
                    "roomCode": "COYOTE",
                    "employeeEmail": "michael@acme.com",
                    "dateFrom": "2024-12-07T09:00:00.000Z",
                    "dateTo": "2024-12-07T11:00:00.000Z"
                }
                """;

        mockMvc.perform(post("/rest/bookings")
                        .contentType(APPLICATION_JSON)
                        .content(bookingRequestRevert))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFailToUpdateNonExistingBooking() throws Exception {
        String bookingRequest = """
                {
                    "bookingId": 100,
                    "roomCode": "COYOTE",
                    "employeeEmail": "george@acme.com",
                    "dateFrom": "2024-12-12T15:00:00.000Z",
                    "dateTo": "2024-12-12T16:00:00.000Z"
                }
                """;
        String expectedResponse = """
                   {
                      "type":"about:blank",
                      "title":"Not Found",
                      "status":404,
                      "detail":"Booking with Id 100 not found",
                      "instance":"/rest/bookings"
                   }
                """;
        mockMvc.perform(post("/rest/bookings")
                        .contentType(APPLICATION_JSON)
                        .content(bookingRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().json(expectedResponse, true));
    }

    @Test
    void shouldFailToUpdateWithInvalidRoom() throws Exception {
        String bookingRequest = """
                {
                    "bookingId": 91,
                    "roomCode": "COYOTE1",
                    "employeeEmail": "george@acme.com",
                    "dateFrom": "2024-12-07T10:00:00.000Z",
                    "dateTo": "2024-12-07T11:00:00.000Z"
                }
                """;
        String expectedResponse = """
                   {
                      "type":"about:blank",
                      "title":"Bad Request",
                      "status":400,
                      "detail":"Room COYOTE1 not found",
                      "instance":"/rest/bookings"
                   }
                """;
        mockMvc.perform(post("/rest/bookings")
                        .contentType(APPLICATION_JSON)
                        .content(bookingRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse, true));
    }

    @Test
    void shouldFailToUpdateWithInvalidEmployee() throws Exception {
        String bookingRequest = """
                {
                    "bookingId": 91,
                    "roomCode": "COYOTE",
                    "employeeEmail": "george1@acme.com",
                    "dateFrom": "2024-12-07T10:00:00.000Z",
                    "dateTo": "2024-12-07T11:00:00.000Z"
                }
                """;
        String expectedResponse = """
                   {
                      "type":"about:blank",
                      "title":"Bad Request",
                      "status":400,
                      "detail":"Employee with email george1@acme.com not found",
                      "instance":"/rest/bookings"
                   }
                """;
        mockMvc.perform(post("/rest/bookings")
                        .contentType(APPLICATION_JSON)
                        .content(bookingRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse, true));
    }


    @Test
    void shouldNotAllowBookingCreationForOverlapping() throws Exception {
        String bookingRequest = """
                {
                    "roomCode": "RUNNER",
                    "employeeEmail": "george@acme.com",
                    "dateFrom": "2024-12-08T09:00:00.000Z",
                    "dateTo": "2024-12-08T11:00:00.000Z"
                }
                """.stripIndent();
        String expectedResponse = """
                   {
                      "type":"about:blank",
                      "title":"Bad Request",
                      "status":400,
                      "detail":"Booking request overlaps with existing bookings for the same room",
                      "instance":"/rest/bookings"
                   }
                """.stripIndent();
        mockMvc.perform(post("/rest/bookings")
                        .contentType(APPLICATION_JSON)
                        .content(bookingRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse, true));
    }



    @Test
    void shouldCancelBooking() throws Exception {
        // create book
        String bookingRequest = """
                {
                    "roomCode": "RUNNER",
                    "employeeEmail": "george@acme.com",
                    "dateFrom": "2024-12-10T10:00:00.000Z",
                    "dateTo": "2024-12-10T11:00:00.000Z"
                }
                """;
        String expectedResponse = """
                   [{
                      "bookingId": 1,
                      "roomName":"Road Runner Room",
                      "roomCode":"RUNNER",
                      "employeeEmail":"george@acme.com",
                      "date":"2024-12-10",
                      "timeFrom":"10:00:00",
                      "timeTo":"11:00:00"
                   }]
                """;
        mockMvc.perform(post("/rest/bookings")
                        .contentType(APPLICATION_JSON)
                        .content(bookingRequest))
                .andExpect(status().isOk());

        // verify booking creation
        mockMvc.perform(get("/rest/bookings?roomCode=RUNNER&date=2024-12-10"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse, true));

        // cancel booking
        mockMvc.perform(delete("/rest/bookings/1")).andExpect(status().isOk());

        // verify booking not exist anymore
        mockMvc.perform(get("/rest/bookings?roomCode=RUNNER&date=2024-12-10"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", true));
    }

    @Test
    void shouldFailCancelBooking() throws Exception {
        mockMvc.perform(delete("/rest/bookings/200"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        """
                    {
                      "type":"about:blank",
                      "title":"Not Found",
                      "status":404,
                      "detail":"Booking not found with id 200",
                      "instance":"/rest/bookings/200"
                   }"""
                ));

    }

    @Test
    void shouldFailCancelBookingInThePast() throws Exception {
        mockMvc.perform(delete("/rest/bookings/95"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        """
                    {
                      "type":"about:blank",
                      "title":"Bad Request",
                      "status":400,
                      "detail":"Cannot cancel booking with id 95 anymore",
                      "instance":"/rest/bookings/95"
                   }"""
                ));

    }
}