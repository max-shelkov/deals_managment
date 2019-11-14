package com.gecko.dealsmanagment.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gecko.dealsmanagment.DealsKeeper;
import com.gecko.dealsmanagment.R;

import static com.gecko.dealsmanagment.GeckoUtils.formattedInt;

public class DashboardFragment extends Fragment {

    private TextView mCurrentVolumePriceTextView;
    private TextView mCurrentVolumeRealTextView;
    private TextView mProlongationVolumePriceTextView;
    private TextView mProlongationVolumeRealTextView;
    private TextView mUniqueClientsCountTextView;

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        mCurrentVolumePriceTextView = root.findViewById(R.id.current_price_volume_text_view);
        mCurrentVolumeRealTextView = root.findViewById(R.id.current_real_volume_text_view);
        mProlongationVolumePriceTextView = root.findViewById(R.id.prolongation_price_volume_text_view);
        mProlongationVolumeRealTextView = root.findViewById(R.id.prolongation_real_volume_text_view);
        mUniqueClientsCountTextView = root.findViewById(R.id.unique_clients_count_text_view);

        dashboardViewModel.getDealsKeeper().observe(this, new Observer<DealsKeeper>() {
            @Override
            public void onChanged(DealsKeeper dealsKeeper) {
                mCurrentVolumePriceTextView.setText("Vp: "+ formattedInt(dealsKeeper.getCurrentVolumePrice()));
                mCurrentVolumeRealTextView.setText("Vr: " + formattedInt(dealsKeeper.getCurrentVolumeReal()));
                mProlongationVolumePriceTextView.setText("Vp: " + formattedInt(dealsKeeper.getProlongationVolumePrice()));
                mProlongationVolumeRealTextView.setText("Vr: " + formattedInt(dealsKeeper.getProlongationVolumeReal()));
                mUniqueClientsCountTextView.setText(""+dealsKeeper.getUniqueClientsCount());
            }
        });


        return root;
    }
}