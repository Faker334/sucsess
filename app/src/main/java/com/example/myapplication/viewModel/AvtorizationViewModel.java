package com.example.myapplication.viewModel;

import android.content.Context;

import com.example.myapplication.repository.ApiMetods;
import com.example.myapplication.repository.SheredPrefsRepository;

public class AvtorizationViewModel {
    Context context;
    ApiMetods apiMetods;
    SheredPrefsRepository mSheredPrefsRepository =new SheredPrefsRepository();
    public AvtorizationViewModel(){
         apiMetods = new ApiMetods();
    }

    public String getToken(Context context){
        return mSheredPrefsRepository.getToken(context);
    }

    public void Login(String email, String password, Context context){
        apiMetods.Login(email,password, context);

    }
    public void putToken(Context context, String token){
        mSheredPrefsRepository.pootToken(context,token);
    }
}
