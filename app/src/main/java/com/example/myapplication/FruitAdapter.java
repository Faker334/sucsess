package com.example.myapplication;




import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
        return fruitList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Здесь оставляем как есть, это вид элемента, когда спиннер закрыт
        return createItemView(i, viewGroup);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Если это первый элемент, возвращаем пустой макет с нулевой высотой
        if (position == 0) {
            View emptyView = new View(context);
            emptyView.setLayoutParams(new AbsListView.LayoutParams(0, 0));
            return emptyView;
        }
        // Для остальных элементов используем обычный макет
        return createItemView(position, parent);
    }

    private View createItemView(int i, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_fruit, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.name);
        ImageView image = rootView.findViewById(R.id.image);

        HotelName hotelName = fruitList.get(i);
        txtName.setText(hotelName.getName());
        image.setImageResource(hotelName.getImage());

        return rootView;
    }
}