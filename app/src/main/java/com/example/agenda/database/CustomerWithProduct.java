package com.example.agenda.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class CustomerWithProduct {
    @Embedded
    public Customer customer;
    @Relation(
            parentColumn = "customer_id",
            entityColumn = "product_id",
            associateBy = @Junction(CustomerProductCrossRef.class)
    )
    public List<Product> products;
    
}
