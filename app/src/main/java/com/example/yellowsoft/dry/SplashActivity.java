package com.example.yellowsoft.dry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by yellowsoft on 7/8/17.
 */

public class SplashActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        get_settings();

    }

    public void get_settings(){
        Ion.with(this)
                .load(Session.SERVER_URL+"settings.php")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.e("settings",result.toString());
                        Session.SetSettings(SplashActivity.this,result.toString());
                        get_words();
                    }
                });
    }

    public void get_words(){
        Ion.with(this)
                .load(Session.SERVER_URL+"words-json.php")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            Session.SetEnWords(SplashActivity.this, result.get("en").getAsJsonObject().toString());
                            Session.SetArWords(SplashActivity.this, result.get("ar").getAsJsonObject().toString());
                            SharedPreferences sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            boolean  firstTime=sharedPreferences.getBoolean("first", true);
                            if(firstTime) {
                                editor.putBoolean("first",false);
                                editor.commit();
                                editor.apply();
                                Intent intent = new Intent(SplashActivity.this, LanguageActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }








}
