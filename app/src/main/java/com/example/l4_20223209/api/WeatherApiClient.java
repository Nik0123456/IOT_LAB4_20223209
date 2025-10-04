package com.example.l4_20223209.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiClient {
    
    public static final String BASE_URL = "https://api.weatherapi.com/v1/";
    public static final String API_KEY = "5586a0acd5a345e0b4361158250210";
    
    private static Retrofit retrofit = null;
    private static WeatherApiService apiService = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static WeatherApiService getApiService() {
        if (apiService == null) {
            apiService = getClient().create(WeatherApiService.class);
        }
        return apiService;
    }
}
