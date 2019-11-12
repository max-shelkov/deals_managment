package com.gecko.dealsmanagment.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gecko.dealsmanagment.DealsKeeper;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<DealsKeeper> mDealsKeeper;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");

        mDealsKeeper = new MutableLiveData<>();
        mDealsKeeper.setValue(new DealsKeeper());


    }

    public int getCurrentVolumePrice(){
        return mDealsKeeper.getValue().getCurrentVolumePrice();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<DealsKeeper> getDealsKeeper(){
        return mDealsKeeper;
    }
}