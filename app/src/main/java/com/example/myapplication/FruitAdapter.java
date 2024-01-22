package com.example.myapplication;




import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.myapplication.inventory.HotelName;

import java.util.List;

/********************************************
 *     Created by DailyCoding on 15-May-21.  *
 ********************************************/

public class FruitAdapter extends BaseAdapter {

    private Context context;
    private List<HotelName> fruitList;

    public FruitAdapter(Context context, List<HotelName> fruitList) {
        this.context = context;
        this.fruitList = fruitList;
    }

    @Override
    public int getCount() {
        return fruitList != null ? fruitList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_fruit, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.name);
        ImageView image = rootView.findViewById(R.id.image);

        txtName.setText(fruitList.get(i).getName());
        image.setImageResource(R.drawable.chelovechekxml);

        return rootView;
    }
}