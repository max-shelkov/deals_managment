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

import com.gecko.dealsmanagment.GeckoUtils;
import com.gecko.dealsmanagment.R;

public class DashboardFragment extends Fragment {

    private TextView mCurrentUniqueClientsCountTextView;
    private TextView mCurrentVolumePriceTextView;
    private TextView mCurrentVolumeRealTextView;
    private TextView mProlongationVolumePriceTextView;
    private TextView mProlongationVolumeRealTextView;
    private TextView mNextUniqueClientsCountTextView;
    private TextView mNextVolumePriceTextView;
    private TextView mNextVolumeRealTextView;
    private TextView mNewDealsCountTextView;
    private TextView mNewDealsVolumePriceTextView;
    private TextView mNewDealsVolumeRealTextView;



    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        mCurrentVolumePriceTextView = root.findViewById(R.id.current_price_volume_text_view);
        mCurrentVolumeRealTextView = root.findViewById(R.id.current_real_volume_text_view);
        mProlongationVolumePriceTextView = root.findViewById(R.id.prolongation_price_volume_text_view);
        mProlongationVolumeRealTextView = root.findViewById(R.id.prolongation_real_volume_text_view);
        mCurrentUniqueClientsCountTextView = root.findViewById(R.id.unique_clients_count_text_view);
        mNextUniqueClientsCountTextView = root.findViewById(R.id.unique_clients_next_month_text_view);
        mNextVolumePriceTextView = root.findViewById(R.id.next_month_price_volume_text_view);
        mNextVolumeRealTextView = root.findViewById(R.id.next_month_real_volume_text_view);
        mNewDealsCountTextView = root.findViewById(R.id.new_deals_next_month_text_view);
        mNewDealsVolumePriceTextView = root.findViewById(R.id.new_deals_price_volume_text_view);
        mNewDealsVolumeRealTextView = root.findViewById(R.id.new_deals_real_volume_text_view);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

//        dashboardViewModel.reloadDealsKeeper();

        dashboardViewModel.getCurrentDealCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mCurrentUniqueClientsCountTextView.setText(integer.toString());
            }
        });

        dashboardViewModel.getCurrentPriceVolume().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mCurrentVolumePriceTextView.setText("Vp: "+GeckoUtils.formattedInt(integer));
            }
        });

        dashboardViewModel.getCurrentRealVolume().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mCurrentVolumeRealTextView.setText("Vr: "+GeckoUtils.formattedInt(integer));
            }
        });

        dashboardViewModel.getProlongationPriceVolume().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mProlongationVolumePriceTextView.setText("Vp: "+GeckoUtils.formattedInt(integer));
            }
        });

        dashboardViewModel.getProlongationRealVolume().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mProlongationVolumeRealTextView.setText("Vr: "+GeckoUtils.formattedInt(integer));
            }
        });

        dashboardViewModel.getNextDealCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mNextUniqueClientsCountTextView.setText(integer.toString());
            }
        });

        dashboardViewModel.getNextPriceVolume().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mNextVolumePriceTextView.setText("Vp: "+ GeckoUtils.formattedInt(integer));
            }
        });

        dashboardViewModel.getNextRealVolume().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mNextVolumeRealTextView.setText("Vr: "+GeckoUtils.formattedInt(integer));
            }
        });

        dashboardViewModel.getNewDealsCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mNewDealsCountTextView.setText("" + integer);
            }
        });

        dashboardViewModel.getNewDealsPriceVolume().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mNewDealsVolumePriceTextView.setText("Vp: "+ GeckoUtils.formattedInt(integer));
            }
        });

        dashboardViewModel.getNewDealsRealVolume().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mNewDealsVolumeRealTextView.setText("Vr: "+ GeckoUtils.formattedInt(integer));
            }
        });

    }
}