package com.example.yellowsoft.dry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by yellowsoft on 7/8/17.
 */

public class LanguageActivity extends Activity {
    RelativeLayout english_btn,arabic_btn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_screen);
        Session.forceRTLIfSupported(this);
        english_btn = (RelativeLayout) findViewById(R.id.english_btn);
        arabic_btn = (RelativeLayout) findViewById(R.id.arabic_btn);


        english_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Session.SetLang(LanguageActivity.this,"en");
                    Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

            }
        });

        arabic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Session.SetLang(LanguageActivity.this,"ar");
                    Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
            }
        });


    }




    


}
