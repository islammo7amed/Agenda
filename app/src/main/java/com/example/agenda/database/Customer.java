package com.example.agenda.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "customers")
@TypeConverters({DateConverter.class})
public class Customer implements Serializable {
    @PrimaryKey(autoGenerate = true )
    private long customer_id;
    @NonNull
    private String name;
    @NonNull
    private Date date_of_buying;
    private double price_of_buying;
    private double cash_paid;
    private double debt;

    public Customer() {
    }

    public Customer(long customer_id, @NonNull String name, @NonNull Date date_of_buying, double price_of_buying, double cash_paid, double debt) {
        this.customer_id = customer_id;
        this.name = name;
        this.date_of_buying = date_of_buying;
        this.price_of_buying = price_of_buying;
        this.cash_paid = cash_paid;
        this.debt = debt;
    }

    public Customer(@NonNull String name, @NonNull Date date_of_buying, double price_of_buying, double cash_paid, double debt) {
        this.name = name;
        this.date_of_buying = date_of_buying;
        this.price_of_buying = price_of_buying;
        this.cash_paid = cash_paid;
        this.debt = debt;

    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Date getDate_of_buying() {
        return date_of_buying;
    }

    public void setDate_of_buying(@NonNull Date date_of_buying) {
        this.date_of_buying = date_of_buying;
    }

    public double getPrice_of_buying() {
        return price_of_buying;
    }

    public void setPrice_of_buying(double price_of_buying) {
        this.price_of_buying = price_of_buying;
    }

    public double getCash_paid() {
        return cash_paid;
    }

    public void setCash_paid(double cash_paid) {
        this.cash_paid = cash_paid;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    @Override
    public String toString() {
        return name;
    }
}
