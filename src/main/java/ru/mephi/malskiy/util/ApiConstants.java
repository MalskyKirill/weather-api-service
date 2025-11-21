package ru.mephi.malskiy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiConstants {
    public static final String YANDEX_API_WEATHER_KEY;
    public static final String BASE_URL = "https://api.weather.yandex.ru/v2/forecast";

    static {
        Properties props = new Properties();
        String key = null;

        try (InputStream input = ApiConstants.class.getClassLoader().getResourceAsStream("application.properties")){
            props.load(input);
            key = props.getProperty("yandex.weather.api.key");

            if (key == null) {
                throw new IllegalStateException("yandex.weather.api.key отсутствует");
            }
        } catch (IOException e) {
            throw new RuntimeException("application.properties не найден", e);
        }

        YANDEX_API_WEATHER_KEY = key;
    }
}
