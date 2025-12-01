package ru.mephi.malskiy.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ru.mephi.malskiy.dto.WeatherResDto;
import ru.mephi.malskiy.enums.Coordinates;
import ru.mephi.malskiy.service.WeatherService;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
@Import(WeatherControllerTest.MockConfig.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WeatherService weatherService;

    @Test
    void getWeatherReturnsDtoForValidRequest() throws Exception {
        WeatherResDto dto = new WeatherResDto("{\"fact\":{\"temp\":7}}", 7, 5.5);
        Mockito.when(weatherService.getWeather(eq(Coordinates.MSС), eq(3))).thenReturn(dto);

        ResultActions result = mockMvc.perform(
            get("/weather")
                .param("cityCode", "MSC")
                .param("days", "3")
                .accept(MediaType.APPLICATION_JSON)
        );

        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.currentTemp").value(7))
            .andExpect(jsonPath("$.awgTemp").value(5.5))
            .andExpect(jsonPath("$.json.fact.temp").value(7));
    }

    @Test
    void returnsBadRequestForUnknownCityCode() throws Exception {
        mockMvc.perform(
                get("/weather")
                    .param("cityCode", "UNKNOWN")
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    void returnsBadRequestForInvalidDays() throws Exception {
        mockMvc.perform(
                get("/weather")
                    .param("cityCode", "MSC")
                    .param("days", "0")
            )
            .andExpect(status().isBadRequest());

        mockMvc.perform(
                get("/weather")
                    .param("cityCode", "MSC")
                    .param("days", "12")
            )
            .andExpect(status().isBadRequest());
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        WeatherService weatherService() {
            return Mockito.mock(WeatherService.class);
        }
    }
}
