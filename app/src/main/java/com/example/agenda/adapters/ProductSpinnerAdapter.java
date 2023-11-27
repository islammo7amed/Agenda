package com.example.agenda.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.database.Product;

import java.util.List;

public class ProductSpinnerAdapter extends BaseAdapter  {

    List<Product> products;

    public ProductSpinnerAdapter(List<Product> products) {
        this.products = products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.get(position).getProduct_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_custom_product_view,null,false);
            //TextView tv_name=v.findViewById(R.id.invoice_custom_tv_name);
            //Product product=getItem(position);
            //tv_name.setText(product.getName());
        }
        return v;
    }
}
