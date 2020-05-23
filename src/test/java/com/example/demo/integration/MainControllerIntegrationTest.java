package com.example.demo.integration;

import com.example.demo.config.DialPadMapperProperties;
import com.example.demo.controllers.MainController;
import com.example.demo.models.Result;
import com.example.demo.services.MainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {MainController.class, DialPadMapperProperties.class, MainService.class})
@WebMvcTest
class MainControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void givenValid_10DigitPhoneInput_withDefaultsForStartPageAndSize_thenReturnsCombinationsSuccessfully() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/combinations?phoneNumber=240 386 6106")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseStr = result.getResponse().getContentAsString();
        assertNotNull(responseStr);
        Result actualResult = mapper.readValue(responseStr, Result.class);
        assertTrue(actualResult.getTotalRecords() == 16384);
        assertTrue(actualResult.getData().size() == 10);
        assertTrue(actualResult.getData().contains("AG0 DTM M10M"));
    }

    @Test
    void givenValid_7DigitPhoneInput_withCustomPageAndSize_thenReturnsCombinationsSuccessfully() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/combinations?phoneNumber=240 386 6&page=2&size=5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseStr = result.getResponse().getContentAsString();
        assertNotNull(responseStr);
        Result actualResult = mapper.readValue(responseStr, Result.class);
        assertTrue(actualResult.getTotalRecords() == 4096);
        assertTrue(actualResult.getData().size() == 5);
        assertTrue(actualResult.getData().contains("CI0 DTM M"));
    }
}
