package com.example.myapplication.view.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.myapplication.R;
import com.example.myapplication.view.InhotelTReeFragment.OneFragment;
import com.example.myapplication.view.InhotelTReeFragment.ThreeFragment;
import com.example.myapplication.view.InhotelTReeFragment.ToFragment;
import com.example.myapplication.view.activiti.test;

public class CustomDialog extends DialogFragment {
    public void setServise(Servise servise) {
        this.servise = servise;
    }

    Servise servise;
    FragmentTransaction transaction;
    FragmentManager manager;
    Button zakazUzlug;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_dialog_layout, container, false);
        view.setBackgroundResource(R.drawable.zakrugl);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.zakrugl);
        zakazUzlug = view.findViewById(R.id.buttonzakazulsug);
        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();
        zakazUzlug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getDialog().getContext(), test.class);
                i.putExtra("variable", servise.getOrganization_id());
                startActivity(i);
                getDialog().dismiss();
            }
        });

        for (int i = 0; i < servise.getOptionsSevis().size(); i++) {
            Log.e("srevise",String.valueOf(servise.getOptionsSevis().size()));
            Log.e("srevise",String.valueOf(i));

            int containerId = 1;

            switch (i) {
                case 0:
                    containerId = R.id.container1;
                    break;
                case 1:
                    containerId = R.id.container2;
                    break;
                case 2:
                    containerId = R.id.container3;
                    break;
            }

            switch (servise.getOptionsSevis().get(i).getType()){
                case(1):
                    OneFragment oneFragment = new OneFragment();
                    transaction.add(containerId,oneFragment,String.valueOf(i));
                    oneFragment.setTextViewName(servise.getOptionsSevis().get(i).getName());
                    break;
                case(2):
                     ToFragment toFragment = new ToFragment();
                    transaction.add(containerId,toFragment,String.valueOf(i));
                    toFragment.setTextViewName(servise.getOptionsSevis().get(i).getName());
                    break;
                case(3):
                    ThreeFragment threeFragment = new ThreeFragment();
                    transaction.add(containerId,threeFragment,String.valueOf(i));
                    threeFragment.setTextViewName(servise.getOptionsSevis().get(i).getName());
                    threeFragment.setOptionsValue(servise.getOptionsSevis().get(i).getValues());
                    break;
            }
        }

        transaction.commit();
        return view;
    }

}
