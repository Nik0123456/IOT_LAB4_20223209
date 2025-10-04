package com.example.l4_20223209.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.l4_20223209.adapters.ForecastAdapter;
import com.example.l4_20223209.databinding.FragmentForecastBinding;
import com.example.l4_20223209.model.WeatherForecast;
import java.util.ArrayList;
import java.util.List;

public class ForecastFragment extends Fragment {

    private FragmentForecastBinding binding;
    private ForecastAdapter adapter;
    private List<WeatherForecast> forecastList;
    private int locationId = -1;
    private String locationName = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                           @Nullable Bundle savedInstanceState) {
        binding = FragmentForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Obtener argumentos del fragmento anterior
        if (getArguments() != null) {
            locationId = getArguments().getInt("locationId", -1);
            locationName = getArguments().getString("locationName", "");
            
            if (locationId != -1) {
                // Pre-llenar campos si viene desde LocationsFragment
                binding.etLocationId.setText(String.valueOf(locationId));
                binding.etDays.setText("7"); // Valor por defecto
                
                // Mostrar información de la ubicación seleccionada
                binding.tvSelectedLocation.setText("Ubicación: " + locationName);
                binding.tvSelectedLocation.setVisibility(View.VISIBLE);
            }
        }
        
        setupRecyclerView();
        setupClickListeners();
    }

    private void setupRecyclerView() {
        forecastList = new ArrayList<>();
        adapter = new ForecastAdapter();
        adapter.setForecasts(forecastList);
        
        binding.rvForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvForecast.setAdapter(adapter);
    }

    private void setupClickListeners() {
        binding.btnSearch.setOnClickListener(v -> {
            String locationIdStr = binding.etLocationId.getText().toString().trim();
            String daysStr = binding.etDays.getText().toString().trim();
            
            if (!locationIdStr.isEmpty() && !daysStr.isEmpty()) {
                int locId = Integer.parseInt(locationIdStr);
                int days = Integer.parseInt(daysStr);
                searchForecast(locId, days);
            } else {
                Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchForecast(int locationId, int days) {
        // Mostrar loading
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.rvForecast.setVisibility(View.GONE);
        binding.tvNoResults.setVisibility(View.GONE);

        // TODO: Implementar llamada a la API cuando esté lista
        // Por ahora mostrar mensaje temporal
        binding.progressBar.setVisibility(View.GONE);
        binding.tvNoResults.setText("Funcionalidad de API en desarrollo...\nID: " + locationId + ", Días: " + days);
        binding.tvNoResults.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
