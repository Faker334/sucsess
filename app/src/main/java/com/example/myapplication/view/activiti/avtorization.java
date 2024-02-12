package com.example.myapplication.view.activiti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.viewModel.AvtorizationViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class avtorization extends AppCompatActivity {
    AvtorizationViewModel avtorizationViewModel;
    Button Voyti_Button;
    Button RegistracuyaButton;
    EditText StrokaLogin;
    EditText StrokaParol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_avtorization);

        avtorizationViewModel =new AvtorizationViewModel();
        StrokaLogin = findViewById(R.id.emailEditTextImya);
        StrokaParol = findViewById(R.id.passwordEditText);
        RegistracuyaButton = findViewById(R.id.Registbutton);
        Voyti_Button =findViewById(R.id.to_registr_but);

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
                avtorizationViewModel.Login(StrokaLogin.getText().toString(),StrokaParol.getText().toString(),avtorization.this);

            }
        });


    }


}