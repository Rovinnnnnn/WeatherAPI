package com.hello.main.model;
import lombok.Data;
@Data
public class WeatherData {
    private String city;
    private double temperature;
    private double feelsLike;
    private double humidity;
    private String description;
    private long CacheAt;
}
