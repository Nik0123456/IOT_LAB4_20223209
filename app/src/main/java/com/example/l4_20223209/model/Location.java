package com.example.l4_20223209.model;

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
    private double latitude;
    
    @SerializedName("lon")
    private double longitude;
    
    @SerializedName("url")
    private String url;

    // Constructor vacío para Gson
    public Location() {}

    // Constructor completo
    public Location(long id, String name, String region, String country, double latitude, double longitude, String url) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.url = url;
    }

    // Getters y Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getUrl() { 
        return url; 
    }
    
    public void setUrl(String url) { 
        this.url = url; 
    }

    // Alias methods for backward compatibility
    public double getLat() { 
        return latitude; 
    }
    
    public double getLon() { 
        return longitude; 
    }

    // Método toString para mostrar información de la ubicación
    @Override
    public String toString() {
        return name + ", " + region + ", " + country;
    }

    // Método para obtener nombre completo
    public String getFullName() {
        StringBuilder fullName = new StringBuilder(name);
        if (region != null && !region.isEmpty()) {
            fullName.append(", ").append(region);
        }
        if (country != null && !country.isEmpty()) {
            fullName.append(", ").append(country);
        }
        return fullName.toString();
    }

    public String getDisplayName() {
        return name + ", " + region + ", " + country;
    }
}
