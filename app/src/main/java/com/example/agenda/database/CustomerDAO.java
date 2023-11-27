package com.example.agenda.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
@TypeConverters({DateConverter.class})
public interface CustomerDAO {
    @Insert
    long insertCustomer(Customer customer);
    @Update
    void updateCustomer(Customer... customer);
    @Delete
    void deleteCustomer(Customer... customer);
    @Query("DELETE FROM customers WHERE name=:name")
    void deleteCustomerByName(String name);
    @Query("SELECT * FROM customers WHERE customer_id=:id")
    LiveData<List<Customer>> getCustomerById(long id);
    @Query("SELECT * FROM customers ORDER BY name ASC")
    LiveData<List<Customer>> getAllCustomers();
    @Query("SELECT * FROM customers WHERE date_of_buying=:date ORDER BY name ASC")
    LiveData<List<Customer>> getAllCustomersForDay(Date date);
    @Query("SELECT * FROM customers WHERE date_of_buying>=:from AND date_of_buying<=:to")
    LiveData<List<Customer>> getAllCustomersByDate(Date from,Date to);
    @Query("SELECT * FROM customers WHERE name LIKE '%' || :name || '%' ORDER BY name ASC")
    LiveData<List<Customer>> getCustomerByName(String name);
    @Query("SELECT * FROM customers WHERE debt > 0")
    LiveData<List<Customer>> getCustomersArrears();
    @Query("SELECT sum(price_of_buying) FROM customers WHERE date_of_buying >= :from AND date_of_buying <= :to")
    double getPriceOfBuyingByDate(Date from,Date to);
    @Query("SELECT sum(price_of_buying - cash_paid) FROM customers WHERE date_of_buying >= :from AND date_of_buying <= :to")
    double getArrearsByDate(Date from,Date to);
}
