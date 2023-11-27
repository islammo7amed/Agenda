package com.example.agenda.ui.invoice;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda.R;
import com.example.agenda.adapters.CustomerProductRecyclerViewAdapter;
import com.example.agenda.database.AgendaViewModel;
import com.example.agenda.database.Customer;
import com.example.agenda.database.CustomerInsertedIdListner;
import com.example.agenda.database.Product;
import com.example.agenda.databinding.FragmentCustomerBinding;
import com.example.agenda.listners.OnCustomerProductItemClickListner;
import com.example.agenda.pojo.CustomerProductModel;
import com.example.agenda.pojo.ProductModel;
import com.example.agenda.ui.test;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;

public class CustomerFragment extends Fragment {

    FragmentCustomerBinding binding;
    AgendaViewModel agendaViewModel;
    ArrayList<String> customerType;
    String name, customer_type, customer_state, customer_s;
    Date date_of_buying;
    Calendar selectedDate;
    CustomerProductRecyclerViewAdapter products_adapter;
    //HashMap<Long, CustomerProductModel> selectedProducts = new HashMap<>();
    HashMap<Long, ProductModel> selectedProducts_ = new HashMap<>();
    ArrayAdapter customersAdapter, customerTypeAdapter;
    Customer customer, customerSelected, customerCreated;
    long customer_id;
    public static final String SELECTED_PRODUCTS = "products";
    public static final String SELECTED_CUSTOMER = "customer";
    public static final String CUSTOMER_TYPE_NEW_EN = "New Customer";
    public static final String CUSTOMER_TYPE_NEW_AR = "عميل جديد";
    public static final String CUSTOMER_TYPE_EXIST_EN = "Existing Customer";
    public static final String CUSTOMER_TYPE_EXIST_AR = "عميل موجود";
    public static final String CASE_TWO = "two";
    public static final String CASE_THREE = "three";
    HashMap<Integer, test> t = new HashMap<>();

    public CustomerFragment() {
        // Required empty public constructor
    }

