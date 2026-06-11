package com.hello.main.controller;
import com.hello.main.Service.WeatherService;
import com.hello.main.model.WeatherData;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @GetMapping
    public WeatherData getWeatherData(@RequestParam String city) throws Exception {
        return  weatherService.getWeatherData(city);
    }
}
