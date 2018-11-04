package com.leveris.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leveris.test.dto.Call;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureWebMvc
@TestPropertySource("classpath:test.properties")
public class RESTTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void containsAllField() throws Exception {
        Call call = new Call();
        call.setLastName("Kozel");
        call.setFirstName("Maksim");
        call.setNumber("+375(33)375-29-18");
        this.mockMvc.perform(post("/addCall")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(call)))
                .andExpect(status().isOk());
    }

    @Test
    public void withoutFirstName() throws Exception {
        Call call = new Call();
        call.setLastName("Kozel");
        call.setNumber("+375(33)375-29-18");
        this.mockMvc.perform(post("/addCall")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(call)))
                .andExpect(status().isOk());
    }

    @Test
    public void badFormatNumber() throws Exception {
        Call call = new Call();
        call.setLastName("Kozel");
        call.setFirstName("Maksim");
        call.setNumber("+375(33)375-29-180");
        this.mockMvc.perform(post("/addCall")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(call)))
                .andExpect(status().is(400));
    }
}

