package com.example.l4_20223209.ui.future;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.l4_20223209.databinding.FragmentFutureBinding;

public class FutureFragment extends Fragment {

    private FragmentFutureBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFutureBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Future functionality will be implemented here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}