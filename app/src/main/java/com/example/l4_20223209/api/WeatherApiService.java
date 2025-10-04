package com.example.l4_20223209.api;

import com.example.l4_20223209.model.Location;
import com.example.l4_20223209.model.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    
    String BASE_URL = "https://api.weatherapi.com/v1/";
    String API_KEY = "5586a0acd5a345e0b4361158250210";

    // Método GET 1: Buscar locaciones
    @GET("search.json")
    Call<List<Location>> searchLocations(
            @Query("key") String apiKey,
            @Query("q") String query
    );

    // Método GET 2: Pronósticos
    @GET("forecast.json")
    Call<WeatherResponse> getForecast(
            @Query("key") String apiKey,
            @Query("q") String locationId,
            @Query("days") int days
    );

    // Método GET 3: Pronósticos futuros
    @GET("future.json")
    Call<WeatherResponse> getFutureForecast(
            @Query("key") String apiKey,
            @Query("q") String locationId,
            @Query("dt") String date
    );

    // Método GET 4: Historial
    @GET("history.json")
    Call<WeatherResponse> getHistoryForecast(
            @Query("key") String apiKey,
            @Query("q") String locationId,
            @Query("dt") String date
    );
}
