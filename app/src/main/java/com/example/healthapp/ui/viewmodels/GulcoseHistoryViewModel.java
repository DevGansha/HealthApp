package com.example.healthapp.ui.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;

import com.example.healthapp.data.entities.UserGulcose;
import com.example.healthapp.data.repo.HealthRepo;

import java.util.List;

public class GulcoseHistoryViewModel extends AndroidViewModel {

    public HealthRepo healthRepo;

    public GulcoseHistoryViewModel(Application application) {
        super(application);
        healthRepo = new HealthRepo(application);
    }

    public List<UserGulcose> getGulcoseDataByMonths(String mon){return healthRepo.getGulcoseDataByMonths(mon); }

}
