package com.example.myapplication.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.myapplication.repository.MyApiService;
import com.example.myapplication.repository.SheredPrefsRepository;
import com.example.myapplication.repository.jsonReader;
import com.example.myapplication.repository.models.CanselRequestt;
import com.example.myapplication.repository.models.FioProfil;
import com.example.myapplication.repository.models.GetNumberRequest;
import com.example.myapplication.repository.models.LoginRequest;
import com.example.myapplication.repository.models.Order;
import com.example.myapplication.repository.models.RegistrRequest;
import com.example.myapplication.view.adapter.HotelName;
import com.example.myapplication.view.adapter.Servise;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class AvtorizationViewModel  extends ViewModel {
    private MutableLiveData<Boolean> MutableAlreadyInhotel;
    private MutableLiveData<Boolean> loginEror;
    private MutableLiveData<Boolean> ExitSuccses;
    private MutableLiveData<Boolean> RegitstrSuccses;
    private MutableLiveData<List<HotelName>> ListHotelName;
    private MutableLiveData<List<HotelName>> ListNumberNomer;
    private MutableLiveData<Boolean> GetNumberSuccses;
    private MutableLiveData<ArrayList<Servise>> GetServiceList;
    private MutableLiveData<FioProfil> getFioProfil;
    private MutableLiveData<List<Order>> getOrderList;


    Boolean isALreadyHotel;
    jsonReader jsonReader = new jsonReader();

    @Inject
    @Named("withToken")
    MyApiService apiServiceWithToken;
    @Inject
    @Named("withoutToken")
    MyApiService apiServiceWithoutToken;

    @Inject
    SheredPrefsRepository mSheredPrefsRepository;
    @Inject
     AvtorizationViewModel(){
    }

    public MutableLiveData<Boolean> getMutableAlreadyInhotel(){
        if(MutableAlreadyInhotel ==null){
            MutableAlreadyInhotel = new MutableLiveData<Boolean>();
        }
        return MutableAlreadyInhotel;
    }
    public MutableLiveData<Boolean> getMutableGetNomberSuccses(){
        if(GetNumberSuccses ==null){
            GetNumberSuccses= new MutableLiveData<Boolean>();
        }
        return GetNumberSuccses;
    }
    public MutableLiveData<Boolean> getMutableGetExitSuccses(){
        if(ExitSuccses ==null){
            ExitSuccses= new MutableLiveData<Boolean>();
        }
        return ExitSuccses;
    }
    public MutableLiveData<Boolean> getMutableLoginEror(){
        if(loginEror ==null){
           loginEror = new MutableLiveData<Boolean>();
        }
        return loginEror;
    }
    public MutableLiveData<Boolean> getMutableRegistrCuccses(){
        if(RegitstrSuccses ==null){
            RegitstrSuccses = new MutableLiveData<Boolean>();
        }
        return RegitstrSuccses;
    }
    public MutableLiveData<List<HotelName>> getMutableListHOtel(){
        if(ListHotelName ==null){
            ListHotelName = new MutableLiveData<List<HotelName>>();
        }
        return ListHotelName;
    }
    public MutableLiveData<List<HotelName>> getMutableListNaumberNomer(){
        if(ListNumberNomer ==null){
            ListNumberNomer = new MutableLiveData<List<HotelName>>();
        }
        return ListNumberNomer;
    }
    public MutableLiveData<ArrayList<Servise>> getMutableListServise(){
        if(GetServiceList ==null){
            GetServiceList = new MutableLiveData<ArrayList<Servise>>();
        }
        return GetServiceList;
    }
    public MutableLiveData<FioProfil> getMutableGetFioProfil(){
        if(getFioProfil==null){
            getFioProfil= new MutableLiveData<FioProfil>();
        }
        return getFioProfil;
    }
    public MutableLiveData<List<Order>> getMutableGetListOrder(){
        if(getOrderList==null){
            getOrderList= new MutableLiveData<List<Order>>();
        }
        return getOrderList;
    }


    public void Login(String email, String password){
        apiServiceWithoutToken.loginUser(new LoginRequest(email,password)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String responseString = response.body().string();
                        if (responseString.contains("true")){
                            Log.e("responsstring", getTokenWithJson(responseString));
                           mSheredPrefsRepository.pootToken(getTokenWithJson(responseString));
                            isAlreadyInHotel();
                        }else getMutableLoginEror().setValue(false);
                    }catch (Exception e){
                        Log.e("eror", e.fillInStackTrace().getMessage());
                        Log.e("eror", e.fillInStackTrace().getMessage());
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("onFailure","eror");
                }
            });

    }
    public void isAlreadyInHotel(){
        apiServiceWithToken.isligin().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String responseString = response.body().string();
                    Log.e("Респонс успешен",responseString);
                    JSONObject jObject = new JSONObject(responseString);
                    JSONObject JopjectINprofile = new JSONObject(jObject.getString("profile"));
                    isALreadyHotel = JopjectINprofile.getBoolean("checked_in");
                    if (isALreadyHotel){
                        getMutableAlreadyInhotel().setValue(true);
                        mSheredPrefsRepository.ALreadyINhotel(true);

                    }else {
                       getMutableAlreadyInhotel().setValue(false);
                    }

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
    String getTokenWithJson(String json) throws JSONException {
        JSONObject jsonObject =new JSONObject(json);
        return jsonObject.getString("token");
    }
    public void registr(String FirstName,String LastName,String email,String password){
        apiServiceWithoutToken.registrUser(new RegistrRequest(FirstName,LastName,email,password)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responstString= response.body().string();
                    Log.e("Registr", responstString);
                    if (responstString.contains("true")){
                        getMutableRegistrCuccses().setValue(true);
                    }else getMutableRegistrCuccses().setValue(false);
                } catch (IOException e) {
                    Log.e("RegistrLog",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void GetListHotel(){
        apiServiceWithToken.getListHotel().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String resoonseString = response.body().string();
                    getMutableListHOtel().setValue(jsonReader.getListHotel(resoonseString));

                } catch (IOException e) {
                    Log.e("eror",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    public void getNamberByhotel(int IdHotel){
        apiServiceWithToken.getNamberByHotel(String.valueOf(IdHotel)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String ResponseString = response.body().string();
                   getMutableListNaumberNomer().setValue(jsonReader.getNumberList(ResponseString));
                } catch (IOException e) {
                    Log.e("eror",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void getNomber(int id,String date){
        apiServiceWithToken.getNumber(new GetNumberRequest(id,date)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = response.body().string();
                    if (responseString.contains("true")){
                        mSheredPrefsRepository.ALreadyINhotel(true);
                        getMutableGetNomberSuccses().setValue(true);
                    }else getMutableGetNomberSuccses().setValue(false);

                } catch (IOException e) {
                   e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void getListServices(){
        apiServiceWithToken.getServices().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String StringResponce =response.body().string();
                    getMutableListServise().setValue(jsonReader.getServise(StringResponce));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    public void ProfilExequte(){
        apiServiceWithToken.getProfilExequte().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String ResponseString = response.body().string();
                    getMutableGetFioProfil().setValue(jsonReader.getProfil(ResponseString));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void GetOrders(){
        apiServiceWithToken.getOrders().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String RespinsString = response.body().string();
                    Log.e("oreders", RespinsString);
                    getMutableGetListOrder().setValue(jsonReader.gerOReders(RespinsString));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void exitHotel(){
        apiServiceWithToken.getExit().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String ResponseString = response.body().string();
                    if (ResponseString.contains("true")){
                        mSheredPrefsRepository.ALreadyINhotel(false);
                    getMutableGetExitSuccses().setValue(true);}
                    else getMutableGetExitSuccses().setValue(false);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void CanselOrder(int id,String coment){
        apiServiceWithToken.canselORder(String.valueOf(id),new CanselRequestt(coment)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("cancel",response.body().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
