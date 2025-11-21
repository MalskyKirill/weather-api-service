package ru.mephi.malskiy.dto;

public class WeatherResDto {
    private String json;
    private Double currentTemp;
    private Double awgTemp;

    public WeatherResDto(String json, Double currentTemp, Double awgTemp) {
        this.json = json;
        this.currentTemp = currentTemp;
        this.awgTemp = awgTemp;
    }
}
