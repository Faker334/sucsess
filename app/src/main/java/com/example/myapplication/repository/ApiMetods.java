package com.example.myapplication.repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.repository.models.LoginRequest;
import com.example.myapplication.view.activiti.InHotel;
import com.example.myapplication.view.activiti.Zaseleniye;
import com.example.myapplication.viewModel.AvtorizationViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiMetods {
    boolean isALreadyHotel = false;
    String  Token = "false";
  private OkHttpClient getHttpClient(String token){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Настраиваем запросы
                        Request request = original.newBuilder()
                                .header("Accept", "application/json")
                                .addHeader("Authorization", "Bearer " + token)
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();
        return client;
    }
   public MyApiService getApiSeviceWithToken(String token){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://app.successhotel.ru/api/")
                .client(getHttpClient(token))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApiService apiService = retrofit.create(MyApiService.class);
        return apiService;
    }
    public MyApiService getApiSevice(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://app.successhotel.ru/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApiService apiService = retrofit.create(MyApiService.class);
        return apiService;
    }
    public void isAlreadyInHotel(MyApiService apiService,Context context){
        apiService.isligin().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String responseString = response.body().string();
                    Log.e("Респонс успешен",responseString);

                        JSONObject jObject = new JSONObject(responseString);
                        JSONObject JopjectINprofile = new JSONObject(jObject.getString("profile"));
                        isALreadyHotel = JopjectINprofile.getBoolean("checked_in");
                    if (isALreadyHotel){
                        new SheredPrefsRepository().ALreadyINhotel(context);
                        context.startActivity(new Intent(context,InHotel.class));
                    }else context.startActivity(new Intent(context,Zaseleniye.class));

                    } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("loginlog","eror"+t.getMessage());

            }
        });
    }

    public void Login(String email, String password, Context context){
        MyApiService myApiService = getApiSevice();
        myApiService.loginUser(new LoginRequest(email,password)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = response.body().string();
                    if (responseString.contains("true")){
                    new SheredPrefsRepository().pootToken(context,getTokenWithJson(responseString));
                    isAlreadyInHotel(getApiSeviceWithToken(getTokenWithJson(responseString)),context);
                    }else Toast.makeText(context,"Неверный логин или пароль",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                   Log.e("erw", e.fillInStackTrace().getMessage());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure","eror");
            }
        });
    }
    String getTokenWithJson(String json) throws JSONException {
        JSONObject jsonObject =new JSONObject(json);
      return jsonObject.getString("token");
  }

}
