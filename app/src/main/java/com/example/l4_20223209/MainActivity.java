package com.example.l4_20223209;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.l4_20223209.databinding.ActivityMainBinding;
import com.example.l4_20223209.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        
        // Inicializar View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetworkAndProceed();
            }
        });
    }

    private void checkNetworkAndProceed() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            // Si hay conexión, proceder a AppActivity
            try {
                Intent intent = new Intent(MainActivity.this, AppActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                // Si hay un error al abrir AppActivity, mostrar mensaje
                showErrorDialog("Error al abrir la aplicación: " + e.getMessage());
            }
        } else {
            // Si no hay conexión, mostrar dialog
            showNoInternetDialog();
        }
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Sin conexión a Internet")
                .setMessage("Esta aplicación requiere conexión a Internet para funcionar. ¿Desea ir a configuración para activar la conexión?")
                .setPositiveButton("Configuración", (dialog, which) -> {
                    try {
                        // Abrir configuración de red del dispositivo
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(intent);
                    } catch (Exception e) {
                        showErrorDialog("No se pudo abrir la configuración.");
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}