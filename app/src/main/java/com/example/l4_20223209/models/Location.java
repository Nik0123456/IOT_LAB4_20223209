package com.example.l4_20223209.models;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("id")
    private long id;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("region")
    private String region;
    
    @SerializedName("country")
    private String country;
    
    @SerializedName("lat")
    private double lat;
    
    @SerializedName("lon")
    private double lon;
    
    @SerializedName("url")
    private String url;

    // Constructor vacío
    public Location() {}

    // Constructor completo
    public Location(long id, String name, String region, String country, double lat, double lon, String url) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.url = url;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", url='" + url + '\'' +
                '}';
    }
}