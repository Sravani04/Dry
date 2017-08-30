package com.example.yellowsoft.dry;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 7/8/17.
 */

public class ServicesAdapter  extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder>{
    Context context;
    LayoutInflater inflater;
    ArrayList<Category> services;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView service_title;
        public ImageView service_image;

        public MyViewHolder(View view) {
            super(view);
             service_title = (TextView) itemView.findViewById(R.id.service_title);
            service_image = (ImageView) itemView.findViewById(R.id.service_image);
        }
    }

    public ServicesAdapter(Context context,ArrayList<Category> services){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.services = services;
    }

    @Override
    public ServicesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.services_items, parent, false);

        return new ServicesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            holder.service_title.setText(services.get(position).title);
            Glide.with(context).load(services.get(position).image).placeholder(R.drawable.placeholder500x250).into(holder.service_image);


        }catch (Exception e1){
            e1.printStackTrace();
        }

        holder.service_image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ServiceDetail.class);
                intent.putExtra("services",services.get(position));
                View sharedView = holder.service_image;
                String transitionName = context.getString(R.string.transition_name_image);
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, sharedView, transitionName);
                context.startActivity(intent, transitionActivityOptions.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

}
