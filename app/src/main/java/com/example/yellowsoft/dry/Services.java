package com.example.yellowsoft.dry;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 14/8/17.
 */

public class Services implements Serializable {
    public String id,title,title_ar,image,duration,price,video,description,description_ar;
    public Services(JsonObject jsonObject, Context context){
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
