package com.example.myapplication.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Options> options;

    OptionsAdapter(Context context, List<Options> options) {
        this.options = options;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public OptionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OptionsAdapter.ViewHolder holder, int position) {
        Options options1 = options.get(position);
        Log.e("414",holder.toString());
        holder.nameTipe1.setText(options1.getTipe1_name());
        holder.nameTipe2.setText(options1.getTipe2name());
        holder.nameTipe3.setText(options1.getTipe3name());
        ArrayAdapter<String> adapter = new ArrayAdapter(inflater.getContext(), android.R.layout.simple_spinner_item, options1.getTipe3value());
        holder.spinner.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Spinner spinner;
        final TextView nameTipe1;
        final TextView nameTipe2;
        final TextView nameTipe3;
        ViewHolder(View view){
            super(view);

            nameTipe1 = view.findViewById(R.id.name_type1);
            nameTipe2 = view.findViewById(R.id.name_tipe2);
            nameTipe3 = view.findViewById(R.id.name_tipe3);
            spinner= view.findViewById(R.id.spinner_options);

        }
    }
}