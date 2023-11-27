package com.example.agenda.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    void insertProduct(Product... product);
    @Update
    void updateProduct(Product... product);
    @Delete
    void deleteProduct(Product... product);
    @Query("DELETE FROM product WHERE name=:name")
    void deleteProductByName(String name);
    @Query("SELECT * FROM product ORDER BY name ASC")
    LiveData<List<Product>> getAllProducts();
    @Query("SELECT * FROM product WHERE name LIKE '%'|| :name ||'%'")
    LiveData<List<Product>> getProductByName(String name);
    @Query("SELECT * FROM product WHERE product_id=:id")
    LiveData<List<Product>> getProductById(long id);
    @Query("SELECT * FROM product WHERE model_number=:model")
    LiveData<List<Product>> getProductByModel(String model);
}
