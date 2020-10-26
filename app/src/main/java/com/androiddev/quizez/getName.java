package com.androiddev.quizez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class getName extends AppCompatActivity {

    EditText editText;
    Button button;
    String IDcode;
    String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_get_name);
        editText = findViewById(R.id.edittext);
        Intent getCode = getIntent();
        IDcode = getCode.getStringExtra("ID");
        Name = getCode.getStringExtra("NameQ");
        button = findViewById(R.id.sendName);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qtext = editText.getText().toString().replaceAll(" ","");
                if (qtext.equals("")){
                    Snackbar.make(v,"Please Enter a Name",Snackbar.LENGTH_LONG).show();
                }
                else if(qtext.contains("'")){
                    Snackbar.make(v,"Special character  '  is invalid",Snackbar.LENGTH_LONG).show();
                }
                else {
                    finish();
                    Intent intent = new Intent(getApplicationContext(),playQuiz.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Name",editText.getText().toString());
                    intent.putExtra("IDcode",IDcode);
                    intent.putExtra("NameQ",Name);
                    startActivity(intent);
                }
            }
        });
    }

}