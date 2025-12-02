package ru.mephi.malskiy.service;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.mephi.malskiy.dto.WeatherResDto;
import ru.mephi.malskiy.enums.Coordinates;
import ru.mephi.malskiy.util.ApiConstants;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    private final Gson gson = new Gson();
    private final ApiConstants apiConstants = new ApiConstants("test-api-key", "https://example.com/weather");

    @Test
    void getWeatherReturnsParsedDto() throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        WeatherService weatherService = new WeatherService(apiConstants, gson);

        HttpClient httpClient = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        String body = """
            {
              \"fact\": {\"temp\": 6},
              \"forecasts\": [
                { \"parts\": { \"day\": { \"temp_avg\": 4 } } },
                { \"parts\": { \"day\": { \"temp_avg\": 6 } } }
              ]
            }
            """;

        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.when(response.body()).thenReturn(body);
        Mockito.when(httpClient.send(Mockito.any(HttpRequest.class), Mockito.<HttpResponse.BodyHandler<String>>any()))
            .thenReturn(response);

        Field httpClientField = WeatherService.class.getDeclaredField("httpClient");
        httpClientField.setAccessible(true);
        httpClientField.set(weatherService, httpClient);

        Coordinates coordinates = Coordinates.findByCode("MSC");
        assertNotNull(coordinates);

        WeatherResDto result = weatherService.getWeather(coordinates, 2);

        assertEquals(5, result.getCurrentTemp());
        assertEquals(5.0, result.getAwgTemp());
    }
}
