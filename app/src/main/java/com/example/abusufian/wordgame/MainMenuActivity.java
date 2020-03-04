package com.example.abusufian.wordgame;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainMenuActivity extends AppCompatActivity {

    Button singleButton,crediteButton,multitButton,helpButton;
    Animation animation;
    ToggleButton toggleButton;
    String snd="on";
    SoundPool sp;
    int sound1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        singleButton= findViewById(R.id.buttonSingle);
        multitButton= findViewById(R.id.buttonMulti);
        crediteButton= findViewById(R.id.ButtonCredite);
        helpButton= findViewById(R.id.ButtonHelp);
        toggleButton= findViewById(R.id.toggleButton);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);
        singleButton.startAnimation(animation);
        multitButton.startAnimation(animation);
        helpButton.startAnimation(animation);
        toggleButton.startAnimation(animation);
        crediteButton.startAnimation(animation);
        sp=new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        sound1=sp.load(getApplicationContext(),R.raw.click,1);
    }

    public void creditClick(View view) {
        Intent intent=new Intent(MainMenuActivity.this,CreditActivity.class);
        playSound();
        startActivity(intent);
    }

    public void startClick(View view) {
        playSound();
        Intent intent=new Intent(MainMenuActivity.this,GameBoardActivity.class);
        intent.putExtra("first","You : ");
        intent.putExtra("second", "Computer : ");
        intent.putExtra("snd",snd);
        startActivity(intent);
    }
    public void playSound()
    {
        if(snd.equalsIgnoreCase("on"))
        {
            sp.play(sound1,1.0f,1.0f,0,0,10f);
        }
    }
    public void toggleButtonClick(View v) {
        if(!toggleButton.isChecked())
            snd="off";
        else if(toggleButton.isChecked())
            snd="on";
    }

    public void multiClick(View view) {
        playSound();
        Intent intent=new Intent(MainMenuActivity.this,PlayerInfoActivity.class);
        intent.putExtra("snd",snd);
        startActivity(intent);
    }

    public void helpClick(View view) {
        playSound();
        Intent intent=new Intent(MainMenuActivity.this,HelpGameActivity.class);
        startActivity(intent);
    }
}
