package com.example.myapplication.repository;

import com.example.myapplication.repository.models.GetNumberRequest;
import com.example.myapplication.repository.models.LoginRequest;
import com.example.myapplication.repository.models.RegistrRequest;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyApiService {
    @POST("client/login")
    Call<ResponseBody> loginUser(@Body LoginRequest loginRequest);

    @GET("client/profile")
    Call<ResponseBody> isligin();
    @POST("client/register")
    Call<ResponseBody> registrUser(@Body RegistrRequest registrRequest);
    @GET("client/organizations")
    Call<ResponseBody> getListHotel();
    @GET("client/organizations/{id}/rooms")
    Call<ResponseBody> getNamberByHotel(@Path("id") String id);
    @POST("client/check-in")
    Call<ResponseBody> getNumber(@Body GetNumberRequest getNumberRequest);

}
