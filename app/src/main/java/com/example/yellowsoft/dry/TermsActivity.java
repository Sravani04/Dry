package com.example.yellowsoft.dry;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yellowsoft on 30/8/17.
 */

public class TermsActivity extends Activity {
    TextView terms;
    String terms_en,terms_ar;
    ImageView back_btn;
    LinearLayout progress_holder;
    TextView st_terms,st_term;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_page);
        Session.forceRTLIfSupported(this);
        if (getIntent()!=null && getIntent().hasExtra("terms")){
            terms_en = getIntent().getStringExtra("terms");
            terms_ar = getIntent().getStringExtra("terms_ar");
        }
        terms = (TextView) findViewById(R.id.terms);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        st_terms = (TextView) findViewById(R.id.st_terms);
        st_term = (TextView) findViewById(R.id.st_term);
        st_terms.setText(Session.GetWord(this,"TERMS AND CONDITION"));
        st_term.setText(Session.GetWord(this,"TERMS AND CONDITION"));

        terms.setText(Html.fromHtml(terms_en));
        try {
            if (Session.GetLang(TermsActivity.this).equals("en")) {
                terms.setText(Html.fromHtml(terms_en));
            }else {
                terms.setText(Html.fromHtml(terms_ar));

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

