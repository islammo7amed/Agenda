package com.example.agenda.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ProductWithCustomer {
    @Embedded
    public Product product;
    @Relation(
            parentColumn = "product_id",
            entityColumn = "customer_id",
            associateBy = @Junction(CustomerProductCrossRef.class)
    )
    public List<Customer> customers;
}
