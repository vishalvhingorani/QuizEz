package com.androiddev.quizez;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

public class intro extends AppIntro2 {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getSupportActionBar().hide();
        addSlide(AppIntroFragment.newInstance("Create Quizzes", "Make and Customize your own Quizzes",R.drawable.make,R.drawable.slide1,Color.BLACK,Color.BLACK,R.font.cambay_bold, R.font.cambay,R.drawable.slide1));
        addSlide(AppIntroFragment.newInstance("Take Tests", "Test yourself or Make Friends take them",R.drawable.play,R.drawable.slide2,Color.BLACK,Color.BLACK,R.font.cambay_bold, R.font.cambay,R.drawable.slide2));
        addSlide(AppIntroFragment.newInstance("Get on Top of the Leaderboard", "Outrank Previous Attempts or Beat your Friends",R.drawable.trophy,R.drawable.slide3,Color.BLACK,Color.BLACK,R.font.cambay_bold, R.font.cambay,R.drawable.slide3));
        setTransformer(new AppIntroPageTransformerType.Parallax(1.0,-1.0,2.0));
        setWizardMode(true);



    }


    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        MainActivity.prefs.edit().putBoolean("firstrun", false).commit();
        finish();
        Intent intent = new Intent(getApplicationContext(),home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_baseline_exit_to_app_24)
                .setTitle("Exit QuizEz")
                .setMessage("Are you sure you want to exit the application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}