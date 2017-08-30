package com.example.yellowsoft.dry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 7/8/17.
 */

public class ServicesActivity extends FragmentActivity {
    ImageView back_btn,my_profile;
    ViewPager viewPager;
    TabsAdapter tabsAdapter;
    ArrayList<Category> categoriesfrom_api;
    PagerSlidingTabStrip tabs;
    ViewPager.OnPageChangeListener mPageChangeListener;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_screen_list);
        Session.forceRTLIfSupported(this);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        my_profile = (ImageView) findViewById(R.id.my_profile);
        categoriesfrom_api = new ArrayList<>();
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager(),categoriesfrom_api);
        viewPager.setAdapter(tabsAdapter);




//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                try {
//
//                    if (position == 0)
//                       viewPager.setCurrentItem(position,true);
//
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//            }
//
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });



        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(ServicesActivity.this).equals("-1")) {
                    Intent intent = new Intent(ServicesActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ServicesActivity.this, EditProfile.class);
                    startActivity(intent);
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServicesActivity.this.onBackPressed();
            }
        });


        get_categories();



    }

    public void get_categories(){
        Ion.with(this)
                .load(Session.SERVER_URL+"category.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Log.e("cat",result.toString());
                        for (int i = 0; i < result.size(); i++) {
                            Category category = new Category(result.get(i).getAsJsonObject(), ServicesActivity.this);
                            categoriesfrom_api.add(category);
                        }

                        tabsAdapter.notifyDataSetChanged();
                        tabs.setViewPager(viewPager);
                        tabs.setOnPageChangeListener(mPageChangeListener);


                    }
                });
    }




}
