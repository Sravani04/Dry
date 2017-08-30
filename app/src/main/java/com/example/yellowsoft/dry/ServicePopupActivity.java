package com.example.yellowsoft.dry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 19/8/17.
 */

public class ServicePopupActivity extends Activity {
    RecyclerView recyclerView;
    ImageView back_btn,my_profile;
    ServicesAdapter adapter;
    LinearLayout progress_holder;
    ArrayList<Category> servicesfrom_api;
    String id;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicepopup_activity);
        Session.forceRTLIfSupported(this);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        my_profile = (ImageView) findViewById(R.id.my_profile);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        servicesfrom_api = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        adapter = new ServicesAdapter(this,servicesfrom_api);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        if (getIntent()!=null && getIntent().hasExtra("category")){
            id = getIntent().getStringExtra("category");
        }


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServicePopupActivity.this.onBackPressed();
            }
        });

        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(ServicePopupActivity.this).equals("-1")) {
                    Intent intent = new Intent(ServicePopupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ServicePopupActivity.this, EditProfile.class);
                    startActivity(intent);
                }
            }
        });



        get_services();
    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void get_services(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"services.php")
                .setBodyParameter("category",id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        JsonObject jsonObject = result.get(0).getAsJsonObject();
                        JsonArray services_array = jsonObject.get("services").getAsJsonArray();
                        Log.e("ssss",services_array.toString());
                        for (int i = 0; i < services_array.size(); i++) {
                            Category category = new Category(services_array.get(i).getAsJsonObject(), ServicePopupActivity.this,"1");
                            servicesfrom_api.add(category);
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
