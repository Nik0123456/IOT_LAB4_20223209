package com.example.l4_20223209.models;

import com.google.gson.annotations.SerializedName;

public class WeatherCondition {
    @SerializedName("text")
    private String text;
    
    @SerializedName("icon")
    private String icon;
    
    @SerializedName("code")
    private int code;

    // Constructor vacío
    public WeatherCondition() {}

    // Constructor completo
    public WeatherCondition(String text, String icon, int code) {
        this.text = text;
        this.icon = icon;
        this.code = code;
    }

    // Getters y Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "WeatherCondition{" +
                "text='" + text + '\'' +
                ", icon='" + icon + '\'' +
                ", code=" + code +
                '}';
    }
}