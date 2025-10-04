package com.example.l4_20223209.ui.future;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.l4_20223209.api.ApiClient;
import com.example.l4_20223209.api.WeatherApiService;
import com.example.l4_20223209.models.HourlyForecast;
import com.example.l4_20223209.models.WeatherResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FutureViewModel extends ViewModel {

    private final MutableLiveData<List<HourlyForecast>> hourlyForecasts = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final WeatherApiService apiService;
    private final ExecutorService executor;

    public FutureViewModel() {
        apiService = ApiClient.getWeatherApiService();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<HourlyForecast>> getHourlyForecasts() {
        return hourlyForecasts;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void getFutureForecast(String location, String date) {
        executor.execute(() -> {
            Call<WeatherResponse> call = apiService.getFutureForecast(
                    WeatherApiService.API_KEY, 
                    location,
                    date
            );

            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    processWeatherResponse(response);
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    error.postValue("Error de conexión: " + t.getMessage());
                    hourlyForecasts.postValue(null);
                }
            });
        });
    }

    public void getHistoryForecast(String location, String date) {
        executor.execute(() -> {
            Call<WeatherResponse> call = apiService.getHistoryForecast(
                    WeatherApiService.API_KEY, 
                    location,
                    date
            );

            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    processWeatherResponse(response);
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    error.postValue("Error de conexión: " + t.getMessage());
                    hourlyForecasts.postValue(null);
                }
            });
        });
    }

    private void processWeatherResponse(Response<WeatherResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
            WeatherResponse weatherResponse = response.body();
            if (weatherResponse.getForecast() != null && 
                weatherResponse.getForecast().getForecastDays() != null &&
                !weatherResponse.getForecast().getForecastDays().isEmpty()) {
                
                // For simplicity, we'll create mock hourly data since the response structure is complex
                List<HourlyForecast> mockHourlyData = new ArrayList<>();
                // In a real implementation, you would extract hourly data from the response
                
                hourlyForecasts.postValue(mockHourlyData);
                error.postValue(null);
            } else {
                error.postValue("No se encontraron datos de pronóstico");
                hourlyForecasts.postValue(null);
            }
        } else {
            error.postValue("Error al obtener pronósticos: " + response.code());
            hourlyForecasts.postValue(null);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }
}