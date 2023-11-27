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
import android.widget.Toast;

import com.example.agenda.R;
import com.example.agenda.adapters.AllCustomersRecyclerViewAdapter;
import com.example.agenda.database.AgendaViewModel;
import com.example.agenda.database.Customer;
import com.example.agenda.databinding.FragmentAllCustomersBinding;
import com.example.agenda.listners.OnCustomerClickListner;

import java.util.ArrayList;
import java.util.List;

public class AllCustomersFragment extends Fragment {

    AllCustomersRecyclerViewAdapter adapter;
    FragmentAllCustomersBinding binding;
    AgendaViewModel agendaViewModel;

    public AllCustomersFragment() {
        // Required empty public constructor
    }

    public static AllCustomersFragment newInstance() {
        AllCustomersFragment fragment = new AllCustomersFragment();
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
        binding = FragmentAllCustomersBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new AllCustomersRecyclerViewAdapter(new ArrayList<>(), new OnCustomerClickListner() {
            @Override
            public void onCustomerClick(Customer customer) {
                if (customer.getDebt() > 0){
                    Toast.makeText(getActivity(), R.string.all_customers_toast_not_deleted, Toast.LENGTH_SHORT).show();
                }else {
                    agendaViewModel.deleteCustomer(customer);
                    Toast.makeText(getActivity(), R.string.all_customers_toast_deleted, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.allCustomersRv.setAdapter(adapter);
        binding.allCustomersRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.allCustomersRv.setHasFixedSize(true);

        agendaViewModel.getAllCustomers().observe(getActivity(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                adapter.setCustomers(customers);
            }
        });
    }
}