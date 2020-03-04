package com.example.abusufian.wordgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class HelpGameActivity extends AppCompatActivity {

    TextView helpTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_game);
        helpTv= findViewById(R.id.textViewHelp);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_down);
        helpTv.startAnimation(animation);
    }
}
