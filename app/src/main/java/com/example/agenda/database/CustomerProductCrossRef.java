package com.example.agenda.database;

import androidx.room.Entity;

@Entity(primaryKeys = {"customer_id","product_id"})
public class CustomerProductCrossRef {
    private long customer_id;
    private long product_id;

    public CustomerProductCrossRef() {
    }

    public CustomerProductCrossRef(long customer_id, long product_id) {
        this.customer_id = customer_id;
        this.product_id = product_id;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }
}
