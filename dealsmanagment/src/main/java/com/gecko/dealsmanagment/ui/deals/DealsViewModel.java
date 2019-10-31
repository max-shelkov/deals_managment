package com.gecko.dealsmanagment.ui.deals;

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
    private MutableLiveData<DealsKeeper> mDealsKeeper;



    public DealsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is deals fragment");

        mNum = new MutableLiveData<>();
        mNum.setValue(0);

        mDealsKeeper = new MutableLiveData<>();

        if (mDealsKeeper.getValue() == null){
            mDealsKeeper.setValue(new DealsKeeper());
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
        mDealsKeeper.getValue().getDeals().get(0).setOwner("Manager3");
        mDealsKeeper.setValue(mDealsKeeper.getValue());
    }

    public void loadDeals(){
        List<Deal> deals = mDealsKeeper.getValue().dealsLoader("201910.xls");
        mDealsKeeper.getValue().setDeals(deals);
        mDealsKeeper.setValue(mDealsKeeper.getValue());
    }

    public MutableLiveData<DealsKeeper> getDealsKeeper(){
        return mDealsKeeper;
    }

    public void serializeDealsKeeper(){
        Log.d(TAG, " ViewModel Serialize action");
        mDealsKeeper.getValue().serializeDeals();
    }

    public void deserializeDealsKeeper(){
        Log.d(TAG, "ViewModel Deserialize action");
        List<Deal> deals = mDealsKeeper.getValue().deserializeDeals();
        mDealsKeeper.getValue().setDeals(deals);
        if (deals == null){
            Log.e(TAG, "deals == null");
        } else {
            mDealsKeeper.setValue(mDealsKeeper.getValue());
        }
    }

}