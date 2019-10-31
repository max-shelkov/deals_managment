package com.gecko.dealsmanagment.ui.deals;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gecko.dealsmanagment.Deal;
import com.gecko.dealsmanagment.DealsKeeper;

import java.util.List;

public class DealsViewModel extends ViewModel {

    private static final String TAG = "myLog";

    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> mNum;
    private MutableLiveData<List<Deal>> mDeals;
//    private MutableLiveData<DealsKeeper> mDealsKeeperMutableLiveData;

    private DealsKeeper mDealsKeeper;

    public DealsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is deals fragment");

        mNum = new MutableLiveData<>();
        mNum.setValue(0);

        mDeals = new MutableLiveData<>();

//        mDealsKeeperMutableLiveData = new MutableLiveData<>();


        if (mDealsKeeper == null){
            mDealsKeeper = new DealsKeeper();
        }
        List<Deal> deals = mDealsKeeper.getDeals();
        if (deals == null){
            Log.e(TAG, "deals == null");
        } else {
            mDeals.setValue(deals);
        }

    }

    public LiveData<String> getText() {
        return mText;
    }

    public void changeText(){
        mText.setValue(mText.getValue()+mNum.getValue());
    }

    public MutableLiveData<Integer> getNum() {
        return mNum;
    }

    public void changeMPP(){
        List<Deal> deals = mDeals.getValue();
        deals.get(0).setOwner("Manager3");
        mDealsKeeper.setDeals(deals);
        mDeals.setValue(deals);
    }

    public void loadDeals(){
        List<Deal> deals = mDealsKeeper.dealsLoader("201910.xls");
        mDealsKeeper.setDeals(deals);
        mDeals.setValue(deals);
    }

    public MutableLiveData<List<Deal>> getDeals() {
        return mDeals;
    }

    public void incNum() {
        if (mNum == null){
            Log.d(TAG, "mNum == null");
        }else {
            int i = mNum.getValue() + 1;
            mNum.setValue(i);
            Log.d(TAG, "mNum = " + i);
        }
    }

    public void serializeDealsKeeper(){
        Log.d(TAG, " ViewModel Serialize action");
        mDealsKeeper.serializeDeals();
    }

    public void deserializeDealsKeeper(){
        Log.d(TAG, "ViewModel Deserialize action");
        List<Deal> deals = mDealsKeeper.deserializeDeals();
        mDealsKeeper.setDeals(deals);
//        List<Deal> deals = mDealsKeeper.getDeals();
        if (deals == null){
            Log.e(TAG, "deals == null");
        } else {
            mDeals.setValue(deals);
        }
    }

}