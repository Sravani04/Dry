package com.example.yellowsoft.dry;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by yellowsoft on 31/8/17.
 */

public class ChangePasswordPage extends Activity {
    EditText old_password,password,confirm_password;
    TextView submit_btn,st_cpassword;
    LinearLayout progress_holder;
    ImageView back_btn;
    Typeface regular,regular_arabic;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        Session.forceRTLIfSupported(ChangePasswordPage.this);
        old_password = (EditText) findViewById(R.id.old_password);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        submit_btn = (TextView) findViewById(R.id.submit_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        st_cpassword = (TextView) findViewById(R.id.st_cpassword);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    change_password();
            }
        });

        regular = Typeface.createFromAsset(this.getAssets(), "fonts/libel-suit-rg.ttf");
        regular_arabic = Typeface.createFromAsset(this.getAssets(), "fonts/Hacen Tunisia.ttf");

        old_password.setHint(Session.GetWord(this,"ENTER OLD PASSWORD"));
        password.setHint(Session.GetWord(this,"PASSWORD"));
        confirm_password.setHint(Session.GetWord(this,"CONFIRM PASSWORD"));
        submit_btn.setText(Session.GetWord(this,"SUBMIT"));
        st_cpassword.setText(Session.GetWord(this,"CHANGE PASSWORD"));


        if (Session.GetLang(this).equals("en")) {
            old_password.setTypeface(regular);
            password.setTypeface(regular);
            confirm_password.setTypeface(regular);
        }else {
            old_password.setTypeface(regular_arabic);
            password.setTypeface(regular_arabic);
            confirm_password.setTypeface(regular_arabic);
        }


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePasswordPage.this,MyProfilePage.class);
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

    public void change_password(){
        String oldPassword_string = old_password.getText().toString();
        String password_string = password.getText().toString();
        String cPassword_string = confirm_password.getText().toString();
        if (oldPassword_string.equals("")){
            Toast.makeText(ChangePasswordPage.this,"Please Enter Old Password",Toast.LENGTH_SHORT).show();
            old_password.requestFocus();
        }else if (password_string.equals("")){
            Toast.makeText(ChangePasswordPage.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }else if (cPassword_string.equals("")){
            Toast.makeText(ChangePasswordPage.this,"Please Enter Confirm Password",Toast.LENGTH_SHORT).show();
            confirm_password.requestFocus();
        }else {
            show_progress();
            Ion.with(this)
                    .load(Session.SERVER_URL + "change-password.php")
                    .setBodyParameter("member_id",Session.GetUserId(ChangePasswordPage.this))
                    .setBodyParameter("opassword", oldPassword_string)
                    .setBodyParameter("password", password_string)
                    .setBodyParameter("cpassword", cPassword_string)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            hide_progress();
                            try {
                                if (result.get("status").getAsString().equals("Success")){
                                    Toast.makeText(ChangePasswordPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ChangePasswordPage.this,MyProfilePage.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(ChangePasswordPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }

                        }
                    });
        }
    }
}
