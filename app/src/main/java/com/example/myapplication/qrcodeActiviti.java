package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class qrcodeActiviti extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    String qrCode;
    int idroom;
    private class ToNomerByQR extends AsyncTask<Void, Void, String> {
        protected void  onPreExecute(){


        }
        @Override
        protected String doInBackground(Void... voids) {


            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"code\": \""+ qrCode +"\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/room-by-code")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + getToken())
                    .build();


           Log.e("tug","{\r\n    \"code\": \""+ qrCode +"\"\r\n}");

            try {
                return client.newCall(request).execute().body().string();
            } catch (IOException e) {
                Log.e("Tag", "tug");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result.contains("true")){
                    JSONObject jsonObject= new JSONObject(result);
                    JSONObject jone = new JSONObject(jsonObject.getString("room"));
                    idroom =jone.getInt("id");
                    new ToNomerInHotel().execute();
                }else {
                    Toast.makeText(qrcodeActiviti.this,"Ошибка",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(qrcodeActiviti.this,Zaseleniye.class));
                }
                Log.e("tag", result);
                Log.e("tag", qrCode);
            }catch (Exception e){

            }

        }
    }
    private class ToNomerInHotel extends AsyncTask<Void, Void, String> {
        protected void  onPreExecute(){


        }
        @Override
        protected String doInBackground(Void... voids) {


            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"room_id\":"+idroom+",\r\n    \"chek_in_date\":\"\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/check-in")
                    .method("POST", body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + getToken())
                    .build();



            Log.e("tug","{\r\n    \"room_id\":"+1+",\r\n    \"chek_in_date\":\"\"\r\n}");

            try {
                return client.newCall(request).execute().body().string();
            } catch (IOException e) {
                Log.e("Tag", "tug");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contains("true")){
                SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                e.putBoolean("ALREADYINHOTEL", true);
                e.apply();
                Intent i = new Intent(qrcodeActiviti.this, InHotel.class);
                startActivity(i);
                finish();
            }else Toast.makeText(qrcodeActiviti.this,"Ошибка",Toast.LENGTH_LONG).show();
            Log.e("tag", result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_activiti);
        if(ContextCompat.checkSelfPermission(qrcodeActiviti.this, android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, 123);
        }


            CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qrCode=result.getText().toString();
                        new ToNomerByQR().execute();

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==123){
            if (grantResults[0]==PackageManager.PERMISSION_DENIED){

            }else  Toast.makeText(this,"Permission otsutstvuet", Toast.LENGTH_LONG);
        }
    }
    private String getToken(){
        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(this);
        return encryptedPrefs.getString("token","123");
    }

}
