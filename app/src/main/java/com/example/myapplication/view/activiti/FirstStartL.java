package com.example.myapplication.view.activiti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;


import com.example.myapplication.R;
import com.example.myapplication.view.adapter.CustomPagerAdapter;

public class FirstStartL extends AppCompatActivity {
    public Button firstStrB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firststartl);


        ViewPager  viewPager =findViewById(R.id.viewpajjer);
        CustomPagerAdapter adapter = new CustomPagerAdapter(this);
        viewPager.setAdapter(adapter);
        adapter.setMyScroller(viewPager);


        firstStrB = findViewById(R.id.firststrbut);
        firstStrB.setOnClickListener(new View.OnClickListener() { //замена текста при нажатии на бт
            @Override
            public void onClick(View v) {
                switch(viewPager.getCurrentItem()) {
                    case 0:
                        viewPager.setCurrentItem(1,true);
                        firstStrB.setText("Хорошо");
                        break;
                    case 1:
                        viewPager.setCurrentItem(2,true);
                        firstStrB.setText("Поехали!");
                        break;

                    case  2:
                        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                        e.putBoolean("FIRST_START", false);
                        e.apply();
                        Intent i = new Intent(FirstStartL.this, MainActivity.class);
                        startActivity(i);
                        break;

                    default:
                }
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                // вызывается при любом скроллинге пользователем.
            }

            @Override
            public void onPageSelected(int position) {   // замена текста кнопки при скроле
                switch(viewPager.getCurrentItem()) {
                    case 0:
                        firstStrB.setText("Понятно");

                        break;
                    case 1:
                        firstStrB.setText("Хорошо");
                        break;

                    case  2:
                        firstStrB.setText("Поехали!");
                        break;

                    default:
                        // code block
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                // Этот метод вызывается при изменении состояния прокрутки.
                // Например, когда пользователь начинает скроллить, прекращает скроллить и т.д.
            }
        });



    }
}