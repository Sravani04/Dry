package com.example.yellowsoft.dry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 23/8/17.
 */

public class OurServicesAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Services> services;

    public OurServicesAdapter(Context context,ArrayList<Services> services){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.services = services;
    }

    @Override
    public int getCount() {
        return services.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item_view = inflater.inflate(R.layout.example_items,null);
        TextView service_title = (TextView) item_view.findViewById(R.id.service_title);
        service_title.setText(services.get(i).title);
        return item_view;
    }
}
