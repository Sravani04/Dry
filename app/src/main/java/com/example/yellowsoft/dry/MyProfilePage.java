package com.example.yellowsoft.dry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yellowsoft on 31/8/17.
 */

public class MyProfilePage extends Activity {
    TextView edit_profile,change_password,appointments,logout_btn,st_profile;
    ImageView  back_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        Session.forceRTLIfSupported(MyProfilePage.this);
        edit_profile = (TextView) findViewById(R.id.edit_profile);
        change_password = (TextView) findViewById(R.id.change_password);
        appointments  =(TextView) findViewById(R.id.appointments);
        logout_btn = (TextView) findViewById(R.id.logout_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        st_profile = (TextView) findViewById(R.id.st_profile);

        st_profile.setText(Session.GetWord(MyProfilePage.this,"MY PROFILE"));
        edit_profile.setText(Session.GetWord(this,"EDIT PROFILE"));
        change_password.setText(Session.GetWord(this,"CHANGE PASSWORD"));
        appointments.setText(Session.GetWord(this,"MY APPOINTMENTS"));
        logout_btn.setText(Session.GetWord(this,"LOGOUT"));

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfilePage.this,EditProfile.class);
                startActivity(intent);
                finish();
            }
        });

        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfilePage.this,AppointmentsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.SetUserId(MyProfilePage.this,"-1");
                Intent intent = new Intent(MyProfilePage.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfilePage.this,ChangePasswordPage.class);
                startActivity(intent);
                finish();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
