package com.example.demo.unit.controllers;

import com.example.demo.controllers.MainController;
import com.example.demo.models.Result;
import com.example.demo.services.MainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MainControllerUnitTest {
    @Mock
    private MainService mainService;

    private MainController mainController;

    @BeforeEach
    public void setup() {
        mainController = new MainController(mainService);
    }

    @Test
    void givenValidDigitPhoneInputOnly_thenReturnsCombinationsSuccessfully() throws Exception {
        String aPhone = "123 456 7";
        Result mockResult = Result.builder().totalRecords(100).data(new ArrayList<>()).build();
        when(mainService.getPhoneCombinations(any(), anyInt(), anyInt())).thenReturn(mockResult);
        Result r = mainController.getCombinations(aPhone, 0, 10);

        verify(mainService).getPhoneCombinations(aPhone, 0, 10);
        assertThat(r.getTotalRecords() == mockResult.getTotalRecords());
        assertThat(r.getData().isEmpty());
    }


}
