package com.example.myapplication.view.InhotelTReeFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.List;

public class ThreeFragment extends Fragment implements FragmentInterface {
    String textViewName="";
    Spinner spinner;


    String[] OptionsValue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.thre_tipe,null);
        TextView Nametipe1 = view.findViewById(R.id.name_tipe3);
        spinner =view.findViewById(R.id.spinner_options);
        Nametipe1.setText(textViewName);
        ArrayAdapter<String> adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, OptionsValue);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return view;
    }

    public void setTextViewName(String textViewName) {
        this.textViewName = textViewName;
    }
    public void setOptionsValue(String[] optionsValue) {
        OptionsValue = optionsValue;
    }

}
