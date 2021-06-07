package com.viksingh.covid19tracker.old;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.viksingh.covid19tracker.R;

public class SplashActivity extends AppCompatActivity {

    int SPLASH_TIME = 3000;
    private ProgressBar splashProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(1024, 1024);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.splashProgress = (ProgressBar)findViewById(R.id.splashProgress);
        playProgress();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, GlobalDashboard.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME);
    }
    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", new int[] { 100 }).setDuration(this.SPLASH_TIME).start();
    }
}