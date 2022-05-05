package com.example.healthapp.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_data")
public class UserData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "full_name")
    public String full_name;
    @ColumnInfo(name = "age")
    public int age;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "password")
    public String password;

    public UserData(String full_name, int age, String email, String password) {
        this.full_name = full_name;
        this.age = age;
        this.email = email;
        this.password = password;
    }
}