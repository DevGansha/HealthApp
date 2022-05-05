package com.example.healthapp.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.healthapp.data.entities.UserGulcose;
import com.example.healthapp.data.repo.HealthRepo;

import java.util.List;

public class FitnessViewModel extends AndroidViewModel {

    public HealthRepo healthRepo;

    public FitnessViewModel(Application application) {
        super(application);
        healthRepo = new HealthRepo(application);
    }

    public List<UserGulcose> getWeeklySteps(String date, String date2){ return healthRepo.getWeeklySteps(date, date2); }

}
