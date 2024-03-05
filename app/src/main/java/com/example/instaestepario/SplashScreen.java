package com.example.instaestepario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    ImageView appLogoImage;
    TextView appNombreText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run(){
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 2000);

        appLogoImage = findViewById(R.id.logoimage);
        appNombreText = findViewById(R.id.puta);

        Animation animationUp = AnimationUtils.loadAnimation(this, R.anim.anim_up);
        Animation animationDown = AnimationUtils.loadAnimation(this, R.anim.anim_down);

        appLogoImage.setAnimation(animationUp);
        appLogoImage.setAnimation(animationDown);
        appNombreText.setAnimation(animationDown);
    }
}