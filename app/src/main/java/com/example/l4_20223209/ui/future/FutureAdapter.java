package com.example.l4_20223209.ui.future;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.l4_20223209.databinding.ItemHourlyForecastBinding;
import com.example.l4_20223209.models.HourlyForecast;

import java.util.ArrayList;
import java.util.List;

public class FutureAdapter extends RecyclerView.Adapter<FutureAdapter.HourlyViewHolder> {

    private List<HourlyForecast> hourlyForecasts = new ArrayList<>();

    public void setHourlyForecasts(List<HourlyForecast> hourlyForecasts) {
        this.hourlyForecasts = hourlyForecasts != null ? new ArrayList<>(hourlyForecasts) : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHourlyForecastBinding binding = ItemHourlyForecastBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new HourlyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        HourlyForecast hourlyForecast = hourlyForecasts.get(position);
        holder.bind(hourlyForecast);
    }

    @Override
    public int getItemCount() {
        return hourlyForecasts.size();
    }

    static class HourlyViewHolder extends RecyclerView.ViewHolder {
        private ItemHourlyForecastBinding binding;

        public HourlyViewHolder(@NonNull ItemHourlyForecastBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(HourlyForecast hourlyForecast) {
            binding.tvHourlyTime.setText(hourlyForecast.getTime());
            binding.tvHourlyTemp.setText(String.format("%.1f°C", hourlyForecast.getTempC()));
            binding.tvHourlyHumidity.setText(String.format("%d%%", hourlyForecast.getHumidity()));
            binding.tvHourlyRainChance.setText(String.format("%d%%", hourlyForecast.getChanceOfRain()));
            
            if (hourlyForecast.getCondition() != null) {
                binding.tvHourlyCondition.setText(hourlyForecast.getCondition().getText());
            }
            
            // For now, we'll show a placeholder for location info
            binding.tvHourlyLocationInfo.setText("Hora: " + hourlyForecast.getTimeEpoch());
        }
    }
}