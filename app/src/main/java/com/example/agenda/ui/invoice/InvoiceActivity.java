package com.example.agenda.ui.invoice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.agenda.R;
import com.example.agenda.database.AgendaViewModel;
import com.example.agenda.database.Customer;
import com.example.agenda.database.CustomerProductCrossRef;
import com.example.agenda.database.Product;
import com.example.agenda.databinding.ActivityInvoiceBinding;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceActivity extends AppCompatActivity {

    ActivityInvoiceBinding binding;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar invoice_toolbar= binding.invoiceToolbar;
        setSupportActionBar(invoice_toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.invoice_tool_bar_title);
        actionBar.show();

        fm=getSupportFragmentManager();

        CustomerFragment customerFragment=CustomerFragment.newInstance();
        fm.beginTransaction().replace(R.id.invoice_container,customerFragment).commit();

    }
}