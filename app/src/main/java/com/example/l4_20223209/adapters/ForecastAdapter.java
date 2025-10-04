package com.example.l4_20223209.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.l4_20223209.R;
import com.example.l4_20223209.model.WeatherForecast;
import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    
    private List<WeatherForecast> forecasts;

    public ForecastAdapter() {
        this.forecasts = new ArrayList<>();
    }

    public void setForecasts(List<WeatherForecast> forecasts) {
        this.forecasts = forecasts != null ? forecasts : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void clearForecasts() {
        this.forecasts.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        WeatherForecast forecast = forecasts.get(position);
        holder.bind(forecast);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvMaxMinTemp;
        private ImageView ivWeatherIcon;
        private TextView tvCondition;
        private TextView tvHumidity;
        private TextView tvWind;
        private TextView tvRainChance;
        private TextView tvUvIndex;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMaxMinTemp = itemView.findViewById(R.id.tvMaxMinTemp);
            ivWeatherIcon = itemView.findViewById(R.id.ivWeatherIcon);
            tvCondition = itemView.findViewById(R.id.tvCondition);
            tvHumidity = itemView.findViewById(R.id.tvHumidity);
            tvWind = itemView.findViewById(R.id.tvWind);
            tvRainChance = itemView.findViewById(R.id.tvRainChance);
            tvUvIndex = itemView.findViewById(R.id.tvUvIndex);
        }

        public void bind(WeatherForecast forecast) {
            // Fecha
            tvDate.setText(forecast.getDate());
            
            // Temperaturas máxima y mínima
            if (forecast.getDay() != null) {
                String tempText = String.format("%.0f°C / %.0f°C", 
                    forecast.getDay().getMaxTempC(), 
                    forecast.getDay().getMinTempC());
                tvMaxMinTemp.setText(tempText);
                
                // Condición del clima
                if (forecast.getDay().getCondition() != null) {
                    tvCondition.setText(forecast.getDay().getCondition().getText());
                }
                
                // Humedad
                String humidityText = String.format("Humedad: %.0f%%", forecast.getDay().getAvgHumidity());
                tvHumidity.setText(humidityText);
                
                // Viento
                String windText = String.format("Viento: %.0f km/h", forecast.getDay().getMaxWindKph());
                tvWind.setText(windText);
                
                // Probabilidad de lluvia
                String rainText = String.format("Lluvia: %d%%", forecast.getDay().getDailyChanceOfRain());
                tvRainChance.setText(rainText);
                
                // Índice UV
                String uvText = String.format("UV: %.0f", forecast.getDay().getUv());
                tvUvIndex.setText(uvText);
            }
            
            // Icono del clima (por defecto usar ic_sunny)
            ivWeatherIcon.setImageResource(R.drawable.ic_sunny);
        }
    }
}
