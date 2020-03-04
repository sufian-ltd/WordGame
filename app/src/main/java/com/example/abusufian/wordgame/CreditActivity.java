package com.example.abusufian.wordgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class CreditActivity extends AppCompatActivity {

    Animation animation;
    TextView tv1,tv2,tv3,tv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);
        tv1.startAnimation(animation);
        tv2.startAnimation(animation);
        tv3.startAnimation(animation);
        tv4.startAnimation(animation);
    }
}
