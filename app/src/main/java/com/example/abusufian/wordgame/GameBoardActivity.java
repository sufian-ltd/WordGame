package com.example.abusufian.wordgame;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class GameBoardActivity extends AppCompatActivity implements View.OnClickListener{

    Animation animation,animation2;
    /* renamed from: b */
    Button[][] f1b;
    /* renamed from: c */
    int f2c = 20,f3r = 7;
    String first,second;
    int leftPosition = 0,rightPosition = 0,dowmPosition = 0,upPosition = 0;
    String leftToRight,uptoDown;
    String letter = "";
    ArrayList<String> list = new ArrayList();
    HorizontalScrollView my1,my3;
    LinearLayout my2;
    String snd = "";
    int sum1 = 0,sum2 = 0;
    boolean turn = false;
    TextView tvEmo,tvFPScore,tvFSScore;
    String up = "",down = "",left = "",right = "";
    Button[] wb;
    WordList word = new WordList();
    SoundPool sp;
    int sound1,sound2,sound3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        first = getIntent().getExtras().getString("first");
        second = getIntent().getExtras().getString("second");
        snd = getIntent().getExtras().getString("snd");
        list = this.word.addList();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.copysequential);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_down);
        initializeButton();
        sp=new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        sound1=sp.load(getApplicationContext(),R.raw.coin,1);
        sound2=sp.load(getApplicationContext(),R.raw.coin,1);
        sound3=sp.load(getApplicationContext(),R.raw.click,1);
    }
    public void afterMatch(int i,int j){
        f1b[i][j].setBackgroundResource(R.drawable.btn5);
        f1b[i][j].setTextColor(Color.WHITE);
    }
    public void afterWrong(int i,int j){
        f1b[i][j].setBackgroundResource(R.drawable.btn6);
        f1b[i][j].setTextColor(Color.BLACK);
    }
    public void onClick(View v) {
        int i = 0;
        while (i < this.f3r) {
            int j = 0;
            while (j < this.f2c) {
                if (this.f1b[i][j] == v && !this.letter.equals("") && this.f1b[i][j].getText().equals("")) {
                    if (this.turn) {
                        this.tvFPScore.setBackgroundResource(0);
                        this.tvFSScore.setBackgroundResource(R.drawable.btn10);
                    } else {
                        this.tvFPScore.setBackgroundResource(R.drawable.btn10);
                        this.tvFSScore.setBackgroundResource(0);
                    }
                    this.f1b[i][j].setText(this.letter.toString());
                    this.letter = "";
                    playerTurn(i, j);
                }
                j++;
            }
            i++;
        }
    }

    public boolean isMatch(String str) {
        if (list.contains(str.toLowerCase())) {
            return true;
        }
        return false;
    }
    public void getWordFromBoard(int i, int j) {
        String strLeft = "";
        int m = j;
        while (m >= 0 && !f1b[i][m].getText().equals("")) {
            strLeft = new StringBuilder(String.valueOf(strLeft)).append(this.f1b[i][m].getText().toString()).toString();
            this.leftPosition = m;
            m--;
        }
        strLeft = strLeft.substring(1);
        String strRight = "";
        m = j;
        while (m < this.f2c && !this.f1b[i][m].getText().equals("")) {
            strRight = new StringBuilder(String.valueOf(strRight)).append(this.f1b[i][m].getText().toString()).toString();
            this.rightPosition = m;
            m++;
        }
        this.leftToRight = reverse(strLeft) + strRight;
        String strUp = "";
        m = i;
        while (m >= 0 && !this.f1b[m][j].getText().equals("")) {
            strUp = new StringBuilder(String.valueOf(strUp)).append(this.f1b[m][j].getText().toString()).toString();
            this.upPosition = m;
            m--;
        }
        strUp = strUp.substring(1);
        String strDown = "";
        m = i;
        while (m < this.f3r && !this.f1b[m][j].getText().equals("")) {
            strDown = new StringBuilder(String.valueOf(strDown)).append(this.f1b[m][j].getText().toString()).toString();
            this.dowmPosition = m;
            m++;
        }
        this.uptoDown = reverse(strUp) + strDown;
    }
    public void leftright(int i, int j) {
        this.tvEmo.setBackgroundResource(R.drawable.match);
        setBeforeTextColor();
        for (int m = this.leftPosition; m <= this.rightPosition; m++) {
            //this.f1b[i][m].setTextColor(-16711681);
            afterMatch(i,m);
        }
        matchSound();
        turnSetting(this.leftToRight.length());
    }

    public void updown(int i, int j) {
        this.tvEmo.setBackgroundResource(R.drawable.match);
        setBeforeTextColor();
        for (int m = this.upPosition; m <= this.dowmPosition; m++) {
            //this.f1b[m][j].setTextColor(-16711681);
            afterMatch(m,j);
        }
        matchSound();
        turnSetting(this.uptoDown.length());
    }

    public void playerTurn(final int i, final int j) {
        getWordFromBoard(i, j);
        boolean match = false;
        if (this.leftToRight.length() >= this.uptoDown.length()) {
            if (isMatch(this.leftToRight)) {
                leftright(i, j);
                match = true;
            } else if (isMatch(this.uptoDown)) {
                updown(i, j);
                match = true;
            }
        } else if (this.leftToRight.length() < this.uptoDown.length()) {
            if (isMatch(this.uptoDown)) {
                updown(i, j);
                match = true;
            } else if (isMatch(this.leftToRight)) {
                leftright(i, j);
                match = true;
            }
        }
        if (!match) {
            int m;
            for (m = this.leftPosition; m <= this.rightPosition; m++) {
                //this.f1b[i][m].setTextColor(SupportMenu.CATEGORY_MASK);
                afterWrong(i,m);
            }
            for (m = this.upPosition; m <= this.dowmPosition; m++) {
                //this.f1b[m][j].setTextColor(SupportMenu.CATEGORY_MASK);
                afterWrong(m,j);
            }
            this.tvEmo.setBackgroundResource(R.drawable.wrong);
            notMatchSound();
            turnSetting(0);
        }
        if (this.second.equalsIgnoreCase("Computer : ")) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    GameBoardActivity.this.getWordForCom(i, j);
                    GameBoardActivity.this.computerTurn(i, j);
                }
            }, 1000);
        }
    }

    public void getWordForCom(int i, int j) {
        int m;
        this.left = "";
        this.right = "";
        this.up = "";
        this.down = "";
        if (this.leftPosition > 1) {
            m = this.leftPosition - 2;
            while (m >= 0 && !this.f1b[i][m].getText().equals("")) {
                this.left += this.f1b[i][m].getText().toString();
                m--;
            }
        }
        if (this.rightPosition <= this.f2c - 2) {
            m = this.rightPosition + 2;
            while (m < this.f2c && !this.f1b[i][m].getText().equals("")) {
                this.right += this.f1b[i][m].getText().toString();
                m++;
            }
        }
        if (this.upPosition > 1) {
            m = this.upPosition - 2;
            while (m >= 0 && !this.f1b[m][j].getText().equals("")) {
                this.up += this.f1b[m][j].getText().toString();
                m--;
            }
        }
        if (this.dowmPosition <= this.f3r - 2) {
            m = this.dowmPosition + 2;
            while (m < this.f3r && !this.f1b[m][j].getText().equals("")) {
                this.down += this.f1b[m][j].getText().toString();
                m++;
            }
        }
    }

    public boolean leftSide(int i, int j) {
        if (this.leftPosition != 0) {
            for (char x = 'A'; x <= 'Z'; x = (char) (x + 1)) {
                if (isMatch(this.left + x + this.leftToRight)) {
                    this.tvEmo.setBackgroundResource(R.drawable.match);
                    setBeforeTextColor();
                    this.f1b[i][this.leftPosition - 1].setText(new StringBuilder(String.valueOf(x)).toString());
                    for (int m = (this.leftPosition - this.left.length()) - 1; m <= this.rightPosition; m++) {
                        //this.f1b[i][m].setTextColor(-16711681);
                        afterMatch(i,m);
                    }
                    matchSound();
                    turnSetting(this.leftToRight.length() + 1);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean rightSide(int i, int j) {
        if (this.rightPosition != this.f2c - 1) {
            for (char x = 'A'; x <= 'Z'; x = (char) (x + 1)) {
                if (isMatch(this.leftToRight + x + this.right)) {
                    this.tvEmo.setBackgroundResource(R.drawable.match);
                    setBeforeTextColor();
                    this.f1b[i][this.rightPosition + 1].setText(new StringBuilder(String.valueOf(x)).toString());
                    for (int m = this.leftPosition; m <= (this.rightPosition + 1) + this.right.length(); m++) {
                        //this.f1b[i][m].setTextColor(-16711681);
                        afterMatch(i,m);
                    }
                    matchSound();
                    turnSetting(this.leftToRight.length() + 1);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean upSide(int i, int j) {
        if (this.upPosition != 0) {
            for (char x = 'A'; x <= 'Z'; x = (char) (x + 1)) {
                if (isMatch(new StringBuilder(String.valueOf(x)).append(this.uptoDown).toString())) {
                    this.tvEmo.setBackgroundResource(R.drawable.match);
                    setBeforeTextColor();
                    this.f1b[this.upPosition - 1][j].setText(new StringBuilder(String.valueOf(x)).toString());
                    for (int m = (this.upPosition - 1) - this.up.length(); m <= this.dowmPosition; m++) {
                        //this.f1b[m][j].setTextColor(-16711681);
                        afterMatch(m,j);
                    }
                    matchSound();
                    turnSetting(this.uptoDown.length() + 1);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean downSide(int i, int j) {
        if (this.dowmPosition != this.f3r - 1) {
            for (char x = 'A'; x <= 'Z'; x = (char) (x + 1)) {
                if (isMatch(this.uptoDown + x + this.down)) {
                    this.tvEmo.setBackgroundResource(R.drawable.match);
                    setBeforeTextColor();
                    this.f1b[this.dowmPosition + 1][j].setText(new StringBuilder(String.valueOf(x)).toString());
                    for (int m = this.upPosition; m <= (this.dowmPosition + 1) + this.down.length(); m++) {
                        //this.f1b[m][j].setTextColor(-16711681);
                        afterMatch(m,j);
                    }
                    matchSound();
                    turnSetting(this.uptoDown.length() + 1);
                    return true;
                }
            }
        }
        return false;
    }

    public void computerTurn(int i, int j) {
        int ri;
        int rc;
        int rl;
        this.tvFPScore.setBackgroundResource(0);
        this.tvFSScore.setBackgroundResource(R.drawable.btn10);
        if (this.leftToRight.length() >= this.uptoDown.length()) {
            if (leftSide(i, j) || rightSide(i, j) || upSide(i, j) || downSide(i, j)) {
                return;
            }
        } else if (!upSide(i, j) && !downSide(i, j) && !leftSide(i, j)) {
            if (rightSide(i, j)) {
                return;
            }
        } else {
            return;
        }
        do {
            ri = new Random().nextInt(7);
            rc = new Random().nextInt(20);
            rl = new Random().nextInt(25) + 65;
        } while (!this.f1b[ri][rc].getText().equals(""));
        this.f1b[ri][rc].setText(new StringBuilder(String.valueOf((char) rl)).toString());
        //this.f1b[ri][rc].setTextColor(SupportMenu.CATEGORY_MASK);
        afterWrong(ri,rc);
        notMatchSound();
        turnSetting(0);
    }

    public void turnSetting(int wordLength) {
        if (this.turn) {
            this.sum2 += wordLength;
            this.tvFSScore.setText(this.second + this.sum2);
        } else {
            this.sum1 += wordLength;
            this.tvFPScore.setText(this.first + this.sum1);
        }
        this.turn = !this.turn;
    }

    public void matchSound() {
        if (this.snd.equalsIgnoreCase("on")) {
            sp.play(sound1,1.0f,1.0f,0,0,10f);
        }
    }

    public void notMatchSound() {
        if (this.snd.equalsIgnoreCase("on")) {
            sp.play(sound2,1.0f,1.0f,0,0,10f);
        }
    }
    public void selectSound() {
        if (this.snd.equalsIgnoreCase("on")) {
            sp.play(sound3,1.0f,1.0f,0,0,10f);
        }
    }

    public String reverse(String str) {
        String temp = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            temp = new StringBuilder(String.valueOf(temp)).append(str.charAt(i)).toString();
        }
        return temp;
    }

    public void setBeforeTextColor() {
        for (int i = 0; i < this.f3r; i++) {
            for (int j = 0; j < this.f2c; j++) {
                this.f1b[i][j].setTextColor(Color.BLACK);
                this.f1b[i][j].setBackgroundResource(R.drawable.btn4);
            }
        }
    }
    public void initializeButton() {
        int i;
        this.f1b = (Button[][]) Array.newInstance(Button.class, new int[]{this.f3r, this.f2c});
        this.f1b[0][0] = findViewById(R.id.b1);
        this.f1b[0][1] = findViewById(R.id.b2);
        this.f1b[0][2] = findViewById(R.id.b3);
        this.f1b[0][3] = findViewById(R.id.b4);
        this.f1b[0][4] =  findViewById(R.id.b5);
        this.f1b[0][5] =  findViewById(R.id.b6);
        this.f1b[0][6] =  findViewById(R.id.b7);
        this.f1b[0][7] =  findViewById(R.id.b8);
        this.f1b[0][8] =  findViewById(R.id.b9);
        this.f1b[0][9] =  findViewById(R.id.b10);
        this.f1b[0][10] =  findViewById(R.id.b11);
        this.f1b[0][11] =  findViewById(R.id.b12);
        this.f1b[0][12] =  findViewById(R.id.b13);
        this.f1b[0][13] =  findViewById(R.id.b14);
        this.f1b[0][14] =  findViewById(R.id.b15);
        this.f1b[0][15] =  findViewById(R.id.b16);
        this.f1b[0][16] =  findViewById(R.id.b17);
        this.f1b[0][17] =  findViewById(R.id.b18);
        this.f1b[0][18] =  findViewById(R.id.b19);
        this.f1b[0][19] =  findViewById(R.id.b20);
        this.f1b[1][0] =  findViewById(R.id.b21);
        this.f1b[1][1] =  findViewById(R.id.b22);
        this.f1b[1][2] =  findViewById(R.id.b23);
        this.f1b[1][3] =  findViewById(R.id.b24);
        this.f1b[1][4] =  findViewById(R.id.b25);
        this.f1b[1][5] =  findViewById(R.id.b26);
        this.f1b[1][6] =  findViewById(R.id.b27);
        this.f1b[1][7] = findViewById(R.id.b28);
        this.f1b[1][8] =  findViewById(R.id.b29);
        this.f1b[1][9] =  findViewById(R.id.b30);
        this.f1b[1][10] =  findViewById(R.id.b31);
        this.f1b[1][11] =  findViewById(R.id.b32);
        this.f1b[1][12] =  findViewById(R.id.b33);
        this.f1b[1][13] =  findViewById(R.id.b34);
        this.f1b[1][14] =  findViewById(R.id.b35);
        this.f1b[1][15] =  findViewById(R.id.b36);
        this.f1b[1][16] =  findViewById(R.id.b37);
        this.f1b[1][17] =  findViewById(R.id.b38);
        this.f1b[1][18] =  findViewById(R.id.b39);
        this.f1b[1][19] =  findViewById(R.id.b40);
        this.f1b[2][0] = findViewById(R.id.b41);
        this.f1b[2][1] =  findViewById(R.id.b42);
        this.f1b[2][2] =  findViewById(R.id.b43);
        this.f1b[2][3] =  findViewById(R.id.b44);
        this.f1b[2][4] =  findViewById(R.id.b45);
        this.f1b[2][5] =  findViewById(R.id.b46);
        this.f1b[2][6] =  findViewById(R.id.b47);
        this.f1b[2][7] =  findViewById(R.id.b48);
        this.f1b[2][8] =  findViewById(R.id.b49);
        this.f1b[2][9] =  findViewById(R.id.b50);
        this.f1b[2][10] =  findViewById(R.id.b51);
        this.f1b[2][11] =  findViewById(R.id.b52);
        this.f1b[2][12] =  findViewById(R.id.b53);
        this.f1b[2][13] =  findViewById(R.id.b54);
        this.f1b[2][14] =  findViewById(R.id.b55);
        this.f1b[2][15] =  findViewById(R.id.b56);
        this.f1b[2][16] =  findViewById(R.id.b57);
        this.f1b[2][17] =  findViewById(R.id.b58);
        this.f1b[2][18] =  findViewById(R.id.b59);
        this.f1b[2][19] =  findViewById(R.id.b60);
        this.f1b[3][0] =  findViewById(R.id.b61);
        this.f1b[3][1] =  findViewById(R.id.b62);
        this.f1b[3][2] =  findViewById(R.id.b63);
        this.f1b[3][3] =  findViewById(R.id.b64);
        this.f1b[3][4] =  findViewById(R.id.b65);
        this.f1b[3][5] =  findViewById(R.id.b66);
        this.f1b[3][6] =  findViewById(R.id.b67);
        this.f1b[3][7] =  findViewById(R.id.b68);
        this.f1b[3][8] =  findViewById(R.id.b69);
        this.f1b[3][9] =  findViewById(R.id.b70);
        this.f1b[3][10] =  findViewById(R.id.b71);
        this.f1b[3][11] =  findViewById(R.id.b72);
        this.f1b[3][12] =  findViewById(R.id.b73);
        this.f1b[3][13] =  findViewById(R.id.b74);
        this.f1b[3][14] =  findViewById(R.id.b75);
        this.f1b[3][15] =  findViewById(R.id.b76);
        this.f1b[3][16] =  findViewById(R.id.b77);
        this.f1b[3][17] =  findViewById(R.id.b78);
        this.f1b[3][18] =  findViewById(R.id.b79);
        this.f1b[3][19] =  findViewById(R.id.b80);
        this.f1b[4][0] =  findViewById(R.id.b81);
        this.f1b[4][1] =  findViewById(R.id.b82);
        this.f1b[4][2] =  findViewById(R.id.b83);
        this.f1b[4][3] =  findViewById(R.id.b84);
        this.f1b[4][4] =  findViewById(R.id.b85);
        this.f1b[4][5] =  findViewById(R.id.b86);
        this.f1b[4][6] =  findViewById(R.id.b87);
        this.f1b[4][7] =  findViewById(R.id.b88);
        this.f1b[4][8] =  findViewById(R.id.b89);
        this.f1b[4][9] =  findViewById(R.id.b90);
        this.f1b[4][10] =  findViewById(R.id.b91);
        this.f1b[4][11] =  findViewById(R.id.b92);
        this.f1b[4][12] =  findViewById(R.id.b93);
        this.f1b[4][13] =  findViewById(R.id.b94);
        this.f1b[4][14] =  findViewById(R.id.b95);
        this.f1b[4][15] =  findViewById(R.id.b96);
        this.f1b[4][16] =  findViewById(R.id.b97);
        this.f1b[4][17] =  findViewById(R.id.b98);
        this.f1b[4][18] =  findViewById(R.id.b99);
        this.f1b[4][19] =  findViewById(R.id.b100);
        this.f1b[5][0] =  findViewById(R.id.b101);
        this.f1b[5][1] =  findViewById(R.id.b102);
        this.f1b[5][2] =  findViewById(R.id.b103);
        this.f1b[5][3] =  findViewById(R.id.b104);
        this.f1b[5][4] =  findViewById(R.id.b105);
        this.f1b[5][5] =  findViewById(R.id.b106);
        this.f1b[5][6] =  findViewById(R.id.b107);
        this.f1b[5][7] =  findViewById(R.id.b108);
        this.f1b[5][8] =  findViewById(R.id.b109);
        this.f1b[5][9] =  findViewById(R.id.b110);
        this.f1b[5][10] =  findViewById(R.id.b111);
        this.f1b[5][11] =  findViewById(R.id.b112);
        this.f1b[5][12] =  findViewById(R.id.b113);
        this.f1b[5][13] =  findViewById(R.id.b114);
        this.f1b[5][14] =  findViewById(R.id.b115);
        this.f1b[5][15] =  findViewById(R.id.b116);
        this.f1b[5][16] =  findViewById(R.id.b117);
        this.f1b[5][17] =  findViewById(R.id.b118);
        this.f1b[5][18] =  findViewById(R.id.b119);
        this.f1b[5][19] =  findViewById(R.id.b120);
        this.f1b[6][0] =  findViewById(R.id.b121);
        this.f1b[6][1] =  findViewById(R.id.b122);
        this.f1b[6][2] =  findViewById(R.id.b123);
        this.f1b[6][3] =  findViewById(R.id.b124);
        this.f1b[6][4] =  findViewById(R.id.b125);
        this.f1b[6][5] =  findViewById(R.id.b126);
        this.f1b[6][6] =  findViewById(R.id.b127);
        this.f1b[6][7] =  findViewById(R.id.b128);
        this.f1b[6][8] =  findViewById(R.id.b129);
        this.f1b[6][9] =  findViewById(R.id.b130);
        this.f1b[6][10] =  findViewById(R.id.b131);
        this.f1b[6][11] =  findViewById(R.id.b132);
        this.f1b[6][12] =  findViewById(R.id.b133);
        this.f1b[6][13] =  findViewById(R.id.b134);
        this.f1b[6][14] =  findViewById(R.id.b135);
        this.f1b[6][15] =  findViewById(R.id.b136);
        this.f1b[6][16] =  findViewById(R.id.b137);
        this.f1b[6][17] =  findViewById(R.id.b138);
        this.f1b[6][18] =  findViewById(R.id.b139);
        this.f1b[6][19] =  findViewById(R.id.b140);
        this.wb = new Button[26];
        this.wb[0] =  findViewById(R.id.button1);
        this.wb[1] =  findViewById(R.id.button2);
        this.wb[2] =  findViewById(R.id.button3);
        this.wb[3] =  findViewById(R.id.button4);
        this.wb[4] =  findViewById(R.id.button5);
        this.wb[5] =  findViewById(R.id.button6);
        this.wb[6] =  findViewById(R.id.button7);
        this.wb[7] =  findViewById(R.id.button8);
        this.wb[8] =  findViewById(R.id.button9);
        this.wb[9] =  findViewById(R.id.button10);
        this.wb[10] =  findViewById(R.id.button11);
        this.wb[11] =  findViewById(R.id.button12);
        this.wb[12] =  findViewById(R.id.button13);
        this.wb[13] =  findViewById(R.id.button14);
        this.wb[14] =  findViewById(R.id.button15);
        this.wb[15] =  findViewById(R.id.button16);
        this.wb[16] =  findViewById(R.id.button17);
        this.wb[17] =  findViewById(R.id.button18);
        this.wb[18] =  findViewById(R.id.button19);
        this.wb[19] =  findViewById(R.id.button20);
        this.wb[20] =  findViewById(R.id.button21);
        this.wb[21] =  findViewById(R.id.button22);
        this.wb[22] =  findViewById(R.id.button23);
        this.wb[23] =  findViewById(R.id.button24);
        this.wb[24] =  findViewById(R.id.button25);
        this.wb[25] =  findViewById(R.id.button26);
        this.my1 =  findViewById(R.id.my1);
        this.my1.startAnimation(this.animation2);
        this.my2 =  findViewById(R.id.my2);
        this.my2.startAnimation(this.animation2);
        this.my3 =  findViewById(R.id.my3);
        this.my3.startAnimation(this.animation2);
        this.tvFPScore =  findViewById(R.id.textViewFirstPlayerScore);
        this.tvFSScore =  findViewById(R.id.textViewSecondPlayerScore);
        this.tvFPScore.setText(this.first.toString());
        this.tvFSScore.setText(this.second.toString());
        this.tvEmo =  findViewById(R.id.textViewEmo);
        this.tvFPScore.setBackgroundResource(R.drawable.btn10);
        char s = 'A';
        for (i = 0; i < 26; i++) {
            this.wb[i].setText(new StringBuilder(String.valueOf(s)).toString());
            this.wb[i].setTextColor(Color.BLACK);
            this.wb[i].setTextSize(35.0f);
            s = (char) (s + 1);
            this.wb[i].setBackgroundResource(R.drawable.btn2);
            this.wb[i].setAnimation(this.animation);
        }
        for (i = 0; i < 26; i++) {
            this.wb[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j < 26; j++) {
                        if (wb[j] == view) {
                            letter = wb[j].getText().toString();
                            selectSound();
                        }
                    }
                }
            });
        }
        for (i = 0; i < this.f3r; i++) {
            for (int j = 0; j < this.f2c; j++) {
                this.f1b[i][j].setTextSize(35.0f);
                this.f1b[i][j].setText("");
                this.f1b[i][j].setTextColor(Color.BLACK);
                this.f1b[i][j].setBackgroundResource(R.drawable.btn4);
                this.f1b[i][j].setOnClickListener(this);
            }
        }
    }

}
