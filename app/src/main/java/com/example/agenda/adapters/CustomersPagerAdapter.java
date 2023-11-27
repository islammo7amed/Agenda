package com.example.agenda.adapters;

import android.text.InputFilter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.agenda.ui.home.AllCustomersFragment;
import com.example.agenda.ui.home.DebtsFragment;

public class CustomersPagerAdapter extends FragmentStateAdapter {


    public CustomersPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 :
                return DebtsFragment.newInstance();
            case 1 :
                return AllCustomersFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}