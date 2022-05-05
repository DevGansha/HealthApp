package com.example.healthapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.healthapp.data.entities.UserData;

@Dao
public interface UserDao {

    @Insert
    public long signup(UserData userData);

    @Query("select * from user_data where email = :email and password = :pwd")
    public UserData login(String email, String pwd);

    @Query("select * from user_data")
    public UserData getUserData();


}
