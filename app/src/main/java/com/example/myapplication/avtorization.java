package com.example.myapplication;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

    private class ProfilInroom extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(Void... voids) {



            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/profile")
                    .method("GET", null)
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " +getToken())
                    .build();



            try {
                String response = client.newCall(request).execute().body().string();
                Log.e("Tag",response);
                return response;


            } catch (IOException e) {
                Log.e("Tag", "мяу");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {


                JSONObject jObject = new JSONObject(result);
                JSONObject JopjectINprofile = new JSONObject(jObject.getString("profile"));
                Boolean checked_in = JopjectINprofile.getBoolean("checked_in");
               if (checked_in){
                   SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                   e.putBoolean("ALREADYINHOTEL", true);
                   e.apply();
                   startActivity(new Intent(avtorization.this, InHotel.class));
               }else startActivity(new Intent(avtorization.this, Zaseleniye.class));

            } catch (JSONException e) {
                ;
                Log.e("ereor",e.getMessage());
            }

        }
    }
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
            SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(avtorization.this);
                SharedPreferences.Editor editor = encryptedPrefs.edit();
                editor.putString("token", TokenFromJson(result));
                editor.apply();
                Log.e("Tag", TokenFromJson(result));
                new ProfilInroom().execute();

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
    public String TokenFromJson(String jsonString){
        try {
            JSONObject jObject = new JSONObject(jsonString);
            return jObject.getString("token");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    private String getToken(){
        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(this);
        return encryptedPrefs.getString("token","123");
    }
}