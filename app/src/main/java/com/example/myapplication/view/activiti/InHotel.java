package com.example.myapplication.view.activiti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.MyPreferences;
import com.example.myapplication.R;
import com.example.myapplication.view.adapter.State;
import com.example.myapplication.view.adapter.StateAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollState;
import me.everything.android.ui.overscroll.IOverScrollStateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class InHotel extends AppCompatActivity {

    WebView Webview;
    ImageButton button_moi_zaprosi;
    ImageButton button_servis;
    ImageButton button_profil;
    ImageView imageView;
    ArrayList<State> uslugi = new ArrayList<State>();
   StateAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView FioProfil;
    TextView PoctaProfil;
    TextView ActivButton;
    Button VisilitcyaButt;
    ImageButton ExitButton;
    ConstraintLayout profilLayout;

    Map<String,Integer> REsoursMap= new HashMap<>();




    private class ListUslug extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            swipeRefreshLayout.setRefreshing(true);
              uslugi.clear();
        }
            @Override
            protected String doInBackground(Void... voids) {


                OkHttpClient client = new OkHttpClient().newBuilder().build();
                Request request = new Request.Builder()
                        .url("https://app.successhotel.ru/api/client/services")
                        .method("GET", null) // Используйте null вместо body для метода GET
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer " + getToken()) // Предполагая, что у вас есть переменная token
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
                String[] ootionsvalue = new String[3];

                   JSONObject jObject = new JSONObject(result);
                        JSONArray jArray = jObject.getJSONArray("services");
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject joneObject = new JSONObject(jArray.getString(i));
                        String name = joneObject.getString("name");
                        String cena = joneObject.getString("price");
                        String icon = joneObject.getString("icon");
                        int id = joneObject.getInt("id");

                        Map<Integer, String> nameOpyionsmMap = new HashMap<>(); // Создаем новый Map для каждого элемента

                        if (!joneObject.isNull("options")) {
                            JSONArray jArraywithOptons = joneObject.getJSONArray("options");
                            for (int j = 0; j < jArraywithOptons.length(); j++) {
                                JSONObject jObjeckWithOneOptions = new JSONObject(jArraywithOptons.getString(j));

                                int TypeOptons = jObjeckWithOneOptions.getInt("type");
                                String OPtionsName = jObjeckWithOneOptions.getString("name");
                                nameOpyionsmMap.put(TypeOptons, OPtionsName);

                                if (TypeOptons == 3) {
                                    ootionsvalue = jObjeckWithOneOptions.getString("values").split(",");
                                }
                            }
                        }
                        Log.e("icon",icon);
                       uslugi.add(new State(name,cena,REsoursMap.get(icon),nameOpyionsmMap,ootionsvalue,id));

                    }

                       adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);

       } catch (JSONException e) {
                    ;
                    Log.e("ereor",e.getMessage());
                }

            }
        }
    private class ListZakazov extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(Void... voids) {


            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/orders")
                    .method("GET", null) // Используйте null вместо body для метода GET
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + getToken()) // Предполагая, что у вас есть переменная token
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
//            try {
//
//
//                JSONObject jObject = new JSONObject(result);   
//                JSONArray jArray = jObject.getJSONArray("orders");
//                for (int i = 0; i < jArray.length(); i++) {
//                    JSONObject joneObject = new JSONObject(jArray.getString(i));
//                    String name = joneObject.getString("name");
//                    String Status = joneObject.getString("status");
//                    String time = joneObject.getString("time");
//
//
//                }
//
//                
//                swipeRefreshLayout.setRefreshing(false);
//
//            } catch (JSONException e) {
//                ;
//                Log.e("ereor",e.getMessage());
//            }
            swipeRefreshLayout.setRefreshing(false);
        }  //обработка ответа на список услуг ответ не известен
    }
    private class ProfilExequete extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            swipeRefreshLayout.setRefreshing(true);
        }
        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/profile")
                    .method("GET", null)
                    .addHeader("Accept", "application/json")
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


                JSONObject jObject = new JSONObject(result);
                JSONObject JopjectINprofile = new JSONObject(jObject.getString("profile"));
               String Emailrofil = JopjectINprofile.getString("email");
                String FullNameProfil = JopjectINprofile.getString("full_name");
                FioProfil.setText(FullNameProfil);
                PoctaProfil.setText(Emailrofil);
                swipeRefreshLayout.setRefreshing(false);

            } catch (JSONException e) {
                ;
                Log.e("ereor",e.getMessage());
            }

        }
    }
    private class Visilenie extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... voids) {



            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/check-out")
                    .method("GET", null)
                    .addHeader("Accept", "application/json")
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
                SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                e.putBoolean("ALREADYINHOTEL", false);
                e.apply();
                Intent i = new Intent(InHotel.this,Zaseleniye.class);
                startActivity(i);
                finish();
                if (!result.contains("true")){
                    Toast.makeText(InHotel.this, "Ошибка", Toast.LENGTH_SHORT).show();

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
    swipeRefreshLayout = findViewById(R.id.refreshlayaut);
    RecyclerView ServisirecyclerView = findViewById(R.id.servis_recikleview);
    RecyclerView MoiZakaziRecyclerView = findViewById(R.id.moizakazi_recikleview);
    FioProfil = findViewById(R.id.FioPRofil);
    PoctaProfil = findViewById(R.id.PochtaProfile);
    VisilitcyaButt =findViewById(R.id.visilitsya_button);
    ExitButton = findViewById(R.id.exitButton);
    profilLayout =findViewById(R.id.ProfilLayout);
    ActivButton = findViewById(R.id.Activ);


    REsoursMap.put("blender.png",R.drawable.blender);
    REsoursMap.put("washing-machine.png",R.drawable.washing_machine);
    REsoursMap.put("safe-box.png",R.drawable.safe_box);
    REsoursMap.put("fence.png",R.drawable.fence);
    REsoursMap.put("nightstand.png",R.drawable.nightstand);
    REsoursMap.put("dining-table.png",R.drawable.dining_table);
    REsoursMap.put("smart-lock.png",R.drawable.smart_lock);
    REsoursMap.put("sofa.png",R.drawable.sofa);
    REsoursMap.put("trash-can.png",R.drawable.trash_can);



        new ListUslug().execute();
        new ProfilExequete().execute();
    ExitButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(InHotel.this);
            encryptedPrefs.edit().remove("token");

            SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
            e.putBoolean("ALREADYINHOTEL", false);
            e.apply();
            Log.e("вышел","удалил");
            Intent i = new Intent(InHotel.this, avtorization.class);
            startActivity(i);
            finish();
        }
    });
    VisilitcyaButt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           new Visilenie().execute();
        }
    });




        // определяем слушателя нажатия элемента в списке
        StateAdapter.OnStateClickListener stateClickListener = new StateAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(State state, int position) {
                if (state.getOptionsName().isEmpty()){
                    Intent i = new Intent(InHotel.this, test.class);
                    i.putExtra("variable",state.getOrganization_id());
                    startActivity(i);

                }else {

                Dialog dialog = new Dialog(InHotel.this);
                ShowCustomDialog(dialog,uslugi.get(position));}
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


                   if (!swipeRefreshLayout.isRefreshing()){
                       new ListUslug().execute();
                   }
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
                    ServisirecyclerView.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    profilLayout.setVisibility(View.GONE);
                    ActivButton.setText("Мои запросы");

                    new ListZakazov().execute();

                }
            });
        button_servis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_servis.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.green)));
                button_moi_zaprosi.setBackgroundTintList(s);
                button_profil.setBackgroundTintList(s);
                imageView.setColorFilter(getColor(R.color.beliy));
                ActivButton.setText("Сервисы");


                swipeRefreshLayout.setVisibility(View.VISIBLE);
                ServisirecyclerView.setVisibility(View.VISIBLE);
                MoiZakaziRecyclerView.setVisibility(View.GONE);
                profilLayout.setVisibility(View.GONE);

            }
        });
        button_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_profil.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.green)));
                button_moi_zaprosi.setBackgroundTintList(s);
                button_servis.setBackgroundTintList(s);
                imageView.setColorFilter(getColor(R.color.cherniy));

                profilLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
                ActivButton.setText("Мой профиль");



            }
        });







    }


    private  void  ShowCustomDialog(Dialog dialog,State state){
        dialog.setContentView(R.layout.item_view_in_dialog_in_zakaz_servis);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.zakrugl);



        ImageButton PlusButton = dialog.findViewById(R.id.Buttoncountplus);
        ImageButton MinusButton = dialog.findViewById(R.id.ButtonCountMinus);
        TextView CunTedittext = dialog.findViewById(R.id.textViewCount);
        ConstraintLayout constraintLayout1=dialog.findViewById(R.id.layout_tipe_1);
        ConstraintLayout constraintLayout2=dialog.findViewById(R.id.layout_tipe_2);
        ConstraintLayout constraintLayou3=dialog.findViewById(R.id.layout_tipe_3);
        TextView Nametipe1 = dialog.findViewById(R.id.name_type1);
        TextView Nametipe2 = dialog.findViewById(R.id.name_tipe2);
        TextView Nametipe3 = dialog.findViewById(R.id.name_tipe3);
        Spinner spinner = dialog.findViewById(R.id.spinner_options);
        Button zakazUslug = dialog.findViewById(R.id.buttonzakazulsug);
        zakazUslug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(dialog.getContext(), test.class);
                 i.putExtra("variable",state.getOrganization_id());
                startActivity(i);

            }
        });


        PlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               CunTedittext.setText(String.valueOf(Integer.parseInt(CunTedittext.getText().toString())+1));

            }
        });
        MinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CunTedittext.setText(String.valueOf(Integer.parseInt(CunTedittext.getText().toString())-1));

            }
        });



        if (state.getOptionsName().containsKey(1)){
            constraintLayout1.setVisibility(View.VISIBLE);
            Nametipe1.setText(state.getOptionsName().get(1));

        }
        if (state.getOptionsName().containsKey(2)){
            constraintLayout2.setVisibility(View.VISIBLE);
            Nametipe2.setText(state.getOptionsName().get(2));

        }
        if (state.getOptionsName().containsKey(3)){
            constraintLayou3.setVisibility(View.VISIBLE);
            Nametipe3.setText(state.getOptionsName().get(3));
            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, state.getOptionsValue());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

        }


        dialog.show();


    }
    private String getToken(){
        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(this);
        return encryptedPrefs.getString("token","123");
    }
}