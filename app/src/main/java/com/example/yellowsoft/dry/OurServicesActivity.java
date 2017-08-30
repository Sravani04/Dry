//package com.example.yellowsoft.dry;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//
//import java.util.ArrayList;
//
///**
// * Created by yellowsoft on 23/8/17.
// */
//
//public class OurServicesActivity extends Activity {
//    ListView listView;
//    OurServicesAdapter adapter;
//    ArrayList<Services> servicesfrom_api;
//    String id;
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.example);
//        Session.forceRTLIfSupported(this);
//        if (getIntent()!=null && getIntent().hasExtra("category")){
//            id = getIntent().getStringExtra("category");
//        }
//        listView = (ListView) findViewById(R.id.our_services_list);
//        adapter = new OurServicesAdapter(this,servicesfrom_api);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//
//        get_services();
//
//
//    }
//
//    public void get_services(){
//        Ion.with(this)
//                .load(Session.SERVER_URL+"services.php")
//                .setBodyParameter("category",id)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        try {
//                            JsonObject jsonObject = result.get(0).getAsJsonObject();
//                            JsonArray services_array = jsonObject.get("services").getAsJsonArray();
//                            Log.e("ssss", services_array.toString());
//                            for (int i = 0; i < services_array.size(); i++) {
//                                Services services = new Services(services_array.get(i).getAsJsonObject(), OurServicesActivity.this);
//                                servicesfrom_api.add(services);
//                            }
//
//                            adapter.notifyDataSetChanged();
//                        }catch (Exception e1){
//                            e1.printStackTrace();
//                        }
//                    }
//                });
//    }
//
//}
