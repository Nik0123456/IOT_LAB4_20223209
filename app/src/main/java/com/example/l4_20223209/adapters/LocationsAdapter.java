package com.example.l4_20223209.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.l4_20223209.databinding.ItemLocationBinding;
import com.example.l4_20223209.model.Location;
import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationViewHolder> {

    private List<Location> locations;
    private OnLocationClickListener listener;

    public interface OnLocationClickListener {
        void onLocationClick(Location location);
    }

    public LocationsAdapter(List<Location> locations, OnLocationClickListener listener) {
        this.locations = locations;
        this.listener = listener;
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
        holder.bind(locations.get(position));
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
            binding.tvLocationDetails.setText(location.getRegion() + ", " + location.getCountry());
            binding.tvCoordinates.setText("Lat: " + location.getLat() + ", Lon: " + location.getLon());
            binding.tvLocationId.setText("ID: " + location.getId());

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLocationClick(location);
                }
            });
        }
    }
}
