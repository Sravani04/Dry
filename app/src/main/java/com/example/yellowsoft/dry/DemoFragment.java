package com.example.yellowsoft.dry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yellowsoft on 18/8/17.
 */

public class DemoFragment extends Fragment {

    public static DemoFragment newInstance(int someInt) {
        DemoFragment myFragment = new DemoFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(String.valueOf(getArguments().getInt("someInt")));

        //setHasOptionsMenu(true);

        return view;
    }


}


