package com.example.myapplication.view.activiti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.repository.MyPreferences;
import com.example.myapplication.R;
import com.example.myapplication.repository.models.Order;
import com.example.myapplication.view.adapter.CustomDialog;
import com.example.myapplication.view.adapter.CustomDialogCancelOrder;
import com.example.myapplication.view.adapter.OrdersAdapter;
import com.example.myapplication.view.adapter.Servise;
import com.example.myapplication.view.adapter.StateAdapter;
import com.example.myapplication.viewModel.AvtorizationViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollState;
import me.everything.android.ui.overscroll.IOverScrollStateListener;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

@AndroidEntryPoint
public class InHotel extends AppCompatActivity {

    ImageButton button_moi_zaprosi;
    ImageButton button_servis;
    ImageButton button_profil;
    ImageView imageView;
    ArrayList<Servise> uslugi = new ArrayList<Servise>();
    List<Order> orders = new ArrayList<>();
   StateAdapter Servisesadapter;
   OrdersAdapter ordersAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayout swipeRefreshLayoutOrders;
    TextView FioProfil;
    TextView PoctaProfil;
    TextView ActivButton;
    Button VisilitcyaButt;
    ImageButton ExitButton;
    ConstraintLayout profilLayout;
    AvtorizationViewModel avtorizationViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_in_hotel);

        avtorizationViewModel = new ViewModelProvider(this).get(AvtorizationViewModel.class);

    button_moi_zaprosi = findViewById(R.id.Button_moi_zaprosi);
    button_servis = findViewById(R.id.Button_servisi);
    button_profil = findViewById(R.id.Button_profil);
    imageView=findViewById(R.id.imageView9);
    swipeRefreshLayout = findViewById(R.id.refreshlayaut);
    swipeRefreshLayoutOrders = findViewById(R.id.refreshlayautOrders);

    RecyclerView ServisirecyclerView = findViewById(R.id.servis_recikleview);
    RecyclerView MoiZakaziRecyclerView = findViewById(R.id.moizakazi_recikleview);
    FioProfil = findViewById(R.id.FioPRofil);
    PoctaProfil = findViewById(R.id.PochtaProfile);
    VisilitcyaButt =findViewById(R.id.visilitsya_button);
    ExitButton = findViewById(R.id.exitButton);
    profilLayout =findViewById(R.id.ProfilLayout);
    ActivButton = findViewById(R.id.Activ);


    avtorizationViewModel.getMutableListServise().observe(this, new Observer<ArrayList<Servise>>() {
        @Override
        public void onChanged(ArrayList<Servise> servises) {
            uslugi.addAll(servises);
            Servisesadapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    });
    avtorizationViewModel.getMutableGetFioProfil().observe(this, new Observer<com.example.myapplication.repository.models.FioProfil>() {
        @Override
        public void onChanged(com.example.myapplication.repository.models.FioProfil fioProfil) {
            FioProfil.setText(fioProfil.getFio());
            PoctaProfil.setText(fioProfil.getEmail());
            swipeRefreshLayout.setRefreshing(false);
        }
    });
    avtorizationViewModel.getMutableGetExitSuccses().observe(this, new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if (aBoolean){
                Intent i = new Intent(InHotel.this,Zaseleniye.class);
                startActivity(i);
                finish();
            }else Toast.makeText(InHotel.this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    });
    avtorizationViewModel.getMutableGetListOrder().observe(this, new Observer<List<Order>>() {
        @Override
        public void onChanged(List<Order> ordersFromREquset) {
            orders.clear();
        orders.addAll(ordersFromREquset);
        ordersAdapter.notifyDataSetChanged();
        swipeRefreshLayoutOrders.setRefreshing(false);

        }
    });
    avtorizationViewModel.getListServices();
    avtorizationViewModel.ProfilExequte();
    ExitButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences encryptedPrefs = MyPreferences.getEncryptedSharedPreferences(InHotel.this);
            encryptedPrefs.edit().remove("token");
            SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
            e.putBoolean("ALREADYINHOTEL", false);
            e.apply();
            Log.e("вышел","удалил");
            Intent i = new Intent(InHotel.this, avtorization.class);
            startActivity(i);
            finish();
        }
    });
    VisilitcyaButt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           avtorizationViewModel.exitHotel();
        }
    });




        // определяем слушателя нажатия элемента в списке
        StateAdapter.OnStateClickListener stateClickListener = new StateAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Servise state, int position) {
                if (state.getOptionsSevis().size()==0){
                    Intent i = new Intent(InHotel.this, test.class);
                    i.putExtra("variable",state.getOrganization_id());
                    startActivity(i);
                }else {

                CustomDialog dialog = new CustomDialog();
                dialog.setServise(uslugi.get(position));
                    dialog.show(getSupportFragmentManager(), "tag");
                }
                //код после нажатия на айтем
            }
        };
        Servisesadapter = new StateAdapter(this, uslugi, stateClickListener);
        ServisirecyclerView.setAdapter(Servisesadapter);

        ServisirecyclerView.setLayoutManager(new LinearLayoutManager(this));

        OrdersAdapter.OnOrderClickListener  onOrderClickListener = new OrdersAdapter.OnOrderClickListener() {
            @Override
            public void onStateClick(Order order, int position) {
                if (order.getStatus()!=4){
                CustomDialogCancelOrder customDialogCancelOrder = new CustomDialogCancelOrder();
                customDialogCancelOrder.setAvtorizationViewModel(avtorizationViewModel);
                customDialogCancelOrder.setIdOrder(orders.get(position).getOrderId());
                customDialogCancelOrder.show(getSupportFragmentManager(),"tag1");
                Log.e("нажатие на ордер", "r");}
            }
        };

        ordersAdapter = new OrdersAdapter(this,orders,onOrderClickListener);
        MoiZakaziRecyclerView.setAdapter(ordersAdapter);
        MoiZakaziRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ordersAdapter.notifyDataSetChanged();

        //Отслежевание чрезмерного скрола
        IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(ServisirecyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        IOverScrollDecor decorOrders = OverScrollDecoratorHelper.setUpOverScroll(MoiZakaziRecyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        decorOrders.setOverScrollStateListener(new IOverScrollStateListener() {
            @Override
            public void onOverScrollStateChange(IOverScrollDecor decor, int oldState, int newState) {
                if (!swipeRefreshLayoutOrders.isRefreshing()){
                    swipeRefreshLayoutOrders.setRefreshing(true);
                    avtorizationViewModel.GetOrders();
                }
            }
        });

        decor.setOverScrollStateListener(new IOverScrollStateListener() {
            @Override
            public void onOverScrollStateChange(IOverScrollDecor decor, int oldState, int newState) {
                if (newState == IOverScrollState.STATE_IDLE) {
                   if (!swipeRefreshLayout.isRefreshing()){
                       swipeRefreshLayout.setRefreshing(true);
                       uslugi.clear();
                       avtorizationViewModel.getListServices();
                   }
                }
            }
        });



        //СЛУШАТЕЛЬ НАЖАТИЙ ТРЕХ КНОПОК СЕРВИС МОИ ЗАПРОЫ, ПРОФИЛЬ
        ColorStateList s =button_moi_zaprosi.getBackgroundTintList();
            button_moi_zaprosi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    button_moi_zaprosi.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.green)));
                    button_profil.setBackgroundTintList(s);
                    button_servis.setBackgroundTintList(s);
                    imageView.setColorFilter(getColor(R.color.cherniy));


                    ServisirecyclerView.setVisibility(View.GONE);
                    MoiZakaziRecyclerView.setVisibility(View.VISIBLE);
                    ServisirecyclerView.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                    swipeRefreshLayoutOrders.setVisibility(View.VISIBLE);
                    profilLayout.setVisibility(View.GONE);
                    ActivButton.setText("Мои запросы");
                    swipeRefreshLayoutOrders.setRefreshing(true);
                   avtorizationViewModel.GetOrders();

                }
            });
        button_servis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_servis.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.green)));
                button_moi_zaprosi.setBackgroundTintList(s);
                button_profil.setBackgroundTintList(s);
                imageView.setColorFilter(getColor(R.color.beliy));
                ActivButton.setText("Сервисы");


                swipeRefreshLayout.setVisibility(View.VISIBLE);
                ServisirecyclerView.setVisibility(View.VISIBLE);
                MoiZakaziRecyclerView.setVisibility(View.GONE);
                profilLayout.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayoutOrders.setVisibility(View.GONE);

            }
        });
        button_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_profil.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.green)));
                button_moi_zaprosi.setBackgroundTintList(s);
                button_servis.setBackgroundTintList(s);
                imageView.setColorFilter(getColor(R.color.cherniy));

                profilLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
                swipeRefreshLayoutOrders.setVisibility(View.GONE);
                ActivButton.setText("Мой профиль");
            }
        });
    }
}