package com.gecko.dealsmanagment.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gecko.dealsmanagment.DealsKeeper;

public class DashboardViewModel extends ViewModel {


    private DealsKeeper mDealsKeeper;

    private MutableLiveData<Integer> mCurrentDealCount;
    private MutableLiveData<Integer> mCurrentPriceVolume;
    private MutableLiveData<Integer> mCurrentRealVolume;
    private MutableLiveData<Integer> mProlongationPriceVolume;
    private MutableLiveData<Integer> mProlongationRealVolume;

    private MutableLiveData<Integer> mNextDealCount;
    private MutableLiveData<Integer> mNextPriceVolume;
    private MutableLiveData<Integer> mNextRealVolume;

    private MutableLiveData<Integer> mNewDealsCount;
    private MutableLiveData<Integer> mNewDealsPriceVolume;
    private MutableLiveData<Integer> mNewDealsRealVolume;



    public DashboardViewModel() {
        mDealsKeeper = new DealsKeeper();

        mCurrentDealCount = new MutableLiveData<>();
        mCurrentDealCount.setValue(mDealsKeeper.getUniqueClientsCount());

        mCurrentPriceVolume = new MutableLiveData<>();
        mCurrentPriceVolume.setValue(mDealsKeeper.getCurrentVolumePrice());
        mCurrentRealVolume = new MutableLiveData<>();
        mCurrentRealVolume.setValue(mDealsKeeper.getCurrentVolumeReal());
        mProlongationPriceVolume = new MutableLiveData<>();
        mProlongationPriceVolume.setValue(mDealsKeeper.getProlongationVolumePrice());
        mProlongationRealVolume = new MutableLiveData<>();
        mProlongationRealVolume.setValue(mDealsKeeper.getProlongationVolumeReal());

        mNextDealCount = new MutableLiveData<>();
        mNextDealCount.setValue(mDealsKeeper.getNextMonthUniqueClientsCount());
        mNextPriceVolume = new MutableLiveData<>();
        mNextPriceVolume.setValue(mDealsKeeper.getNextVolumePrice());
        mNextRealVolume = new MutableLiveData<>();
        mNextRealVolume.setValue(mDealsKeeper.getNextVolumeReal());

        mNewDealsCount = new MutableLiveData<>();
        mNewDealsCount.setValue(mDealsKeeper.getNewDealsCount());
        mNewDealsPriceVolume  = new MutableLiveData<>();
        mNewDealsPriceVolume.setValue(mDealsKeeper.getNewDealsPriceVolume());
        mNewDealsRealVolume  = new MutableLiveData<>();
        mNewDealsRealVolume.setValue(mDealsKeeper.getNewDealsRealVolume());

    }

/*
    public void reloadDealsKeeper(){
        mDealsKeeper = new DealsKeeper();
    }
*/

    public MutableLiveData<Integer> getCurrentDealCount() {
        return mCurrentDealCount;
    }

    public MutableLiveData<Integer> getCurrentPriceVolume() {
        return mCurrentPriceVolume;
    }

    public MutableLiveData<Integer> getCurrentRealVolume() {
        return mCurrentRealVolume;
    }

    public MutableLiveData<Integer> getProlongationPriceVolume() {
        return mProlongationPriceVolume;
    }

    public MutableLiveData<Integer> getProlongationRealVolume() {
        return mProlongationRealVolume;
    }

    public MutableLiveData<Integer> getNextDealCount() {
        return mNextDealCount;
    }

    public MutableLiveData<Integer> getNextPriceVolume() {
        return mNextPriceVolume;
    }

    public MutableLiveData<Integer> getNextRealVolume() {
        return mNextRealVolume;
    }

    public MutableLiveData<Integer> getNewDealsCount() {
        return mNewDealsCount;
    }

    public MutableLiveData<Integer> getNewDealsPriceVolume() {
        return mNewDealsPriceVolume;
    }

    public MutableLiveData<Integer> getNewDealsRealVolume() {
        return mNewDealsRealVolume;
    }


}