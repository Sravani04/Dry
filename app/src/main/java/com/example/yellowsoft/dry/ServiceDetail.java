package com.example.yellowsoft.dry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by yellowsoft on 7/8/17.
 */

public class ServiceDetail extends Activity {
    TextView title,service_title,service_description,service_price,service_duration;
    ImageView service_image,book_now,back_btn,my_profile;
    Category services;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_detail_screen);
        Session.forceRTLIfSupported(this);
        title = (TextView) findViewById(R.id.title);
        service_title = (TextView) findViewById(R.id.service_title);
        service_description = (TextView) findViewById(R.id.service_description);
        service_image = (ImageView) findViewById(R.id.service_image);
        book_now = (ImageView) findViewById(R.id.book_now);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        my_profile = (ImageView) findViewById(R.id.my_profile);
        service_price = (TextView) findViewById(R.id.service_price);
        service_duration = (TextView) findViewById(R.id.service_duration);


        if (getIntent()!=null && getIntent().hasExtra("services")){
            services = (Category) getIntent().getSerializableExtra("services");
        }

        title.setText(services.title);
        service_title.setText(services.title);
        service_description.setText(Html.fromHtml(services.description));
        service_price.setText(services.price);
        service_duration.setText(services.duration);
        Glide.with(this).load(services.image).placeholder(R.drawable.placeholder500x250).into(service_image);


        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(ServiceDetail.this).equals("-1")) {
                    Intent intent = new Intent(ServiceDetail.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ServiceDetail.this, BookAppointmentActivity.class);
                    intent.putExtra("title", title.getText().toString());
                    intent.putExtra("id", services.id);
                    startActivity(intent);
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetail.this.onBackPressed();
            }
        });

        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(ServiceDetail.this).equals("-1")) {
                    Intent intent = new Intent(ServiceDetail.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ServiceDetail.this, EditProfile.class);
                    startActivity(intent);
                }
            }
        });

    }
}