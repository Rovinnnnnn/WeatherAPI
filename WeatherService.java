package com.hello.main.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hello.main.Cache.WeatherCache;
import com.hello.main.model.WeatherData;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class WeatherService {

    private final WeatherCache weatherCache;

    public WeatherService(WeatherCache weatherCache) {
        this.weatherCache = weatherCache;
    }

    public WeatherData getWeatherData(String city) throws Exception {
        
        WeatherData cached = weatherCache.get(city);
        if (cached != null) {
            System.out.println("Returning cached data: " + city);
            return cached;
        }


        String urlStr = BASE_URL + city + "?unitGroup=metric&key=" + API_KEY + "&contentType=json";
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonArray days = json.getAsJsonArray("days");
        JsonObject current = days.get(0).getAsJsonObject();

        WeatherData data = new WeatherData();
        data.setCity(city);
        data.setTemperature(current.get("temp").getAsDouble());
        data.setFeelsLike(current.get("feelslike").getAsDouble());
        data.setHumidity(current.get("humidity").getAsDouble());
        data.setDescription(current.get("description").getAsString());

        weatherCache.put(city, data);

        return data;
    }

    private static final String API_KEY = "5DSBXKMXDY9FKR9JWNRYMEVQT";
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
}