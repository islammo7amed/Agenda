package com.example.agenda.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.database.AgendaViewModel;
import com.example.agenda.database.Product;
import com.example.agenda.databinding.CustomerCustomProductViewBinding;
import com.example.agenda.listners.OnCustomerProductItemClickListner;
import com.example.agenda.pojo.CustomerProductModel;

import java.util.List;

public class CustomerProductRecyclerViewAdapter  extends RecyclerView.Adapter<CustomerProductRecyclerViewAdapter.CustomerProductViewHolder> {

    List<CustomerProductModel> products;
    OnCustomerProductItemClickListner listner;

    public CustomerProductRecyclerViewAdapter(List<CustomerProductModel> products, OnCustomerProductItemClickListner listner) {
        this.products = products;
        this.listner = listner;
    }

    public List<CustomerProductModel> getProducts(){
        return products;
    }

    public void setProducts(List<CustomerProductModel> products){
        this.products=products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomerProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_custom_product_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerProductViewHolder holder, int position) {
        CustomerProductModel product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class CustomerProductViewHolder extends RecyclerView.ViewHolder{

        CustomerCustomProductViewBinding binding;
        CustomerProductModel product;

        public CustomerProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomerCustomProductViewBinding.bind(itemView);
            binding.customerCustomCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (binding.customerCustomCb.isChecked()){
                        listner.onItemClick(product,true);
                        binding.customerCustomEtPieces.setVisibility(View.VISIBLE);
                        binding.customerCustomEtPieces.setText(0+"");
                    }else {
                        listner.onItemClick(product,false);
                        binding.customerCustomEtPieces.setVisibility(View.GONE);
                    }
                }
            });
        }

        public void bind (CustomerProductModel product){
            this.product=product;
            byte[] im=product.getProduct().getImage();
            if(im==null){
                binding.customerCustomIv.setImageResource(R.drawable.ic_image_hint);
            }else{
                Bitmap bitmap= BitmapFactory.decodeByteArray(product.getProduct().getImage(),0,product.getProduct().getImage().length);
                binding.customerCustomIv.setImageBitmap(bitmap);
            }
            binding.customerCustomTvName.setText(product.getProduct().getName());
            binding.customerCustomTvPieces.setText(String.valueOf(product.getProduct().getNumber_of_pieces()));

            binding.customerCustomEtPieces.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    long num;
                    if (s.toString().isEmpty()){
                        num = 0;
                    }else {
                        num = Long.parseLong(s.toString());
                    }
                    listner.ontTextNumOfPiecesChange(product.getProduct(),num);

                }
            });

        }
    }
}
