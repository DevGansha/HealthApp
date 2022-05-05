package com.example.healthapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.healthapp.R;
import com.example.healthapp.databinding.ActivityMainBinding;
import com.example.healthapp.databinding.HeaderNavigationBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    public ActivityMainBinding binding;
    HeaderNavigationBinding headerNavigationBinding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        headerNavigationBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.header_navigation, binding.containerMain, false);

        binding.navView.addHeaderView(headerNavigationBinding.getRoot());
        binding.navView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedpreferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        headerNavigationBinding.welcomeTxt.setText("Welcome " + sharedpreferences.getString("user_name", ""));
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.logInfoFragment) {
            Navigation.findNavController(requireViewById(R.id.nav_host_fragment)).navigate(R.id.InfoFragment);
        }else if(id == R.id.GulcoseLevelsFragment){
            Navigation.findNavController(requireViewById(R.id.nav_host_fragment)).navigate(R.id.gulcoseLevelsFragment);
        }else if(id == R.id.GulcoseHistoryFragment){
            Navigation.findNavController(requireViewById(R.id.nav_host_fragment)).navigate(R.id.gulcoseHistoryFragment);
        }else if(id == R.id.FitnessFragment){
            Navigation.findNavController(requireViewById(R.id.nav_host_fragment)).navigate(R.id.fitnessFragment);
        }else if(id == R.id.logOutFragment){
            SharedPreferences sharedpreferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("user_id", "");
            editor.putString("user_name", "");
            editor.apply();

            Navigation.findNavController(requireViewById(R.id.nav_host_fragment)).navigate(R.id.mainActivity2);
            Navigation.findNavController(requireViewById(R.id.nav_host_fragment)).popBackStack();
        }
        binding.drawerLayout.closeDrawers();

        return false;
    }
}