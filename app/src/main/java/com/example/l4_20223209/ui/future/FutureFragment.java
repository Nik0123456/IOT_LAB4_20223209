package com.example.l4_20223209.ui.future;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.l4_20223209.api.WeatherApiService;
import com.example.l4_20223209.databinding.FragmentFutureBinding;
import com.example.l4_20223209.models.WeatherResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FutureFragment extends Fragment {

    private FragmentFutureBinding binding;
    private WeatherApiService apiService;
    private ExecutorService executorService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFutureBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initializeComponents();
        setupListeners();
        setDefaultDate();
    }

    private void initializeComponents() {
        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(WeatherApiService.class);
        executorService = Executors.newFixedThreadPool(3);
    }

    private void setupListeners() {
        // Botón de búsqueda histórica
        binding.btnBuscarHistorico.setOnClickListener(v -> {
            String locationId = binding.etLocationId.getText().toString().trim();
            String fecha = binding.etFecha.getText().toString().trim();
            
            if (!locationId.isEmpty() && !fecha.isEmpty()) {
                if (isValidDateFormat(fecha)) {
                    searchHistoricalWeather(locationId, fecha);
                } else {
                    Toast.makeText(getContext(), 
                        "Formato de fecha inválido. Use YYYY-MM-DD",
                        Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), 
                    "Por favor complete todos los campos",
                    Toast.LENGTH_SHORT).show();
            }
        });

        // Botón de búsqueda futura
        binding.btnBuscarFuturo.setOnClickListener(v -> {
            String locationId = binding.etLocationId.getText().toString().trim();
            String fecha = binding.etFecha.getText().toString().trim();
            
            if (!locationId.isEmpty() && !fecha.isEmpty()) {
                if (isValidDateFormat(fecha)) {
                    searchFutureWeather(locationId, fecha);
                } else {
                    Toast.makeText(getContext(), 
                        "Formato de fecha inválido. Use YYYY-MM-DD",
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
            String fecha = binding.etFecha.getText().toString().trim();
            
            if (!locationId.isEmpty() && !fecha.isEmpty()) {
                // Por defecto, buscar datos futuros
                searchFutureWeather(locationId, fecha);
            } else {
                Toast.makeText(getContext(), 
                    "Configure primero una ubicación y fecha",
                    Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDefaultDate() {
        // Establecer fecha actual + 1 día por defecto
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tomorrow = sdf.format(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        binding.etFecha.setText(tomorrow);
    }

    private boolean isValidDateFormat(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void searchHistoricalWeather(String locationId, String date) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.cardWeatherInfo.setVisibility(View.GONE);
        binding.cardNoResults.setVisibility(View.GONE);

        // Llamada a la API para datos históricos
        Call<WeatherResponse> call = apiService.getHistoryForecast(
            "5586a0acd5a345e0b4361158250210", locationId, date);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    displayWeatherData(response.body(), "📜 Datos Históricos - " + date);
                    
                    Toast.makeText(getContext(), 
                        "Datos históricos cargados correctamente",
                        Toast.LENGTH_SHORT).show();
                } else {
                    showError("Error en la consulta histórica. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                showError("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void searchFutureWeather(String locationId, String date) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.cardWeatherInfo.setVisibility(View.GONE);
        binding.cardNoResults.setVisibility(View.GONE);

        // Llamada a la API para datos futuros
        Call<WeatherResponse> call = apiService.getFutureForecast(
            "5586a0acd5a345e0b4361158250210", locationId, date);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    displayWeatherData(response.body(), "🔮 Pronóstico Futuro - " + date);
                    
                    Toast.makeText(getContext(), 
                        "Pronóstico futuro cargado correctamente",
                        Toast.LENGTH_SHORT).show();
                } else {
                    showError("Error en la consulta futura. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                showError("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void displayWeatherData(WeatherResponse weatherData, String title) {
        binding.cardWeatherInfo.setVisibility(View.VISIBLE);
        binding.tvWeatherTitle.setText(title);

        if (weatherData.getForecast() != null && 
            !weatherData.getForecast().getForecastDays().isEmpty()) {
            
            var dayData = weatherData.getForecast().getForecastDays().get(0);
            
            if (dayData.getDay() != null) {
                binding.tvMaxTemp.setText(String.format("%.1f°C", dayData.getDay().getMaxTempC()));
                binding.tvMinTemp.setText(String.format("%.1f°C", dayData.getDay().getMinTempC()));
                binding.tvHumidity.setText(String.format("%.0f%%", dayData.getDay().getAvgHumidity()));
                
                if (dayData.getDay().getCondition() != null) {
                    binding.tvCondition.setText(dayData.getDay().getCondition().getText());
                }
            }
        }
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        binding.cardNoResults.setVisibility(View.VISIBLE);
        binding.cardWeatherInfo.setVisibility(View.GONE);
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