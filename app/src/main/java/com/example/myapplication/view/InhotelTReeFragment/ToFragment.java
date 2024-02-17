package com.example.myapplication.view.InhotelTReeFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class ToFragment extends Fragment implements  FragmentInterface {
    String textViewName="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.to_type,null);
        TextView Nametipe1 = view.findViewById(R.id.name_tipe2);
        ImageButton PLusButton = view.findViewById(R.id.Buttoncountplus);
        ImageButton MinusButton = view.findViewById(R.id.ButtonCountMinus);
        TextView CunTedittext = view.findViewById(R.id.textViewCount);
        Nametipe1.setText(textViewName);
                PLusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CunTedittext.setText(String.valueOf(Integer.parseInt(CunTedittext.getText().toString())+1));

            }
        });
        MinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CunTedittext.setText(String.valueOf(Integer.parseInt(CunTedittext.getText().toString())-1));

            }
        });
        return view;
    }

    public void setTextViewName(String textViewName) {
        this.textViewName = textViewName;
    }
}
