package com.example.notesroom;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

public class App extends Application {

    public static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "notesRoom").build();
        Log.i("Hello", "Database created");
    }

    public AppDatabase getDatabase()
    {
        return database;
    }
}

