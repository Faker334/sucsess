package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollState;
import me.everything.android.ui.overscroll.IOverScrollStateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class InHotel extends AppCompatActivity {

    WebView Webview;
    ImageButton button_moi_zaprosi;
    ImageButton button_servis;
    ImageButton button_profil;
    ImageView imageView;
    ArrayList<State> uslugi = new ArrayList<State>();
    StateAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    private class ListUslug extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
              uslugi.clear();
        }
            @Override
            protected String doInBackground(Void... voids) {
                String token = "155|PSoTmjnJCMs69eKh9cac7Ajktvg1i1dgugCtcFX0"; // Замените на реальный токен пользователя


                OkHttpClient client = new OkHttpClient().newBuilder().build();
                Request request = new Request.Builder()
                        .url("https://app.successhotel.ru/api/client/services")
                        .method("GET", null) // Используйте null вместо body для метода GET
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer " + token) // Предполагая, что у вас есть переменная token
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
                        JSONArray jArray = jObject.getJSONArray("services");
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject joneObject =new JSONObject(jArray.getString(i));
                        String name = joneObject.getString("name");
                        String cena = joneObject.getString("price");

                       uslugi.add(new State(name,cena,R.drawable.datasvg));}

                       adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);

//                    for (int i=0; i < jArray.length(); i++)
//                    {
//                        JSONObject oneObject = jArray.getJSONObject(i);
//                        String TypeOptions = oneObject.getString("name");
//                        String nameOptions = oneObject.getString("price");
//                        states.add(new State(jObject.getString(TypeOptions),jObject.getString(nameOptions),R.drawable.datasvg));
//
//
//                    }
                    //adapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    ;
                    Log.e("ereor",e.getMessage());
                }

            }
        }
    private class zalazuslug extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {


            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");;
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"service_id\": 1,\r\n    \"comment\": \"Комментарий\",\r\n    \"options\": [\r\n        {\r\n            \"type\": null,\r\n            \"name\": \"null\",\r\n            \"selected_values\": \"null\"\r\n        }\r\n    ]\r\n}");
            Log.d("tTtqttqtqq", "{\r\n    \"service_id\": 1,\r\n    \"comment\": \"Комментарий\",\r\n    \"options\": [\r\n        {\r\n            \"type\": 3,\r\n            \"name\": \"Название предмета\",\r\n            \"selected_values\": \"Рубашка, Джинсы\"\r\n        }\r\n    ]\r\n}");
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/orders/create")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer " + getToken())
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
                JSONObject  j = new JSONObject(result);
              String confirmation_url = j.getString("confirmation_url");
                Webview.getSettings().setJavaScriptEnabled(true);
                Webview.loadUrl(confirmation_url);
            } catch (JSONException e) {
                Log.e("413","41");
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_hotel);
    button_moi_zaprosi = findViewById(R.id.Button_moi_zaprosi);
    button_servis = findViewById(R.id.Button_servisi);
    button_profil = findViewById(R.id.Button_profil);
    imageView=findViewById(R.id.imageView9);
    RecyclerView ServisirecyclerView = findViewById(R.id.servis_recikleview);
    RecyclerView MoiZakaziRecyclerView = findViewById(R.id.moizakazi_recikleview);


        swipeRefreshLayout = findViewById(R.id.refreshlayaut);

        // определяем слушателя нажатия элемента в списке
        StateAdapter.OnStateClickListener stateClickListener = new StateAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(State state, int position) {

                Toast.makeText(getApplicationContext(), "Был выбран пункт " + state.getName(),
                        Toast.LENGTH_SHORT).show();

                //код после нажатия на айтем
            }
        };
         adapter = new StateAdapter(this, uslugi, stateClickListener);

        ServisirecyclerView.setAdapter(adapter);




        ServisirecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {  //кольцо обновления не раб
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(true);
//                new ListUslug().execute();
//
//            }
//        });

        IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(ServisirecyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        decor.setOverScrollStateListener(new IOverScrollStateListener() {  //Отслежевание чрезмерного скрола
            @Override
            public void onOverScrollStateChange(IOverScrollDecor decor, int oldState, int newState) {
                if (newState == IOverScrollState.STATE_IDLE) {
                    swipeRefreshLayout.setRefreshing(true);
                    new ListUslug().execute();
                }
            }
        });



        //СЛУШАТЕЛЬ НАЖАТИЙ ТРЕХ КНОПОК СЕРВИС МОИ ЗАПРОЫ, ПРОФИЛЬ
        ColorStateList s =button_moi_zaprosi.getBackgroundTintList();
            button_moi_zaprosi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    button_moi_zaprosi.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.green)));
                    button_profil.setBackgroundTintList(s);
                    button_servis.setBackgroundTintList(s);
                    imageView.setColorFilter(getColor(R.color.cherniy));
                    ServisirecyclerView.setVisibility(View.GONE);
                    MoiZakaziRecyclerView.setVisibility(View.VISIBLE);
                }
            });
        button_servis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_servis.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.green)));
                button_moi_zaprosi.setBackgroundTintList(s);
                button_profil.setBackgroundTintList(s);
                imageView.setColorFilter(getColor(R.color.beliy));


                ServisirecyclerView.setVisibility(View.VISIBLE);
                MoiZakaziRecyclerView.setVisibility(View.GONE);
            }
        });
        button_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_profil.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.green)));
                button_moi_zaprosi.setBackgroundTintList(s);
                button_servis.setBackgroundTintList(s);
                imageView.setColorFilter(getColor(R.color.cherniy));

            }
        });


//        Webview =findViewById(R.id.webview1);
        new ListUslug().execute();
        // new zalazuslug().execute();




    }
    private String getToken(){
        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(this);
        return encryptedPrefs.getString("token","123");
    }
}