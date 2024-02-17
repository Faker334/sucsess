package com.example.myapplication.repository;

import android.util.Log;

import com.example.myapplication.repository.models.FioProfil;
import com.example.myapplication.repository.models.Order;
import com.example.myapplication.view.adapter.HotelName;
import com.example.myapplication.view.adapter.OptionsServis;
import com.example.myapplication.view.adapter.Servise;

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
    public ArrayList<Servise> getServise(String response){
        try {
            IconMap iconMap = new IconMap();
            ArrayList<Servise> uslugi = new ArrayList<>();
            JSONObject jObject = new JSONObject(response);
            JSONArray jArray = jObject.getJSONArray("services");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject joneObject = new JSONObject(jArray.getString(i));
                String name = joneObject.getString("name");
                String cena = joneObject.getString("price");
                String icon = joneObject.getString("icon");
                int id = joneObject.getInt("id");
                List<OptionsServis> optionsServis = new ArrayList<>();

                if (!joneObject.isNull("options")) {
                    JSONArray jArraywithOptons = joneObject.getJSONArray("options");
                    for (int j = 0; j < jArraywithOptons.length(); j++) {
                        JSONObject jObjeckWithOneOptions = new JSONObject(jArraywithOptons.getString(j));
                        int TypeOptons = jObjeckWithOneOptions.getInt("type");
                        String OPtionsName = jObjeckWithOneOptions.getString("name");
                        String[] optionValue = jObjeckWithOneOptions.getString("values").split(",");
                        optionsServis.add(new OptionsServis(TypeOptons,OPtionsName,optionValue));
                    }
                }
                Log.e("icon",icon);
                uslugi.add(new Servise(name,cena,iconMap.REsoursMap.get(icon),id,optionsServis));

            }
            return uslugi;

        } catch (JSONException e) {
            Log.e("getUslugi",e.getMessage());
        }
        Log.e("uslugi","null");
        return null;

    }
    public FioProfil getProfil(String responce){
        try {
            JSONObject jObject = new JSONObject(responce);
            JSONObject JopjectINprofile = new JSONObject(jObject.getString("profile"));
            String Emailrofil = JopjectINprofile.getString("email");
            String FullNameProfil = JopjectINprofile.getString("full_name");
            return new FioProfil(FullNameProfil,Emailrofil);
        } catch (JSONException e) {
            Log.e("ereor",e.getMessage());
        }
        return null;
    }
    public List<Order> gerOReders(String responce){
        try {
            List<Order> orders = new ArrayList<>();
            JSONObject jobject = new JSONObject(responce);
            JSONArray jOrders= jobject.getJSONArray("orders");
            for (int i = 0; i < jOrders.length(); i++) {
                JSONObject oneOrders =jOrders.getJSONObject(i);
              String data = oneOrders.getString("created_at");
                int status = oneOrders.getInt("status");
              JSONObject servis = oneOrders.getJSONObject("service");
              String name = servis.getString("name");
              int id = oneOrders.getInt("id");
              orders.add(new Order(id,name,data,status));
            }
            return orders;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}