package com.gecko.yogaguide;

import android.os.Bundle;
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

public class UpFragment extends Fragment {

    Button mButtonToActivity, mButtonToDown;
    TextView mText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up, container, false);
//        final LinearLayout rootLayout = view.findViewById(R.id.fragment_up_root_layout);

        mButtonToActivity = view.findViewById(R.id.button_up_to_activity);
        mButtonToActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(rootLayout,"click in up", Snackbar.LENGTH_LONG).show();
                TextView activityText = getActivity().findViewById(R.id.text_view_activity);
                activityText.setText("got from up fragment");
            }
        });

        mButtonToDown = view.findViewById(R.id.button_up_to_down);
        mButtonToDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(rootLayout,"click in up", Snackbar.LENGTH_LONG).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment downFragment = fm.findFragmentById(R.id.fragment_container_down);
                TextView downText = downFragment.getView().findViewById(R.id.text_view_down);
                downText.setText("got from up fragment");
            }
        });



        return view;
    }
}
