package com.example.yellowsoft.dry;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by yellowsoft on 11/8/17.
 */

public class LoginActivity extends Activity {
    EditText email,password;
    TextView login_btn;
    LinearLayout progress_holder;
    TextView forget_pwd,signup_btn;
    ImageView back_btn;
    TextView st_login;
    Typeface regular,regular_arabic;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        Session.forceRTLIfSupported(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login_btn = (TextView) findViewById(R.id.login_btn);
        forget_pwd = (TextView) findViewById(R.id.forget_pwd);
        signup_btn = (TextView) findViewById(R.id.signup_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        st_login = (TextView) findViewById(R.id.st_login);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        regular = Typeface.createFromAsset(this.getAssets(), "fonts/libel-suit-rg.ttf");
        regular_arabic = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen Tunisia.ttf");

        st_login.setText(Session.GetWord(this,"LOGIN"));
        email.setHint(Session.GetWord(this,"Email Address"));
        password.setHint(Session.GetWord(this,"Password"));
        signup_btn.setText(Session.GetWord(this,"REGISTER"));
        login_btn.setText(Session.GetWord(this,"LOGIN"));
        forget_pwd.setText(Session.GetWord(this,"FORGOT PASSWORD"));

        if (Session.GetLang(this).equals("en")) {
            email.setTypeface(regular);
            password.setTypeface(regular);
        }else {
            email.setTypeface(regular_arabic);
            password.setTypeface(regular_arabic);
        }

        forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignInActivity.class);
                startActivity(intent);
                LoginActivity.this.onBackPressed();

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
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

    public void login(){
        String email_string = email.getText().toString();
        String password_string = password.getText().toString();
        if (email_string.equals("") || !email_string.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
            Toast.makeText(LoginActivity.this,"Please Enter Email Id",Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }else if (password_string.equals("")){
            Toast.makeText(LoginActivity.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }else {
            show_progress();
            Ion.with(this)
                    .load(Session.SERVER_URL+"login.php")
                    .setBodyParameter("email", email_string)
                    .setBodyParameter("password", password_string)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            hide_progress();
                            Log.e("res",result.toString());
                            try {
                                if (result.get("status").getAsString().equals("Success")) {
                                    Session.SetUserId(LoginActivity.this, result.get("member_id").getAsString());
                                    Toast.makeText(LoginActivity.this, result.get("name").getAsString(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }
                        }
                    });
        }
    }


}
