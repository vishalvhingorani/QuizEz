package com.androiddev.quizez;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import com.google.android.material.snackbar.Snackbar;

public class setQuiz extends AppCompatActivity {


    String title;
    int numberOfQuestions;
    RadioButton radioButton0;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton;
    int radioId;
    EditText question;
    EditText option0;
    EditText option1;
    EditText option2;
    EditText option3;
    int count;
    Button nextButton;
    boolean last;
    int ID;
    int duration = 10;
    int points = 10;
    SharedPreferences idgen;
    int tr;
    Integer[] durationOptions = {10,15,20,25,30,45,60,90,120};
    Integer[] pointsOptions = {1,2,5,10,15,20,25,50,100};
    Spinner spinnerDuration;
    Spinner spinnerPoints;

    public void setf(View view){
        switch (view.getTag().toString())
        {
            case "0":
                radioId = radioButton0.getId();
                radioButton1.setChecked(false);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                break;
            case "1":
                radioId = radioButton1.getId();
                radioButton0.setChecked(false);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                break;
            case "2":
                radioId = radioButton2.getId();
                radioButton1.setChecked(false);
                radioButton0.setChecked(false);
                radioButton3.setChecked(false);
                break;
            default:
                radioId = radioButton3.getId();
                radioButton1.setChecked(false);
                radioButton2.setChecked(false);
                radioButton0.setChecked(false);
                break;
        }
    }


    public void next(View v) {
        radioButton = findViewById(radioId);
        String qtext = question.getText().toString().replaceAll(" ","");
        String optiontext = question.getText().toString()+option0.getText().toString()+option1.getText().toString()+option2.getText().toString()+option3.getText().toString();
        if(qtext.equals("")){
            Snackbar.make(v,"Question cannot be empty",Snackbar.LENGTH_LONG).show();
        }
        else if(optiontext.contains("'")){
            Snackbar.make(v,"Special character  '  is invalid",Snackbar.LENGTH_LONG).show();
        }
        else {
            if(!last) {

                count++;
                if(count==numberOfQuestions-1) {

                    nextButton.setText("Submit");
                    last = true;

                }

                String execute = "('" +question.getText().toString()+"'," + "'" +option0.getText().toString()+"'," + "'" +option1.getText().toString()+"'," + "'" +option2.getText().toString()+"'," + "'" +option3.getText().toString()+"'," + "'" +radioButton.getTag().toString()+"'," +duration+","+points+")";
                //('question','option0','option1','option2','option3','correctAnswer',2)
                home.exsql("INSERT INTO q" + ID + " (question, option0 , option1, option2, option3, correctanswer, duration, points) VALUES " + execute);

            }
            else {
                String execute = "(" + "'" +question.getText().toString()+"'," + "'" +option0.getText().toString()+"'," + "'" +option1.getText().toString()+"'," + "'" +option2.getText().toString()+"'," + "'" +option3.getText().toString()+"'," + "'" +radioButton.getTag().toString()+"'," +duration+","+points+")";
                //('question','option0','option1','option2','option3','correctAnswer',2)
                home.exsql("INSERT INTO q" + ID + " (question, option0 , option1, option2, option3, correctanswer, duration, points) VALUES " + execute);
                home.exsql("INSERT INTO mainlist (titles, id) VALUES ('"+title+"',"+ID+")");

                finish();
                Intent goHome = new Intent(getApplicationContext(),home.class);
                goHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goHome);
            }

            question.setText("");
            option0.setText("");
            option1.setText("");
            option2.setText("");
            option3.setText("");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_set_quiz);

        Intent getInfo = getIntent();

        title = getInfo.getStringExtra("quizTitle");
        numberOfQuestions = getInfo.getIntExtra("numberOfOptions",1);
        radioButton0 = (RadioButton) findViewById(R.id.radioButton0);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioId = radioButton0.getId();
        question = (EditText) findViewById(R.id.questionEditText);
        option0 = (EditText) findViewById(R.id.option0);
        option1 = (EditText) findViewById(R.id.option1);
        option2 = (EditText) findViewById(R.id.option2);
        option3 = (EditText) findViewById(R.id.option3);
        nextButton = (Button) findViewById(R.id.nextButton);
        count = 0;
        last = false;

        if(numberOfQuestions == 1) {
            nextButton.setText("Submit");
            last = true;
        }

        spinnerDuration = (Spinner)findViewById(R.id.spinnerDuration);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,R.layout.custom_spinner,durationOptions);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerDuration.setAdapter(adapter);
        spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                duration = durationOptions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                duration = 10;

            }
        });

        spinnerPoints = (Spinner)findViewById(R.id.spinnerPoints);
        ArrayAdapter<Integer> adapterPoints = new ArrayAdapter<Integer>(this,R.layout.custom_spinner,pointsOptions);
        adapterPoints.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerPoints.setAdapter(adapterPoints);
        spinnerPoints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                points = pointsOptions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                points = 10;

            }
        });


        idgen = this.getSharedPreferences(" com.androiddev.quizez", Context.MODE_PRIVATE);

        tr = idgen.getInt("IDforAll",0);
        if(tr==0){
            ID = 1;
        }
        else {
            ID = ++tr;
        }
        idgen.edit().putInt("IDforAll",ID).apply();


        home.exsql("CREATE TABLE IF NOT EXISTS q" + ID +" (question VARCHAR, option0 VARCHAR, option1 VARCHAR, option2 VARCHAR, option3 VARCHAR, correctanswer VARCHAR, duration INT(3), points INT(3))");
        home.exsql("CREATE TABLE IF NOT EXISTS l" + ID +" (name VARCHAR, score INT(9))");
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_menu_delete)
                .setTitle("Exit Quiz Creator?")
                .setMessage("You will lose all progress")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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