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

        private class ListUslug extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                String token = "155|PSoTmjnJCMs69eKh9cac7Ajktvg1i1dgugCtcFX0"; // Замените на реальный токен пользователя


                OkHttpClient client = new OkHttpClient().newBuilder().build();
                Request request = new Request.Builder()
                        .url("https://app.successhotel.ru/api/client/services")
                        .method("GET", null) // Используйте null вместо body для метода GET
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer " + token) // Предполагая, что у вас есть переменная token
                        .build();



                try {
                    Response response = client.newCall(request).execute();


                    Log.e("Tag",response.body().string());

                } catch (IOException e) {
                    Log.e("Tag", "мяу");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // Обновление пользовательского интерфейса по завершении
            }
        }
    private class avtorizaciya extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"email\":\"t.itarova@yandex.ru\",\r\n    \"password\":\"123456\"\r\n\r\n}");
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/login")
                    .method("POST", body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build();




            try {
                Response response = client.newCall(request).execute();


                Log.e("Tag",response.body().string());

            } catch (IOException e) {
                Log.e("Tag", "мяу");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Обновление пользовательского интерфейса по завершении
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         //   ifFirst();
            //new ListUslug().execute();
     //  new avtorizaciya().execute();
//        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(this);
//        if (encryptedPrefs.contains("token")){
//            String data = encryptedPrefs.getString("token", "123");
//            Log.e("tag","токен есть");
//            Log.e("tag",data);
//        }else {
            Intent i = new Intent(MainActivity.this, avtorization.class);
            startActivity(i);



            Log.e("tag","токена нет, записал");

//        }

// Чтение данных





    }
    public void ifFirst(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                boolean isFirstStart = getPrefs.getBoolean("FIRST_START", true);

                if (isFirstStart) {
                    Intent i = new Intent(MainActivity.this, FirstStartL.class);
                    startActivity(i);

                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("FIRST_START", false);
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();
    }

}