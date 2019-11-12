package com.gecko.dealsmanagment.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gecko.dealsmanagment.DealsKeeper;
import com.gecko.dealsmanagment.R;

import static com.gecko.dealsmanagment.GeckoUtils.formattedInt;

public class DashboardFragment extends Fragment {

    private TextView mVolumePriceTextView;
    private TextView mVolumeRealTextView;

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final TextView textView = root.findViewById(R.id.text_dashboard);
        mVolumePriceTextView = root.findViewById(R.id.current_price_volume_text_view);
        mVolumeRealTextView = root.findViewById(R.id.current_real_volume_text_view);

        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        dashboardViewModel.getDealsKeeper().observe(this, new Observer<DealsKeeper>() {
            @Override
            public void onChanged(DealsKeeper dealsKeeper) {
                mVolumePriceTextView.setText("Vp: "+ formattedInt(dealsKeeper.getCurrentVolumePrice()));
                mVolumeRealTextView.setText("Vr: " + formattedInt(dealsKeeper.getCurrentVolumeReal()));
            }
        });


        return root;
    }
}