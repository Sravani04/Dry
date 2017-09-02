package com.example.yellowsoft.dry;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.yellowsoft.dry.R.id.imageView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    TextView services_btn,appointments_btn,contact_btn,about_btn,home_btn,arabic_btn,profile_btn;
    ImageView menu_btn;
    ImageView page_logo;
    ImageView profile,ad_image;
    LinearLayout drawerView,progress_holder;
    RelativeLayout mainView,book_appointment,services;
    String about,servicesId,about_ar;
    ViewPager viewPager;
    MainActivityAdapter adapter;
    private static int currentPage = 0;
    Timer timer;
    TextView title,st_select_services;
    ArrayList<Banners> bannersfrom_api;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categoriesfrom_api;
    String cat_id;
    TextView st_bookapp,st_services,price;
    ListView listView;
    ImageView close_btn;
    LinearLayout search_service;
    EditText search;
    ServicesPageAdapter servicesPageAdapter;
    LinearLayout popup_view;
    ArrayList<Services> servicesfrom_api;
    String lang;
    String term_en,term_ar;
    ViewPager pager;
    AdvertisementsAdapter advertisementsAdapter;
    ArrayList<Advertisements> advertisementsfrom_api;
    Timer timer2;
    Runnable Update_ad;
    private static long back_pressed;
    Bitmap bitMap;
    Object new_width;





    private void openNavigation(){

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))

            mDrawerLayout.closeDrawer(GravityCompat.START,true);
        else
            mDrawerLayout.openDrawer(GravityCompat.START,true);

    }


    private void setupActionBar() {
//set action bar
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));



        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.action_bar_title,null);
        Session.forceRTLIfSupported(this);

        page_logo = (ImageView) v.findViewById(R.id.page_logo);

        menu_btn = (ImageView) v.findViewById(R.id.btn_menu);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavigation();
            }
        });



        profile = (ImageView) v.findViewById(R.id.profile_pic);


        getSupportActionBar().setCustomView(v, layoutParams);
        Toolbar parent = (Toolbar) v.getParent();
        parent.setContentInsetsAbsolute(0, 0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Session.forceRTLIfSupported(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        services_btn = (TextView) findViewById(R.id.services_btn);
        appointments_btn = (TextView) findViewById(R.id.appointments_btn);
        contact_btn = (TextView) findViewById(R.id.contact_btn);
        profile_btn = (TextView) findViewById(R.id.profile_btn);
        about_btn = (TextView) findViewById(R.id.about_btn);
        book_appointment = (RelativeLayout) findViewById(R.id.book_appointment);
        services = (RelativeLayout) findViewById(R.id.services);
        home_btn = (TextView) findViewById(R.id.home_btn);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        title = (TextView) findViewById(R.id.title);
        //ad_image = (ImageView) findViewById(R.id.ad_image);
        arabic_btn = (TextView) findViewById(R.id.arabic_btn);
        st_select_services = (TextView) findViewById(R.id.st_select_services);
        st_bookapp = (TextView) findViewById(R.id.st_bookapp);
        st_services = (TextView) findViewById(R.id.st_services);
        price = (TextView) findViewById(R.id.price);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        popup_view = (LinearLayout) findViewById(R.id.popup_view);
        pager = (ViewPager) findViewById(R.id.pager);
        progress_holder.setVisibility(View.GONE);
        bannersfrom_api = new ArrayList<>();
        categoriesfrom_api = new ArrayList<>();
        servicesfrom_api = new ArrayList<>();
        advertisementsfrom_api = new ArrayList<>();

        final Typeface arabic_font = Typeface.createFromAsset(getAssets(), "fonts/Hacen Tunisia Bd.ttf");
        final Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/libel-suit-rg.ttf");

        st_bookapp.setText(Session.GetWord(this,"Book Appointments"));
        st_services.setText(Session.GetWord(this,"OUR SERVICES"));


        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        drawerView = (LinearLayout) findViewById(R.id.drawerView);
        mainView = (RelativeLayout) findViewById(R.id.mainView);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();

            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (Session.GetLang(MainActivity.this).equals("en")){
                    mainView.setTranslationX(slideOffset * drawerView.getWidth());
                }else {
                    mainView.setTranslationX(-slideOffset * drawerView.getWidth());
                }
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();


            }
        };


        advertisementsAdapter = new AdvertisementsAdapter(this,advertisementsfrom_api);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setPageTransformer(true,new FadePageTransformer());
        pager.setAdapter(advertisementsAdapter);


        final Handler handler_ad = new Handler();
        Update_ad = new Runnable() {
            public void run() {

                if(pager.getCurrentItem()<advertisementsfrom_api.size()-1)
                    pager.setCurrentItem(pager.getCurrentItem()+1);
                else
                    pager.setCurrentItem(0);

                handler_ad.postDelayed(Update_ad,2000);

            }
        };
        Update_ad.run();








        listView = (ListView) findViewById(R.id.service_list);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        search_service = (LinearLayout) findViewById(R.id.search_service);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        search = (EditText) findViewById(R.id.search);
        servicesPageAdapter = new ServicesPageAdapter(this,categoriesfrom_api);
        listView.setAdapter(servicesPageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        home_btn.setText(Session.GetWord(this,"Home"));
        about_btn.setText(Session.GetWord(this,"ABOUT US"));
        services_btn.setText(Session.GetWord(this,"SERVICES"));
        appointments_btn.setText(Session.GetWord(this,"APPOINTMENTS"));
        profile_btn.setText(Session.GetWord(this,"MY PROFILE"));
        contact_btn.setText(Session.GetWord(this,"CONTACT US"));
        arabic_btn.setText(Session.GetWord(this,"ARABIC"));
        st_select_services.setText(Session.GetWord(this,"SELECT SERVICES"));



        mDrawerLayout.setDrawerListener(mDrawerToggle);



        services_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(MainActivity.this,ServicesActivity.class);
//                startActivity(intent);
                popup_view.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.myanim);
                popup_view.startAnimation(anim);
                mDrawerLayout.closeDrawer(GravityCompat.START,true);

            }
        });

        appointments_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(MainActivity.this).equals("-1")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(MainActivity.this, AppointmentsActivity.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START, true);
                }
            }
        });

        contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,ContactActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.START,true);
            }
        });

        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Log.e("comin","coming or not");
                    Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                    intent.putExtra("about", about);
                    intent.putExtra("about_ar",about_ar);
                try {
                    Log.e("aaaaaaaaa",about_ar);
                }catch (Exception e){
                    e.printStackTrace();
                }

                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START,true);


            }
        });

        arabic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetLang(MainActivity.this).equals("en")) {
                    Session.SetLang(MainActivity.this, "ar");
                    setLocale(Session.GetLang(MainActivity.this));
                }else {
                    Session.SetLang(MainActivity.this, "en");
                    setLocale(Session.GetLang(MainActivity.this));
//                    arabic_btn.setText(Session.GetWord(MainActivity.this,"ARABIC"));
                }

            }
        });

        if (Session.GetLang(MainActivity.this).equals("en")){
            arabic_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            //Typeface arabic_font = Typeface.createFromAsset(getAssets(), "fonts/Hacen Tunisia.ttf");
            arabic_btn.setTypeface(arabic_font);
        }else {
            arabic_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            arabic_btn.setTypeface(bold,Typeface.BOLD);
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(MainActivity.this).equals("-1")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, MyProfilePage.class);
                    startActivity(intent);
                }
            }
        });

        book_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(MainActivity.this).equals("-1")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, BookAppointmentActivity.class);
                    intent.putExtra("terms",term_en);
                    intent.putExtra("terms_ar",term_ar);
                    try {
                        Log.e("aaaaaaaaa",term_en);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            }
        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.GetUserId(MainActivity.this).equals("-1")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, MyProfilePage.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawer(GravityCompat.START,true);
                }


            }
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               popup_view.setVisibility(View.VISIBLE);
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup_view.setVisibility(View.GONE);
                Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_to_bottom);
                popup_view.startAnimation(anim);
                View view1 = MainActivity.this.getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                }

            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                mDrawerLayout.closeDrawer(GravityCompat.START,true);
            }
        });

