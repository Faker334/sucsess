package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String token = "155|PSoTmjnJCMs69eKh9cac7Ajktvg1i1dgugCtcFX0"; // Замените на реальный токен пользователя

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/profile")
                    .method("GET", null)
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + token) // Добавление заголовка Authorization
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
    private class MyAsyncTaskk extends AsyncTask<Void, Void, Void> {
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

        button = findViewById(R.id.knopka);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MyAsyncTask().execute();
            }
        });
        new MyAsyncTaskk().execute();




    }
}