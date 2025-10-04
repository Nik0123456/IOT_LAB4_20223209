package com.example.l4_20223209.model;

import com.google.gson.annotations.SerializedName;

public class WeatherForecast {
    @SerializedName("date")
    private String date;
    
    @SerializedName("date_epoch")
    private long dateEpoch;
    
    @SerializedName("day")
    private DayWeather day;

    // Constructor vacío
    public WeatherForecast() {}

    // Getters y Setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public long getDateEpoch() { return dateEpoch; }
    public void setDateEpoch(long dateEpoch) { this.dateEpoch = dateEpoch; }

    public DayWeather getDay() { return day; }
    public void setDay(DayWeather day) { this.day = day; }

    // Clase interna para datos del día
    public static class DayWeather {
        @SerializedName("maxtemp_c")
        private double maxTempC;
        
        @SerializedName("mintemp_c")
        private double minTempC;
        
        @SerializedName("avgtemp_c")
        private double avgTempC;
        
        @SerializedName("maxwind_kph")
        private double maxWindKph;
        
        @SerializedName("avghumidity")
        private double avgHumidity;
        
        @SerializedName("daily_chance_of_rain")
        private int dailyChanceOfRain;
        
        @SerializedName("condition")
        private WeatherCondition condition;
        
        @SerializedName("uv")
        private double uv;

        // Constructor vacío
        public DayWeather() {}

        // Getters y Setters
        public double getMaxTempC() { return maxTempC; }
        public void setMaxTempC(double maxTempC) { this.maxTempC = maxTempC; }

        public double getMinTempC() { return minTempC; }
        public void setMinTempC(double minTempC) { this.minTempC = minTempC; }
        
        public double getAvgTempC() { return avgTempC; }
        public void setAvgTempC(double avgTempC) { this.avgTempC = avgTempC; }
        
        public double getMaxWindKph() { return maxWindKph; }
        public void setMaxWindKph(double maxWindKph) { this.maxWindKph = maxWindKph; }
        
        public double getAvgHumidity() { return avgHumidity; }
        public void setAvgHumidity(double avgHumidity) { this.avgHumidity = avgHumidity; }
        
        public int getDailyChanceOfRain() { return dailyChanceOfRain; }
        public void setDailyChanceOfRain(int dailyChanceOfRain) { this.dailyChanceOfRain = dailyChanceOfRain; }
        
        public double getUv() { return uv; }
        public void setUv(double uv) { this.uv = uv; }

        public WeatherCondition getCondition() { return condition; }
        public void setCondition(WeatherCondition condition) { this.condition = condition; }
    }

    // Clase para condición del clima
    public static class WeatherCondition {
        @SerializedName("text")
        private String text;
        
        @SerializedName("icon")
        private String icon;

        // Constructor vacío
        public WeatherCondition() {}

        // Getters y Setters
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }

        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }
}
