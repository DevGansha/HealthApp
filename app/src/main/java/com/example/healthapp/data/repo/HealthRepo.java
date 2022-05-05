package com.example.healthapp.data.repo;

import android.app.Application;
import android.content.Context;

import com.example.healthapp.data.dao.UserDao;
import com.example.healthapp.data.dao.UserGulcoseDao;
import com.example.healthapp.data.database.HealthDB;
import com.example.healthapp.data.entities.UserData;
import com.example.healthapp.data.entities.UserGulcose;

import java.util.List;

public class HealthRepo{

    private final Context app;
    private final HealthDB healthDB;
    private final UserDao userDao;
    private final UserGulcoseDao userGulcoseDao;

    public HealthRepo(Application application) {
        app = application.getApplicationContext();
        healthDB = HealthDB.getInstance(app);
        userDao = healthDB.getUserDao();
        userGulcoseDao = healthDB.getUserGulcoseDao();
    }

    public UserData login(String email, String pwd){
        return userDao.login(email, pwd);
    }

    public long Signup(UserData userData){
        return userDao.signup(userData);
    }

    public long insertData(UserGulcose userGulcose) {return userGulcoseDao.insertData(userGulcose);}

    public List<UserGulcose> getAllGulcoseData(){return userGulcoseDao.getAllGulcoseData(); }

    public UserGulcose getGulcoseDataByDate(String date){return userGulcoseDao.getGulcoseByDate(date); }

    public List<UserGulcose> getGulcoseDataByMonths(String mon){return userGulcoseDao.getGulcoseDataByMonths(mon); }

    public List<UserGulcose> getWeeklySteps(String date, String date2){ return userGulcoseDao.getWeeklySteps(date, date2); }
}
