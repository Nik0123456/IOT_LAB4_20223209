package com.example.l4_20223209.model;

import com.google.gson.annotations.SerializedName;

public class HourlyWeather {
    @SerializedName("time_epoch")
    private long timeEpoch;
    
    @SerializedName("time")
    private String time;
    
    @SerializedName("temp_c")
    private double tempC;
    
    @SerializedName("is_day")
    private int isDay;
    
    @SerializedName("condition")
    private WeatherCondition condition;
    
    @SerializedName("wind_kph")
    private double windKph;
    
    @SerializedName("wind_degree")
    private int windDegree;
    
    @SerializedName("wind_dir")
    private String windDir;
    
    @SerializedName("pressure_mb")
    private double pressureMb;
    
    @SerializedName("precip_mm")
    private double precipMm;
    
    @SerializedName("humidity")
    private int humidity;
    
    @SerializedName("cloud")
    private int cloud;
    
    @SerializedName("feelslike_c")
    private double feelsLikeC;
    
    @SerializedName("windchill_c")
    private double windchillC;
    
    @SerializedName("heatindex_c")
    private double heatIndexC;
    
    @SerializedName("dewpoint_c")
    private double dewPointC;
    
    @SerializedName("will_it_rain")
    private int willItRain;
    
    @SerializedName("chance_of_rain")
    private int chanceOfRain;
    
    @SerializedName("will_it_snow")
    private int willItSnow;
    
    @SerializedName("chance_of_snow")
    private int chanceOfSnow;
    
    @SerializedName("vis_km")
    private double visKm;
    
    @SerializedName("gust_kph")
    private double gustKph;
    
    @SerializedName("uv")
    private double uv;

    // Constructor vacío
    public HourlyWeather() {}

    // Getters
    public long getTimeEpoch() {
        return timeEpoch;
    }

    public String getTime() {
        return time;
    }

    public double getTempC() {
        return tempC;
    }

    public int getIsDay() {
        return isDay;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public double getWindKph() {
        return windKph;
    }

    public int getWindDegree() {
        return windDegree;
    }

    public String getWindDir() {
        return windDir;
    }

    public double getPressureMb() {
        return pressureMb;
    }

    public double getPrecipMm() {
        return precipMm;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public double getFeelsLikeC() {
        return feelsLikeC;
    }

    public double getWindchillC() {
        return windchillC;
    }

    public double getHeatIndexC() {
        return heatIndexC;
    }

    public double getDewPointC() {
        return dewPointC;
    }

    public int getWillItRain() {
        return willItRain;
    }

    public int getChanceOfRain() {
        return chanceOfRain;
    }

    public int getWillItSnow() {
        return willItSnow;
    }

    public int getChanceOfSnow() {
        return chanceOfSnow;
    }

    public double getVisKm() {
        return visKm;
    }

    public double getGustKph() {
        return gustKph;
    }

    public double getUv() {
        return uv;
    }

    // Setters
    public void setTimeEpoch(long timeEpoch) {
        this.timeEpoch = timeEpoch;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTempC(double tempC) {
        this.tempC = tempC;
    }

    public void setIsDay(int isDay) {
        this.isDay = isDay;
    }

    public void setCondition(WeatherCondition condition) {
        this.condition = condition;
    }

    public void setWindKph(double windKph) {
        this.windKph = windKph;
    }

    public void setWindDegree(int windDegree) {
        this.windDegree = windDegree;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public void setPressureMb(double pressureMb) {
        this.pressureMb = pressureMb;
    }

    public void setPrecipMm(double precipMm) {
        this.precipMm = precipMm;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }

    public void setFeelsLikeC(double feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public void setWindchillC(double windchillC) {
        this.windchillC = windchillC;
    }

    public void setHeatIndexC(double heatIndexC) {
        this.heatIndexC = heatIndexC;
    }

    public void setDewPointC(double dewPointC) {
        this.dewPointC = dewPointC;
    }

    public void setWillItRain(int willItRain) {
        this.willItRain = willItRain;
    }

    public void setChanceOfRain(int chanceOfRain) {
        this.chanceOfRain = chanceOfRain;
    }

    public void setWillItSnow(int willItSnow) {
        this.willItSnow = willItSnow;
    }

    public void setChanceOfSnow(int chanceOfSnow) {
        this.chanceOfSnow = chanceOfSnow;
    }

    public void setVisKm(double visKm) {
        this.visKm = visKm;
    }

    public void setGustKph(double gustKph) {
        this.gustKph = gustKph;
    }

    public void setUv(double uv) {
        this.uv = uv;
    }

    // Método auxiliar para obtener la hora en formato simple
    public String getSimpleTime() {
        if (time != null && time.length() >= 16) {
            return time.substring(11, 16); // Extrae HH:MM de "YYYY-MM-DD HH:MM"
        }
        return time;
    }

    // Método auxiliar para verificar si es de día
    public boolean isDayTime() {
        return isDay == 1;
    }
}
