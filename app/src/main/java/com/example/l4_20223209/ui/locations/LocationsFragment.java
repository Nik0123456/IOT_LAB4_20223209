package com.example.l4_20223209.ui.locations;

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
import com.example.l4_20223209.databinding.FragmentLocationsBinding;
import com.example.l4_20223209.models.Location;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationsFragment extends Fragment {

    private FragmentLocationsBinding binding;
    private LocationsAdapter adapter;
    private WeatherApiService apiService;
    private ExecutorService executorService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initializeComponents();
        setupRecyclerView();
        setupListeners();
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
        adapter = new LocationsAdapter(location -> {
            // Callback cuando se selecciona una ubicación
            Toast.makeText(getContext(), 
                "Ubicación seleccionada: " + location.getName(),
                Toast.LENGTH_SHORT).show();
        });
    }

    private void setupRecyclerView() {
        binding.recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewLocations.setAdapter(adapter);
    }

    private void setupListeners() {
        // Botón de búsqueda
        binding.btnBuscar.setOnClickListener(v -> {
            String query = binding.etBusqueda.getText().toString().trim();
            if (!query.isEmpty()) {
                searchLocations(query);
            } else {
                Toast.makeText(getContext(), 
                    "Por favor ingrese una ubicación para buscar",
                    Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchLocations(String query) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewLocations.setVisibility(View.GONE);
        binding.cardNoResults.setVisibility(View.GONE);

        // Llamada a la API
        Call<List<Location>> call = apiService.searchLocations(
            "5586a0acd5a345e0b4361158250210", query);

        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Location> locations = response.body();
                    
                    if (!locations.isEmpty()) {
                        adapter.updateLocations(locations);
                        binding.recyclerViewLocations.setVisibility(View.VISIBLE);
                        
                        Toast.makeText(getContext(), 
                            "Se encontraron " + locations.size() + " ubicaciones",
                            Toast.LENGTH_SHORT).show();
                    } else {
                        showNoResults();
                    }
                } else {
                    showError("Error en la búsqueda. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                showError("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void showNoResults() {
        binding.cardNoResults.setVisibility(View.VISIBLE);
        binding.recyclerViewLocations.setVisibility(View.GONE);
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