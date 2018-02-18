package com.example.sajagjain.mework;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=3000;
    TextView appName;
    ViewGroup splashLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                SplashScreen.this.finish();
            }
        },SPLASH_TIME_OUT);
    }
}