//        home_btn.setText(Session.GetWord(this,"Home"));

        search.setText("");

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(servicesPageAdapter!=null)
                    servicesPageAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        adapter = new MainActivityAdapter(this,bannersfrom_api);
        viewPager.setAdapter(adapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == bannersfrom_api.size() +1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);









        get_settings();
        get_banners();
        get_advertisements();
        get_categories();


    }





    @Override
    public void onResume(){
       super.onResume();
        if (popup_view!=null){
            popup_view.setVisibility(View.GONE);
        }
    }


    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        if (popup_view!=null){
            popup_view.setVisibility(View.GONE);
        }

        if (back_pressed + 10000 > System.currentTimeMillis()){
        super.onBackPressed();}
        else{
            back_pressed = System.currentTimeMillis();
        }
    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void get_settings(){
        Ion.with(this)
                .load(Session.SERVER_URL+"settings.php")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            if (e != null)
                                e.printStackTrace();
                            Log.e("settings",result.toString());
                            about = result.get("about").getAsString();
                            about_ar = result.get("about_ar").getAsString();
                            term_en = result.get("terms").getAsString();
                            term_ar = result.get("terms_ar").getAsString();
                            Log.e("a",term_en);

                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }

    public void get_banners(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"banners.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            hide_progress();
                            for (int i=0;i<result.size();i++){
                                Banners banners = new Banners(result.get(i).getAsJsonObject(),MainActivity.this);
                                bannersfrom_api.add(banners);
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }

    public void get_advertisements(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"advertisements.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        try {
                            try {
                                Log.e("ad", result.toString());
                                for (int i = 0; i < result.size(); i++) {
                                    Advertisements advertisements = new Advertisements(result.get(i).getAsJsonObject(), MainActivity.this);
                                    advertisementsfrom_api.add(advertisements);


                                }
                                advertisementsAdapter.notifyDataSetChanged();
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }

                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
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
                                Category category = new Category(result.get(i).getAsJsonObject(), MainActivity.this,"0");
                                categoriesfrom_api.add(category);

                                for (int j = 0; j < result.get(i).getAsJsonObject().get("services").getAsJsonArray().size(); j++) {

                                    Category sub_category = new Category(result.get(i).getAsJsonObject().get("services").getAsJsonArray().get(j).getAsJsonObject(), MainActivity.this, "1");
                                    categoriesfrom_api.add(sub_category);

                                }
                            }

                            servicesPageAdapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }



    public void show_alert_edit(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);


// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater=null;
        inflater = (LayoutInflater) MainActivity.this.
                getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.select_service_popup, null);
        Session.forceRTLIfSupported(this);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        TextView pop_title = (TextView) dialogView.findViewById(R.id.pop_up_title);
        final ListView listView = (ListView) dialogView.findViewById(R.id.service_list);
        categoryAdapter = new CategoryAdapter(MainActivity.this,categoriesfrom_api);

        listView.setAdapter(categoryAdapter);
        pop_title.setText("Select Category");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this,ServicePopupActivity.class);
                intent.putExtra("category",categoriesfrom_api.get(i).id);
                cat_id = categoriesfrom_api.get(i).id;
                startActivity(intent);

                alertDialog.dismiss();
            }
        });

        final EditText search_city = (EditText) dialogView.findViewById(R.id.search_service);
        TextView save = (TextView) dialogView.findViewById(R.id.pop_save_btn);
        TextView cancel = (TextView) dialogView.findViewById(R.id.pop_cancel_btn);
        LinearLayout main_cat_select = (LinearLayout) dialogView.findViewById(R.id.select_product_category);
        main_cat_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

        search_city.setText("");

        search_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(categoryAdapter!=null)
                    categoryAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });



        categoryAdapter.notifyDataSetChanged();


        alertDialog.show();


    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }



}
