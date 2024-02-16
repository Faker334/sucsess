package com.example.myapplication.view.activiti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.view.adapter.Adapter;
import com.example.myapplication.view.adapter.CustomSpinner;
import com.example.myapplication.repository.MyPreferences;
import com.example.myapplication.R;
import com.example.myapplication.view.adapter.HotelName;
import com.example.myapplication.viewModel.AvtorizationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class Zaseleniye extends AppCompatActivity  {
    AvtorizationViewModel avtorizationViewModel;
    int IdHotel=-1;
    int idroom;
    String NumberNomer="-1";
    String data="-1";

    TextView SpinerHOtelTextVIew;
    TextView SpinerNumberTextVIew;

    List<HotelName> HotelList = new ArrayList<>();
    List<HotelName> NumberlList = new ArrayList<>();
    private CustomSpinner spinner_Hotel;
    private CustomSpinner spinner_Number;

    private Adapter HotelAdapter;
    private Adapter NumberAdapter;
    Button qrCodeButton;
    Button InhotelBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaseleniye);

        avtorizationViewModel = new ViewModelProvider(this).get(AvtorizationViewModel.class);
        HotelList.add(new HotelName("-1",-1));
        NumberlList.add(new HotelName("-1",-1));
        avtorizationViewModel.GetListHotel(); //запрос списка отелей

        //3 мутабла
        avtorizationViewModel.getMutableListHOtel().observe(this, new Observer<List<HotelName>>() {  // по приходу фиксирует список отелей
            @Override
            public void onChanged(List<HotelName> hotelNames) {
                HotelList.addAll(hotelNames);
            }
        });
        avtorizationViewModel.getMutableListNaumberNomer().observe(this, new Observer<List<HotelName>>() {
            @Override
            public void onChanged(List<HotelName> hotelNames) {
                NumberlList.addAll(hotelNames);
            }
        });
        avtorizationViewModel.getMutableGetNomberSuccses().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    startActivity(new Intent(Zaseleniye.this,InHotel.class));
                }else Toast.makeText(Zaseleniye.this,"Eror",Toast.LENGTH_LONG);
            }
        });

        currentDateTime = findViewById(R.id.currentDateTime);
        SpinerHOtelTextVIew =findViewById(R.id.spinerhoteltext);
        SpinerNumberTextVIew= findViewById(R.id.spinernumberltext);
        qrCodeButton= findViewById(R.id.button_qrcod);

        qrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Zaseleniye.this, qrcodeActiviti.class));
            }
        });
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

        InhotelBut= findViewById(R.id.button_in_hoyel);
        InhotelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IdHotel!=-1 && idroom!=-1&& !(data.equals("-1"))){
                avtorizationViewModel.getNomber(idroom,data);
                }

            }
        });


        spinner_Hotel = findViewById(R.id.spinner_hotel);                   //присвоение адаптеров
        HotelAdapter = new Adapter(Zaseleniye.this, HotelList);
        spinner_Hotel.setAdapter(HotelAdapter);

        spinner_Number =findViewById(R.id.spinner_nomer);
        NumberAdapter = new Adapter(Zaseleniye.this, NumberlList);
        spinner_Number.setPrompt("выберите номеррррр");
        spinner_Number.setAdapter(NumberAdapter);

        spinner_Hotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получение выбранного элемента как объекта
                Log.e("нажали на спинер_хотел","777");
                IdHotel = HotelList.get(position).getIdroom();
                if (position!=0){
                    spinner_Number.setEnabled(true);
                    avtorizationViewModel.getNamberByhotel(IdHotel);
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
                idroom = NumberlList.get(position).getIdroom();
                SpinerNumberTextVIew.setText(NumberlList.get(position).getName());
                Log.e("Зашел в лист спинер_намбер",NumberNomer);}
                            }
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("tata","Twet");
            }
        });
    }
//////////////выбор даты

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