package com.example.e_learningapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.loadingview.LoadingView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {



   static LoadingView loadingView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingView = findViewById(R.id.load);


    }

    public static void startLoading(){
        loadingView.start();
        loadingView.setVisibility(View.VISIBLE);

    }
    public static void stopLoading(){

        loadingView.setVisibility(View.GONE);
        loadingView.stop();

    }

}