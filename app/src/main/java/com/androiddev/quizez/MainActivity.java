package com.androiddev.quizez;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static SharedPreferences prefs;
    ImageView logo;
    TextView maintitle;
    Animation imganim, textanim;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        prefs = this.getSharedPreferences("com.androiddev.quizez", Context.MODE_PRIVATE);

        logo = findViewById(R.id.logo);
        maintitle = findViewById(R.id.maintitle);
        imganim = AnimationUtils.loadAnimation(this,R.anim.imganim);
        textanim = AnimationUtils.loadAnimation(this,R.anim.textanim);
        logo.setAnimation(imganim);
        maintitle.setAnimation(textanim);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (prefs.getBoolean("firstrun", true)) {
                    finish();
                    Intent intent1 = new Intent(getApplicationContext(),usergetname.class);
                    //intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);


                }
                else {
                    finish();
                    Intent intent = new Intent(getApplicationContext(),home.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
        }, 2400);


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
                        handler.removeCallbacksAndMessages(null);
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