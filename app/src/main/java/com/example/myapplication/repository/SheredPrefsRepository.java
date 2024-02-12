package com.example.myapplication.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.myapplication.MyPreferences;
import com.example.myapplication.view.activiti.avtorization;

public  class SheredPrefsRepository implements SheredPrefsRepositoryImpl{

    public String getToken(Context context) {
        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(context);
        return encryptedPrefs.getString("token","123");
    }

    @Override
    public void pootToken(Context context, String token) {
        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(context);
        SharedPreferences.Editor editor = encryptedPrefs.edit();
        editor.putString("token", token);
        editor.apply();
    }
    public void ALreadyINhotel(Context context){
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putBoolean("ALREADYINHOTEL", true);
        e.apply();
    }

}
