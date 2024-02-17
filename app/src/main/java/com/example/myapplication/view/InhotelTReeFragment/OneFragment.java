package com.example.myapplication.view.InhotelTReeFragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class OneFragment extends Fragment implements FragmentInterface{
    String textViewName="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.first_type,null);
       TextView Nametipe1 = view.findViewById(R.id.name_type1);
        Nametipe1.setText(textViewName);
        return view;
    }

    public void setTextViewName(String textViewName) {
        this.textViewName = textViewName;
    }
}
