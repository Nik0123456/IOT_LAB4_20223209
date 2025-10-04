package com.example.l4_20223209.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {
    @SerializedName("location")
    private Location location;
    
    @SerializedName("forecast")
    private Forecast forecast;

    // Constructor vacío
    public WeatherResponse() {}

    // Getters y Setters
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public Forecast getForecast() { return forecast; }
    public void setForecast(Forecast forecast) { this.forecast = forecast; }

    // Clase interna para forecast
    public static class Forecast {
        @SerializedName("forecastday")
        private List<WeatherForecast> forecastDays;

        public Forecast() {}

        public List<WeatherForecast> getForecastDays() { return forecastDays; }
        public void setForecastDays(List<WeatherForecast> forecastDays) { this.forecastDays = forecastDays; }
    }
}
