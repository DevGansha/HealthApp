package com.example.healthapp.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.healthapp.data.entities.UserData;
import com.example.healthapp.data.entities.UserGulcose;
import com.example.healthapp.data.repo.HealthRepo;

public class InfoViewModel extends AndroidViewModel {

    public HealthRepo healthRepo;

    public InfoViewModel(Application application) {
        super(application);
        healthRepo = new HealthRepo(application);
    }

    public long insertGulcoseData(UserGulcose userGulcose) {return healthRepo.insertData(userGulcose);}
}
