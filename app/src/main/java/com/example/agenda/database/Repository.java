package com.example.agenda.database;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class Repository {
    ProductDAO productDAO;
    CustomerDAO customerDAO;
    CustomerProductCrossRefDao customerProductCrossRefDao;

    public Repository(Application application){
        AgendaDatabase db=AgendaDatabase.getDatabase(application);
        productDAO= db.productDAO();
        customerDAO=db.customerDAO();
        customerProductCrossRefDao=db.customerProductCrossRefDao();
    }

    // Product

    void insertProduct(Product... product){
        AgendaDatabase.dataBaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                productDAO.insertProduct(product);
            }
        });
    }

    void updateProduct(Product... product){
        AgendaDatabase.dataBaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                productDAO.updateProduct(product);
            }
        });
    }

    void deleteProduct(Product... product){
        AgendaDatabase.dataBaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                productDAO.deleteProduct(product);
            }
        });
    }

    void deleteProductByName(String name){
        AgendaDatabase.dataBaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                productDAO.deleteProductByName(name);
            }
        });
    }

    LiveData<List<Product>> getAllProducts(){
        return productDAO.getAllProducts();
    }

    LiveData<List<Product>> getProductByName(String name){
        return productDAO.getProductByName(name);
    }

    LiveData<List<Product>> getProductById(long id){
        return productDAO.getProductById(id);
    }

    LiveData<List<Product>> getProductByModel(String model){
        return productDAO.getProductByModel(model);
    }

    // Customer

    void insertCustomer(Customer customer, CustomerInsertedIdListner listner){
        AgendaDatabase.dataBaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run(){
                long id = customerDAO.insertCustomer(customer);
                listner.onCustomerInserted(id);
            }
        });
    }

    void updateCustomer(Customer... customer){
        AgendaDatabase.dataBaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                customerDAO.updateCustomer(customer);
            }
        });
    }

    void deleteCustomer(Customer... customer){
        AgendaDatabase.dataBaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                customerDAO.deleteCustomer(customer);
            }
        });
    }

    void deleteCustomerByName(String name){
        AgendaDatabase.dataBaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                customerDAO.deleteCustomerByName(name);
            }
        });
    }

    LiveData<List<Customer>> getAllCustomers(){
        return customerDAO.getAllCustomers();
    }

    LiveData<List<Customer>> getAllCustomersForDay(Date date){
        return customerDAO.getAllCustomersForDay(date);
    }

    LiveData<List<Customer>> getAllCustomersByDate(Date from,Date to){
        return customerDAO.getAllCustomersByDate(from,to);
    }

    LiveData<List<Customer>> getCustomerByName(String name){
        return customerDAO.getCustomerByName(name);
    }

    LiveData<List<Customer>> getCustomerById(long id){
        return customerDAO.getCustomerById(id);
    }

    LiveData<List<Customer>> getCustomersArrears(){
        return customerDAO.getCustomersArrears();
    }

    void getPriceOfBuyingByDate(Date from,Date to,DoubleValueListner listner){
        AgendaDatabase.dataBaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                double value = customerDAO.getPriceOfBuyingByDate(from,to);
                listner.onValueSubmit(value);
            }
        });
    }

    void getArrearsByDate(Date from,Date to,DoubleValueListner listner){
        AgendaDatabase.dataBaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                double value = customerDAO.getArrearsByDate(from,to);
                listner.onValueSubmit(value);
            }
        });
    }

    // CustomerProductCrossRef

    void insertCustomerProduct(CustomerProductCrossRef... customerProductCrossRefs){
        AgendaDatabase.dataBaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                customerProductCrossRefDao.insertCustomerProduct(customerProductCrossRefs);
            }
        });
    }

    LiveData<List<CustomerWithProduct>> getCustomersWithProducts(){
        return customerProductCrossRefDao.getCustomersWithProducts();
    }

    LiveData<List<ProductWithCustomer>> getProductsWithCustomers(){
        return customerProductCrossRefDao.getProductsWithCustomers();
    }
}
