package com.example.myapplication.repository;

import android.util.Log;

import com.example.myapplication.view.adapter.HotelName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class jsonReader {
    public List<HotelName> getListHotel(String jsonResult){
        try {
            List<HotelName> HotelList = new ArrayList<>();
            JSONObject j = new JSONObject(jsonResult);
            JSONArray jArray = j.getJSONArray("organizations");
            for (int i=0; i < jArray.length(); i++)
            {
                JSONObject oneObject = jArray.getJSONObject(i);
                String oneObjectsItem = oneObject.getString("title");
                int idHotela  = oneObject.getInt("id");
                HotelList.add(new HotelName(oneObjectsItem,idHotela));


            }
            return HotelList;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public List<HotelName> getNumberList(String jsonResponce) {
        try {
            List<HotelName> NumberList = new ArrayList<>();
            JSONObject j = new JSONObject(jsonResponce);
            JSONArray jArray = j.getJSONArray("rooms");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject oneObject = jArray.getJSONObject(i);
                String oneObjectsItem = oneObject.getString("name");
                int idroom = oneObject.getInt("id");
                NumberList.add(new HotelName(oneObjectsItem, idroom));
                Log.e("добавил в намбер лист", NumberList.get(i).getName());
            }

            return NumberList;
        } catch (JSONException e) {
            Log.e("tatat", "не нашел комнаты по айди");
            return null;
        }
    }
}