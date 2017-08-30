package com.example.yellowsoft.dry;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 18/8/17.
 */

public class Category implements Serializable {
    public String id,title,title_ar,image,type,duration,price,video,description,description_ar;
    public ArrayList<Services> services;
    public ArrayList<Services> services_all;
    public Category(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        image = jsonObject.get("image").getAsString();
        if (Session.GetLang(context).equals("ar")) {
            title = title_ar;
        }
        type="0";
    }
    public Category(JsonObject jsonObject, Context context,String type){
        this.type = type;
        if(type.equals("0")){
            id = jsonObject.get("id").getAsString();
            title = jsonObject.get("title").getAsString();
            title_ar = jsonObject.get("title_ar").getAsString();
            image = jsonObject.get("image").getAsString();
            if (Session.GetLang(context).equals("ar")) {
                title = title_ar;
            }

        }else {
            id = jsonObject.get("id").getAsString();
            title = jsonObject.get("title").getAsString();
            title_ar = jsonObject.get("title_ar").getAsString();
            image = jsonObject.get("image").getAsString();
            duration = jsonObject.get("duration").getAsString();
            price = jsonObject.get("price").getAsString();
            video = jsonObject.get("video").getAsString();
            description = jsonObject.get("description").getAsString();
            description_ar = jsonObject.get("description_ar").getAsString();
            if (Session.GetLang(context).equals("ar")) {
                title = title_ar;
                description = description_ar;
            }

        }
    }
}
