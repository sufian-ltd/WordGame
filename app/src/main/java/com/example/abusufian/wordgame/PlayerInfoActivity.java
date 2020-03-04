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
import android.widget.EditText;
import android.widget.Toast;

public class PlayerInfoActivity extends AppCompatActivity {

    EditText et1,et2;
    Button nextButton;
    String snd="";
    SoundPool sp;
    int sound1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);
        snd=getIntent().getExtras().getString("snd");
        et1= findViewById(R.id.editTextF);
        et2= findViewById(R.id.EditTextS);
        nextButton= findViewById(R.id.buttonNext);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);
        nextButton.startAnimation(animation);
        et1.startAnimation(animation);
        et2.startAnimation(animation);
        sp=new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        sound1=sp.load(getApplicationContext(),R.raw.click,1);
    }
    public void nextClick(View v)
    {

        String f=et1.getText().toString();
        String s=et2.getText().toString();
        if(!f.equals("") && !s.equals(""))
        {
            if(snd.equalsIgnoreCase("snd"))
            {
                sp.play(sound1,1.0f,1.0f,0,0,10f);
            }
            if(f.length()>6)
                f=f.substring(0, 6);
            if(s.length()>6)
                s=s.substring(0, 6);
            Intent intent=new Intent(PlayerInfoActivity.this,GameBoardActivity.class);
            intent.putExtra("first", f+" : ");
            intent.putExtra("second",s+" : ");
            intent.putExtra("snd",snd);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"please enter players name..!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
