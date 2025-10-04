package com.example.l4_20223209.ui.forecast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.l4_20223209.api.ApiClient;
import com.example.l4_20223209.api.WeatherApiService;
import com.example.l4_20223209.models.ForecastDay;
import com.example.l4_20223209.models.WeatherResponse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastViewModel extends ViewModel {

    private MutableLiveData<List<ForecastDay>> forecastDays = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private WeatherApiService apiService;
    private ExecutorService executor;

    public ForecastViewModel() {
        apiService = ApiClient.getWeatherApiService();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ForecastDay>> getForecastDays() {
        return forecastDays;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void getForecast(String location, int days) {
        executor.execute(() -> {
            Call<WeatherResponse> call = apiService.getForecast(
                    WeatherApiService.API_KEY, 
                    location,
                    days
            );

            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        WeatherResponse weatherResponse = response.body();
                        if (weatherResponse.getForecast() != null && 
                            weatherResponse.getForecast().getForecastDays() != null) {
                            forecastDays.postValue(weatherResponse.getForecast().getForecastDays());
                            error.postValue(null);
                        } else {
                            error.postValue("No se encontraron datos de pronóstico");
                            forecastDays.postValue(null);
                        }
                    } else {
                        error.postValue("Error al obtener pronósticos: " + response.code());
                        forecastDays.postValue(null);
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    error.postValue("Error de conexión: " + t.getMessage());
                    forecastDays.postValue(null);
                }
            });
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }
}