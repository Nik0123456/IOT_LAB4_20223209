package com.example.l4_20223209.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.l4_20223209.R;
import com.example.l4_20223209.adapters.LocationsAdapter;
import com.example.l4_20223209.api.WeatherApiClient;
import com.example.l4_20223209.databinding.FragmentLocationsBinding;
import com.example.l4_20223209.model.Location;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationsFragment extends Fragment {

    private FragmentLocationsBinding binding;
    private LocationsAdapter adapter;
    private List<Location> locationsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                           @Nullable Bundle savedInstanceState) {
        binding = FragmentLocationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        setupRecyclerView();
        setupClickListeners();
    }

    private void setupRecyclerView() {
        locationsList = new ArrayList<>();
        adapter = new LocationsAdapter(locationsList, this::onLocationClick);
        
        binding.rvLocations.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvLocations.setAdapter(adapter);
    }

    private void setupClickListeners() {
        binding.btnSearch.setOnClickListener(v -> {
            String query = binding.etSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                searchLocations(query);
            } else {
                Toast.makeText(getContext(), "Ingresa una ubicación para buscar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchLocations(String query) {
        // Mostrar loading
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.rvLocations.setVisibility(View.GONE);
        binding.tvNoResults.setVisibility(View.GONE);

        // Llamada a la API
        Call<List<Location>> call = WeatherApiClient.getApiService()
                .searchLocations(WeatherApiClient.API_KEY, query);

        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                binding.progressBar.setVisibility(View.GONE);
                
                if (response.isSuccessful() && response.body() != null) {
                    List<Location> locations = response.body();
                    if (!locations.isEmpty()) {
                        locationsList.clear();
                        locationsList.addAll(locations);
                        adapter.notifyDataSetChanged();
                        binding.rvLocations.setVisibility(View.VISIBLE);
                    } else {
                        binding.tvNoResults.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.tvNoResults.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Error al buscar ubicaciones", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.tvNoResults.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onLocationClick(Location location) {
        // Navegar al fragmento de pronósticos con los datos de la ubicación
        Bundle args = new Bundle();
        args.putLong("locationId", location.getId());
        args.putString("locationName", location.getDisplayName());
        
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_locations_to_forecast, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
