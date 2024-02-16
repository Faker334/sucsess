package com.example.myapplication.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public  class SheredPrefsRepository implements SheredPrefsRepositoryImpl{
    Context context;

    public SheredPrefsRepository(Context context){
        this.context=context;

    }

    public String getToken() {
        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(context);
        Log.e(" getToken()", "выдал токен");
        return encryptedPrefs.getString("token","123");

    }


    public void pootToken(String token) {
        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(context);
        SharedPreferences.Editor editor = encryptedPrefs.edit();
        editor.putString("token", token);
        editor.apply();
        Log.e("pootToken", "положил токен");
    }
    public void ALreadyINhotel(){
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putBoolean("ALREADYINHOTEL", true);
        Log.e("ALreadyINhotel()", "записал тру");
        e.apply();
    }

}
