package ru.mephi.malskiy.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class WeatherResDto {
    @JsonRawValue
    private String json;
    private int currentTemp;
    private double awgTemp;

    public WeatherResDto(String json, int currentTemp, double awgTemp) {
        this.json = json;
        this.currentTemp = currentTemp;
        this.awgTemp = awgTemp;
    }

    public WeatherResDto() {}

    @JsonRawValue
    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public double getAwgTemp() {
        return awgTemp;
    }

    public void setAwgTemp(double awgTemp) {
        this.awgTemp = awgTemp;
    }

    @Override
    public String toString() {
        return "WeatherResDto{" +
            "json='" + json + '\'' +
            ", currentTemp=" + currentTemp +
            ", awgTemp=" + awgTemp +
            '}';
    }
}
