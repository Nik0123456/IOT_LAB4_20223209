package com.example.l4_20223209.model;

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

    // Getters
    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

    public int getCode() {
        return code;
    }

    // Setters
    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return text;
    }
}
