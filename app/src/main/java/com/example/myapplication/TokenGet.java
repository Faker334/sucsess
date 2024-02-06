package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenGet {
    String Token;
    TokenGet(Context context){
        SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(context);
        Token = encryptedPrefs.getString("token","123");
    }


}
