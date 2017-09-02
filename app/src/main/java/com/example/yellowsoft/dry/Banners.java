package com.example.yellowsoft.dry;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 14/8/17.
 */

public class Banners implements Serializable {
    public String id,title,title_ar,image;
    public Category category;
    public Banners(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        image = jsonObject.get("image").getAsString();
        category = new Category(jsonObject.get("service").getAsJsonObject(),context,"1");
        if (Session.GetLang(context).equals("ar")) {
            title = title_ar;
        }
    }
}

