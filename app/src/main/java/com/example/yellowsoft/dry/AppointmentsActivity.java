package com.example.yellowsoft.dry;

import android.app.Activity;
import android.os.Bundle;
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
    TextView st_appointment;
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
        st_appointment.setText(Session.GetWord(this,"APPOINTMENTS"));
        progress_holder.setVisibility(View.GONE);
        adapter = new AppointmentAdapter(this,appointmentsfrom_api);
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
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        for (int i=0;i<result.size();i++){
                            Appointments appointments = new Appointments(result.get(i).getAsJsonObject(),AppointmentsActivity.this);
                            appointmentsfrom_api.add(appointments);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
