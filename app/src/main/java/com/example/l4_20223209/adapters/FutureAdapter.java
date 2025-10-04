package com.example.l4_20223209.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.l4_20223209.R;
import com.example.l4_20223209.model.HourlyWeather;
import java.util.ArrayList;
import java.util.List;

public class FutureAdapter extends RecyclerView.Adapter<FutureAdapter.FutureViewHolder> {
    
    private List<HourlyWeather> hourlyWeatherList;

    public FutureAdapter() {
        this.hourlyWeatherList = new ArrayList<>();
    }

    public void setHourlyWeatherList(List<HourlyWeather> hourlyWeatherList) {
        this.hourlyWeatherList = hourlyWeatherList != null ? hourlyWeatherList : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void clearHourlyWeatherList() {
        this.hourlyWeatherList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FutureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false); // Usar temporalmente item_location
        return new FutureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FutureViewHolder holder, int position) {
        HourlyWeather hourlyWeather = hourlyWeatherList.get(position);
        holder.bind(hourlyWeather);
    }

    @Override
    public int getItemCount() {
        return hourlyWeatherList.size();
    }

    static class FutureViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvTemp;
        private ImageView ivWeatherIcon;
        private TextView tvCondition;
        private TextView tvFeelsLike;
        private TextView tvHumidity;
        private TextView tvWindSpeed;
        private TextView tvRainChance;
        private TextView tvPressure;
        private TextView tvVisibility;

        public FutureViewHolder(@NonNull View itemView) {
            super(itemView);
            // Temporarily using location layout, so only basic views
            tvTime = itemView.findViewById(R.id.tvLocationName); // Reusing existing ID
            tvTemp = itemView.findViewById(R.id.tvLocationDetails); // Reusing existing ID
            
            // Comment out IDs that don't exist in item_location
            // tvTime = itemView.findViewById(R.id.tvTime);
            // tvTemp = itemView.findViewById(R.id.tvTemp);
            // ivWeatherIcon = itemView.findViewById(R.id.ivWeatherIcon);
            // tvCondition = itemView.findViewById(R.id.tvCondition);
            // tvFeelsLike = itemView.findViewById(R.id.tvFeelsLike);
            // tvHumidity = itemView.findViewById(R.id.tvHumidity);
            // tvWindSpeed = itemView.findViewById(R.id.tvWindSpeed);
            // tvRainChance = itemView.findViewById(R.id.tvRainChance);
            // tvPressure = itemView.findViewById(R.id.tvPressure);
            // tvVisibility = itemView.findViewById(R.id.tvVisibility);
        }

        public void bind(HourlyWeather hourlyWeather) {
            // Simplified binding for temporary layout
            if (tvTime != null) {
                tvTime.setText(hourlyWeather.getSimpleTime());
            }
            if (tvTemp != null) {
                String tempText = String.format("%.0f°C", hourlyWeather.getTempC());
                tvTemp.setText(tempText);
            }
            
            // Comment out until we have proper layout
            /*
            // Hora
            tvTime.setText(hourlyWeather.getSimpleTime());
            
            // Temperatura
            String tempText = String.format("%.0f°C", hourlyWeather.getTempC());
            tvTemp.setText(tempText);
            
            // Condición del clima
            if (hourlyWeather.getCondition() != null) {
                tvCondition.setText(hourlyWeather.getCondition().getText());
            }
            
            // Sensación térmica
            String feelsLikeText = String.format("Sensación: %.0f°C", hourlyWeather.getFeelsLikeC());
            tvFeelsLike.setText(feelsLikeText);
            
            // Humedad
            String humidityText = String.format("Humedad: %d%%", hourlyWeather.getHumidity());
            tvHumidity.setText(humidityText);
            
            // Velocidad del viento
            String windText = String.format("Viento: %.0f km/h", hourlyWeather.getWindKph());
            tvWindSpeed.setText(windText);
            
            // Probabilidad de lluvia
            String rainText = String.format("Lluvia: %d%%", hourlyWeather.getChanceOfRain());
            tvRainChance.setText(rainText);
            
            // Presión atmosférica
            String pressureText = String.format("Presión: %.0f mb", hourlyWeather.getPressureMb());
            tvPressure.setText(pressureText);
            
            // Visibilidad
            String visibilityText = String.format("Visibilidad: %.0f km", hourlyWeather.getVisKm());
            tvVisibility.setText(visibilityText);
            
            // Icono del clima (por defecto usar ic_sunny)
            ivWeatherIcon.setImageResource(R.drawable.ic_sunny);
            */
        }
    }
}
