package com.example.agenda.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agenda.R;
import com.example.agenda.databinding.FragmentSalesBinding;

public class SalesFragment extends Fragment {

    FragmentSalesBinding binding;

    public SalesFragment() {
        // Required empty public constructor
    }

    public static SalesFragment newInstance() {
        SalesFragment fragment = new SalesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSalesBinding.inflate(inflater, container, false);

        Toolbar sales_toolbar= binding.salesToolbar;
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.setSupportActionBar(sales_toolbar);

        ActionBar actionBar=activity.getSupportActionBar();
        actionBar.setTitle(R.string.main_tv_sales);
        actionBar.show();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}