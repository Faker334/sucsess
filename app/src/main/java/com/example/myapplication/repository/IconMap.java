package com.example.myapplication.repository;

import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Map;

public class IconMap {
    Map<String,Integer> REsoursMap= new HashMap<>();
    IconMap(){
    REsoursMap.put("blender.png",R.drawable.blender);
    REsoursMap.put("washing-machine.png",R.drawable.washing_machine);
    REsoursMap.put("safe-box.png",R.drawable.safe_box);
    REsoursMap.put("fence.png",R.drawable.fence);
    REsoursMap.put("nightstand.png",R.drawable.nightstand);
    REsoursMap.put("dining-table.png",R.drawable.dining_table);
    REsoursMap.put("smart-lock.png",R.drawable.smart_lock);
    REsoursMap.put("sofa.png",R.drawable.sofa);
    REsoursMap.put("trash-can.png",R.drawable.trash_can);
    }
}
