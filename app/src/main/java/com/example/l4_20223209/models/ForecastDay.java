package com.example.l4_20223209.models;

import com.google.gson.annotations.SerializedName;

public class ForecastDay {
    @SerializedName("date")
    private String date;
    
    @SerializedName("date_epoch")
    private long dateEpoch;
    
    @SerializedName("day")
    private DayForecast day;

    // Constructor vacío
    public ForecastDay() {}

    // Constructor completo
    public ForecastDay(String date, long dateEpoch, DayForecast day) {
        this.date = date;
        this.dateEpoch = dateEpoch;
        this.day = day;
    }

    // Getters y Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getDateEpoch() {
        return dateEpoch;
    }

    public void setDateEpoch(long dateEpoch) {
        this.dateEpoch = dateEpoch;
    }

    public DayForecast getDay() {
        return day;
    }

    public void setDay(DayForecast day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "ForecastDay{" +
                "date='" + date + '\'' +
                ", dateEpoch=" + dateEpoch +
                ", day=" + day +
                '}';
    }
}