package com.acme.management.controller;

import com.acme.management.AbstractBaseTest;
import org.junit.jupiter.api.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MeetingRoomControllerTest extends AbstractBaseTest {


    @Test
    void shouldGetAllRooms() throws Exception {
        String expectedResponse = """
                [
                   {
                      "name":"Coyote Room",
                      "code":"COYOTE",
                      "capacity":10
                   },
                   {
                      "name":"Beep-Beep Room",
                      "code":"BEEP",
                      "capacity":null
                   },
                   {
                      "name":"Wile E Room",
                      "code":"WILE",
                      "capacity":null
                   },
                   {
                      "name":"Road Runner Room",
                      "code":"RUNNER",
                      "capacity":null
                   }
                ]
                """;

        mockMvc.perform(get("/rest/rooms"))
                .andExpect(status().isOk());
    }

    @Test
    void createMeetingRoom() throws Exception {

        mockMvc.perform(post("/rest/rooms")
                        .contentType(APPLICATION_JSON)
                        .content("""
                            {
                                "name": "Warner Bros Room",
                                "capacity": 15
                            }
                            """.stripIndent())
        ).andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "name": "Warner Bros Room",
                            "code": "WARNER BROS ROOM",
                            "capacity": 15
                        }

""", true));
    }
}