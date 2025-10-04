package com.example.l4_20223209.models;

import com.google.gson.annotations.SerializedName;

public class HourlyForecast {
    @SerializedName("time_epoch")
    private long timeEpoch;
    
    @SerializedName("time")
    private String time;
    
    @SerializedName("temp_c")
    private double tempC;
    
    @SerializedName("temp_f")
    private double tempF;
    
    @SerializedName("condition")
    private WeatherCondition condition;
    
    @SerializedName("humidity")
    private int humidity;
    
    @SerializedName("will_it_rain")
    private int willItRain;
    
    @SerializedName("chance_of_rain")
    private int chanceOfRain;

    // Constructor vacío
    public HourlyForecast() {}

    // Getters y Setters
    public long getTimeEpoch() {
        return timeEpoch;
    }

    public void setTimeEpoch(long timeEpoch) {
        this.timeEpoch = timeEpoch;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTempC() {
        return tempC;
    }

    public void setTempC(double tempC) {
        this.tempC = tempC;
    }

    public double getTempF() {
        return tempF;
    }

    public void setTempF(double tempF) {
        this.tempF = tempF;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public void setCondition(WeatherCondition condition) {
        this.condition = condition;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWillItRain() {
        return willItRain;
    }

    public void setWillItRain(int willItRain) {
        this.willItRain = willItRain;
    }

    public int getChanceOfRain() {
        return chanceOfRain;
    }

    public void setChanceOfRain(int chanceOfRain) {
        this.chanceOfRain = chanceOfRain;
    }

    @Override
    public String toString() {
        return "HourlyForecast{" +
                "timeEpoch=" + timeEpoch +
                ", time='" + time + '\'' +
                ", tempC=" + tempC +
                ", tempF=" + tempF +
                ", condition=" + condition +
                ", humidity=" + humidity +
                ", willItRain=" + willItRain +
                ", chanceOfRain=" + chanceOfRain +
                '}';
    }
}