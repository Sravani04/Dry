package com.example.yellowsoft.dry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 18/8/17.
 */

public class TabsAdapter extends FragmentStatePagerAdapter {
    ArrayList<Category> categories;
    Context context;
    ServicesFragment fragment;



    public TabsAdapter(FragmentManager fm,ArrayList<Category> categories) {
        super(fm);
        this.categories = categories;
    }


    @Override
    public Fragment getItem(int position) {

        return ServicesFragment.newInstance(position + 1,categories.get(position).id);

//        Log.e("pos", String.valueOf(position));
//
//        if (position == 0) {
//
//               fragment =  ServicesFragment.newInstance();
//                return fragment;
//
//        }else {
//            return DemoFragment.newInstance(position);
//        }




    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return categories.get(position).title;
    }

//    @Override
//    public int getPageIconResId(int position) {
//       return categories.get(position).image;
//    }



//    @Override
//    public int getPageIconResId(int position) {
//        return Integer.parseInt(categories.get(position).image);
//    }
}





