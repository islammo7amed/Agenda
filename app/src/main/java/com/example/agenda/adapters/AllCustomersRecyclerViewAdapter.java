package com.example.agenda.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.database.Customer;
import com.example.agenda.databinding.AllCustomersCustomCustomerViewBinding;
import com.example.agenda.listners.OnCustomerClickListner;

import java.util.List;

public class AllCustomersRecyclerViewAdapter extends RecyclerView.Adapter<AllCustomersRecyclerViewAdapter.AllCustomersViewHolder> {

    List<Customer> customers;
    OnCustomerClickListner listner;

    public AllCustomersRecyclerViewAdapter(List<Customer> customers, OnCustomerClickListner listner) {
        this.customers = customers;
        this.listner = listner;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllCustomersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllCustomersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_customers_custom_customer_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllCustomersViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.bind(customer);

    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    class AllCustomersViewHolder extends RecyclerView.ViewHolder {

        AllCustomersCustomCustomerViewBinding binding;
        Customer customer;

        public AllCustomersViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AllCustomersCustomCustomerViewBinding.bind(itemView);
            binding.allCustomerCustomCustomerIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onCustomerClick(customer);
                }
            });
        }

        public void bind (Customer customer){
            this.customer = customer;
            binding.allCustomersCustomCustomerTvName.setText(customer.getName());
        }
    }
}
