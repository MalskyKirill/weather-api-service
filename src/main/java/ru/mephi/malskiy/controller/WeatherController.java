package ru.mephi.malskiy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.mephi.malskiy.dto.WeatherResDto;
import ru.mephi.malskiy.enums.Coordinates;
import ru.mephi.malskiy.service.WeatherService;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public WeatherResDto getWeather(@RequestParam String cityCode,
                                    @RequestParam(defaultValue = "1") int days) throws IOException, InterruptedException {

        Coordinates coordinates = Optional.ofNullable(Coordinates.findByCode(cityCode)).orElseThrow(() -> new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "неизвестный код города     " + cityCode
        ));

        if (days < 1 || days > 11) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "кол-во дней должно быть от 1 до 11"
            );
        }

        return weatherService.getWeather(coordinates, days);
    }
}
