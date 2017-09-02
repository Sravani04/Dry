package com.example.yellowsoft.dry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by yellowsoft on 24/8/17.
 */

public class ServicesPageAdapter extends BaseAdapter implements Filterable {
    Context context;
    LayoutInflater inflater;
    ArrayList<Category> categories;
    ArrayList<Category> categories_all;
    Typeface bold,regular,bold_arabic,regular_arabic;
    PlanetFilter planetFilter;
    String term_en,term_ar;
    public ServicesPageAdapter(Context context,ArrayList<Category> categories){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.categories = categories;
        this.categories_all = categories;
//        this.services =services;

            bold = Typeface.createFromAsset(context.getAssets(), "fonts/libel-suit-rg.ttf");
            bold_arabic = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen Tunisia Bd.ttf");
            regular = Typeface.createFromAsset(context.getAssets(), "fonts/libel-suit-rg.ttf");
            regular_arabic = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen Tunisia.ttf");



    }
    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View item_view = inflater.inflate(R.layout.service_page_items,null);
        TextView category_title = (TextView) item_view.findViewById(R.id.category_title);
        category_title.setText(categories.get(i).title);
        category_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(categories.get(i).type.equals("0")){

                }else {
                    Intent intent = new Intent(context, ServiceDetail.class);
                    intent.putExtra("services", categories.get(i));
                    intent.putExtra("terms",term_en);
                    intent.putExtra("terms_ar",term_ar);
                    context.startActivity(intent);
                }
            }
        });

        if(categories.get(i).type.equals("0")) {

            if (Session.GetLang(context).equals("en")){

                category_title.setTypeface(bold, Typeface.BOLD);
        }else {
                category_title.setTypeface(bold_arabic);
            }



        }else{

            if (Session.GetLang(context).equals("en")) {

                category_title.setTypeface(regular);
            }else {
                category_title.setTypeface(regular_arabic);
            }

        }

        get_settings();
        return item_view;
    }

    @Override
    public Filter getFilter() {
        if(planetFilter==null)
            planetFilter=new PlanetFilter();
        return planetFilter;
    }

    private class PlanetFilter extends Filter {
        Boolean clear_all=false;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
// We implement here the filter logic
            clear_all=false;
            if (constraint == null || constraint.length() == 0) {
                clear_all=true;
// No filter implemented we return all the list
                results.values = categories;
                results.count = categories.size();
            }
            else {
// We perform filtering operation
                List<Category> nPlanetList = new ArrayList<>();

                for (Category p : categories_all) {

//Pattern.compile(Pattern.quote(s2), Pattern.CASE_INSENSITIVE).matcher(s1).find();

                    if (Pattern.compile(Pattern.quote(constraint.toString()), Pattern.CASE_INSENSITIVE).matcher(p.title).find())
                        nPlanetList.add(p);
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.count == 0) {
//                restaurants = (ArrayList<Restaurants>) results.values;
                notifyDataSetChanged();
            }
            else if(clear_all){

                categories = categories_all;
                notifyDataSetChanged();
            }


            else {
                categories = (ArrayList<Category>) results.values;
                notifyDataSetChanged();
            }



        }

    }

    public void get_settings(){
        Ion.with(context)
                .load(Session.SERVER_URL+"settings.php")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            if (e != null)
                                e.printStackTrace();
                            Log.e("settings",result.toString());
                            term_en = result.get("terms").getAsString();
                            term_ar = result.get("terms_ar").getAsString();
                            Log.e("a",term_en);

                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }
}
