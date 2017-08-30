package com.example.yellowsoft.dry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yellowsoft on 7/8/17.
 */

public class BookAppointmentActivity extends Activity {
    ImageView back_btn,my_profile;
    String service_title,serv_id;
    TextView service_option,date,time;
    LinearLayout progress_holder;
    EditText fname,lname,search;
    ArrayList<Category> categoriesfrom_api;
    RelativeLayout booknow_btn;
    TextView st_bookapp,st_appointment,st_fname,st_lname,st_mobile,st_services,st_date,st_time,st_terms,st_booknow;
    LinearLayout popup_view,search_service;
    ImageView close_btn;
    ListView listView;
    PopServicesAdapter popServicesAdapter;
    String serv_title,service_id,terms_en,terms_ar;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_apointment_screen);
        Session.forceRTLIfSupported(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        my_profile = (ImageView) findViewById(R.id.my_profile);
        service_option = (TextView) findViewById(R.id.service_option);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        booknow_btn =  (RelativeLayout) findViewById(R.id.booknow_btn);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        st_bookapp = (TextView) findViewById(R.id.st_bookapp);
        st_appointment = (TextView) findViewById(R.id.st_appointment);
        st_fname = (TextView) findViewById(R.id.st_fname);
        st_lname = (TextView) findViewById(R.id.st_lname);
        st_mobile = (TextView) findViewById(R.id.st_mobile);
        st_services = (TextView) findViewById(R.id.st_services);
        st_date = (TextView) findViewById(R.id.st_date);
        st_time = (TextView) findViewById(R.id.st_time);
        st_terms = (TextView) findViewById(R.id.st_terms);
        st_booknow = (TextView) findViewById(R.id.st_booknow);
        popup_view = (LinearLayout) findViewById(R.id.popup_view);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        listView = (ListView) findViewById(R.id.service_list);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        search_service = (LinearLayout) findViewById(R.id.search_service);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        search = (EditText) findViewById(R.id.search);
        categoriesfrom_api = new ArrayList<>();
        popServicesAdapter = new PopServicesAdapter(this,categoriesfrom_api,this);
        listView.setAdapter(popServicesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        progress_holder.setVisibility(View.GONE);

        if (getIntent()!=null && getIntent().hasExtra("terms")){
            terms_en = getIntent().getStringExtra("terms");
            terms_ar = getIntent().getStringExtra("terms_ar");
        }


        st_bookapp.setText(Session.GetWord(this,"BOOK APPOINTMENT"));
        st_appointment.setText(Session.GetWord(this,"Book Appointments"));
        st_fname.setText(Session.GetWord(this,"FIRST NAME"));
        st_lname.setText(Session.GetWord(this,"LAST NAME"));
        st_mobile.setText(Session.GetWord(this,"MOBILE"));
        st_services.setText(Session.GetWord(this,"SELECT SERVICES"));
        st_date.setText(Session.GetWord(this,"SELECT DATE"));
        st_time.setText(Session.GetWord(this,"SELECT TIME"));
        st_terms.setText(Session.GetWord(this,"TERMS AND CONDITIONS"));
        st_booknow.setText(Session.GetWord(this,"BOOK NOW"));



        if (getIntent()!=null && getIntent().hasExtra("title")){
            service_title = getIntent().getStringExtra("title");
            serv_id = getIntent().getStringExtra("id");
        }
        service_option.setText(service_title);
        date.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar mcurrentDate= Calendar.getInstance();
                final int mYear = mcurrentDate.get(Calendar.YEAR);
                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(BookAppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                mTimePicker = new TimePickerDialog(BookAppointmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                popup_view.setVisibility(View.VISIBLE);
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup_view.setVisibility(View.GONE);
            }
        });

        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookAppointmentActivity.this,EditProfile.class);
                startActivity(intent);
            }
        });

        st_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookAppointmentActivity.this,TermsActivity.class);
                intent.putExtra("terms",terms_en);
                intent.putExtra("terms_ar",terms_ar);
                startActivity(intent);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        booknow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_appointment();
            }
        });

        get_categories();


    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void add_appointment(){
        String fname_string = fname.getText().toString();
        String lname_string = lname.getText().toString();
        String service_string  = serv_id;
        String date_string = date.getText().toString();
        String time_string = time.getText().toString();
        if (fname_string.equals("")){
            Toast.makeText(BookAppointmentActivity.this,"Please Enter First Name",Toast.LENGTH_SHORT).show();
            fname.requestFocus();
        }else if (lname_string.equals("")){
            Toast.makeText(BookAppointmentActivity.this,"Please Enter Last Name",Toast.LENGTH_SHORT).show();
            lname.requestFocus();
        }else if (service_string == ""){
            Toast.makeText(BookAppointmentActivity.this,"Please Enter Service Id",Toast.LENGTH_SHORT).show();
            service_option.requestFocus();
        }else if (date_string.equals("")){
            Toast.makeText(BookAppointmentActivity.this,"Please Enter Date",Toast.LENGTH_SHORT).show();
            date.requestFocus();
        }else if (time_string.equals("")){
            Toast.makeText(BookAppointmentActivity.this,"Please Enter Time",Toast.LENGTH_SHORT).show();
            time.requestFocus();
        }else {
            show_progress();
            Ion.with(this)
                    .load(Session.SERVER_URL + "add-appointment.php")
                    .setBodyParameter("member_id", Session.GetUserId(this))
                    .setBodyParameter("fname",fname_string)
                    .setBodyParameter("lname",lname_string)
                    .setBodyParameter("service_id", service_id)
                    .setBodyParameter("date", date.getText().toString())
                    .setBodyParameter("time", time.getText().toString())
                    .setBodyParameter("message", "")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            hide_progress();
                            try {
                                if (result.get("status").getAsString().equals("Success")) {
                                    Toast.makeText(BookAppointmentActivity.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(BookAppointmentActivity.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();

                                }
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }

                        }
                    });
        }
    }


    public Dialog onCreateDialogSingleChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] array = new CharSequence[categoriesfrom_api.size()];
        for(int i=0;i<categoriesfrom_api.size();i++){

            array[i] = categoriesfrom_api.get(i).title;
        }
        builder.setTitle("Select City").setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                String selectedItem = array[i].toString();
                Log.e("select",selectedItem);
                service_option.setText(selectedItem);
                serv_id = categoriesfrom_api.get(i).id;


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

    @Override
    public void onResume(){
        super.onResume();
        if (popup_view!=null){
            popup_view.setVisibility(View.GONE);
        }
    }

    public void get_categories(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"services.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        try {
                            Log.e("cat", result.toString());
                            for (int i = 0; i < result.size(); i++) {
                                Category category = new Category(result.get(i).getAsJsonObject(), BookAppointmentActivity.this,"0");
                                categoriesfrom_api.add(category);

                                for (int j = 0; j < result.get(i).getAsJsonObject().get("services").getAsJsonArray().size(); j++) {

                                    Category sub_category = new Category(result.get(i).getAsJsonObject().get("services").getAsJsonArray().get(j).getAsJsonObject(), BookAppointmentActivity.this, "1");
                                    categoriesfrom_api.add(sub_category);

                                }
                            }

                            popServicesAdapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }


    public void onActivityResult(int requestCode,int resultCode, Intent imageReturnedIntent){
        super.onActivityResult(requestCode,resultCode,imageReturnedIntent);
        if (resultCode== Activity.RESULT_OK) {
            if (requestCode == 1) {

                if (resultCode == Activity.RESULT_OK) {

                    // do something with the result
                    if (imageReturnedIntent!=null && imageReturnedIntent.hasExtra("title")){
                        serv_title = imageReturnedIntent.getExtras().getString("title");
                        Log.e("cate",serv_title);
                        service_id = imageReturnedIntent.getExtras().getString("id");
                        Log.e("catid",service_id);
                        service_option.setText(serv_title);
                    }



                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // some stuff that will happen if there's no result
                }
            }

        }
    }
}
