package com.example.l4_20223209.ui.locations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.l4_20223209.databinding.ItemLocationBinding;
import com.example.l4_20223209.models.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationViewHolder> {

    private List<Location> locations = new ArrayList<>();
    private OnLocationClickListener listener;

    public interface OnLocationClickListener {
        void onLocationClick(Location location);
    }

    public LocationsAdapter(OnLocationClickListener listener) {
        this.listener = listener;
    }

    public void updateLocations(List<Location> locations) {
        this.locations = locations != null ? new ArrayList<>(locations) : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setLocations(List<Location> locations) {
        updateLocations(locations);
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLocationBinding binding = ItemLocationBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new LocationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        private ItemLocationBinding binding;

        public LocationViewHolder(@NonNull ItemLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Location location) {
            binding.tvLocationName.setText(location.getName());
            binding.tvLocationRegion.setText(location.getRegion() + ", " + location.getCountry());
            binding.tvLocationCoordinates.setText(
                    String.format("Lat: %.2f, Lon: %.2f", location.getLat(), location.getLon()));
            binding.tvLocationId.setText("ID: " + location.getId());

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLocationClick(location);
                }
            });
        }
    }
}