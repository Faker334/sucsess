package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class avtorization extends AppCompatActivity {
    Button Voyti_Button;
    Button RegistracuyaButton;
    EditText StrokaLogin;
    EditText StrokaParol;
    List<String> Login;

    private class Avtorizaciya extends AsyncTask<List<String>, Void, String> {

        protected void  onPreExecute(){
            Login = new ArrayList<>();
            Login.add(StrokaLogin.getText().toString());
            Login.add(StrokaParol.getText().toString());
        }
    @Override

        protected String doInBackground(List<String>... voids) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"email\":\""+ Login.get(0)+ "\",\r\n    \"password\":\""+Login.get(1)+"\"\r\n\r\n}");

            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/login")
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
                Intent i = new Intent(avtorization.this, Zaseleniye.class);
                startActivity(i);


            SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(avtorization.this);
                SharedPreferences.Editor editor = encryptedPrefs.edit();
                editor.putString("token", jsonPars(result));
                editor.apply();
                Log.e("Tag", jsonPars(result));



            }else Toast.makeText(avtorization.this,"Неверный логин или пароль",Toast.LENGTH_LONG).show();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtorization);

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

                new Avtorizaciya().execute(Login);
            }
        });







    }
    public String jsonPars(String jsonString){
        try {
            JSONObject jObject = new JSONObject(jsonString);
            return jObject.getString("token");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}