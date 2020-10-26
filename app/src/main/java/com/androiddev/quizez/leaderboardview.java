package com.androiddev.quizez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.graphics.Color.parseColor;

public class leaderboardview extends AppCompatActivity {

    String code;
    ArrayList<String> NameList;
    ArrayList<String> ScoreList;
    String name;
    String score;
    String titleOfQuiz;
    TextView titleView;
    TextView highscore;
    TextView scoreLeader;
    TextView firstText;
    TextView secondText;
    TextView thirdText;
    TextView firstScore;
    TextView secondScore;
    TextView thirdScore;
    LinearLayout bg1;
    LinearLayout bg2;
    LinearLayout bg3;

    public void go2list(View view){
        finish();
        Intent intent = new Intent(getApplicationContext(),leaderboard.class);
        intent.putExtra("IDcode",code);
        intent.putExtra("name",name);
        intent.putExtra("NameQ",titleOfQuiz);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_leaderboardview);
        Intent intent = getIntent();
        code = intent.getStringExtra("IDcode");
        name = intent.getStringExtra("name");
        score = intent.getStringExtra("point");
        titleOfQuiz = intent.getStringExtra("NameQ");
        home.exsql("INSERT INTO l" + code +"(name, score) VALUES ('"+name+"',"+score+")");
        NameList = home.getLeaderboardName(code);
        ScoreList = home.getLeaderboardScore(code);
        sortMyLists();
        titleView = findViewById(R.id.titleLeader);
        highscore = findViewById(R.id.highscore);
        scoreLeader = findViewById(R.id.scoreLeader);
        firstText = findViewById(R.id.firstText);
        firstScore = findViewById(R.id.firstScore);
        secondText = findViewById(R.id.secondText);
        secondScore = findViewById(R.id.secondScore);
        thirdText = findViewById(R.id.thirdText);
        thirdScore = findViewById(R.id.thirdScore);
        bg1 = findViewById(R.id.bg1);
        bg2 = findViewById(R.id.bg2);
        bg3 = findViewById(R.id.bg3);
        scoreLeader.setText("Your Score : "+score);
        highscore.setText("High Score : "+ScoreList.get(0));
        titleView.setText(titleOfQuiz);
        firstText.setText(NameList.get(0));
        firstScore.setText(ScoreList.get(0));
        if(ScoreList.size()>1){
            secondText.setText(NameList.get(1));
            secondScore.setText(ScoreList.get(1));
            if (ScoreList.size()>2){
                thirdText.setText(NameList.get(2));
                thirdScore.setText(ScoreList.get(2));
            }
        }

        if(NameList.indexOf(name)<=2){
            switch (NameList.indexOf(name))
            {
                case 0:
                    bg1.setBackgroundColor(parseColor("#38C6F8"));
                    break;
                case 1:
                    bg2.setBackgroundColor(parseColor("#38C6F8"));
                    break;
                default:
                    bg3.setBackgroundColor(parseColor("#38C6F8"));
                    break;
            }
        }

    }

    public void sortMyLists() {

        for (int i = 0; i < ScoreList.size(); i++) {
            for (int j = 1; j < ScoreList.size() - i; j++) {
                if (Integer.parseInt(ScoreList.get(j - 1)) < Integer.parseInt(ScoreList.get(j))) {
                    String tempID = ScoreList.get(j - 1);
                    String temp = NameList.get(j - 1);
                    ScoreList.set(j - 1, ScoreList.get(j));
                    NameList.set(j - 1, NameList.get(j));
                    ScoreList.set(j, tempID);
                    NameList.set(j, temp);
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        Intent gback = new Intent(getApplicationContext(),home.class);
        gback.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gback);
    }
}