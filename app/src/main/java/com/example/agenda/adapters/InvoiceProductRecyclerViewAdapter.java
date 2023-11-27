package com.example.agenda.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.database.AgendaViewModel;
import com.example.agenda.database.Product;
import com.example.agenda.databinding.EndInvoiceCustomProductViewBinding;
import com.example.agenda.pojo.CustomerProductModel;
import com.example.agenda.pojo.ProductModel;
import com.example.agenda.ui.invoice.EndInvoiceActivity;

import java.util.ArrayList;
import java.util.List;

public class InvoiceProductRecyclerViewAdapter extends RecyclerView.Adapter<InvoiceProductRecyclerViewAdapter.InvoiceProductViewHolder> {

    ArrayList<ProductModel> productModels;
    AgendaViewModel agendaViewModel;
    Context context;

    public InvoiceProductRecyclerViewAdapter(ArrayList<ProductModel> productModels, AgendaViewModel agendaViewModel, Context context) {
        this.productModels = productModels;
        this.agendaViewModel = agendaViewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public InvoiceProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InvoiceProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.end_invoice_custom_product_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceProductViewHolder holder, int position) {
        ProductModel productModel = productModels.get(position);
        holder.bind(productModel, position,getItemCount());
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    class InvoiceProductViewHolder extends RecyclerView.ViewHolder{

        EndInvoiceCustomProductViewBinding binding;
        ProductModel productModel;

        public InvoiceProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = EndInvoiceCustomProductViewBinding.bind(itemView);
        }

        public void bind (ProductModel productModel, int pos, int s){
            this.productModel = productModel;

            agendaViewModel.getProductById(productModel.getProductId()).observe((LifecycleOwner) context, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    Product p = products.get(0);
                    binding.endInvoiceCustomName.setText(p.getName());
                    binding.endInvoiceCustomPrice.setText(String.valueOf((p.getSelling_price() * productModel.getNumber_of_pieces())));
                }
            });

            //binding.endInvoiceCustomName.setText(productModel.getProduct().getName());
            binding.endInvoiceCustomNumOfPieces.setText(String.valueOf(productModel.getNumber_of_pieces()));
            binding.endInvoiceCustomX.setText("x");
            //binding.endInvoiceCustomPrice.setText(String.valueOf((productModel.getProduct().getSelling_price() * productModel.getNumber_of_pieces())));


            if(pos == s-1){
                binding.endInvoiceCustomLine.setBackgroundColor(Color.parseColor("#F6F6F6"));

            }else {
                binding.endInvoiceCustomLine.setBackgroundColor(Color.parseColor("#1A707070"));
            }
        }
    }
}
