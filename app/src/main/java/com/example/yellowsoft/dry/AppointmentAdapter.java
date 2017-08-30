package com.example.yellowsoft.dry;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 14/8/17.
 */

public class AppointmentAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Appointments> appointments;

    public AppointmentAdapter(Context context,ArrayList<Appointments> appointments){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.appointments  = appointments;
    }

    @Override
    public int getCount() {
        return appointments.size();
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
        View item_view = inflater.inflate(R.layout.appointment_items,null);
        TextView fname = (TextView) item_view.findViewById(R.id.fname);
        TextView lname = (TextView) item_view.findViewById(R.id.lname);
        TextView service_title = (TextView) item_view.findViewById(R.id.service_title);
        TextView date = (TextView) item_view.findViewById(R.id.date);
        TextView time = (TextView) item_view.findViewById(R.id.time);
        TextView appointment_id = (TextView) item_view.findViewById(R.id.appointment_id);

        TextView st_fname = (TextView) item_view.findViewById(R.id.st_fname);
        TextView st_lname = (TextView) item_view.findViewById(R.id.st_lname);
        TextView st_service_title = (TextView) item_view.findViewById(R.id.st_service_title);
        TextView st_date = (TextView) item_view.findViewById(R.id.st_date);
        TextView st_time = (TextView) item_view.findViewById(R.id.st_time);
        TextView st_id = (TextView) item_view.findViewById(R.id.st_id);

        st_fname.setText(Session.GetWord(context,"FIRST NAME"));
        st_lname.setText(Session.GetWord(context,"LAST NAME"));
        st_service_title.setText(Session.GetWord(context,"SERVICE"));
        st_date.setText(Session.GetWord(context,"DATE"));
        st_time.setText(Session.GetWord(context,"TIME"));

//        ImageView edit_btn = (ImageView) item_view.findViewById(R.id.edit_btn);
//        edit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context,EditAppointment.class);
//                intent.putExtra("appointments",appointments.get(i));
//                context.startActivity(intent);
//            }
//        });

        fname.setText(appointments.get(i).fname);
        lname.setText(appointments.get(i).lname);
        service_title.setText(appointments.get(i).title);
        date.setText(appointments.get(i).date);
        time.setText(appointments.get(i).time);
        appointment_id.setText(appointments.get(i).id);
        return item_view;
    }
}
