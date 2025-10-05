package com.example.l4_20223209.ui.forecast;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastFragment extends Fragment implements SensorEventListener {

    private FragmentForecastBinding binding;
    private ForecastAdapter adapter;
    private WeatherApiService apiService;
    private ExecutorService executorService;
    
    // Variables para el acelerómetro
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final float SHAKE_THRESHOLD = 10.0f; // m/s²
    private static final int SHAKE_TIMEOUT = 1000; // 1 segundo
    private long lastShakeTime = 0;
    
    // Para funcionalidad de deshacer
    private List<ForecastDay> lastForecastData;
    private boolean hasData = false;

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
            String locationQuery = args.getString("locationQuery", "");
            
            if (locationId != 0) {
                // Usar el formato id:NUMERO para la API
                String idQuery = locationQuery.isEmpty() ? "id:" + locationId : locationQuery;
                binding.etLocationId.setText(idQuery);
                binding.etLocationName.setText(locationName);
                binding.etDias.setText("14");
                
                // Ejecutar búsqueda automáticamente
                searchForecast(idQuery, 14);
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
        
        // Inicializar lista para funcionalidad de deshacer
        lastForecastData = new ArrayList<>();
        
        // Configurar acelerómetro
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
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

        // Asegurar formato correcto para ID numérico
        String queryLocation = formatLocationQuery(locationId);

        // Llamada a la API para obtener pronóstico
        Call<WeatherResponse> call = apiService.getForecast(
            "5586a0acd5a345e0b4361158250210", queryLocation, days);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse forecast = response.body();
                    List<ForecastDay> dayForecasts = forecast.getForecast().getForecastDays();
                    
                    if (!dayForecasts.isEmpty()) {
                        // Guardar datos anteriores para funcionalidad de deshacer
                        lastForecastData = new ArrayList<>(dayForecasts);
                        hasData = true;
                        
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

    // Métodos del SensorEventListener para acelerómetro
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calcular la aceleración total (excluyendo la gravedad)
            double acceleration = Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

            // Verificar si supera el umbral y ha pasado suficiente tiempo desde la última agitación
            long currentTime = System.currentTimeMillis();
            if (Math.abs(acceleration) > SHAKE_THRESHOLD && 
                (currentTime - lastShakeTime) > SHAKE_TIMEOUT) {
                
                lastShakeTime = currentTime;
                
                // Solo activar si hay datos para deshacer
                if (hasData && !lastForecastData.isEmpty()) {
                    showUndoDialog();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No necesitamos hacer nada aquí
    }

    /**
     * Muestra un diálogo de confirmación para deshacer la última acción
     */
    private void showUndoDialog() {
        if (getContext() == null) return;

        new AlertDialog.Builder(getContext())
                .setTitle("🔄 Deshacer Acción")
                .setMessage("¿Desea limpiar los últimos pronósticos obtenidos?")
                .setPositiveButton("Sí, limpiar", (dialog, which) -> {
                    clearForecastData();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Limpia los datos del pronóstico actual
     */
    private void clearForecastData() {
        // Limpiar solo la visualización de datos, mantener la información de ubicación
        adapter.updateForecasts(new ArrayList<>());
        binding.recyclerViewForecast.setVisibility(View.GONE);
        binding.cardNoResults.setVisibility(View.VISIBLE);
        
        // NO limpiar los campos de ubicación para mantener el contexto de navegación
        // Solo limpiar el campo de días si se desea
        // binding.etDias.setText("");
        
        hasData = false;
        lastForecastData.clear();
        
        Toast.makeText(getContext(), 
            "📅 Pronósticos eliminados (ubicación conservada)", 
            Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Registrar el listener del acelerómetro solo cuando el fragment está activo
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Desregistrar el listener para ahorrar batería
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
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