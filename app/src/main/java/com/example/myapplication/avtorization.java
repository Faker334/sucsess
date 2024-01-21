package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class avtorization extends AppCompatActivity {
    Button Voyti_Button;
    Button RegistracuyaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtorization);

        RegistracuyaButton = findViewById(R.id.Registbutton);
        Voyti_Button =findViewById(R.id.VoityButton);


        RegistracuyaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(avtorization.this, regestration.class);
                startActivity(i);
            }
        });

        Voyti_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(avtorization.this, Zaseleniye.class);
                startActivity(i);
            }
        });




        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(this);
        SharedPreferences.Editor editor = encryptedPrefs.edit();
        editor.putString("token", "sikritinfo");
        editor.apply();


    }
}