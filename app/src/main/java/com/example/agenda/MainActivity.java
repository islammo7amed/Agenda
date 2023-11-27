package com.example.agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.agenda.databinding.ActivityMainBinding;
import com.example.agenda.ui.addproduct.AddProductActivity;
import com.example.agenda.ui.home.CustomersFragment;
import com.example.agenda.ui.home.HomeFragment;
import com.example.agenda.ui.home.SalesFragment;
import com.example.agenda.ui.home.StatisticsFragment;
import com.example.agenda.ui.invoice.InvoiceActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FragmentManager fm=getSupportFragmentManager();
    public static int MAIN_FLAG=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        HomeFragment homeFragment=HomeFragment.newInstance();
        fm.beginTransaction().replace(R.id.container,homeFragment).addToBackStack(null).commit();
        convertTextViewColor(binding.mainTvHome,R.color.shopping_color);

        binding.fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        binding.mainTvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTextViewColor(binding.mainTvHome,R.color.shopping_color);
                convertTextViewColor(binding.mainTvCustomers,R.color.white);
                convertTextViewColor(binding.mainTvSales,R.color.white);
                convertTextViewColor(binding.mainTvStatistics,R.color.white);
                HomeFragment homeFragment = HomeFragment.newInstance();
                fm.beginTransaction().replace(R.id.container,homeFragment).commit();
                MAIN_FLAG = 0;

            }
        });

        binding.mainTvCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTextViewColor(binding.mainTvHome,R.color.white);
                convertTextViewColor(binding.mainTvCustomers,R.color.shopping_color);
                convertTextViewColor(binding.mainTvSales,R.color.white);
                convertTextViewColor(binding.mainTvStatistics,R.color.white);
                CustomersFragment customersFragment = CustomersFragment.newInstance();
                fm.beginTransaction().replace(R.id.container,customersFragment).commit();
                MAIN_FLAG = 1;
            }
        });

        binding.mainTvSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTextViewColor(binding.mainTvHome,R.color.white);
                convertTextViewColor(binding.mainTvCustomers,R.color.white);
                convertTextViewColor(binding.mainTvSales,R.color.shopping_color);
                convertTextViewColor(binding.mainTvStatistics,R.color.white);
                SalesFragment salesFragment = SalesFragment.newInstance();
                fm.beginTransaction().replace(R.id.container,salesFragment).commit();
                MAIN_FLAG = 1;
            }
        });

        binding.mainTvStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTextViewColor(binding.mainTvHome,R.color.white);
                convertTextViewColor(binding.mainTvCustomers,R.color.white);
                convertTextViewColor(binding.mainTvSales,R.color.white);
                convertTextViewColor(binding.mainTvStatistics,R.color.shopping_color);
                StatisticsFragment statisticsFragment = StatisticsFragment.newInstance();
                fm.beginTransaction().replace(R.id.container,statisticsFragment).commit();
                MAIN_FLAG = 1;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.shopping :
                Intent intent=new Intent(MainActivity.this, InvoiceActivity.class);
                startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (fm.getBackStackEntryCount() >= 1 && MAIN_FLAG==1) {
            convertTextViewColor(binding.mainTvHome,R.color.shopping_color);
            convertTextViewColor(binding.mainTvCustomers,R.color.white);
            HomeFragment homeFragment = HomeFragment.newInstance();
            fm.beginTransaction().replace(R.id.container, homeFragment).commit();
            fm.popBackStack();
        }
        else {
            finish();
        }
    }

    public void convertTextViewColor(TextView textView, int color){
        Drawable drawable=textView.getCompoundDrawables()[1];

        drawable.setTint(getColor(color));

        textView.setCompoundDrawables(null,drawable,null,null);
        textView.setTextColor(getColor(color));
    }
}