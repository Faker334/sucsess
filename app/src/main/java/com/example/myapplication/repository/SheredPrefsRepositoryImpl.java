package com.example.myapplication.repository;

import android.content.Context;

interface SheredPrefsRepositoryImpl {
    String getToken();
    void pootToken(String token);
    void ALreadyINhotel(Boolean bool);
}
