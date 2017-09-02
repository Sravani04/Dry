package com.example.yellowsoft.dry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by yellowsoft on 7/8/17.
 */

public class ContactActivity extends Activity {
    EditText fname,lname,phone,subject,message;
    ImageView back_btn,my_profile;
    LinearLayout progress_holder;
    TextView st_contactus,st_contact,st_fname,st_lname,st_mobile,st_subject,st_message,st_send;
            RelativeLayout send_btn;
    Typeface regular,regular_arabic;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us_screen);
        Session.forceRTLIfSupported(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        phone = (EditText) findViewById(R.id.phone);
        subject = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.message);
        send_btn = (RelativeLayout) findViewById(R.id.send_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        my_profile = (ImageView) findViewById(R.id.my_profile);
        st_contactus = (TextView) findViewById(R.id.st_contactus);
        st_contact = (TextView) findViewById(R.id.st_contact);
        st_fname = (TextView) findViewById(R.id.st_fname);
        st_lname = (TextView) findViewById(R.id.st_lname);
        st_mobile = (TextView) findViewById(R.id.st_mobile);
        st_subject = (TextView) findViewById(R.id.st_subject);
        st_message = (TextView) findViewById(R.id.st_message);
        st_send = (TextView) findViewById(R.id.st_send);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);


        st_contactus.setText(Session.GetWord(this,"CONTACT US"));
        st_contact.setText(Session.GetWord(this,"CONTACT US"));
        st_fname.setText(Session.GetWord(this,"FIRST NAME"));
        st_lname.setText(Session.GetWord(this,"LAST NAME"));
        st_mobile.setText(Session.GetWord(this,"MOBILE"));
        st_subject.setText(Session.GetWord(this,"SUBJECT"));
        st_message.setText(Session.GetWord(this,"MESSAGE"));
        st_send.setText(Session.GetWord(this,"SEND"));

        regular = Typeface.createFromAsset(this.getAssets(), "fonts/libel-suit-rg.ttf");
        regular_arabic = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen Tunisia.ttf");


        if (Session.GetLang(this).equals("en")) {
            fname.setTypeface(regular);
            lname.setTypeface(regular);
            phone.setTypeface(regular);
            subject.setTypeface(regular);
            message.setTypeface(regular);
        }else {
            fname.setTypeface(regular_arabic);
            lname.setTypeface(regular_arabic);
            phone.setTypeface(regular_arabic);
            subject.setTypeface(regular_arabic);
            message.setTypeface(regular_arabic);
        }

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_contact();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactActivity.this.onBackPressed();
            }
        });

        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(ContactActivity.this).equals("-1")) {
                    Intent intent = new Intent(ContactActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ContactActivity.this, MyProfilePage.class);
                    startActivity(intent);
                }
            }
        });

        get_members();
    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }


    public void send_contact(){
        String fname_string = fname.getText().toString();
        String lname_string = lname.getText().toString();
        String phone_string = phone.getText().toString();
        String subject_string = subject.getText().toString();
        String message_string = message.getText().toString();
        if (fname_string.equals("")){
            Toast.makeText(ContactActivity.this,"Please Enter First Name",Toast.LENGTH_SHORT).show();
            fname.requestFocus();
        }else if (lname_string.equals("")){
            Toast.makeText(ContactActivity.this,"Please Enter Last Name",Toast.LENGTH_SHORT).show();
            lname.requestFocus();
        }else if (phone_string.equals("") || !validCellPhone(phone_string) || phone_string.length() < 6 || phone_string.length() > 13){
            Toast.makeText(ContactActivity.this,"Please Enter Phone",Toast.LENGTH_SHORT).show();
            phone.requestFocus();
        }else if (subject_string.equals("")){
            Toast.makeText(ContactActivity.this,"Please Enter Subject",Toast.LENGTH_SHORT).show();
            subject.requestFocus();
        }else if (message_string.equals("")){
            Toast.makeText(ContactActivity.this,"Please Enter Message",Toast.LENGTH_SHORT).show();
            message.requestFocus();
        }else {
            show_progress();
            Ion.with(this)
                    .load(Session.SERVER_URL + "contact-us.php")
                    .setBodyParameter("fname", fname_string)
                    .setBodyParameter("lname", lname_string)
                    .setBodyParameter("phone", phone_string)
                    .setBodyParameter("subject", subject_string)
                    .setBodyParameter("message", message_string)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            hide_progress();
                            try {
                                if (result.get("status").getAsString().equals("Success")) {
                                    Toast.makeText(ContactActivity.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    ContactActivity.this.onBackPressed();
                                } else {
                                    Toast.makeText(ContactActivity.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }
                        }
                    });
        }
    }

    public boolean validCellPhone(String number){
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    public void get_members(){
        Ion.with(ContactActivity.this)
                .load(Session.SERVER_URL+"members.php")
                .setBodyParameter("member_id",Session.GetUserId(ContactActivity.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        JsonObject jsonObject = result.get(0).getAsJsonObject();
                        fname.setText(jsonObject.get("fname").getAsString());
                        lname.setText(jsonObject.get("lname").getAsString());
                        phone.setText(jsonObject.get("phone").getAsString());


                    }
                });
    }
}
