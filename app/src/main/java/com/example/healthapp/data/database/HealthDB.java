package com.example.healthapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.healthapp.data.dao.UserGulcoseDao;
import com.example.healthapp.data.entities.UserData;
import com.example.healthapp.data.dao.UserDao;
import com.example.healthapp.data.entities.UserGulcose;

@Database(entities = { UserData.class, UserGulcose.class}, version = 3, exportSchema = false)
public abstract class HealthDB extends RoomDatabase {

    private static final String DB_NAME = "HealthApp.db";
    private static volatile HealthDB instance;

    public static synchronized HealthDB getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public static HealthDB create(final Context context) {
        return Room.databaseBuilder(
                context,
                HealthDB.class,
                DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public abstract UserDao getUserDao();
    public abstract UserGulcoseDao getUserGulcoseDao();
}
