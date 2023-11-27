package com.example.agenda.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agenda.R;
import com.example.agenda.adapters.ProductRecyclerViewAdapter;
import com.example.agenda.database.AgendaViewModel;
import com.example.agenda.database.Product;
import com.example.agenda.databinding.FragmentHomeBinding;
import com.example.agenda.listners.OnProductItemClickListner;
import com.example.agenda.ui.addproduct.AddProductActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    AgendaViewModel agendaViewModel;
    ProductRecyclerViewAdapter adapter;
    public static final String PRODUCT_KEY = "product";
    public final int SPAN_COUNT = 2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        agendaViewModel = new ViewModelProvider(getActivity()).get(AgendaViewModel.class);
        adapter = new ProductRecyclerViewAdapter(new ArrayList<>(), new OnProductItemClickListner() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                intent.putExtra(PRODUCT_KEY, product);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeBinding.inflate(inflater,container,false);

        Toolbar home_toolbar= binding.homeToolbar;
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.setSupportActionBar(home_toolbar);

        ActionBar actionBar=activity.getSupportActionBar();
        actionBar.setTitle(R.string.main_tv_home);
        actionBar.show();


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.homeRv.setAdapter(adapter);
        binding.homeRv.setHasFixedSize(true);
        binding.homeRv.setLayoutManager(new GridLayoutManager(getActivity(),SPAN_COUNT));

        agendaViewModel.getAllProducts().observe(getActivity(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if(products!=null){
                    adapter.setProducts(products);
                }
            }
        });

         
    }
}