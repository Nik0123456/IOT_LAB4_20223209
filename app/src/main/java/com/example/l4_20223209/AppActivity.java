package com.example.l4_20223209;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.l4_20223209.databinding.ActivityAppBinding;

public class AppActivity extends AppCompatActivity {

    private ActivityAppBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavigation();
    }

    private void setupNavigation() {
        try {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_app);
            NavigationUI.setupWithNavController(binding.navView, navController);
        } catch (Exception e) {
            // Log the error and handle gracefully
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}