package com.example.myapplication.repository;

import com.example.myapplication.repository.models.LoginRequest;
import com.example.myapplication.repository.models.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyApiService {
    @POST("client/login")
    Call<ResponseBody> loginUser(@Body LoginRequest loginRequest);

    @GET("client/profile")
    Call<ResponseBody> isligin();


}