    public static CustomerFragment newInstance() {
        CustomerFragment fragment = new CustomerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        agendaViewModel = new ViewModelProvider(getActivity()).get(AgendaViewModel.class);
        //selectedProducts = new HashMap<>();

        products_adapter = new CustomerProductRecyclerViewAdapter(new ArrayList<>(), new OnCustomerProductItemClickListner() {
            @Override
            public void onItemClick(CustomerProductModel product, boolean state) {
                if (state){
                    //selectedProducts.put(product.getProduct().getProduct_id(),product);
                    selectedProducts_.put(product.getProduct().getProduct_id(),new ProductModel(product.getProduct().getProduct_id(),product.getNumber_of_pieces()));
                    //Toast.makeText(getActivity(), product.getProduct().getName()+ " added", Toast.LENGTH_SHORT).show();
                }else {
                    //selectedProducts.remove(product.getProduct().getProduct_id());
                    selectedProducts_.remove(product.getProduct().getProduct_id());
                    //Toast.makeText(getActivity(), product.getProduct().getName()+ " removed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void ontTextNumOfPiecesChange(Product product,long num_of_pieces) {
                //selectedProducts.remove(product.getProduct_id());
                selectedProducts_.remove(product.getProduct_id());
                //selectedProducts.put(product.getProduct_id(),new CustomerProductModel(product,num_of_pieces));
                selectedProducts_.put(product.getProduct_id(),new ProductModel(product.getProduct_id(),num_of_pieces));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentCustomerBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeVisibilityGone(binding.customerTilName, binding.customerTilDateOfBuying, binding.invoiceAutoCompleteCustomerTil, binding.customerRvProducts);

        customerType=new ArrayList<>();
        customerType.add(getResources().getString(R.string.customer_type_new));
        customerType.add(getResources().getString(R.string.customer_type_exist));

        agendaViewModel.getAllCustomers().observe(getActivity(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                customersAdapter = new ArrayAdapter(getActivity(), R.layout.invoice_custom_product_view,customers);
            }
        });

        binding.customerRvProducts.setAdapter(products_adapter);
        binding.customerRvProducts.setHasFixedSize(true);
        binding.customerRvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));

        customerTypeAdapter = new ArrayAdapter(getActivity(), R.layout.invoice_custom_product_view,customerType);
        binding.invoiceAutoCompleteCustomerTypeTv.setAdapter(customerTypeAdapter);

        binding.invoiceAutoCompleteCustomerTypeTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                customer_type =(String) parent.getItemAtPosition(position);
                customer_state =(String) parent.getItemAtPosition(position);
            }
        });

        binding.invoiceAutoCompleteCustomerTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                customerSelected = (Customer) parent.getAdapter().getItem(position);
            }
        });

        binding.customerBtnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                switch (customer_state){
                    case CUSTOMER_TYPE_NEW_EN:
                    case CUSTOMER_TYPE_NEW_AR:
                        changeVisibilityGone(binding.invoiceAutoCompleteCustomerTypeTil, binding.invoiceAutoCompleteCustomerTil, binding.customerRvProducts);
                        changeVisibilityVisible(binding.customerTilName, binding.customerTilDateOfBuying);
                        changeBackground(new TextView[]{binding.customerTvCircleOne, binding.customerTvLineOne, binding.customerTvCircleTwo},
                                new int[]{R.drawable.invoice_slide, R.drawable.invoice_slide_line_selected_shape, R.drawable.invoice_slide_selected_shape});
                        customer_s = CUSTOMER_TYPE_NEW_EN;
                        customer_state = CASE_TWO;
                        break;
                    case CUSTOMER_TYPE_EXIST_EN:
                    case CUSTOMER_TYPE_EXIST_AR:
                        changeVisibilityGone(binding.invoiceAutoCompleteCustomerTypeTil, binding.customerTilName, binding.customerRvProducts);
                        changeVisibilityVisible(binding.invoiceAutoCompleteCustomerTil, binding.customerTilDateOfBuying);
                        binding.invoiceAutoCompleteCustomerTv.setAdapter(customersAdapter);
                        changeBackground(new TextView[]{binding.customerTvCircleOne, binding.customerTvLineOne, binding.customerTvCircleTwo},
                                new int[]{R.drawable.invoice_slide, R.drawable.invoice_slide_line_selected_shape, R.drawable.invoice_slide_selected_shape});
                        customer_state = CASE_TWO;
                        customer_s = CUSTOMER_TYPE_EXIST_EN;
                        break;
                    case CASE_TWO:
                        if(customer_s.equals(CUSTOMER_TYPE_EXIST_EN)){
                            if(checkEmbtyFields(binding.customerEtDateOfBuying)||binding.invoiceAutoCompleteCustomerTv.getText().toString().isEmpty()){
                                Toast.makeText(getActivity(), getResources().getString(R.string.add_product_toast_enter_data), Toast.LENGTH_SHORT).show();
                            }else {
                                date_of_buying = selectedDate.getTime();
                                customerSelected.setDate_of_buying(date_of_buying);
                                agendaViewModel.updateCustomer(customerSelected);

                                changeVisibilityGone(binding.invoiceAutoCompleteCustomerTypeTil, binding.invoiceAutoCompleteCustomerTil, binding.customerTilName, binding.customerTilDateOfBuying);
                                changeVisibilityVisible(binding.customerRvProducts);
                                changeBackground(new TextView[]{binding.customerTvCircleOne, binding.customerTvLineOne, binding.customerTvCircleTwo, binding.customerTvLineTwo, binding.customerTvCircleThree},
                                        new int[]{R.drawable.invoice_slide, R.drawable.invoice_slide_line_selected_shape, R.drawable.invoice_slide, R.drawable.invoice_slide_line_selected_shape, R.drawable.invoice_slide_selected_shape});
                                customer_state = CASE_THREE;

                                agendaViewModel.getAllProducts().observe(getActivity(), new Observer<List<Product>>() {
                                    @Override
                                    public void onChanged(List<Product> products) {
                                        List<CustomerProductModel> productsModels = new ArrayList<>();
                                        if(products!=null){
                                            for (Product i : products){
                                                productsModels.add(new CustomerProductModel(i,0));
                                            }
                                            products_adapter.setProducts(productsModels);
                                        }

                                    }
                                });

                            }
                        }else {

                            if(checkEmbtyFields(binding.customerEtName,binding.customerEtDateOfBuying)){
                                Toast.makeText(getActivity(), getResources().getString(R.string.add_product_toast_enter_data), Toast.LENGTH_SHORT).show();
                            }else {
                                name = binding.customerEtName.getText().toString();
                                date_of_buying = selectedDate.getTime();
                                customer=new Customer(name,date_of_buying,0.0,0.0,0.0);

                                agendaViewModel.insertCustomer(customer, new CustomerInsertedIdListner() {
                                    @Override
                                    public void onCustomerInserted(long customerId) {
                                        customer_id = customerId;
                                    }
                                });

                                changeVisibilityGone(binding.invoiceAutoCompleteCustomerTypeTil, binding.invoiceAutoCompleteCustomerTil, binding.customerTilName, binding.customerTilDateOfBuying);
                                changeVisibilityVisible(binding.customerRvProducts);
                                changeBackground(new TextView[]{binding.customerTvCircleOne, binding.customerTvLineOne, binding.customerTvCircleTwo, binding.customerTvLineTwo, binding.customerTvCircleThree},
                                        new int[]{R.drawable.invoice_slide, R.drawable.invoice_slide_line_selected_shape, R.drawable.invoice_slide, R.drawable.invoice_slide_line_selected_shape, R.drawable.invoice_slide_selected_shape});
                                customer_state = CASE_THREE;

                                agendaViewModel.getAllProducts().observe(getActivity(), new Observer<List<Product>>() {
                                    @Override
                                    public void onChanged(List<Product> products) {
                                        List<CustomerProductModel> productsModels = new ArrayList<>();
                                        if(products!=null){
                                            for (Product i : products){
                                                productsModels.add(new CustomerProductModel(i,0));
                                            }
                                            products_adapter.setProducts(productsModels);
                                        }

                                    }
                                });
                            }

                        }

                        break;
                    case CASE_THREE:


                        //for (CustomerProductModel c : selectedProducts.values()){
                            //Toast.makeText(getActivity(), c.getProduct().getName(), Toast.LENGTH_SHORT).show();
                        //}

                        //Intent intent = new Intent(getActivity(),EndInvoiceActivity.class);
                        //ArrayList<CustomerProductModel> cu =new ArrayList<>();
                        //for (CustomerProductModel c : selectedProducts.values()){
                            //cu.add(c);
                        //}
                        //intent.putExtra(SELECTED_PRODUCTS,cu);
                        //startActivity(intent);




                        Intent intent = new Intent(getActivity(),EndInvoiceActivity.class);
                        //intent.putExtra(SELECTED_PRODUCTS,selectedProducts);
                        intent.putExtra(SELECTED_PRODUCTS,selectedProducts_);
                        //startActivity(intent);
                        //Toast.makeText(getActivity(), selectedProducts.size()+"", Toast.LENGTH_SHORT).show();
                        if (customer_s.equals(CUSTOMER_TYPE_NEW_EN)){

                            agendaViewModel.getCustomerById(customer_id).observe(getActivity(), new Observer<List<Customer>>() {
                                @Override
                                public void onChanged(List<Customer> customers) {

                                    customerCreated = customers.get(0);
                                    intent.putExtra(SELECTED_CUSTOMER,customerCreated);
                                    startActivity(intent);
                                }
                            });

                        }else if (customer_s.equals(CUSTOMER_TYPE_EXIST_EN)){
                            intent.putExtra(SELECTED_CUSTOMER,customerSelected);
                            startActivity(intent);
                        }
                        //startActivity(intent);

                        break;
                    default:
                        Toast.makeText(getActivity(), getString(R.string.customer_toast_select_item), Toast.LENGTH_SHORT).show();


                }
            }
        });

        binding.customerEtDateOfBuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog=DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        binding.customerEtDateOfBuying.setText((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
                        selectedDate=Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR,year);
                        selectedDate.set(Calendar.MONTH,monthOfYear);
                        selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    }
                }, java.util.Calendar.getInstance());
                dialog.show(getParentFragmentManager(),null);
            }

        });

    }

    public boolean checkEmbtyFields(TextInputEditText... editText){
        for (int i = 0; i < editText.length; i++){
            if(editText[i].getText().toString().isEmpty())
                return true;
        }
        return false;
    }

    public void changeVisibilityVisible(View... views){
        for (View v : views){
            v.setVisibility(View.VISIBLE);
        }
    }

    public void changeVisibilityGone(View... views){
        for (View v : views){
            v.setVisibility(View.GONE);
        }
    }

    public void changeBackground(TextView[] views, int[] backgrounds){
        for (int i = 0; i < views.length; i++){
            views[i].setBackground(ContextCompat.getDrawable(getActivity(), backgrounds[i]));
        }
    }

}