package com.example.yellowsoft.dry;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 28/8/17.
 */

public class Settings implements Serializable{
    public String logo,title,title_ar,email,about,about_ar,contact,contact_ar;
    public Settings(JsonObject jsonObject, Context context){
        logo = jsonObject.get("logo").getAsString();
        title = jsonObject.get("title").getAsString();
        title_ar = jsonObject.get("title_ar").getAsString();
        email = jsonObject.get("email").getAsString();
        about = jsonObject.get("about").getAsString();
        about_ar = jsonObject.get("about_ar").getAsString();
        contact = jsonObject.get("contact").getAsString();
        contact_ar = jsonObject.get("contact_ar").getAsString();
        if (Session.GetLang(context).equals("ar")) {
            title = title_ar;
            about = about_ar;
            contact = contact_ar;
        }
    }
}
