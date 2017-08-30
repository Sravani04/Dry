package com.example.yellowsoft.dry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by yellowsoft on 18/8/17.
 */

public class ForgetPasswordActivity extends Activity {
    ImageView back_btn;
    RelativeLayout send_btn;
    EditText email;
    LinearLayout progress_holder;
    TextView st_forgot_password;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        Session.forceRTLIfSupported(this);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        send_btn = (RelativeLayout) findViewById(R.id.send_btn);
        email    = (EditText) findViewById(R.id.email);
        st_forgot_password = (TextView) findViewById(R.id.st_forgot_password);
        st_forgot_password.setText(Session.GetWord(this,"FORGOT PASSWORD"));
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forget_password();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void forget_password(){
        String email_string = email.getText().toString();
        if (email_string.equals("")){
            Toast.makeText(ForgetPasswordActivity.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
        }else {
            show_progress();
            Ion.with(this)
                    .load(Session.SERVER_URL + "forget-password.php")
                    .setBodyParameter("email", email_string)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            hide_progress();
                            try {
                                if (result.get("status").getAsString().equals("Success")) {
                                    Toast.makeText(ForgetPasswordActivity.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();

                                }
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }
                        }
                    });
        }
    }
}
