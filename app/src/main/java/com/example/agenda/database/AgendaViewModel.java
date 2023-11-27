package com.example.agenda.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class AgendaViewModel extends AndroidViewModel {

    Repository repository;

    public AgendaViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    // Product

    public void insertProduct(Product... product){
        repository.insertProduct(product);
    }

    public void updateProduct(Product... product){
        repository.updateProduct(product);
    }

    public void deleteProduct(Product... product){
        repository.deleteProduct(product);
    }

    public void deleteProductByName(String name){
        repository.deleteProductByName(name);
    }

    public LiveData<List<Product>> getAllProducts(){
        return repository.getAllProducts();
    }

    public LiveData<List<Product>> getProductByName(String name){
        return repository.getProductByName(name);
    }

    public LiveData<List<Product>> getProductById(long id){
        return repository.getProductById(id);
    }

    public LiveData<List<Product>> getProductByModel(String model){
        return repository.getProductByModel(model);
    }

    // Customer

    public void insertCustomer(Customer customer, CustomerInsertedIdListner listner){
         repository.insertCustomer(customer, listner);
    }

    public LiveData<List<Customer>> getCustomerById(long id){
        return repository.getCustomerById(id);
    }

    public void updateCustomer(Customer... customer){
        repository.updateCustomer(customer);
    }

    public void deleteCustomer(Customer... customer){
        repository.deleteCustomer(customer);
    }

    public void deleteCustomerByName(String name){
        repository.deleteCustomerByName(name);
    }

    public LiveData<List<Customer>> getAllCustomers(){
        return repository.getAllCustomers();
    }

    public LiveData<List<Customer>> getAllCustomersForDay(Date date){
        return repository.getAllCustomersForDay(date);
    }

    public LiveData<List<Customer>> getAllCustomersByDate(Date from,Date to){
        return repository.getAllCustomersByDate(from,to);
    }

    public LiveData<List<Customer>> getCustomerByName(String name){
        return repository.getCustomerByName(name);
    }

    public LiveData<List<Customer>> getCustomersArrears(){
        return repository.getCustomersArrears();
    }

    public void getPriceOfBuyingByDate(Date from,Date to,DoubleValueListner listner){
        repository.getPriceOfBuyingByDate(from,to,listner);
    }

    public void getArrearsByDate(Date from,Date to,DoubleValueListner listner){
        repository.getArrearsByDate(from,to,listner);
    }

    // CustomerProductCrossRef

    public void insertCustomerProduct(CustomerProductCrossRef... customerProductCrossRefs){
        repository.insertCustomerProduct(customerProductCrossRefs);
    }

    public LiveData<List<CustomerWithProduct>> getCustomersWithProducts(){
        return repository.getCustomersWithProducts();
    }

    public LiveData<List<ProductWithCustomer>> getProductsWithCustomers(){
        return repository.getProductsWithCustomers();
    }

}
