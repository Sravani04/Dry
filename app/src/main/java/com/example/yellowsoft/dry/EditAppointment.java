package com.example.yellowsoft.dry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 18/8/17.
 */

public class EditAppointment extends Activity {
    ImageView back_btn;
    EditText fname,lname;
    TextView service_option,date,time,booknow_btn;
    Appointments appointments;
    LinearLayout progress_holder;
    ArrayList<Services> servicesfrom_api;
    String serv_id;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_appointment);
        Session.forceRTLIfSupported(this);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        booknow_btn = (TextView) findViewById(R.id.booknow_btn);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        service_option = (TextView) findViewById(R.id.service_option);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        servicesfrom_api = new ArrayList<>();

        if (getIntent()!=null && getIntent().hasExtra("appointments")){
            appointments = (Appointments) getIntent().getSerializableExtra("appointments");
        }



        fname.setText(appointments.fname);
        lname.setText(appointments.lname);
        service_option.setText(appointments.title);
        date.setText(appointments.date);
        time.setText(appointments.time);

        date.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar mcurrentDate=Calendar.getInstance();
                final int mYear = mcurrentDate.get(Calendar.YEAR);
                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(EditAppointment.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        date.setText(selectedday +"-"+(selectedmonth+1) +"-"+selectedyear);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditAppointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        boolean isPM = (selectedHour >= 12);
                        time.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));
                    }

                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        service_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = onCreateDialogSingleChoice();
                dialog.show();
            }
        });

        booknow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_appointment();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        get_services();


    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void edit_appointment(){
        String fname_string = fname.getText().toString();
        String lname_string = lname.getText().toString();
        String service_option = serv_id;
        String date_string = date.getText().toString();
        String time_string = time.getText().toString();
        if (fname_string.equals("")){
            Toast.makeText(EditAppointment.this,"Please Enter First Name",Toast.LENGTH_SHORT).show();
        }else if (lname_string.equals("")){
            Toast.makeText(EditAppointment.this,"Please Enter Last Name",Toast.LENGTH_SHORT).show();
        }else if (service_option == ""){
            Toast.makeText(EditAppointment.this,"Please Enter Service Id",Toast.LENGTH_SHORT).show();
        }else if (date_string.equals("")){
            Toast.makeText(EditAppointment.this,"Please Enter Date",Toast.LENGTH_SHORT).show();
        }else if (time_string.equals("")){
            Toast.makeText(EditAppointment.this,"Please Enter Time",Toast.LENGTH_SHORT).show();
        }else {
            show_progress();
            Ion.with(EditAppointment.this)
                    .load(Session.SERVER_URL+"edit-appointment.php")
                    .setBodyParameter("appointment_id", appointments.id)
                    .setBodyParameter("member_id", Session.GetUserId(this))
                    .setBodyParameter("fname", fname_string)
                    .setBodyParameter("lname", lname_string)
                    .setBodyParameter("service_id",serv_id)
                    .setBodyParameter("date",date_string)
                    .setBodyParameter("time",time_string)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            hide_progress();
                            try {
                                if (result.get("status").getAsString().equals("Success")) {
                                    Toast.makeText(EditAppointment.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    EditAppointment.this.onBackPressed();
                                } else {
                                    Toast.makeText(EditAppointment.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();

                                }
                            }catch (Exception e1){
                                e1.printStackTrace();                            }
                        }
                    });
        }
    }

    public Dialog onCreateDialogSingleChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] array = new CharSequence[servicesfrom_api.size()];
        for(int i=0;i<servicesfrom_api.size();i++){

            array[i] = servicesfrom_api.get(i).title;
        }
        builder.setTitle("Select City").setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                String selectedItem = array[i].toString();
                Log.e("select",selectedItem);
                service_option.setText(selectedItem);
                serv_id = servicesfrom_api.get(i).id;


            }
        })

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public void get_services(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"services.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        JsonObject jsonObject = result.get(0).getAsJsonObject();
                        JsonArray services_array = jsonObject.get("services").getAsJsonArray();
                        Log.e("ssss",services_array.toString());
                        for (int i = 0; i < services_array.size(); i++) {
                            Services services = new Services(services_array.get(i).getAsJsonObject(), EditAppointment.this);
                            servicesfrom_api.add(services);
                        }

                    }
                });
    }
}
