package com.acme.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@ActiveProfiles("it")
@AutoConfigureMockMvc
@SpringBootTest
public abstract class AbstractBaseTest {

    @Autowired
    protected MockMvc mockMvc;


}