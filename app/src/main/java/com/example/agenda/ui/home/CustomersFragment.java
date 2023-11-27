package com.example.agenda.ui.home;

import android.content.Context;
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
import com.example.agenda.adapters.CustomersPagerAdapter;
import com.example.agenda.databinding.FragmentCustomersBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CustomersFragment extends Fragment {

    FragmentCustomersBinding binding;
    CustomersPagerAdapter pagerAdapter;

    public CustomersFragment() {
        // Required empty public constructor
    }

    public static CustomersFragment newInstance() {
        CustomersFragment fragment = new CustomersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerAdapter = new CustomersPagerAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCustomersBinding.inflate(inflater,container,false);

        Toolbar customers_toolbar= binding.customersToolbar;
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.setSupportActionBar(customers_toolbar);

        ActionBar actionBar=activity.getSupportActionBar();
        actionBar.setTitle(R.string.main_tv_customers);
        actionBar.show();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.customersPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(binding.customersTabLayout, binding.customersPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0 :
                        tab.setText(R.string.customers_tab_debts);
                        break;
                    case 1 :
                      tab.setText(R.string.customers_tab_all_customers);
                }
            }
        }).attach();

    }

}