package com.example.agenda.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.databinding.EndInvoiceCustomProductViewBinding;
import com.example.agenda.pojo.CustomerProductModel;

import java.util.ArrayList;
import java.util.HashMap;

public class EndInvoiceProductRecyclerViewAdapter extends RecyclerView.Adapter<EndInvoiceProductRecyclerViewAdapter.EndInvoiceProductViewHolder> {

    ArrayList<CustomerProductModel> productModels;

    public EndInvoiceProductRecyclerViewAdapter(ArrayList<CustomerProductModel> productModels){
        this.productModels = productModels;
    }


    @NonNull
    @Override
    public EndInvoiceProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EndInvoiceProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.end_invoice_custom_product_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EndInvoiceProductViewHolder holder, int position) {

        CustomerProductModel productModel = productModels.get(position);
        holder.bind(productModel, position,getItemCount());


    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    class EndInvoiceProductViewHolder extends RecyclerView.ViewHolder{

        EndInvoiceCustomProductViewBinding binding;
        CustomerProductModel productModel;

        public EndInvoiceProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = EndInvoiceCustomProductViewBinding.bind(itemView);
        }

        public void bind (CustomerProductModel productModel,int pos, int s){
            this.productModel = productModel;

            binding.endInvoiceCustomName.setText(productModel.getProduct().getName());
            binding.endInvoiceCustomNumOfPieces.setText(String.valueOf(productModel.getNumber_of_pieces()));
            binding.endInvoiceCustomX.setText("x");
            binding.endInvoiceCustomPrice.setText(String.valueOf((productModel.getProduct().getSelling_price() * productModel.getNumber_of_pieces())));


            if(pos == s-1){
                binding.endInvoiceCustomLine.setBackgroundColor(Color.parseColor("#F6F6F6"));

            }else {
                binding.endInvoiceCustomLine.setBackgroundColor(Color.parseColor("#1A707070"));
            }
        }
    }
}
