package ru.mephi.malskiy.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import ru.mephi.malskiy.enums.Coordinates;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class ApiConstants {
    public final String apiKey;
    public final String baseUrl;

    public ApiConstants(@Value("${yandex.weather.api.key}") String apiKey,
                        @Value("${yandex.weather.base.url}") String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String buildUrl(Coordinates coordinates, int days) {
        return baseUrl + "?" +
            coordinates.getLocation() +
            "&limit=" + days;
    }
}
