package com.example.yellowsoft.dry;

import android.content.Context;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 14/8/17.
 */

public class Appointments implements Serializable {
    public String id,fname,lname,member_id,member_name,service_id,title,title_ar,date,time,message;
    public Appointments(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        fname = jsonObject.get("fname").getAsString();
        lname = jsonObject.get("lname").getAsString();
        member_id = jsonObject.get("member").getAsJsonObject().get("member_id").getAsString();
        member_name = jsonObject.get("member").getAsJsonObject().get("name") != JsonNull.INSTANCE ? member_name = jsonObject.get("member").getAsJsonObject().get("name").getAsString() : null;
        service_id = jsonObject.get("service").getAsJsonObject().get("service_id").getAsString();
        title = jsonObject.get("service").getAsJsonObject().get("title") != JsonNull.INSTANCE ?  title = jsonObject.get("service").getAsJsonObject().get("title").getAsString() : null;
        title_ar = jsonObject.get("service").getAsJsonObject().get("title_ar") != JsonNull.INSTANCE ? title_ar = jsonObject.get("service").getAsJsonObject().get("title_ar").getAsString() : null;
        date = jsonObject.get("date").getAsString();
        time = jsonObject.get("time").getAsString();
        message = jsonObject.get("message").getAsString();
        if (Session.GetLang(context).equals("ar")) {
            title = title_ar;
        }

    }
}
