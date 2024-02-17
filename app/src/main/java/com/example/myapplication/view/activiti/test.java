package com.example.myapplication.view.activiti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.myapplication.repository.MyPreferences;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class test extends AppCompatActivity {
    TextView editText;

    public int id;
    private class zakazuslug extends AsyncTask<Void, Void, String> {


        WebView webView=findViewById(R.id.webviewtuda);
        @Override
        protected String doInBackground(Void... voids) {


            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");;
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"service_id\":"+id+",\r\n    \"comment\": \"Комментарий\",\r\n    \"options\": [\r\n        {\r\n            \"type\": null,\r\n            \"name\": \"null\",\r\n            \"selected_values\": \"null\"\r\n        }\r\n    ]\r\n}");
            Log.d("tTtqttqtqq", "{\r\n    \"service_id\": 1,\r\n    \"comment\": \"Комментарий\",\r\n    \"options\": [\r\n        {\r\n            \"type\": 3,\r\n            \"name\": \"Название предмета\",\r\n            \"selected_values\": \"Рубашка, Джинсы\"\r\n        }\r\n    ]\r\n}");
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/orders/create")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + getToken())
                    .build();



            try {
                String response = client.newCall(request).execute().body().string();
                Log.e("Tagata",response);
                return response;

            } catch (IOException e) {
                Log.e("Tag", "мяу");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            Log.e("edittext",result);
            if (result.contains("true")){
            try {
                JSONObject j = new JSONObject(result);
                String confirmation_url = j.getString("confirmation_url");
               webView.getSettings().setJavaScriptEnabled(true);
              webView.loadUrl(confirmation_url);


            } catch (JSONException e) {

                Log.e("413","41");
            }}


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id= extras.getInt("variable");
        }
        new zakazuslug().execute();
    }
    private String getToken(){
        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(this);
        return encryptedPrefs.getString("token","123");
    }
}