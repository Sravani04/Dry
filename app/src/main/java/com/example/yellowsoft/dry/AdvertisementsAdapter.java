package com.example.yellowsoft.dry;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by yellowsoft on 31/8/17.
 */

public class AdvertisementsAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Advertisements> advertisementses;


    public AdvertisementsAdapter(Context context, ArrayList<Advertisements> advertisementses) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.advertisementses = advertisementses;
    }

    @Override
    public int getCount() {
        return advertisementses.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.advertisement_images, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.banner_image);
        TextView price = (TextView) itemView.findViewById(R.id.price);
        TextView title = (TextView) itemView.findViewById(R.id.title);
        Glide.with(context).load(advertisementses.get(position).image).placeholder(R.drawable.placeholder500x250).into(imageView);
        Log.e("im",advertisementses.get(position).image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,ServiceDetail.class);
                intent.putExtra("services",advertisementses.get(position).category);
                context.startActivity(intent);
            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}


