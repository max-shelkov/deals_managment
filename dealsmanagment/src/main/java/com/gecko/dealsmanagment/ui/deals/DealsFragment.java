package com.gecko.dealsmanagment.ui.deals;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gecko.dealsmanagment.Deal;
import com.gecko.dealsmanagment.DealsKeeper;
import com.gecko.dealsmanagment.R;

import java.util.Calendar;
import java.util.List;

import static com.gecko.dealsmanagment.GeckoUtils.formattedInt;
import static com.gecko.dealsmanagment.GeckoUtils.monthsBetween;

public class DealsFragment extends Fragment {

    public static final String TAG = "myLog";

    private static final int READ_REQUEST_CODE = 42;
    private static final int DEAL_DETAILS_REQUEST_CODE = 43;

    private DealsViewModel mDealsViewModel;

    private Button mButtonLoadDealsFromXls;
    private Button mButtonSerialize, mButtonDeserialize;
    private RecyclerView mDealsRecyclerView;
    private DealsAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDealsViewModel = ViewModelProviders.of(this).get(DealsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_deals, container, false);

//        mTextView = root.findViewById(R.id.text_deals);
        mButtonSerialize = root.findViewById(R.id.button_serialize);
        mButtonDeserialize = root.findViewById(R.id.button_deserialize);
        mButtonLoadDealsFromXls = root.findViewById(R.id.button_read_from_asset_file);
        mDealsRecyclerView = root.findViewById(R.id.recycler_view_deals);
        mDealsRecyclerView.setHasFixedSize(true);
        mDealsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        mButtonSerialize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "Serialize clicked");
//                mDealsViewModel.serializeDealsKeeper();
                mDealsViewModel.logDealsStatuses();
            }
        });

        mButtonDeserialize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Deserialize clicked");
                mDealsViewModel.deserializeDealsKeeper();
            }
        });

        mButtonLoadDealsFromXls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "load from xls clicked");
                loadDealsFromXls();
                mDealsViewModel.serializeDealsKeeper();
            }
        });

        return root;
    }

    private void loadDealsFromXls(){
        mDealsViewModel.loadDeals();
    }

    private void loadDealsFromXls(Uri uri){
        mDealsViewModel.loadDeals(uri);
    }

    public void printDealsToLog(List<Deal> deals){
        for (Deal d : deals) {
            Log.d(TAG, "owner = " + d.getOwner());
        }
    }

    private void updateRecyclerView(List<Deal> deals) {
        Log.d(TAG, "updateRecyclerView started");
//        printDealsToLog(deals);
        if (mAdapter == null) {
            mAdapter = new DealsAdapter(deals);
            mDealsRecyclerView.setAdapter(mAdapter);
        } else{
            Log.d(TAG, "mAdapter notified");
            mAdapter.setDealList(deals);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mDealsViewModel.serializeDealsKeeper();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume action");
        mDealsViewModel.getDealsKeeper().observe(this, new Observer<DealsKeeper>() {
            @Override
            public void onChanged(DealsKeeper dealsKeeper) {
                Log.d(TAG, "got dealsKeeer observe notification");
                Log.d(TAG, "mDeals.size = " + dealsKeeper.getDeals().size());
                updateRecyclerView(dealsKeeper.getDeals());
            }
        });

    }

    private class DealsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mFirmName;
        private TextView mOwnerName;
        private TextView mPriceVolume;
        private TextView mToPay;
        private ProgressBar mPeriod;

        private Deal mDeal;


        public DealsViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_deal, parent, false));
            itemView.setOnClickListener(this);
            mFirmName = itemView.findViewById(R.id.firm_name);
            mOwnerName = itemView.findViewById(R.id.owner_name);
            mPriceVolume = itemView.findViewById(R.id.price_volume);
            mToPay = itemView.findViewById(R.id.to_pay);
            mPeriod = itemView.findViewById(R.id.period_progress_bar_recycler_item);

        }

        public void bind(Deal d){
            mDeal = d;
            mFirmName.setText(mDeal.getName()+" ["+mDeal.getContractor()+"]");
            mOwnerName.setText(mDeal.getOwner());
            mPriceVolume.setText("V: "+ formattedInt(mDeal.getPriceVolume()));
            mToPay.setText("Rub: "+ formattedInt(mDeal.getToPay()));
            mPeriod.setMax(mDeal.getDuration());
            mPeriod.setProgress(monthsBetween(mDeal.getStartMonth(), Calendar.getInstance()));
//            Log.d(TAG, "finish month = " + finish.get(Calendar.MONTH) + " start month = " +start.get(Calendar.MONTH));
            int monthBetween = monthsBetween(mDeal.getStartMonth(), Calendar.getInstance());
            String status = mDeal.getStatus();
            Log.d(TAG, "name = " + mDeal.getName() + " status = " + mDeal.getStatus());
            if(mDeal.getStatus().equals(Deal.DEAL_STATUS_PROLONGATION)){
                mPeriod.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.color2GisYellowDark)));
            } else if (mDeal.getStatus().equals(Deal.DEAL_STATUS_PREPROLONGATION)){
                mPeriod.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.color2GisGreenDark)));
            }else{
                mPeriod.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.color2GisGreenMedium)));
            }
        }

        @Override
        public void onClick(View v) {
//            String s = "item " + mDeal.getName() + "clicked";
//            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), DealDetailsActivity.class);
            intent.putExtra("deal", mDeal);
            startActivityForResult(intent, DEAL_DETAILS_REQUEST_CODE);
        }
    }

    private class DealsAdapter extends RecyclerView.Adapter<DealsViewHolder>{

        private List<Deal> mDealList;

        public DealsAdapter(List<Deal> dealList) {
            mDealList = dealList;
        }

        @NonNull
        @Override
        public DealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DealsViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DealsViewHolder holder, int position) {
            Deal d = mDealList.get(position);
            holder.bind(d);
        }

        @Override
        public int getItemCount() {
            return mDealList.size();
        }

        public void setDealList(List<Deal> dealList) {
            mDealList = dealList;
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.deals_list_options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.load_xls_menu_item:
                loadDealsFromXls();
                mDealsViewModel.serializeDealsKeeper();
                break;
            case R.id.choose_file_menu_item:
                Toast.makeText(getActivity(), "choose file", Toast.LENGTH_LONG).show();
                performFileSearch();
//                startAdditionalActivity();
                break;
            case R.id.clear_deals_menu_item:
                mDealsViewModel.clearDeals();
                mDealsViewModel.serializeDealsKeeper();
                break;
        }

        return true;
    }



    private void performFileSearch(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");

/*
        List<ResolveInfo> activities;
        PackageManager pm = getActivity().getPackageManager();
        activities = pm.queryIntentActivities(intent, PackageManager.MATCH_ALL);
        Log.d(TAG, "number of activities = " + activities.size());
*/

        startActivityForResult(intent, READ_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            Toast.makeText(getActivity(), "data is null", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i(TAG, "Uri: " + uri.getPath());
                loadDealsFromXls(uri);
            }
        }

        if (requestCode == DEAL_DETAILS_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d(TAG, "got activity result");
            Deal d = (Deal) data.getSerializableExtra("edited_deal");
            Toast.makeText(getActivity(), d.getName(), Toast.LENGTH_LONG).show();
            mDealsViewModel.changeDeal(d);

        }
    }
}