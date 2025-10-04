package com.example.l4_20223209.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Forecast {
    @SerializedName("forecastday")
    private List<ForecastDay> forecastDays;

    // Constructor vacío
    public Forecast() {}

    // Constructor completo
    public Forecast(List<ForecastDay> forecastDays) {
        this.forecastDays = forecastDays;
    }

    // Getters y Setters
    public List<ForecastDay> getForecastDays() {
        return forecastDays;
    }

    public void setForecastDays(List<ForecastDay> forecastDays) {
        this.forecastDays = forecastDays;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "forecastDays=" + forecastDays +
                '}';
    }
}