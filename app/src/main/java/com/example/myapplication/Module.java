package com.example.myapplication;


import android.content.Context;

import com.example.myapplication.repository.MyApiService;
import com.example.myapplication.repository.SheredPrefsRepository;

import java.io.IOException;

import javax.inject.Named;

import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@dagger.Module
@InstallIn(SingletonComponent.class)
public class Module {

    @Provides
    @Named("withoutToken")
    public static MyApiService getProvideApiCLient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://app.successhotel.ru/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApiService apiService = retrofit.create(MyApiService.class);

        return apiService;
    }

    @Provides
    public OkHttpClient GetProvideOkHttpClient(SheredPrefsRepository sheredPrefsRepository){
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Настраиваем запросы
                        Request request = original.newBuilder()
                                .header("Accept", "application/json")
                                .addHeader("Authorization", "Bearer " + sheredPrefsRepository.getToken())
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    @Provides
    @Named("withToken")
    public MyApiService  GetProvideApiSeviceWithToken(OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://app.successhotel.ru/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApiService apiService = retrofit.create(MyApiService.class);
        return apiService;
    }

    @Provides
    public  SheredPrefsRepository getSheredPefRepisitory(@ApplicationContext Context context){
        return new SheredPrefsRepository(context);
    }


}