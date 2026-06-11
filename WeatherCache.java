package com.hello.main.Cache;
import com.hello.main.model.WeatherData;
import org.springframework.stereotype.Service;
import java.util.HashMap;
@Service
public class WeatherCache {
   private final HashMap<String, WeatherData> cache = new HashMap<>();
   private static final long EXPIRY_MS = 12 * 60 * 60 * 1000;
   public void put(String city,WeatherData data) {
      data.setCacheAt(System.currentTimeMillis());
      cache.put(city.toLowerCase(), data);
   }
   public WeatherData get(String city) {
       WeatherData data = cache.get(city.toLowerCase());
       if (data == null) {
         return null;
       }
       long age =  System.currentTimeMillis() - data.getCacheAt();
       if (age > EXPIRY_MS) {
           cache.remove(city.toLowerCase());
           return null;
       }
       return data;
   }
}
