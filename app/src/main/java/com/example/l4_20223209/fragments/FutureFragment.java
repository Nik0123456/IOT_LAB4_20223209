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
import com.example.l4_20223209.adapters.FutureAdapter;
import com.example.l4_20223209.databinding.FragmentFutureBinding;
import com.example.l4_20223209.model.HourlyWeather;
import java.util.ArrayList;
import java.util.List;

public class FutureFragment extends Fragment {

    private FragmentFutureBinding binding;
    private FutureAdapter adapter;
    private List<HourlyWeather> hourlyList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                           @Nullable Bundle savedInstanceState) {
        binding = FragmentFutureBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        setupRecyclerView();
        setupClickListeners();
    }

    private void setupRecyclerView() {
        hourlyList = new ArrayList<>();
        adapter = new FutureAdapter();
        adapter.setHourlyWeatherList(hourlyList);
        
        binding.rvFuture.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFuture.setAdapter(adapter);
    }

    private void setupClickListeners() {
        binding.btnSearch.setOnClickListener(v -> {
            String locationId = binding.etLocationId.getText().toString().trim();
            String date = binding.etDate.getText().toString().trim();
            
            if (!locationId.isEmpty() && !date.isEmpty()) {
                searchFutureWeather(locationId, date);
            } else {
                Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchFutureWeather(String locationId, String date) {
        // Mostrar loading
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.rvFuture.setVisibility(View.GONE);
        binding.tvNoResults.setVisibility(View.GONE);

        // TODO: Implementar validación de fecha y llamada a API correspondiente
        // Por ahora mostrar mensaje temporal
        binding.progressBar.setVisibility(View.GONE);
        binding.tvNoResults.setText("Funcionalidad de API en desarrollo...\nID: " + locationId + ", Fecha: " + date);
        binding.tvNoResults.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
