package com.example.yellowsoft.dry;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yellowsoft on 11/8/17.
 */

public class AboutActivity extends Activity {
    TextView about;
    String aboutus,about_ar;
    ImageView back_btn;
    LinearLayout progress_holder;
    TextView st_aboutus,st_about;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_page);
        Session.forceRTLIfSupported(this);
        if (getIntent()!=null && getIntent().hasExtra("about")){
            aboutus = getIntent().getStringExtra("about");
            about_ar = getIntent().getStringExtra("about_ar");
        }
        about = (TextView) findViewById(R.id.about);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        st_aboutus = (TextView) findViewById(R.id.st_aboutus);
        st_about = (TextView) findViewById(R.id.st_about);
        st_about.setText(Session.GetWord(this,"ABOUT US"));
        st_aboutus.setText(Session.GetWord(this,"ABOUT US"));
        about.setText(Html.fromHtml(aboutus));

        try {
            if (Session.GetLang(AboutActivity.this).equals("en")) {
                about.setText(Html.fromHtml(aboutus));
            }else {
                about.setText(Html.fromHtml(about_ar));

            }
        }catch (Exception e){
            e.printStackTrace();
        }




        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
