package com.example.yellowsoft.dry;

/**
 * Created by mac on 3/20/17.
 */


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yellowsoft.dry.BookAppointmentActivity;
import com.example.yellowsoft.dry.Category;
import com.example.yellowsoft.dry.Services;
//import com.google.android.gms.ads.formats.NativeAd;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class AddCurrencyAdapter extends RecyclerView.Adapter<AddCurrencyAdapter.MyViewHolder> implements Filterable {

    public ArrayList<Category> moviesList;
    ArrayList<Category> moviesList_all;

    public HashMap<Integer,Category> dummyList;
    Typeface bold,regular,bold_arabic,regular_arabic;
    PlanetFilter planetFilter;

    private Context context;
    BookAppointmentActivity activity;

    @Override
    public Filter getFilter() {
        if(planetFilter==null)
            planetFilter=new PlanetFilter();
        return planetFilter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        public TextView categorry_title, base_rate, value,full_name;
        public ImageView flag_id,selected_tick;
        public Category category;

        public MyViewHolder(View view) {

            super(view);
            categorry_title = (TextView) view.findViewById(R.id.category_title);
            selected_tick = (ImageView)  view.findViewById(R.id.selcted_tick);
            view.setOnClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {

            categorry_title.setBackgroundColor(Color.parseColor("black"));

            return false;
        }

        @Override
        public void onClick(View view) {
            if(moviesList.get(getAdapterPosition()).type.equals("0")){

            }else {
                if (!activity.selectedServices.contains(moviesList.get(getAdapterPosition()).title)) {

                    activity.selectedServices.add(moviesList.get(getAdapterPosition()).title);
                    activity.servicesnames.add(moviesList.get(getAdapterPosition()).id);
                    selected_tick.setVisibility(View.VISIBLE);
                } else {
                    activity.selectedServices.remove(moviesList.get(getAdapterPosition()).title);
                    activity.servicesnames.add(moviesList.get(getAdapterPosition()).id);
                    selected_tick.setVisibility(View.INVISIBLE);

                }
            }


            Log.e("selected",activity.selectedServices.toString());

        }
    }


    public AddCurrencyAdapter(ArrayList<Category> moviesList, Context context, BookAppointmentActivity activity) {
        this.moviesList = moviesList;
        this.context = context;
        this.activity = activity;
        this.moviesList_all = moviesList;

        bold = Typeface.createFromAsset(context.getAssets(), "fonts/libel-suit-rg.ttf");
        bold_arabic = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen Tunisia Bd.ttf");
        regular = Typeface.createFromAsset(context.getAssets(), "fonts/libel-suit-rg.ttf");
        regular_arabic = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen Tunisia.ttf");


    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.multiservices, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final Category category = moviesList.get(position);
            holder.category = category;

            holder.categorry_title.setText(category.title);

        holder.categorry_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moviesList.get(position).type.equals("0")){

                }else {
                    if (!activity.selectedServices.contains(moviesList.get(position).title)) {

                        activity.selectedServices.add(moviesList.get(position).title);
                        activity.servicesnames.add(moviesList.get(position).id);
                        holder.selected_tick.setVisibility(View.VISIBLE);
                    } else {
                        activity.selectedServices.remove(moviesList.get(position).title);
                        activity.servicesnames.add(moviesList.get(position).id);
                        holder.selected_tick.setVisibility(View.INVISIBLE);

                    }
                }


            }
        });

        if(moviesList.get(position).type.equals("0")){

            if (Session.GetLang(context).equals("en")) {

                holder.categorry_title.setTypeface(bold, Typeface.BOLD);
            }else {
                holder.categorry_title.setTypeface(bold_arabic);
            }

        }else{
            if (Session.GetLang(context).equals("en")) {
                holder.categorry_title.setTypeface(regular);
            }else {
                holder.categorry_title.setTypeface(regular_arabic);
            }



        }

        if(activity.selectedServices.contains(moviesList.get(position).title)){

            holder.selected_tick.setVisibility(View.VISIBLE);
        }else{
            holder.selected_tick.setVisibility(View.INVISIBLE);
        }




    }

    @Override
    public int getItemCount() {

        return moviesList.size();

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
                results.values = moviesList;
                results.count = moviesList.size();
            }
            else {
// We perform filtering operation
                List<Category> nPlanetList = new ArrayList<>();

                for (Category p : moviesList_all) {

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

                moviesList = moviesList_all;
                notifyDataSetChanged();
            }


            else {
                moviesList = (ArrayList<Category>) results.values;
                notifyDataSetChanged();
            }



        }

    }

}