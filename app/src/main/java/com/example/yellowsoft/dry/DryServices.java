package com.example.yellowsoft.dry;

import android.app.Service;
import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 14/8/17.
 */

public class DryServices implements Serializable {
    public String id,title,title_ar,image;
//    public ArrayList<Services> services;
    public DryServices(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        image = jsonObject.get("image").getAsString();
//        services = new ArrayList<>();
//        for (int i=0;i<jsonObject.get("services").getAsJsonArray().size();i++){
//            Services service = new Services(jsonObject.get("services").getAsJsonArray().get(i).getAsJsonObject(),context);
//            services.add(service);
//        }
    }




}
