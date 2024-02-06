package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;

import java.io.IOException;
import java.security.GeneralSecurityException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ifFirst();

    }
    public void ifFirst(){
        Thread t = new Thread(new Runnable() {
            @Override
           public void run() {
                if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("FIRST_START", true)) {
                    Intent i = new Intent(MainActivity.this, FirstStartL.class);
                    startActivity(i);
                    finish();
                    Log.e("запустил приветствие","afa");
                }else {
                    Log.e("не запустил приветствие","afa");

                    if (MyPreferences.getEncryptedSharedPreferences(getBaseContext()).contains("token")){
                        Log.e("токен есть","data");
                        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("ALREADYINHOTEL",false)){
                            Log.e("уже в отеле","00");
                            startActivity(new Intent(MainActivity.this, InHotel.class));
                            finish();

                        }
                        else {startActivity(new Intent(MainActivity.this, Zaseleniye.class));
                            finish();
                            Log.e("еще не в отеле","00");}
                    }
                    else {
                        Intent i = new Intent(MainActivity.this, avtorization.class);
                        startActivity(i);
                        finish();
                        Log.e("токена нет","в авторизацию");
                    }}


            }
       });
        t.start();
    }

}