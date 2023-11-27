package com.example.agenda.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.database.AgendaViewModel;
import com.example.agenda.database.Product;
import com.example.agenda.databinding.HomeCustomProductViewBinding;
import com.example.agenda.listners.OnProductItemClickListner;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {

    List<Product> products;
    OnProductItemClickListner listner;

    public ProductRecyclerViewAdapter(List<Product> products, OnProductItemClickListner listner) {
        this.products = products;
        this.listner = listner;
    }

    public List<Product> getProducts(){
        return products;
    }

    public void setProducts(List<Product> products){
        this.products=products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_custom_product_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = products.get(position);
        holder.bind(product);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        HomeCustomProductViewBinding binding;
        Product product;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = HomeCustomProductViewBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onItemClick(product);
                }
            });
        }

        public void bind (Product product){
            this.product=product;
            byte[] im=product.getImage();
            if(im==null){
                binding.customProductIv.setImageResource(R.drawable.ic_image_hint);
            }else{
                Bitmap bitmap= BitmapFactory.decodeByteArray(product.getImage(),0,product.getImage().length);
                binding.customProductIv.setImageBitmap(bitmap);
            }

            String productName = product.getName();
            String subName;

            if (productName.length() > 11){
                subName = productName.substring(0,11);
                binding.customProductTvName.setText(subName + "...");
            }else {
                binding.customProductTvName.setText(productName);
            }

            binding.customProductTvNumberOfPieces.setText(String.valueOf(product.getNumber_of_pieces()));
            binding.customProductTvPrice.setText(String.valueOf(product.getSelling_price()) + " L.E");

        }
    }
}
