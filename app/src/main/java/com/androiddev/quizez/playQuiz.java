package com.androiddev.quizez;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class playQuiz extends AppCompatActivity {

    ArrayList<String> questionList = new ArrayList<String>();
    ArrayList<String> option0List = new ArrayList<String>();
    ArrayList<String> option1List = new ArrayList<String>();
    ArrayList<String> option2List = new ArrayList<String>();
    ArrayList<String> option3List = new ArrayList<String>();
    ArrayList<String> correctAnswerList = new ArrayList<String>();
    ArrayList<Integer> duration = new ArrayList<Integer>();
    ArrayList<Integer> pointslist = new ArrayList<Integer>();
    String code;
    String namePlayer;
    String NameQ;
    TextView option0;
    TextView option1;
    TextView option2;
    TextView option3;
    TextView scoreTextView;
    TextView timerTextView;
    TextView name;
    TextView roundTextView;
    Intent getName;
    CardView option00;
    CardView option11;
    CardView option22;
    CardView option33;
    CountDownTimer count;
    ProgressBar progressBar;
    ProgressBar timeProgressBar;
    TextView questionTextView;
    boolean active;
    int c;
    int cMax;
    int r ;
    int points = 0;
    int sum;

    String point ="0";
    String question;
    int time;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void chooseAnswer(View view){

        if(correctAnswerList.get(c-1).equals(view.getTag().toString()) && active) {
            view.setBackgroundColor(Color.parseColor("#03C04A"));
            r++;
            active = false;
            count.cancel();
            points += pointslist.get(c-1);

            progressBar.setProgress(points,true);
            scoreTextView = (TextView) findViewById(R.id.pointsTextView);
            point = Integer.toString(points);
            scoreTextView.setText(point);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showRound(c);

                    final Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            newQuestion();
                        }
                    }, 2000);
                }
            }, 1000);
        }
        else if(active) {
            view.setBackgroundColor(Color.parseColor("#D30000"));
            if (correctAnswerList.get(c-1).equals("0")){
                option0.setBackgroundColor(Color.parseColor("#03C04A"));
            }
            else if (correctAnswerList.get(c-1).equals("1")){
                option1.setBackgroundColor(Color.parseColor("#03C04A"));
            }
            else if (correctAnswerList.get(c-1).equals("2")){
                option2.setBackgroundColor(Color.parseColor("#03C04A"));
            }
            else {
                option3.setBackgroundColor(Color.parseColor("#03C04A"));
            }

            active = false;
            count.cancel();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showRound(c);

                    final Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            newQuestion();
                        }
                    }, 2000);
                }
            }, 1000);
        }
    }

    public void newQuestion() {

        if(c<cMax) {

            active = true;


            questionTextView.setText(questionList.get(c));
            option0.setText(option0List.get(c));
            option1.setText(option1List.get(c));
            option2.setText(option2List.get(c));
            option3.setText(option3List.get(c));

            roundTextView.setVisibility(View.INVISIBLE);

            option0.setBackgroundColor(Color.parseColor("#FFFFFF"));
            option1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            option2.setBackgroundColor(Color.parseColor("#FFFFFF"));
            option3.setBackgroundColor(Color.parseColor("#FFFFFF"));
            time = (duration.get(c))*1000;
            timeProgressBar.setMax(time/1000);

            count = new CountDownTimer(time + 100,1000) {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onTick(long l) {
                    timerTextView.setText(String.valueOf(l / 1000) + "s");
                    timeProgressBar.setProgress(((int) l/1000),true);
                }

                @Override
                public void onFinish() {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showRound(c);

                            final Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    newQuestion();
                                }
                            }, 2000);
                        }
                    }, 1000);
                }
            }.start();
            c++;
        }
    }

    public void showRound(int c) {

        if(c<cMax) {
            progressBar.setVisibility(View.INVISIBLE);
            option00.setVisibility(View.INVISIBLE);
            option11.setVisibility(View.INVISIBLE);
            option22.setVisibility(View.INVISIBLE);
            option33.setVisibility(View.INVISIBLE);
            questionTextView.setVisibility(View.INVISIBLE);
            timerTextView.setText(String.valueOf(duration.get(c)) + "s");

            int cs = c+1;


            String round = "Question " + cs;
            roundTextView.setText(round);
            roundTextView.setAlpha(0f);
            roundTextView.setVisibility(View.VISIBLE);
            roundTextView.animate().alpha(1f).setDuration(600);

            new CountDownTimer(2100,1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {

                    option00.setVisibility(View.VISIBLE);
                    option11.setVisibility(View.VISIBLE);
                    option22.setVisibility(View.VISIBLE);
                    option33.setVisibility(View.VISIBLE);
                    questionTextView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    roundTextView.animate().alpha(0f).setDuration(600);

                }
            }.start();
        }
        else {
            finish();
            Intent go2score = new Intent(getApplicationContext(),leaderboardview.class);
            go2score.putExtra("name",namePlayer);
            go2score.putExtra("IDcode",code);
            go2score.putExtra("NameQ",NameQ);
            go2score.putExtra("point",point);
            startActivity(go2score);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_play_quiz);

        Intent getCode = getIntent();
        code = getCode.getStringExtra("IDcode");
        NameQ = getCode.getStringExtra("NameQ");
        namePlayer = getCode.getStringExtra("Name");

        questionList = home.getQuestion(code);
        option0List = home.getOption0(code);
        option1List = home.getOption1(code);
        option2List = home.getOption2(code);
        option3List = home.getOption3(code);
        correctAnswerList = home.getCorrectAnswer(code);
        duration = home.getDuration(code);
        pointslist = home.getPoints(code);

        questionTextView = (TextView) findViewById(R.id.questionTextView);
        option0 = (TextView) findViewById(R.id.option0);
        option1 = (TextView) findViewById(R.id.option1);
        option2 = (TextView) findViewById(R.id.option2);
        option3 = (TextView) findViewById(R.id.option3);
        option00 =(CardView) findViewById(R.id.option00);
        option11 =(CardView) findViewById(R.id.option11);
        option22 =(CardView) findViewById(R.id.option22);
        option33 =(CardView) findViewById(R.id.option33);
        roundTextView = (TextView) findViewById(R.id.roundTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timeProgressBar = (ProgressBar) findViewById(R.id.timeProgressBar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        name = (TextView) findViewById(R.id.nameTextView);
        name.setText(namePlayer);

        sum = 0;

        for(int i = 0;i<pointslist.size();i++){
            sum += pointslist.get(i);
        }

        c=0;
        r=0;
        cMax = home.getcCount(code);
        progressBar.setMax(sum);



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showRound(c);

                final Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newQuestion();
                    }
                }, 2000);
            }
        }, 0);



    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_menu_delete)
                .setTitle("Exit Quiz?")
                .setMessage("You will lose all progress")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        count.cancel();
                        Intent intent = new Intent(getApplicationContext(),home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}