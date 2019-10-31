package com.gecko.dealsmanagment.ui.deals;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.gecko.dealsmanagment.R;

import java.util.List;

public class DealsFragment extends Fragment {

    public static final String TAG = "myLog";

    private DealsViewModel mDealsViewModel;

    private TextView mTextView;
    private Button mButtonChangeMpp, mButtonLoadDealsFromXls;
    private Button mButtonSerialize, mButtonDeserialize;
    private RecyclerView mDealsRecyclerView;
    private DealsAdapter mAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDealsViewModel = ViewModelProviders.of(this).get(DealsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_deals, container, false);

        mTextView = root.findViewById(R.id.text_deals);
        mButtonSerialize = root.findViewById(R.id.button_serialize);
        mButtonDeserialize = root.findViewById(R.id.button_deserialize);
        mButtonChangeMpp = root.findViewById(R.id.button_change_num);
        mButtonLoadDealsFromXls = root.findViewById(R.id.button_change_text);
        mDealsRecyclerView = root.findViewById(R.id.recycler_view_deals);
        mDealsRecyclerView.setHasFixedSize(true);
        mDealsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDealsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                mTextView.setText(s);
            }
        });

        mDealsViewModel.getNum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mTextView.setText(integer.toString());
            }
        });

        mDealsViewModel.getDeals().observe(this, new Observer<List<Deal>>() {
            @Override
            public void onChanged(List<Deal> deals) {
                Log.d(TAG, "got deals observe notification");

                Log.d(TAG, "mDeals.size = " + deals.size());
                updateRecyclerView(deals);
            }
        });

        mButtonSerialize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Serialize clicked");
                mDealsViewModel.serializeDealsKeeper();
            }
        });

        mButtonDeserialize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Deserialize clicked");
                mDealsViewModel.deserializeDealsKeeper();
            }
        });

        mButtonChangeMpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mDealsViewModel.incNum();
                mDealsViewModel.changeMPP();

            }
        });

        mButtonLoadDealsFromXls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mDealsViewModel.changeText();
                mDealsViewModel.loadDeals();
            }
        });

//        mDealsViewModel.loadDeals();
        return root;
    }

    public void printDealsToLog(List<Deal> deals){
        for (Deal d : deals) {
            Log.d(TAG, "owner = " + d.getOwner());
        }
    }

    private void updateRecyclerView(List<Deal> deals) {
        Log.d(TAG, "updateRecyclerView started");
        printDealsToLog(deals);
        if (mAdapter == null) {
            mAdapter = new DealsAdapter(deals);
            mDealsRecyclerView.setAdapter(mAdapter);
        } else{
            Log.d(TAG, "mAdapter notified");
            mAdapter.setDealList(deals);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class DealsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mFirmName;
        private TextView mOwnerName;
        private TextView mPriceVolume;
        private TextView mToPay;

        private Deal mDeal;


        public DealsViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_deal, parent, false));
            itemView.setOnClickListener(this);
            mFirmName = itemView.findViewById(R.id.firm_name);
            mOwnerName = itemView.findViewById(R.id.owner_name);
            mPriceVolume = itemView.findViewById(R.id.price_volume);
            mToPay = itemView.findViewById(R.id.to_pay);
        }

        public void bind(Deal d){
            mDeal = d;
/*
            String s = mDeal.getName();
            s = s.substring(5);
*/
            mFirmName.setText(mDeal.getName()+" ["+mDeal.getContractor()+"]");
            mOwnerName.setText(mDeal.getOwner());
            mPriceVolume.setText("Объем: "+mDeal.getPriceVolume());
            mToPay.setText("К оплате: "+mDeal.getToPay());

        }

        @Override
        public void onClick(View v) {
            String s = "item " + mDeal.getName() + "clicked";
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
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
}