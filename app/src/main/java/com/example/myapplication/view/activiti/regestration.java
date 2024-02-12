package com.example.myapplication.view.activiti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class regestration extends AppCompatActivity {
    List<String> Registr;
    EditText regImya;
    EditText regFamil;
    EditText regPocha;
    EditText regPArol;
    Button ToRegist;

    private class RegistraciyaAsins extends AsyncTask<List<String>, Void, String> {

        protected void  onPreExecute(){
            Registr = new ArrayList<>();
            Registr.add(regImya.getText().toString());
            Registr.add(regFamil.getText().toString());
            Registr.add(regPocha.getText().toString());
            Registr.add(regPArol.getText().toString());
        }
        @Override

        protected String doInBackground(List<String>... voids) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"firstName\":\""+Registr.get(0)+"\",\r\n    \"lastName\":\""+Registr.get(1)+"\",\r\n    \"email\":\""+Registr.get(2)+"\",\r\n    \"password\":\""+Registr.get(3)+"\",\r\n    \"confirmPassword\":\""+Registr.get(3)+"\",\r\n    \"guard\":\"client\"\r\n}");
            Log.e("TAg","{\r\n    \"firstName\":\""+Registr.get(0)+"\",\r\n    \"lastName\":\""+Registr.get(1)+"\",\r\n    \"email\":\""+Registr.get(2)+"\",\r\n    \"password\":\""+Registr.get(3)+"\",\r\n    \"confirmPassword\":\""+Registr.get(3)+"\",\r\n    \"guard\":\"client\"\r\n}" );
            Log.e("tag","{\r\n    \"firstName\":\"name\",\r\n    \"lastName\":\"surname\",\r\n    \"email\":\"t.itarova@yandex.ru\",\r\n    \"password\":\"123456\",\r\n    \"confirmPassword\":\"123456\",\r\n    \"guard\":\"client\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/register")
                    .method("POST", body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build();
            try {
                String response = client.newCall(request).execute().body().string();
                Log.e("Tag", response);
                return response;




            } catch (IOException e) {
                Log.e("Tag", "мяу");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contains("true")){
                Intent i = new Intent(regestration.this, avtorization.class);
                startActivity(i);
            }else Toast.makeText(regestration.this,"ошибка",Toast.LENGTH_LONG).show();

            // Обновление пользовательского интерфейса по завершении
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);
        regImya=findViewById(R.id.emailEditTextImya);
        regFamil=findViewById(R.id.emailEditTextFamili);
        regPocha=findViewById(R.id.emailEditTextPochta);
        regPArol=findViewById(R.id.emailEditTextParol);
        ToRegist = findViewById(R.id.to_registr_but);

        ToRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegistraciyaAsins().execute(Registr);
            }
        });

    }
}