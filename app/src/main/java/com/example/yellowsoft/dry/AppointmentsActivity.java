package com.example.yellowsoft.dry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 14/8/17.
 */

public class AppointmentsActivity extends Activity {
    ListView listView;
    LinearLayout progress_holder;
    AppointmentAdapter adapter;
    ArrayList<Appointments> appointmentsfrom_api;
    ImageView back_btn;
    TextView st_appointment,st_notfound;
    LinearLayout no_appointment_page;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointments_list);
        Session.forceRTLIfSupported(this);
        appointmentsfrom_api = new ArrayList<>();
        listView = (ListView) findViewById(R.id.appointment_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        st_appointment = (TextView) findViewById(R.id.st_appointment);
        no_appointment_page = (LinearLayout) findViewById(R.id.no_appointment_page);
        st_notfound = (TextView) findViewById(R.id.st_notfound);
        st_appointment.setText(Session.GetWord(this,"APPOINTMENTS"));
        st_notfound.setText(Session.GetWord(this,"No appointments found"));
        progress_holder.setVisibility(View.GONE);
        adapter = new AppointmentAdapter(AppointmentsActivity.this,appointmentsfrom_api,AppointmentsActivity.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });




        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        get_appointments();
    }

    public void  show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void get_appointments(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"appointments.php")
                .setBodyParameter("member_id",Session.GetUserId(AppointmentsActivity.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        try {
                            for (int i = 0; i < result.size(); i++) {
                                Log.e("app", result.toString());
                                Appointments appointments = new Appointments(result.get(i).getAsJsonObject(), AppointmentsActivity.this);
                                appointmentsfrom_api.add(appointments);
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                        if (appointmentsfrom_api.size() == 0)
                        {
                            no_appointment_page.setVisibility(View.VISIBLE);
                        }else {
                            no_appointment_page.setVisibility(View.GONE);
                        }

                    }
                });
    }
}
