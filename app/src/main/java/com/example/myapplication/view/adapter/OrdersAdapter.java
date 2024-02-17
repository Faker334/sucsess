package com.example.myapplication.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.repository.models.Order;

import java.util.List;


public class OrdersAdapter   extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>{


    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.options_item, parent, false);
        return new OrdersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.nameView.setText(order.getNameOrder());
        switch (order.getStatus()){
            case (1):
                holder.Status.setText("не принята");
                holder.Status.setTextColor(Color.rgb(255, 0, 0));

                break;
            case (2):
                holder.Status.setText("выполняется");
                holder.Status.setTextColor(Color.rgb(0, 255, 0));
                break;
            case (3):
                holder.Status.setText("принята");
                holder.Status.setTextColor(Color.rgb(0, 255, 0));
                break;
            case (4):
                holder.Status.setText("отклонено");
                holder.canselButton.setText("отклонено");
                holder.imageView.setImageResource(R.drawable.otmeneno);
                holder.Status.setTextColor(Color.rgb(0, 255, 0));

                break;

        }
        Log.e("order",String.valueOf(order.getOrderId()));


        holder.date.setText(order.getData());



        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onStateClick(order, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public interface OnOrderClickListener{
        void onStateClick(Order order, int position);
    }
    private final LayoutInflater inflater;
    List<Order> orders;
    private final OnOrderClickListener onClickListener;

    public OrdersAdapter(Context context, List<Order> orders, OnOrderClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.orders = orders;
        this.inflater = LayoutInflater.from(context);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Button canselButton;
        final TextView nameView;
        final TextView date;
        final TextView Status;
        final ImageView imageView;

        ViewHolder(View view){
            super(view);
            canselButton= view.findViewById(R.id.buttonCancel);
            nameView = view.findViewById(R.id.name);
            date = view.findViewById(R.id.DateTextView);
            Status = view.findViewById(R.id.statusTextView);
            imageView=view.findViewById(R.id.imageViewOptions);
        }
    }


}
