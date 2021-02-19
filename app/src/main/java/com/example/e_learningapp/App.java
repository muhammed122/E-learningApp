package com.example.e_learningapp;

import android.app.Application;

import com.example.e_learningapp.data.MySharedPrefrance;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {


    @Override
    public void onCreate()
    {
        super.onCreate();
        MySharedPrefrance.init(this);
    }
}
