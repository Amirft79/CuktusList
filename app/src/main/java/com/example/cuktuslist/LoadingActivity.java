package com.example.cuktuslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingActivity.this,HomeActivity.class));
            }
        },3000);
        int num=20;

    }
}