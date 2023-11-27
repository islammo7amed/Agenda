package com.example.agenda.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CustomerProductCrossRefDao {
    @Insert
    void insertCustomerProduct(CustomerProductCrossRef... customerProductCrossRefs);
    @Transaction
    @Query("SELECT * FROM customers")
    LiveData<List<CustomerWithProduct>> getCustomersWithProducts();
    @Transaction
    @Query("SELECT * FROM product")
    LiveData<List<ProductWithCustomer>> getProductsWithCustomers();
}
