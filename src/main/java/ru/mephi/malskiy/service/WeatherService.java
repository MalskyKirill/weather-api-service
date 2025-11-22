package ru.mephi.malskiy.service;

import com.google.gson.*;
import org.springframework.stereotype.Service;
import ru.mephi.malskiy.dto.WeatherResDto;
import ru.mephi.malskiy.enums.Coordinates;
import ru.mephi.malskiy.util.ApiConstants;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {
    private HttpClient httpClient;
    private Gson gson;
    private ApiConstants apiConstants;

    public WeatherService(ApiConstants apiConstants, Gson gson) {
        this.apiConstants = apiConstants;
        this.gson = gson;
        this.httpClient = HttpClient.newHttpClient();
    }

    public WeatherResDto getWeather(Coordinates coordinates, int days) throws IOException, InterruptedException {
        JsonObject rawJson = getWeatherJsonFromApi(coordinates, days);
        return buildDtoFromJson(rawJson, days);
    }

    private WeatherResDto buildDtoFromJson(JsonObject rawJson, int days) {
        int currentTemp = extractCurrentTemp(rawJson);
        double avgTemp = extractAvgTemp(rawJson, days);
        String prettyJson = gson.toJson(rawJson);


        WeatherResDto weatherResDto = new WeatherResDto(prettyJson, currentTemp, avgTemp);
        System.out.println(weatherResDto);
        return weatherResDto;
    }

    private double extractAvgTemp(JsonObject rawJson, int days) {
        JsonArray forecasts = rawJson.getAsJsonArray("forecasts");
        int sum = 0;
        for (JsonElement el : forecasts) {
            JsonObject forecast = el.getAsJsonObject();
            JsonObject parts = forecast.getAsJsonObject("parts");
            JsonObject day = parts.getAsJsonObject("day");
            int tempAvg = day.get("temp_avg").getAsInt();
            sum += tempAvg;
        }

        double avg = (double) sum / days;
        return avg;
    }

    private int extractCurrentTemp(JsonObject rawJson) {
        JsonObject fact = rawJson.get("fact").getAsJsonObject();
        return fact.get("temp").getAsInt();
    }

    private JsonObject getWeatherJsonFromApi(Coordinates coordinates, int days) throws IOException, InterruptedException {
        String url = apiConstants.buildUrl(coordinates, days);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("X-Yandex-API-Key", apiConstants.getApiKey())
            .GET()
            .build();

        HttpResponse<String> res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() >= 400) {
            throw new IOException("Error from weather API, status: " + res.statusCode());
        }

        JsonObject jsonObject = JsonParser.parseString(res.body()).getAsJsonObject();

        return jsonObject;
    }
}
