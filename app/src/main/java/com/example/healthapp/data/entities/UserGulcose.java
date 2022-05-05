package com.example.healthapp.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_gulcose")
public class UserGulcose {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int user_id;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "gulcose_breakfast")
    public Double gulcose_breakfast;

    @ColumnInfo(name = "gulcose_lunch")
    public Double gulcose_lunch;

    @ColumnInfo(name = "gulcose_dinner")
    public Double gulcose_dinner;

    @ColumnInfo(name = "gulcose_breakfast_b")
    public Double gulcose_breakfast_b;

    @ColumnInfo(name = "gulcose_lunch_b")
    public Double gulcose_lunch_b;

    @ColumnInfo(name = "gulcose_dinner_b")
    public Double gulcose_dinner_b;

    @ColumnInfo(name = "daily_steps")
    public int dailySteps;

    public UserGulcose(int user_id, String date, Double gulcose_breakfast, Double gulcose_lunch, Double gulcose_dinner, Double gulcose_breakfast_b, Double gulcose_lunch_b, Double gulcose_dinner_b, int dailySteps) {
        this.user_id = user_id;
        this.date = date;
        this.gulcose_breakfast = gulcose_breakfast;
        this.gulcose_lunch = gulcose_lunch;
        this.gulcose_dinner = gulcose_dinner;
        this.gulcose_breakfast_b = gulcose_breakfast_b;
        this.gulcose_lunch_b = gulcose_lunch_b;
        this.gulcose_dinner_b = gulcose_dinner_b;
        this.dailySteps = dailySteps;
    }
}
