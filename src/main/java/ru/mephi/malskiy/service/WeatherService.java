package ru.mephi.malskiy.service;

import com.google.gson.*;
import ru.mephi.malskiy.util.ApiConstants;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {
    HttpClient httpClient;
    Gson gson;

    public WeatherService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void getWeather(String coordinates, int lim) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(ApiConstants.BASE_URL + "?" + coordinates + "&limit=" + lim))
            .header("X-Yandex-Weather-Key", ApiConstants.YANDEX_API_WEATHER_KEY)
            .build();

        try {
            // получили json от сервиса
            HttpResponse<String> response =  httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            // отфарматировали json
            printPrettyJSON(jsonObject);

            // взяли из json текущую температуру
            printCurrentTemp(jsonObject);

            // подсчет средней температуры за кол-во (lim) суток
            printAvgTemp(jsonObject, lim);

        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при обращении к API");
            System.out.println(e.getMessage());
        }
    }

    private void printPrettyJSON(JsonObject jsonObject) {
        String prettyJson = gson.toJson(jsonObject);
        System.out.println("Весь ответ от сервера: ");
        System.out.println(prettyJson);
    }

    private void printCurrentTemp(JsonObject jsonObject) {
        JsonObject fact = jsonObject.get("fact").getAsJsonObject();
        int temp = fact.get("temp").getAsInt();
        System.out.println("Текущая температура: " + temp + "°C");
    }

    private void printAvgTemp(JsonObject jsonObject, int lim) {
        JsonArray forecasts = jsonObject.getAsJsonArray("forecasts");
        int sum = 0;
        for (JsonElement el : forecasts) {
            JsonObject forecast = el.getAsJsonObject();
            JsonObject parts = forecast.getAsJsonObject("parts");
            JsonObject day = parts.getAsJsonObject("day");
            int tempAvg = day.get("temp_avg").getAsInt();
            sum += tempAvg;
        }

        double avg = (double) sum / lim;
        System.out.printf("Средняя температура за %d дней: %.1f°C%n", lim, avg);
        System.out.println();
    }
}
