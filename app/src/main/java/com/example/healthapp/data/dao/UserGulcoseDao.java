package com.example.healthapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.healthapp.data.entities.UserData;
import com.example.healthapp.data.entities.UserGulcose;

import java.util.List;

@Dao
public interface UserGulcoseDao {

    @Insert
    public long insertData(UserGulcose userGulcose);

    @Query("select * from user_gulcose")
    public List<UserGulcose> getAllGulcoseData();

    @Query("select * from user_gulcose c where c.date like :date order by id desc limit 1" )
    public UserGulcose getGulcoseByDate(String date);

    @Query("select * from user_gulcose c where c.date LIKE '%/' || :mon || '/%'")
    public List<UserGulcose> getGulcoseDataByMonths(String mon);

    @Query("select * from user_gulcose c where date between :date and :date2")
    public List<UserGulcose> getWeeklySteps(String date, String date2);
}
