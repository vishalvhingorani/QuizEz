package com.androiddev.quizez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class leaderboard extends AppCompatActivity {

    RecyclerView recyclerView;
    leaderboardRecyclerView recyclerViewAdapter;
    ArrayList NameList;
    ArrayList ScoreList;
    String code;
    String playerName;
    String titleOfQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_leaderboard);
        Intent intent = getIntent();
        code = intent.getStringExtra("IDcode");
        playerName = intent.getStringExtra("name");
        titleOfQuiz = intent.getStringExtra("NameQ");

        NameList = home.getLeaderboardName(code);
        ScoreList = home.getLeaderboardScore(code);
        sortMyLists();

        recyclerView = findViewById(R.id.leaderboardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(leaderboard.this));
        recyclerViewAdapter = new leaderboardRecyclerView(NameList,ScoreList);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    public void sortMyLists() {

        for (int i = 0; i < ScoreList.size(); i++) {
            for (int j = 1; j < ScoreList.size() - i; j++) {
                if (Integer.parseInt((String) ScoreList.get(j - 1)) < Integer.parseInt((String) ScoreList.get(j))) {
                    String tempID = (String) ScoreList.get(j - 1);
                    String temp = (String) NameList.get(j - 1);
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

        Intent tohome = new Intent(getApplicationContext(),home.class);
        tohome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(tohome);
    }
}