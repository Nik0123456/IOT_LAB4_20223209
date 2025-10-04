package com.example.l4_20223209.ui.forecast;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.l4_20223209.databinding.ItemForecastBinding;
import com.example.l4_20223209.models.ForecastDay;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastDay> forecastDays = new ArrayList<>();

    public void updateForecasts(List<ForecastDay> forecastDays) {
        this.forecastDays = forecastDays != null ? new ArrayList<>(forecastDays) : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setForecastDays(List<ForecastDay> forecastDays) {
        updateForecasts(forecastDays);
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemForecastBinding binding = ItemForecastBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ForecastViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        ForecastDay forecastDay = forecastDays.get(position);
        holder.bind(forecastDay);
    }

    @Override
    public int getItemCount() {
        return forecastDays.size();
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        private ItemForecastBinding binding;

        public ForecastViewHolder(@NonNull ItemForecastBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ForecastDay forecastDay) {
            binding.tvForecastDate.setText(forecastDay.getDate());
            
            if (forecastDay.getDay() != null) {
                binding.tvMaxTemp.setText(String.format("%.1f°C", forecastDay.getDay().getMaxTempC()));
                binding.tvMinTemp.setText(String.format("%.1f°C", forecastDay.getDay().getMinTempC()));
                
                if (forecastDay.getDay().getCondition() != null) {
                    binding.tvCondition.setText(forecastDay.getDay().getCondition().getText());
                }
            }
            
            // For now, we'll show a placeholder for location info
            binding.tvLocationInfo.setText("Location ID: " + forecastDay.getDateEpoch());
        }
    }
}