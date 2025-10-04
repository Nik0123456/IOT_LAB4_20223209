package com.example.l4_20223209.ui.forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.l4_20223209.api.WeatherApiService;
import com.example.l4_20223209.databinding.FragmentForecastBinding;
import com.example.l4_20223209.models.ForecastDay;
import com.example.l4_20223209.models.WeatherResponse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastFragment extends Fragment {

    private FragmentForecastBinding binding;
    private ForecastAdapter adapter;
    private WeatherApiService apiService;
    private ExecutorService executorService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initializeComponents();
        setupRecyclerView();
        setupListeners();
        
        // Check if we received arguments from navigation
        Bundle args = getArguments();
        if (args != null) {
            long locationId = args.getLong("locationId", 0);
            String locationName = args.getString("locationName", "");
            
            if (locationId != 0) {
                binding.etLocationId.setText(String.valueOf(locationId));
                binding.etLocationName.setText(locationName);
                binding.etDias.setText("14");
            }
        }
    }

    private void initializeComponents() {
        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(WeatherApiService.class);
        executorService = Executors.newFixedThreadPool(3);
        
        // Configurar adapter
        adapter = new ForecastAdapter();
    }

    private void setupRecyclerView() {
        binding.recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewForecast.setAdapter(adapter);
    }

    private void setupListeners() {
        // Botón de búsqueda de pronóstico
        binding.btnBuscarPronostico.setOnClickListener(v -> {
            String locationId = binding.etLocationId.getText().toString().trim();
            String dias = binding.etDias.getText().toString().trim();
            
            if (!locationId.isEmpty() && !dias.isEmpty()) {
                int days = Integer.parseInt(dias);
                if (days > 0 && days <= 14) {
                    searchForecast(locationId, days);
                } else {
                    Toast.makeText(getContext(), 
                        "El número de días debe estar entre 1 y 14",
                        Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), 
                    "Por favor complete todos los campos",
                    Toast.LENGTH_SHORT).show();
            }
        });

        // FAB de refrescar
        binding.fabRefresh.setOnClickListener(v -> {
            String locationId = binding.etLocationId.getText().toString().trim();
            String dias = binding.etDias.getText().toString().trim();
            
            if (!locationId.isEmpty() && !dias.isEmpty()) {
                searchForecast(locationId, Integer.parseInt(dias));
            } else {
                Toast.makeText(getContext(), 
                    "Configure primero una ubicación",
                    Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchForecast(String locationId, int days) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewForecast.setVisibility(View.GONE);
        binding.cardNoResults.setVisibility(View.GONE);

        // Llamada a la API para obtener pronóstico
        Call<WeatherResponse> call = apiService.getForecast(
            "db77e6a0b7184c3e9b2170648241215", locationId, days);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse forecast = response.body();
                    List<ForecastDay> dayForecasts = forecast.getForecast().getForecastDays();
                    
                    if (!dayForecasts.isEmpty()) {
                        adapter.updateForecasts(dayForecasts);
                        binding.recyclerViewForecast.setVisibility(View.VISIBLE);
                        
                        // Actualizar el nombre de la ubicación
                        binding.etLocationName.setText(forecast.getLocation().getName());
                        
                        Toast.makeText(getContext(), 
                            "Pronóstico de " + dayForecasts.size() + " días cargado",
                            Toast.LENGTH_SHORT).show();
                    } else {
                        showNoResults();
                    }
                } else {
                    showError("Error en la consulta. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                showError("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void showNoResults() {
        binding.cardNoResults.setVisibility(View.VISIBLE);
        binding.recyclerViewForecast.setVisibility(View.GONE);
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        showNoResults();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}