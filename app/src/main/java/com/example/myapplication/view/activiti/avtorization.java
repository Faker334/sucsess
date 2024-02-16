package com.example.myapplication.view.activiti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.viewModel.AvtorizationViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
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
        avtorizationViewModel = new ViewModelProvider(this).get(AvtorizationViewModel.class);

        StrokaLogin = findViewById(R.id.emailEditTextImya);
        StrokaParol = findViewById(R.id.passwordEditText);
        RegistracuyaButton = findViewById(R.id.Registbutton);
        Voyti_Button =findViewById(R.id.to_registr_but);
        avtorizationViewModel.getMutableAlreadyInhotel().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    startActivity(new Intent(avtorization.this, InHotel.class));
                }else startActivity(new Intent(avtorization.this, Zaseleniye.class));

            }
        });
        avtorizationViewModel.getMutableLoginEror().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(avtorization.this,"Неверный логин или пароль",Toast.LENGTH_LONG).show();
            }
        });

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
                avtorizationViewModel.Login(StrokaLogin.getText().toString(),StrokaParol.getText().toString());
            }
        });
    }


}