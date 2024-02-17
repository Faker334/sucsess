package com.example.myapplication.view.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.ViewHolder>{

    public interface OnStateClickListener{
        void onStateClick(Servise servise, int position);
    }

    private final OnStateClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<Servise> servises;

    public StateAdapter(Context context, List<Servise> servises, OnStateClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.servises = servises;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {
        Servise servise = servises.get(position);
        holder.imageview.setImageResource(servise.getIkonUslugi());
        holder.nameView.setText(servise.getName());
        holder.capitalView.setText(servise.getCenaUslugi());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onStateClick(servise, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return servises.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageview;
        final TextView nameView, capitalView;
        ViewHolder(View view){
            super(view);
            imageview = view.findViewById(R.id.flag);
            nameView = view.findViewById(R.id.name);
            capitalView = view.findViewById(R.id.cenatextview);
        }
    }
}