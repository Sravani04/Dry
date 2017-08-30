//package com.example.yellowsoft.dry;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//
//import com.google.gson.JsonArray;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//
//import java.util.ArrayList;
//
///**
// * Created by yellowsoft on 24/8/17.
// */
//
//public class ServicesPageActivity extends Activity {
//    ListView listView;
//    ImageView close_btn;
//    LinearLayout search_service;
//    EditText search;
//    ArrayList<Category> categoriesfrom_api;
//    ServicesPageAdapter adapter;
//    LinearLayout progress_holder;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.services_page_list);
//        categoriesfrom_api = new ArrayList<>();
//        listView = (ListView) findViewById(R.id.service_list);
//        close_btn = (ImageView) findViewById(R.id.close_btn);
//        search_service = (LinearLayout) findViewById(R.id.search_service);
//        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
//        progress_holder.setVisibility(View.GONE);
//        search = (EditText) findViewById(R.id.search);
//        adapter = new ServicesPageAdapter(this,categoriesfrom_api,);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//
//        get_categories();
//
//    }
//
//    public void show_progress(){
//        progress_holder.setVisibility(View.VISIBLE);
//    }
//
//    public void hide_progress(){
//        progress_holder.setVisibility(View.GONE);
//    }
//
//
//    public void get_categories(){
//        show_progress();
//        Ion.with(this)
//                .load(Session.SERVER_URL+"services.php")
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        hide_progress();
//                        try {
//                            Log.e("cat", result.toString());
//                            for (int i = 0; i < result.size(); i++) {
//                                Category category = new Category(result.get(i).getAsJsonObject(), ServicesPageActivity.this,"0");
//                                categoriesfrom_api.add(category);
//
//                                for(int j=0;j<result.get(i).getAsJsonObject().get("services").getAsJsonArray().size();j++){
//
//                                    Category sub_category = new Category(result.get(i).getAsJsonObject().get("services").getAsJsonArray().get(j).getAsJsonObject(), ServicesPageActivity.this,"1");
//                                    categoriesfrom_api.add(sub_category);
//
//                                }
//
//                            }
//
//                            adapter.notifyDataSetChanged();
//                        }catch (Exception e1){
//                            e1.printStackTrace();
//                        }
//
//                    }
//                });
//    }
//}
