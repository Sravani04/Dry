//package com.example.yellowsoft.dry;
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//
//import java.util.ArrayList;
//
///**
// * Created by yellowsoft on 18/8/17.
// */
//
//public class ServicesFragment extends Fragment {
//    RecyclerView recyclerView;
//    ServicesAdapter adapter;
//    LinearLayout progress_holder;
//    ImageView back_btn,my_profile;
//    ArrayList<Category> servicesfrom_api;
//    private static final String ARG_PAGE_NUMBER = "page_number";
//    String id;
//
//
//    public static ServicesFragment newInstance(int page,String id) {
//        ServicesFragment myFragment = new ServicesFragment();
//        Bundle args = new Bundle();
//        args.putSerializable("category",id);
//        myFragment.setArguments(args);
//        return myFragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.service_list,container,false);
//        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
//        progress_holder = (LinearLayout) view.findViewById(R.id.progress_holder);
//        back_btn = (ImageView) view.findViewById(R.id.back_btn);
//        my_profile = (ImageView) view.findViewById(R.id.my_profile);
//        //TextView txt = (TextView) view.findViewById(R.id.page_number_label);
//         id  = getArguments().getString("category");
//        Log.e("cat_id",id);
//
////        txt.setText(String.format("Page %d", page));
//        servicesfrom_api = new ArrayList<>();
//        progress_holder.setVisibility(View.GONE);
//        adapter = new ServicesAdapter(getActivity(),servicesfrom_api);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setHasFixedSize(true);
//
//        get_services();
//        return view;
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
//    public void get_services(){
//        show_progress();
//        Ion.with(this)
//                .load(Session.SERVER_URL+"services.php")
//                .setBodyParameter("category",id)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        hide_progress();
//                        try {
//                            JsonObject jsonObject = result.get(0).getAsJsonObject();
//                            JsonArray services_array = jsonObject.get("services").getAsJsonArray();
//                            Log.e("ssss", services_array.toString());
//                            for (int i = 0; i < services_array.size(); i++) {
//                                Category category = new Category(services_array.get(i).getAsJsonObject(), getActivity(),"1");
//                                servicesfrom_api.add(category);
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
//
//
//
//
//
//
//
//
//
//
//
//}
//
