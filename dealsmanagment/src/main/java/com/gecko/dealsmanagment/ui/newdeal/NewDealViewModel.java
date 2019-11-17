package com.gecko.dealsmanagment.ui.newdeal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gecko.dealsmanagment.Deal;
import com.gecko.dealsmanagment.DealsKeeper;

import java.util.Calendar;

public class NewDealViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<DealsKeeper> mDealsKeeper;
    private MutableLiveData<Deal> mNewDeal;

    public NewDealViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");

        mDealsKeeper = new MutableLiveData<>();
        mDealsKeeper.setValue(new DealsKeeper());

        mNewDeal = new MutableLiveData<>();
        mNewDeal.setValue(new Deal());

    }

    public MutableLiveData<Deal> getNewDeal(){
        return mNewDeal;
    }

    public MutableLiveData<DealsKeeper> getDealsKeeper() {
        return mDealsKeeper;
    }

    public LiveData<String> getText() {
        return mText;
    }



    public void addDeal(Deal d){
        mDealsKeeper.getValue().addDeal(d);
        mDealsKeeper.getValue().serializeDeals();
        mDealsKeeper.setValue(mDealsKeeper.getValue());
    }

    public String findContractor(String name){
        return mDealsKeeper.getValue().findContractorByFirmName(name);
    }

    public void addNewDealToKeeper(){
        mDealsKeeper.getValue().addDeal(mNewDeal.getValue());
        mDealsKeeper.getValue().serializeDeals();
        mDealsKeeper.setValue(mDealsKeeper.getValue());
    }

    public void setStartDate(Calendar date){
        mNewDeal.getValue().setStartMonth(date);
        mNewDeal.setValue(mNewDeal.getValue());
    }

    public void setContractor(String contractor){
        if (contractor != null) {
            mNewDeal.getValue().setContractor(contractor);
            mNewDeal.setValue(mNewDeal.getValue());
        }
    }

    public void setFirmName(String name){
        if (name != null) {
            mNewDeal.getValue().setName(name);
            mNewDeal.setValue(mNewDeal.getValue());
        }
    }

    public void setDuration(short t){
        if ( t>=0 &&t <= 12) {
            mNewDeal.getValue().setDuration(t);
            mNewDeal.setValue(mNewDeal.getValue());
        }
    }

    public void setPriceVolume(int v){
        mNewDeal.getValue().setPriceVolume(v);
        mNewDeal.setValue(mNewDeal.getValue());
    }

    public void setRealVolume(int v){
        mNewDeal.getValue().setRealVolume(v);
        mNewDeal.setValue(mNewDeal.getValue());
    }

}