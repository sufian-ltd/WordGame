package com.example.abusufian.wordgame;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    //TextView tv;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        tv=findViewById(R.id.tv);
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.together);
//        tv.startAnimation(animation);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent intent=new Intent(SplashScreenActivity.this,MainMenuActivity.class);
                startActivity(intent);
            }
        },3800);
    }
}
