package com.example.healthapp.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.healthapp.data.entities.UserData;
import com.example.healthapp.data.repo.HealthRepo;

public class LoginViewModel extends AndroidViewModel {

    public HealthRepo healthRepo;

    public LoginViewModel(Application application) {
        super(application);
        healthRepo = new HealthRepo(application);
    }

    public UserData login(String email, String pwd){
        return healthRepo.login(email, pwd);
    }
}
