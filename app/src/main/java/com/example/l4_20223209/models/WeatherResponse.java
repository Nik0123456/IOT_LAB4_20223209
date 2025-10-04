package com.example.l4_20223209.models;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("location")
    private Location location;
    
    @SerializedName("forecast")
    private Forecast forecast;

    // Constructor vacío
    public WeatherResponse() {}

    // Constructor completo
    public WeatherResponse(Location location, Forecast forecast) {
        this.location = location;
        this.forecast = forecast;
    }

    // Getters y Setters
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "location=" + location +
                ", forecast=" + forecast +
                '}';
    }
}