package com.androiddev.quizez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class settings extends AppCompatActivity {

    EditText editText;
    Button submit;

    public void changeName(View view){
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings);
        editText = findViewById(R.id.usernameChange);
        editText.setText(MainActivity.prefs.getString("userfirst","Null"));
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals("")){
                    Snackbar.make(v,"Please Enter a Name",Snackbar.LENGTH_SHORT).show();
                }
                else {
                    String n = editText.getText().toString();
                    MainActivity.prefs.edit().putString("userfirst",n).apply();
                    Toast.makeText(settings.this, "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent tohome = new Intent(getApplicationContext(),home.class);
        tohome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(tohome);
    }
}