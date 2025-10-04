package com.example.l4_20223209.models;

import com.google.gson.annotations.SerializedName;

public class DayForecast {
    @SerializedName("maxtemp_c")
    private double maxTempC;
    
    @SerializedName("maxtemp_f")
    private double maxTempF;
    
    @SerializedName("mintemp_c")
    private double minTempC;
    
    @SerializedName("mintemp_f")
    private double minTempF;
    
    @SerializedName("avgtemp_c")
    private double avgTempC;
    
    @SerializedName("avgtemp_f")
    private double avgTempF;
    
    @SerializedName("condition")
    private WeatherCondition condition;
    
    @SerializedName("avghumidity")
    private int avgHumidity;

    // Constructor vacío
    public DayForecast() {}

    // Getters y Setters
    public double getMaxTempC() {
        return maxTempC;
    }

    public void setMaxTempC(double maxTempC) {
        this.maxTempC = maxTempC;
    }

    public double getMaxTempF() {
        return maxTempF;
    }

    public void setMaxTempF(double maxTempF) {
        this.maxTempF = maxTempF;
    }

    public double getMinTempC() {
        return minTempC;
    }

    public void setMinTempC(double minTempC) {
        this.minTempC = minTempC;
    }

    public double getMinTempF() {
        return minTempF;
    }

    public void setMinTempF(double minTempF) {
        this.minTempF = minTempF;
    }

    public double getAvgTempC() {
        return avgTempC;
    }

    public void setAvgTempC(double avgTempC) {
        this.avgTempC = avgTempC;
    }

    public double getAvgTempF() {
        return avgTempF;
    }

    public void setAvgTempF(double avgTempF) {
        this.avgTempF = avgTempF;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public void setCondition(WeatherCondition condition) {
        this.condition = condition;
    }

    public int getAvgHumidity() {
        return avgHumidity;
    }

    public void setAvgHumidity(int avgHumidity) {
        this.avgHumidity = avgHumidity;
    }

    @Override
    public String toString() {
        return "DayForecast{" +
                "maxTempC=" + maxTempC +
                ", maxTempF=" + maxTempF +
                ", minTempC=" + minTempC +
                ", minTempF=" + minTempF +
                ", avgTempC=" + avgTempC +
                ", avgTempF=" + avgTempF +
                ", condition=" + condition +
                ", avgHumidity=" + avgHumidity +
                '}';
    }
}