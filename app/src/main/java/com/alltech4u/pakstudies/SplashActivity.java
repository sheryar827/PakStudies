package com.alltech4u.pakstudies;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView = findViewById(R.id.logo_id);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splashscreen);
        imageView.startAnimation(animation);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                    Intent intent=new Intent(getApplicationContext(),MyQuizBook.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                super.run();
            }
        };
        thread.start();
    }
}
