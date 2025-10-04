package com.example.l4_20223209;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.l4_20223209.databinding.ActivityAppBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AppActivity extends AppCompatActivity {

    private ActivityAppBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar Toolbar
        setSupportActionBar(binding.toolbar);

        // Configurar Navigation Component
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        
        // Configurar AppBarConfiguration - permitir navegación hacia arriba en todos los fragmentos
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.locationsFragment, R.id.forecastFragment, R.id.futureFragment)
                .build();
        
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Configurar Bottom Navigation
        BottomNavigationView bottomNav = binding.bottomNavigation;
        NavigationUI.setupWithNavController(bottomNav, navController);
        
        // Configurar el BackStack para que regrese al MainActivity
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Limpiar el BackStack para permitir regresar directamente al MainActivity
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        });

        // Configurar el comportamiento del botón atrás usando OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Al presionar atrás, regresar al MainActivity
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
