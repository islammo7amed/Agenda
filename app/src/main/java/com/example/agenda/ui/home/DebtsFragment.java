package com.example.agenda.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agenda.R;
import com.example.agenda.adapters.DebtCustomerRecyclerViewAdapter;
import com.example.agenda.database.AgendaViewModel;
import com.example.agenda.database.Customer;
import com.example.agenda.databinding.FragmentDebtsBinding;

import java.util.ArrayList;
import java.util.List;

public class DebtsFragment extends Fragment {

    FragmentDebtsBinding binding;
    DebtCustomerRecyclerViewAdapter adapter;
    AgendaViewModel agendaViewModel;

    public DebtsFragment() {
        // Required empty public constructor
    }

    public static DebtsFragment newInstance() {
        DebtsFragment fragment = new DebtsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agendaViewModel = new ViewModelProvider(getActivity()).get(AgendaViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDebtsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new DebtCustomerRecyclerViewAdapter(new ArrayList<>(), agendaViewModel);
        binding.debtsRv.setAdapter(adapter);
        binding.debtsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.debtsRv.setHasFixedSize(true);

        agendaViewModel.getCustomersArrears().observe(getActivity(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                adapter.setCustomers(customers);
            }
        });

    }
}