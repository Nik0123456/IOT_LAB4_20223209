package com.example.l4_20223209.ui.future;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.l4_20223209.api.WeatherApiService;
import com.example.l4_20223209.databinding.FragmentFutureBinding;
import com.example.l4_20223209.models.DayForecast;
import com.example.l4_20223209.models.WeatherResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        
        try {
            if (binding != null) {
                initializeComponents();
                setupListeners();
                setDefaultDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Si hay un error, intenta al menos inicializar componentes básicos
            try {
                initializeComponents();
            } catch (Exception ex) {
                ex.printStackTrace();
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
    }

    private void setupListeners() {
        try {
            if (binding == null || binding.etFecha == null || binding.btnBuscarHistorico == null || 
                binding.btnBuscarFuturo == null) {
                return;
            }
            
            // DatePicker para el campo de fecha
            binding.etFecha.setOnClickListener(v -> showDatePicker());
            
            // Botón de búsqueda histórica
            binding.btnBuscarHistorico.setOnClickListener(v -> {
                String locationId = binding.etLocationId.getText().toString().trim();
                String fecha = binding.etFecha.getText().toString().trim();
                
                if (!locationId.isEmpty() && !fecha.isEmpty()) {
                    if (isValidDateFormat(fecha)) {
                        if (isValidHistoricalDate(fecha)) {
                            searchHistoricalWeather(locationId, fecha);
                        } else {
                            Toast.makeText(getContext(), 
                                "Fecha histórica debe estar entre 1 y 365 días atrás",
                                Toast.LENGTH_LONG).show();
                        }
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

            // Botón de búsqueda futura
            binding.btnBuscarFuturo.setOnClickListener(v -> {
                String locationId = binding.etLocationId.getText().toString().trim();
                String fecha = binding.etFecha.getText().toString().trim();
                
                if (!locationId.isEmpty() && !fecha.isEmpty()) {
                    if (isValidDateFormat(fecha)) {
                        if (isValidFutureDate(fecha)) {
                            searchFutureWeather(locationId, fecha);
                        } else {
                            Toast.makeText(getContext(), 
                                "Fecha futura debe estar entre 14 y 300 días adelante",
                                Toast.LENGTH_LONG).show();
                        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    private void showDatePicker() {
        try {
            if (getContext() == null || binding == null || binding.etFecha == null) {
                return;
            }
            
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    try {
                        // Formatear la fecha seleccionada
                        String selectedDate = String.format(Locale.getDefault(), 
                            "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        if (binding != null && binding.etFecha != null) {
                            binding.etFecha.setText(selectedDate);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                year, month, day
            );

            // Configurar límites de fecha según API WeatherAPI
            // Históricos: 365 días atrás hasta 1 día atrás
            Calendar minDate = Calendar.getInstance();
            minDate.add(Calendar.DAY_OF_YEAR, -365);
            datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());

            // Futuros: desde mañana hasta 300 días en el futuro
            Calendar maxDate = Calendar.getInstance();
            maxDate.add(Calendar.DAY_OF_YEAR, 300);
            datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

            datePickerDialog.show();
        } catch (Exception e) {
            // Manejo de errores para evitar crashes
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al abrir selector de fecha", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Valida si una fecha es apropiada para búsqueda histórica (entre 365 y 1 días atrás)
     */
    private boolean isValidHistoricalDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date selectedDate = sdf.parse(dateStr);
            Date today = new Date();
            
            long diffInMillis = today.getTime() - selectedDate.getTime();
            long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
            
            // Entre 1 y 365 días atrás
            return diffInDays >= 1 && diffInDays <= 365;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Valida si una fecha es apropiada para búsqueda futura (entre 14 y 300 días adelante)
     */
    private boolean isValidFutureDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date selectedDate = sdf.parse(dateStr);
            Date today = new Date();
            
            long diffInMillis = selectedDate.getTime() - today.getTime();
            long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
            
            // Entre 14 y 300 días en el futuro
            return diffInDays >= 14 && diffInDays <= 300;
        } catch (Exception e) {
            return false;
        }
    }

    private void setDefaultDate() {
        // Establecer fecha actual + 15 días por defecto (válida para futuro)
        try {
            if (binding != null && binding.etFecha != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String futureDate = sdf.format(new Date(System.currentTimeMillis() + 15 * 24 * 60 * 60 * 1000L));
                binding.etFecha.setText(futureDate);
            }
        } catch (Exception e) {
            // Manejo silencioso del error para evitar crashes
            e.printStackTrace();
        }
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

        // Asegurar formato correcto para ID numérico
        String queryLocation = formatLocationQuery(locationId);

        // Log para depuración - Verificar parámetros exactos
        System.out.println("=== HISTORY API CALL ===");
        System.out.println("Original locationId: " + locationId);
        System.out.println("Formatted queryLocation: " + queryLocation);
        System.out.println("Date: " + date);
        System.out.println("API Key: 5586a0acd5a345e0b4361158250210");
        System.out.println("Full URL would be: https://api.weatherapi.com/v1/history.json?key=5586a0acd5a345e0b4361158250210&q=" + queryLocation + "&dt=" + date);
        System.out.println("=====================");

        // Llamada a la API para datos históricos
        Call<WeatherResponse> call = apiService.getHistoryForecast(
            "5586a0acd5a345e0b4361158250210", queryLocation, date);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                binding.progressBar.setVisibility(View.GONE);

                System.out.println("=== HISTORY API RESPONSE ===");
                System.out.println("Response code: " + response.code());
                System.out.println("Response successful: " + response.isSuccessful());
                System.out.println("Response body null: " + (response.body() == null));
                if (response.errorBody() != null) {
                    try {
                        System.out.println("Error body: " + response.errorBody().string());
                    } catch (Exception e) {
                        System.out.println("Could not read error body");
                    }
                }
                System.out.println("============================");

                if (response.isSuccessful() && response.body() != null) {
                    displayWeatherData(response.body(), "📜 Datos Históricos - " + date);
                    
                    Toast.makeText(getContext(), 
                        "Datos históricos cargados correctamente",
                        Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = "Error en la consulta histórica. Código: " + response.code();
                    if (response.code() == 400) {
                        errorMessage += " - Verifique ID de ubicación y formato de fecha";
                    }
                    showError(errorMessage);
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

        // Asegurar formato correcto para ID numérico
        String queryLocation = formatLocationQuery(locationId);

        // Log para depuración - Verificar parámetros exactos
        System.out.println("=== FUTURE API CALL ===");
        System.out.println("Original locationId: " + locationId);
        System.out.println("Formatted queryLocation: " + queryLocation);
        System.out.println("Date: " + date);
        System.out.println("API Key: 5586a0acd5a345e0b4361158250210");
        System.out.println("Full URL would be: https://api.weatherapi.com/v1/future.json?key=5586a0acd5a345e0b4361158250210&q=" + queryLocation + "&dt=" + date);
        System.out.println("=====================");

        // Llamada a la API para datos futuros
        Call<WeatherResponse> call = apiService.getFutureForecast(
            "5586a0acd5a345e0b4361158250210", queryLocation, date);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                binding.progressBar.setVisibility(View.GONE);

                System.out.println("=== FUTURE API RESPONSE ===");
                System.out.println("Response code: " + response.code());
                System.out.println("Response successful: " + response.isSuccessful());
                System.out.println("Response body null: " + (response.body() == null));
                if (response.errorBody() != null) {
                    try {
                        System.out.println("Error body: " + response.errorBody().string());
                    } catch (Exception e) {
                        System.out.println("Could not read error body");
                    }
                }
                System.out.println("============================");

                if (response.isSuccessful() && response.body() != null) {
                    displayWeatherData(response.body(), "🔮 Pronóstico Futuro - " + date);
                    
                    Toast.makeText(getContext(), 
                        "Pronóstico futuro cargado correctamente",
                        Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = "Error en la consulta futura. Código: " + response.code();
                    if (response.code() == 400) {
                        errorMessage += " - Verifique ID de ubicación y rango de fecha (14-300 días)";
                    }
                    showError(errorMessage);
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
        try {
            if (binding == null || weatherData == null) {
                showError("Error al mostrar datos del clima");
                return;
            }
            
            binding.cardWeatherInfo.setVisibility(View.VISIBLE);
            binding.tvWeatherTitle.setText(title);

            // Intentar obtener datos del forecast
            if (weatherData.getForecast() != null && 
                !weatherData.getForecast().getForecastDays().isEmpty()) {
                
                var forecastDay = weatherData.getForecast().getForecastDays().get(0);
                
                if (forecastDay != null && forecastDay.getDay() != null) {
                    // Los datos están en forecastDay.getDay() - que es un DayForecast
                    DayForecast dayData = forecastDay.getDay();
                    
                    binding.tvMaxTemp.setText(String.format("%.1f°C", dayData.getMaxTempC()));
                    binding.tvMinTemp.setText(String.format("%.1f°C", dayData.getMinTempC()));
                    binding.tvHumidity.setText(String.format("%d%%", dayData.getAvgHumidity()));
                    
                    if (dayData.getCondition() != null) {
                        binding.tvCondition.setText(dayData.getCondition().getText());
                    }
                }
            } else {
                showError("No hay datos de pronóstico disponibles");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error al procesar datos del clima: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        binding.cardNoResults.setVisibility(View.VISIBLE);
        binding.cardWeatherInfo.setVisibility(View.GONE);
    }

    /**
     * Formatea el query de ubicación para asegurar el formato correcto con la API
     * Si es un ID numérico, lo convierte a formato "id:NUMERO"
     */
    private String formatLocationQuery(String locationId) {
        if (locationId == null || locationId.trim().isEmpty()) {
            return locationId;
        }
        
        String trimmed = locationId.trim();
        
        // Si ya tiene el formato "id:", no lo modificamos
        if (trimmed.toLowerCase().startsWith("id:")) {
            return trimmed;
        }
        
        // Si es solo números, agregamos el prefijo "id:"
        if (trimmed.matches("\\d+")) {
            return "id:" + trimmed;
        }
        
        // Para cualquier otro caso (nombre de ciudad, coordenadas), devolvemos tal como está
        return trimmed;
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