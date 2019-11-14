package com.gecko.dealsmanagment.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gecko.dealsmanagment.DealsKeeper;

public class DashboardViewModel extends ViewModel {


    private MutableLiveData<DealsKeeper> mDealsKeeper;

    public DashboardViewModel() {

        mDealsKeeper = new MutableLiveData<>();
        mDealsKeeper.setValue(new DealsKeeper());


    }

    public int getCurrentVolumePrice(){
        return mDealsKeeper.getValue().getCurrentVolumePrice();
    }



    public MutableLiveData<DealsKeeper> getDealsKeeper(){
        return mDealsKeeper;
    }
}