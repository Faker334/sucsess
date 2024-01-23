package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.inventory.HotelName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Zaseleniye extends AppCompatActivity  {
    String IdHotel="-1";

    String NumberNomer="-1";
    String data="-1";

    TextView SpinerHOtelTextVIew;
    TextView SpinerNumberTextVIew;

    List<HotelName> HotelList = new ArrayList<>();
    List<HotelName> NumberlList = new ArrayList<>();
    private CustomSpinner spinner_Hotel;
    private CustomSpinner spinner_Number;

    private FruitAdapter HotelAdapter;
    private FruitAdapter NumberAdapter;


    private class GetListHotel extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {


            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/organizations")
                    .method("GET", null)
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + getToken()) // Добавление заголовка Authorization
                    .build();


            try {
               return client.newCall(request).execute().body().string();
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
                JSONArray jArray = j.getJSONArray("organizations");
                for (int i=0; i < jArray.length(); i++)
                {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        String oneObjectsItem = oneObject.getString("id");
                        HotelList.add(new HotelName(oneObjectsItem));


                }
                Log.e("TAAAAAAG", result);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private class GetNomerBYHOtel extends AsyncTask<Void, Void, String> {
        protected void  onPreExecute(){

        NumberlList.clear();
        NumberlList.add(new HotelName("1"));
        }
        @Override
        protected String doInBackground(Void... voids) {


            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/organizations/"+IdHotel+"/rooms")
                    .method("GET", null)
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + getToken()) // Добавление заголовка Authorization
                    .build();


            try {
                return client.newCall(request).execute().body().string();
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
                JSONArray jArray = j.getJSONArray("rooms");
                for (int i=0; i < jArray.length(); i++)
                {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    String oneObjectsItem = oneObject.getString("name");
                    NumberlList.add(new HotelName(oneObjectsItem));
                   Log.e("добавил в намбер лист",NumberlList.get(i).getName());
                }

            } catch (JSONException e) {
                Log.e("tatat","не нашел комнаты по айди"+IdHotel);
            }
            Log.e("TTTTTTTTTAAAAAAAAAAAAAAAGGGG", result);
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
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"room_id\":"+NumberNomer+",\r\n    \"chek_in_date\":\""+data+"\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://app.successhotel.ru/api/client/check-in")
                    .method("POST", body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + getToken())
                    .build();



            Log.e("tatata","{\r\n    \"room_id\":"+NumberNomer+",\r\n    \"chek_in_date\":\""+data+"\"\r\n}");

            try {
                return client.newCall(request).execute().body().string();
            } catch (IOException e) {
                Log.e("Tag", "мяу");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contains("true")){

                Intent i = new Intent(Zaseleniye.this, InHotel.class);
                startActivity(i);
            }else Toast.makeText(Zaseleniye.this,"Ошибка",Toast.LENGTH_LONG).show();
            Log.e("TTTTTTTTTAAAAAAAAAAAAAAAGGGG", result);
        }
    }

    Button InhotelBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaseleniye);

        currentDateTime = findViewById(R.id.currentDateTime);

        SpinerHOtelTextVIew =findViewById(R.id.spinerhoteltext);
        SpinerNumberTextVIew= findViewById(R.id.spinernumberltext);
        SpinerNumberTextVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinner_Number.performClick();
            }
        });
        SpinerHOtelTextVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_Hotel.performClick();
            }
        });
        HotelList.add(new HotelName("-1"));
        NumberlList.add(new HotelName("-1"));







        InhotelBut= findViewById(R.id.button_in_hoyel);
        InhotelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IdHotel.equals("-1") && !(NumberNomer.equals("-1"))&& !(data.equals("-1"))){
                new ToNomerInHotel().execute();}

            }
        });


        spinner_Hotel = findViewById(R.id.spinner_hotel);                   //присвоение адаптеров
        HotelAdapter = new FruitAdapter(Zaseleniye.this, HotelList);
        spinner_Hotel.setAdapter(HotelAdapter);

        spinner_Number =findViewById(R.id.spinner_nomer);
        NumberAdapter = new FruitAdapter(Zaseleniye.this, NumberlList);
        spinner_Number.setPrompt("выберите номеррррр");
        spinner_Number.setAdapter(NumberAdapter);




        spinner_Hotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получение выбранного элемента как объекта
                Log.e("нажали на спинер_хотел","777");
                IdHotel = HotelList.get(position).getName();
                if (position!=0){
                    spinner_Number.setEnabled(true);
                    new GetNomerBYHOtel().execute();
                    SpinerHOtelTextVIew.setText(HotelList.get(position).getName());
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner_Number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                NumberNomer = NumberlList.get(position).getName();
                SpinerNumberTextVIew.setText(NumberNomer);
                Log.e("Зашел в лист спинер_намбер",NumberNomer);}
                            }
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("tata","Twet");
            }
        });


        new GetListHotel().execute();




    }

private String getToken(){
    SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(this);
    return encryptedPrefs.getString("token","123");
}

    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(Zaseleniye.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();

    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(Zaseleniye.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());


        data = dateFormat.format(dateAndTime.getTimeInMillis());
        currentDateTime.setText(data);
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
            setTime(view);
        }
    };


}