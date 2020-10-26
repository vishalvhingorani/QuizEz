package com.androiddev.quizez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class createNew extends AppCompatActivity {

    EditText title;
    int n = 1;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView10;
    TextView textView;



    public void selectN(View view){
        textView1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView5.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView6.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView7.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView8.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView9.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView10.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView1.setTextColor(Color.parseColor("#38C6F8"));
        textView2.setTextColor(Color.parseColor("#38C6F8"));
        textView3.setTextColor(Color.parseColor("#38C6F8"));
        textView4.setTextColor(Color.parseColor("#38C6F8"));
        textView5.setTextColor(Color.parseColor("#38C6F8"));
        textView6.setTextColor(Color.parseColor("#38C6F8"));
        textView7.setTextColor(Color.parseColor("#38C6F8"));
        textView8.setTextColor(Color.parseColor("#38C6F8"));
        textView9.setTextColor(Color.parseColor("#38C6F8"));
        textView10.setTextColor(Color.parseColor("#38C6F8"));
        view.setBackgroundColor(getResources().getColor(R.color.mainblue));
        n = Integer.parseInt(view.getTag().toString());
        textView = findViewById(view.getId());
        textView.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void submission(View view) {

        String qtext = title.getText().toString().replaceAll(" ","");
        if (qtext.equals("")){
            Snackbar.make(view,"Please Enter a Title",Snackbar.LENGTH_LONG).show();
        }
        else if(qtext.contains("'")){
            Snackbar.make(view,"Special character  '  is invalid",Snackbar.LENGTH_LONG).show();
        }
        else {
            finish();
            Intent go2setUp = new Intent(getApplicationContext(),setQuiz.class);
            go2setUp.putExtra("numberOfOptions",n);
            go2setUp.putExtra("quizTitle",title.getText().toString());
            go2setUp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(go2setUp);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_new);


        title = (EditText) findViewById(R.id.titleEditText);
        textView1 = findViewById(R.id.textview1);
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);
        textView4 = findViewById(R.id.textview4);
        textView5 = findViewById(R.id.textview5);
        textView6 = findViewById(R.id.textview6);
        textView7 = findViewById(R.id.textview7);
        textView8 = findViewById(R.id.textview8);
        textView9 = findViewById(R.id.textview9);
        textView10 = findViewById(R.id.textview10);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}