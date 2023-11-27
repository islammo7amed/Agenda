package com.example.agenda.ui.invoice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.agenda.MainActivity;
import com.example.agenda.R;
import com.example.agenda.adapters.EndInvoiceProductRecyclerViewAdapter;
import com.example.agenda.adapters.InvoiceProductRecyclerViewAdapter;
import com.example.agenda.database.AgendaViewModel;
import com.example.agenda.database.Customer;
import com.example.agenda.database.Product;
import com.example.agenda.databinding.ActivityEndInvoiceBinding;
import com.example.agenda.pojo.CustomerProductModel;
import com.example.agenda.pojo.ProductModel;
import com.example.agenda.ui.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EndInvoiceActivity extends AppCompatActivity {

    ActivityEndInvoiceBinding binding;
    AgendaViewModel agendaViewModel;
    HashMap <Long, CustomerProductModel> selectedProducts = new HashMap<>();
    HashMap <Long, ProductModel> selectedProducts_;
    ArrayList<CustomerProductModel> products ;
    ArrayList<CustomerProductModel> products_ ;
    ArrayList<Product> pro = new ArrayList<>() ;
    ArrayList<Long> nums= new ArrayList<>();
    ArrayList<ProductModel> productModels = new ArrayList<>();
    EndInvoiceProductRecyclerViewAdapter adapter;
    InvoiceProductRecyclerViewAdapter adapter_;
    Double totalPrice = 0.0;
    Double cashPaid = 0.0;
    Double discount = 0.0;
    Double debt = 0.0;
    Double oldDebt = 0.0;
    Double allDebt =0.0;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEndInvoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        //selectedProducts = (HashMap<Long, CustomerProductModel>) intent.getSerializableExtra("products");
        selectedProducts_ = (HashMap<Long, ProductModel>) intent.getSerializableExtra("products");
        customer = (Customer) intent.getSerializableExtra("customer");

        //Toast.makeText(this, selectedProducts.size()+"", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, selectedProducts_.size()+"", Toast.LENGTH_SHORT).show();
        Toolbar end_invoice_toolbar= binding.endInvoiceToolbar;
        setSupportActionBar(end_invoice_toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.invoice_tool_bar_title);
        actionBar.show();

        agendaViewModel = new ViewModelProvider(this).get(AgendaViewModel.class);



        binding.endInvoiceCustomerTvName.setText(customer.getName());
        binding.endInvoiceCustomerTvDate.setText(String.valueOf(customer.getDate_of_buying()));
        binding.endInvoiceCustomerTvDebt.setText(String.valueOf(customer.getDebt()));

        for (ProductModel i : selectedProducts_.values()){
            productModels.add(i);
        }

        adapter_ = new InvoiceProductRecyclerViewAdapter(productModels,agendaViewModel,EndInvoiceActivity.this);

        binding.endInvoiceRvProducts.setAdapter(adapter_);
        binding.endInvoiceRvProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.endInvoiceRvProducts.setHasFixedSize(true);

        oldDebt = customer.getDebt();
        allDebt = oldDebt;

        for (ProductModel p : selectedProducts_.values()){
            agendaViewModel.getProductById(p.getProductId()).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    Product pr = products.get(0);
                    totalPrice += (pr.getSelling_price()) * (p.getNumber_of_pieces());
                    //Toast.makeText(EndInvoiceActivity.this, totalPrice+"", Toast.LENGTH_SHORT).show();
                    binding.endInvoiceTvTotalPrice.setText(totalPrice + " L.E");

                    long num = pr.getNumber_of_pieces() - p.getNumber_of_pieces();

                    pro.add(pr);
                    nums.add(num);

                    //long num = i.getProduct().getNumber_of_pieces() - i.getNumber_of_pieces();
                    //Product p = i.getProduct();
                    //p.setNumber_of_pieces(num);
                }
            });
        }

        //Toast.makeText(this, totalPrice+"", Toast.LENGTH_SHORT).show();

        //binding.endInvoiceTvTotalPrice.setText(totalPrice + " L.E");

        binding.endInvoiceEtCashPaid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    cashPaid = 0.0;
                }else {
                    cashPaid = Double.parseDouble(s.toString());
                }
                debt = totalPrice - (cashPaid + discount);
                allDebt = debt + oldDebt;
                binding.endInvoiceCustomerTvDebt.setText(String.valueOf(allDebt));
            }
        });

        binding.endInvoiceEtDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    discount = 0.0;
                }else {
                    discount = Double.parseDouble(s.toString());
                }
                debt = totalPrice - (cashPaid + discount);
                allDebt = debt + oldDebt;
                binding.endInvoiceCustomerTvDebt.setText(String.valueOf(allDebt));
            }
        });

        binding.endInvoiceBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customer.setCash_paid(cashPaid);
                customer.setDebt(allDebt);
                customer.setPrice_of_buying(totalPrice);

                agendaViewModel.updateCustomer(customer);

                for (int i=0; i<pro.size(); i++){
                    Product prod = pro.get(i);
                    prod.setNumber_of_pieces(nums.get(i));
                    agendaViewModel.updateProduct(prod);
                }

                //for (CustomerProductModel i : products) {
                    //long num = i.getProduct().getNumber_of_pieces() - i.getNumber_of_pieces();
                    //Product p = i.getProduct();
                    //p.setNumber_of_pieces(num);
                    //agendaViewModel.updateProduct(p);
                //}

                //Toast.makeText(EndInvoiceActivity.this, customer.getCustomer_id()+"", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        /*



        for (ProductModel i : selectedProducts_.values()){
            agendaViewModel.getProductById(i.getProductId()).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    //selectedProducts.put(products.get(0).getProduct_id(), new CustomerProductModel(products.get(0), i.getNumber_of_pieces()));
                    //Toast.makeText(EndInvoiceActivity.this, selectedProducts.size()+"", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(EndInvoiceActivity.this, products.get(0).getName()+"", Toast.LENGTH_SHORT).show();
                }
            });
        }


        //Toast.makeText(this, selectedProducts.size()+"", Toast.LENGTH_SHORT).show();




        oldDebt = customer.getDebt();

        //Toast.makeText(this, selectedProducts.size()+"", Toast.LENGTH_SHORT).show();

        for (ProductModel i : selectedProducts_.values()){
            agendaViewModel.getProductById(i.getProductId()).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    Product p =products.get(0);

                    selectedProducts.put(p.getProduct_id(),new CustomerProductModel(p,i.getNumber_of_pieces()));

                }
            });
        }


        for (CustomerProductModel i : selectedProducts.values()) {
            totalPrice += (i.getProduct().getSelling_price() * i.getNumber_of_pieces());
            products.add(i);
            Toast.makeText(this, i.getProduct().getName()+"", Toast.LENGTH_SHORT).show();
        }


        adapter = new EndInvoiceProductRecyclerViewAdapter(products);

        binding.endInvoiceRvProducts.setAdapter(adapter);
        binding.endInvoiceRvProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.endInvoiceRvProducts.setHasFixedSize(true);

        binding.endInvoiceTvTotalPrice.setText(totalPrice + " L.E");

        binding.endInvoiceEtCashPaid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    cashPaid = 0.0;
                }else {
                    cashPaid = Double.parseDouble(s.toString());
                }
                debt = totalPrice - (cashPaid + discount);
                allDebt = debt + oldDebt;
                binding.endInvoiceCustomerTvDebt.setText(String.valueOf(allDebt));
            }
        });

        binding.endInvoiceEtDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    discount = 0.0;
                }else {
                    discount = Double.parseDouble(s.toString());
                }
                debt = totalPrice - (cashPaid + discount);
                allDebt = debt + oldDebt;
                binding.endInvoiceCustomerTvDebt.setText(String.valueOf(allDebt));
            }
        });



        binding.endInvoiceBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customer.setCash_paid(cashPaid);
                customer.setDebt(allDebt);
                customer.setPrice_of_buying(totalPrice);

                agendaViewModel.updateCustomer(customer);

                for (CustomerProductModel i : products) {
                    long num = i.getProduct().getNumber_of_pieces() - i.getNumber_of_pieces();
                    Product p = i.getProduct();
                    p.setNumber_of_pieces(num);
                    agendaViewModel.updateProduct(p);
                }

                Toast.makeText(EndInvoiceActivity.this, customer.getCustomer_id()+"", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

         */


    }
}