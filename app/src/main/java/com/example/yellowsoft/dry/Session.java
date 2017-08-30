package com.example.yellowsoft.dry;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by yellowsoft on 11/8/17.
 */

public class Session {
    public static String SERVER_URL1 = "http://clients.yellowsoft.in/saloon/api/";
    public static String SERVER_URL = "http://drysalonkw.com/api/";
    public static final String setting = "settings";
    public  static  final String mem_id="mem_id";
    public  static  final String lang="lan";
    public  static  final String Words_en="en";
    public  static  final String Words_ar="ar";


    public  static void SetUserId(Context context, String id){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(mem_id,id);
        editor.commit();
    }

    public  static String GetUserId(Context context) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(mem_id,"-1");
    }

    public static   void forceRTLIfSupported(Activity activity)
    {
        SharedPreferences sharedPref;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        Log.e("lan", sharedPref.getString(lang, "-1"));

        if (GetLang(activity).equals("en")) {
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("en".toLowerCase());
            res.updateConfiguration(conf, dm);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

        else if(GetLang(activity).equals("ar")){
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("ar".toLowerCase());
            res.updateConfiguration(conf, dm);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }

        else {
            Resources res = activity.getResources();
            // Change locale settings in the app.
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("en".toLowerCase());
            res.updateConfiguration(conf, dm);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
        }

    }

    public static void SetSettings(Context context, String settings){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(setting,settings);
        editor.commit();
    }

    public static String GetSettings(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(setting,"-1");
    }

    public  static void SetLang(Context context, String ar){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(lang,ar);
        editor.commit();
    }

    public  static String GetLang(Context context) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(lang,"en");
    }

    public  static void SetEnWords(Context context, String en){
        Log.e("engres",en);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Words_en,en);
        editor.commit();
    }

    public  static String GetEnWords(Context context) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Words_en,"-1");
    }

    public  static void SetArWords(Context context, String ar){
        Log.e("arabicres",ar);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Words_ar,ar);
        editor.commit();
    }

    public  static String GetArWords(Context context) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Words_ar,"-1");
    }

    public static String GetWord(Context context,String word){
        if (Session.GetLang(context).equals("ar")){
            try {
                Log.e("ar_words",GetArWords(context));
                JSONObject jsonObject = new JSONObject(GetArWords(context));
                return jsonObject.getString(word);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            try {
                JSONObject jsonObject = new JSONObject(GetEnWords(context));
                return jsonObject.getString(word);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return word;
    }
}
