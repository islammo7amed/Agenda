package com.example.agenda.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.agenda.database.Product;

import java.io.Serializable;

public class CustomerProductModel implements Serializable {
    Product product;
    long number_of_pieces;

    public CustomerProductModel(Product product, long number_of_pieces) {
        this.product = product;
        this.number_of_pieces = number_of_pieces;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getNumber_of_pieces() {
        return number_of_pieces;
    }

    public void setNumber_of_pieces(long number_of_pieces) {
        this.number_of_pieces = number_of_pieces;
    }

}
