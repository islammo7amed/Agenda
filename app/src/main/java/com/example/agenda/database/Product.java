package com.example.agenda.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "product")
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long product_id;
    @NonNull
    private String name;
    @NonNull
    private String model_number;
    private double buying_price;
    private double selling_price;
    private long number_of_pieces;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Product() {
    }

    public Product(@NonNull String name, @NonNull String model_number, double buying_price, double selling_price, long number_of_pieces,byte[] image) {
        this.name = name;
        this.model_number = model_number;
        this.buying_price = buying_price;
        this.selling_price = selling_price;
        this.number_of_pieces = number_of_pieces;
        this.image=image;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getModel_number() {
        return model_number;
    }

    public void setModel_number(@NonNull String model_number) {
        this.model_number = model_number;
    }

    public double getBuying_price() {
        return buying_price;
    }

    public void setBuying_price(double buying_price) {
        this.buying_price = buying_price;
    }

    public double getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(double selling_price) {
        this.selling_price = selling_price;
    }

    public long getNumber_of_pieces() {
        return number_of_pieces;
    }

    public void setNumber_of_pieces(long number_of_pieces) {
        this.number_of_pieces = number_of_pieces;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
