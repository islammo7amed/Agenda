package com.example.agenda.adapters;

import android.animation.LayoutTransition;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.agenda.R;
import com.example.agenda.database.AgendaViewModel;
import com.example.agenda.database.Customer;
import com.example.agenda.databinding.DebtCustomCustomerViewBinding;

import java.util.List;

public class DebtCustomerRecyclerViewAdapter extends RecyclerView.Adapter<DebtCustomerRecyclerViewAdapter.DebtCustomerViewHolder> {

    List<Customer> customers;
    AgendaViewModel agendaViewModel;

    public DebtCustomerRecyclerViewAdapter(List<Customer> customers, AgendaViewModel agendaViewModel) {
        this.customers = customers;
        this.agendaViewModel = agendaViewModel;
    }

    @NonNull
    @Override
    public DebtCustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DebtCustomerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.debt_custom_customer_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DebtCustomerViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.bind(customer);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void setCustomers(List<Customer> customers){
        this.customers = customers;
        notifyDataSetChanged();
    }

    public List<Customer> getCustomers(){
        return customers;
    }

    class DebtCustomerViewHolder extends RecyclerView.ViewHolder {

        Customer customer;
        DebtCustomCustomerViewBinding binding;

        public DebtCustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DebtCustomCustomerViewBinding.bind(itemView);

            binding.debtCustomCustomerBtnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double oldDebt = customer.getDebt();
                    if(!binding.debtCustomCustomerEtRefund.getText().toString().isEmpty()){
                        double debt = Double.parseDouble(binding.debtCustomCustomerEtRefund.getText().toString());
                        double d = oldDebt - debt;
                        customer.setDebt(d);
                        agendaViewModel.updateCustomer(customer);
                        binding.debtCustomCustomerEtRefund.getText().clear();
                    }

                }
            });

            LayoutTransition transition = new LayoutTransition();
            transition.setDuration(400);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                transition.enableTransitionType(LayoutTransition.CHANGING);
            }

            binding.debtCustomCustomerCardView.setLayoutTransition(transition);

            binding.debtCustomCustomerIvArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int visibility = (binding.debtCustomCustomerConstraintRefund.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                    TransitionManager.beginDelayedTransition(binding.debtCustomCustomerConstraintRefund,new AutoTransition());
                    if (visibility == View.VISIBLE){
                        binding.debtCustomCustomerIvArrow.setImageResource(R.drawable.ic_baseline_arrow_up);
                    }else {
                        binding.debtCustomCustomerIvArrow.setImageResource(R.drawable.ic_baseline_arrow_down);
                    }
                    binding.debtCustomCustomerConstraintRefund.setVisibility(visibility);
                }
            });
        }

        public void bind (Customer customer){
            this.customer = customer;
            binding.debtCustomCustomerTvName.setText(customer.getName());
            binding.debtCustomCustomerTvDebt.setText(String.valueOf(customer.getDebt()));
        }
    }
}
