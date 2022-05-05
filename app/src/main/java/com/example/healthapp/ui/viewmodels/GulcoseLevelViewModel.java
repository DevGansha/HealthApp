package com.example.healthapp.ui.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;

import com.example.healthapp.data.entities.UserGulcose;
import com.example.healthapp.data.repo.HealthRepo;

public class GulcoseLevelViewModel extends AndroidViewModel {

    public HealthRepo healthRepo;

    public GulcoseLevelViewModel(Application application) {
        super(application);
        healthRepo = new HealthRepo(application);
    }

    public UserGulcose getGulcoseDataByDate(String date){return healthRepo.getGulcoseDataByDate(date); }
}
