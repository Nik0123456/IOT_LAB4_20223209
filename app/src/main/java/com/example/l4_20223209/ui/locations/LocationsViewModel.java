package com.example.l4_20223209.ui.locations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.l4_20223209.api.ApiClient;
import com.example.l4_20223209.api.WeatherApiService;
import com.example.l4_20223209.models.Location;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationsViewModel extends ViewModel {

    private MutableLiveData<List<Location>> locations = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private WeatherApiService apiService;
    private ExecutorService executor;

    public LocationsViewModel() {
        apiService = ApiClient.getWeatherApiService();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Location>> getLocations() {
        return locations;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void searchLocations(String query) {
        executor.execute(() -> {
            Call<List<Location>> call = apiService.searchLocations(
                    WeatherApiService.API_KEY, 
                    query
            );

            call.enqueue(new Callback<List<Location>>() {
                @Override
                public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        locations.postValue(response.body());
                        error.postValue(null);
                    } else {
                        error.postValue("Error al buscar ubicaciones: " + response.code());
                        locations.postValue(null);
                    }
                }

                @Override
                public void onFailure(Call<List<Location>> call, Throwable t) {
                    error.postValue("Error de conexión: " + t.getMessage());
                    locations.postValue(null);
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