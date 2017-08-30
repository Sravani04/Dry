package com.example.yellowsoft.dry;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by yellowsoft on 18/8/17.
 */

public class CategoryAdapter extends BaseAdapter implements Filterable {
    LayoutInflater inflater;
    Context context;
    PlanetFilter planetFilter;
    ArrayList<Category> categories_all;


    //    ArrayList<String> cities;
    ArrayList<Category> categories ;
    public CategoryAdapter(Context context,ArrayList<Category>categories){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.categories = categories;
        this.categories_all = categories;
    }
    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Filter getFilter() {
        if(planetFilter==null)
            planetFilter=new PlanetFilter();
        return planetFilter;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View item_view = inflater.inflate(R.layout.service_items,null);
        ImageView city_image = (ImageView) item_view.findViewById(R.id.service_image);
        TextView city_name = (TextView) item_view.findViewById(R.id.service_name);
        city_name.setText(categories.get(i).title);
        Glide.with(context).load(categories.get(i).image).placeholder(R.drawable.placeholder500x250).into(city_image);
        Log.e("catimage",categories.get(i).image);
        return item_view;
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



}

