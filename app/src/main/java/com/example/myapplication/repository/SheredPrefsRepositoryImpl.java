package com.example.myapplication.repository;

import android.content.Context;

interface SheredPrefsRepositoryImpl {
    String getToken(Context context);
    void pootToken(Context context,String token);
}
