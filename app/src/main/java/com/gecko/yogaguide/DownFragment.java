package com.gecko.yogaguide;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;

public class DownFragment extends Fragment {

    Button mButtonToUp, mButtonToActivity;
    TextView mText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_down, container, false);
//        final LinearLayout rootLayout = view.findViewById(R.id.fragment_down_root_layout);

        mText = view.findViewById(R.id.text_view_down);
        mText.setTextSize(25);

        mButtonToUp = view.findViewById(R.id.button_down_to_up);
        mButtonToUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(rootLayout, "click from down to up", Snackbar.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment upFragment = fm.findFragmentById(R.id.fragment_container_up);
                TextView upText = upFragment.getView().findViewById(R.id.text_view_up);
                if (upText == null){
                    Log.i(MainActivity.TAG, "TextView in up not found");
                }else{
                    upText.setText("got from down fragment");
                }

            }
        });

        mButtonToActivity = view.findViewById(R.id.button_down_to_activity);
        mButtonToActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(rootLayout, "click from down to activity", Snackbar.LENGTH_LONG).show();
                TextView activityText = getActivity().findViewById(R.id.text_view_activity);
                if (activityText == null) {
                    Log.i(MainActivity.TAG, "TextView in activity not found");
                } else {
                    activityText.setText("got from down fragment");
                    Log.i(MainActivity.TAG, "TextView in activity edited successfully");
                }
            }
        });

        return view;
    }
}
