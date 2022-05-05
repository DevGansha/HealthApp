package com.example.healthapp.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.healthapp.data.entities.UserData;
import com.example.healthapp.data.repo.HealthRepo;

public class SignupViewModel extends AndroidViewModel {

    public HealthRepo healthRepo;

    public SignupViewModel(Application application) {
        super(application);
        healthRepo = new HealthRepo(application);
    }

    public long signup(UserData userData){
        return healthRepo.Signup(userData);
    }
}
