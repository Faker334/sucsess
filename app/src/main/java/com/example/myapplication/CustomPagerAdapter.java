package com.example.myapplication;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;

    @Override
    public int getCount() {
        return ModelObject.values().length;
    }

    public CustomPagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ModelObject modelObject = ModelObject.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout =(ViewGroup)inflater.inflate(modelObject.getmLayoutResID(), container, false);
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    public CharSequence getPageTitle(int position){
        ModelObject custompajerEnum = ModelObject.values()[position];
        return  mContext.getString(custompajerEnum.getmTitleResID());
    }
    public void setMyScroller(ViewPager viewPager) {
        try {
            Class<?> viewpagerClass = ViewPager.class;
            Field scroller = viewpagerClass.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(viewPager, new MyScroller(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
