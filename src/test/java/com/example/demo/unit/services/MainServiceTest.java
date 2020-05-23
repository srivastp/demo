package com.example.demo.unit.services;

import com.example.demo.exceptions.InvalidParameterException;
import com.example.demo.models.Result;
import com.example.demo.services.MainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MainServiceTest {

    @Autowired
    private MainService mainService;

    @Autowired
    ObjectMapper mapper;

    @BeforeAll
    static void setup() {
        //Setup
    }

    @Test
    void givenValid_7DigitPhoneInput_withStartPageAndSize_thenReturnsCombinationsSuccessfully() throws JsonProcessingException, InvalidParameterException {
        String expectedJSON = "{\"totalRecords\":5120,\"data\":[\"1CD 4JM P\",\"12D 4JM P\",\"1AE 4JM P\",\"1BE 4JM P\",\"1CE 4JM P\",\"12E 4JM P\",\"1AF 4JM P\",\"1BF 4JM P\",\"1CF 4JM P\",\"12F 4JM P\"]}";
        Result expectedResult = mapper.readValue(expectedJSON, Result.class);
        Result actualResult = mainService.getPhoneCombinations("123 456 7", 5, 10);
        String x = mapper.writeValueAsString(actualResult);
        System.out.println("%%%%%%%%%");
        System.out.println(x);
        System.out.println("%%%%%%%%%");
        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(actualResult.getData()).contains("1CD 4JM P", "1CE 4JM P", "12F 4JM P");
    }

    @Test
    void givenValid_10DigitPhoneInput_withStartPageAndSize_thenReturnsCombinationsSuccessfully() throws JsonProcessingException, InvalidParameterException {
        String expectedJSON = "{\"totalRecords\":102400,\"data\":[\"1AD GJM PTW0\",\"1BD GJM PTW0\",\"1CD GJM PTW0\",\"12D GJM PTW0\",\"1AE GJM PTW0\",\"1BE GJM PTW0\",\"1CE GJM PTW0\",\"12E GJM PTW0\",\"1AF GJM PTW0\",\"1BF GJM PTW0\"]}";
        Result expectedResult = mapper.readValue(expectedJSON, Result.class);
        Result actualResult = mainService.getPhoneCombinations("123 456 7890", 0, 10);
        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(actualResult.getData()).contains("1AD GJM PTW0", "1BF GJM PTW0");
    }

    @Test
    void givenInvalid_3DigitPhoneInput_thenThrowsInvalidParameterException() {
        Exception exception = assertThrows(InvalidParameterException.class, () -> {
            mainService.getPhoneCombinations("123", 0, 10);
        });
        String expectedMessage = "Invalid input";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenInvalidCharsInPhoneInput_thenThrowsInvalidParameterException() {
        Exception exception = assertThrows(InvalidParameterException.class, () -> {
            mainService.getPhoneCombinations("123 456 * 7", 1, -4);
        });
        String expectedMessage = "Invalid input";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenNullInputForPhone_thenThrowsNullPointerException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            mainService.getPhoneCombinations(null, 0, 10);
        });
        String expectedMessage = "PhoneNumber input cannot be null or empty";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenInvalid_PageInput_thenThrowsInvalidParameterException() {
        Exception exception = assertThrows(InvalidParameterException.class, () -> {
            mainService.getPhoneCombinations("1234567", -1, 10);
        });
        String expectedMessage = "Input cannot be negative";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenInvalid_PageSize_thenThrowsInvalidParameterException() {
        Exception exception = assertThrows(InvalidParameterException.class, () -> {
            mainService.getPhoneCombinations("1234567", 1, -4);
        });
        String expectedMessage = "Input cannot be negative";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
