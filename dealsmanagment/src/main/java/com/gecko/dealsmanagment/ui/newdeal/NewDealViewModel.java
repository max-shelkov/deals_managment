package com.gecko.dealsmanagment.ui.newdeal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gecko.dealsmanagment.Deal;
import com.gecko.dealsmanagment.DealsKeeper;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class NewDealViewModel extends ViewModel {


//    private MutableLiveData<DealsKeeper> mDealsKeeper;
    private DealsKeeper mDealsKeeper;
    private MutableLiveData<Deal> mNewDeal;

    public NewDealViewModel() {

//        mDealsKeeper = new MutableLiveData<>();
//        mDealsKeeper.setValue(new DealsKeeper());

        mDealsKeeper = new DealsKeeper();

        mNewDeal = new MutableLiveData<>();
        mNewDeal.setValue(new Deal());

    }

    public MutableLiveData<Deal> getNewDeal(){
        return mNewDeal;
    }

//    public MutableLiveData<DealsKeeper> getDealsKeeper() {
//        return mDealsKeeper;
//    }

/*
    public void addDeal(Deal d){
        mDealsKeeper.getValue().addDeal(d);
        mDealsKeeper.getValue().serializeDeals();
        mDealsKeeper.setValue(mDealsKeeper.getValue());
    }
*/

    public String[] findManagersFromDeals(){
        return  mDealsKeeper.findMppFromDeals();
    }

    public String[] findProlongations(){
        return mDealsKeeper.findProlongations();
    }

    public String[] findDealsForOversell() {
        return mDealsKeeper.findDealsForOversell();
    }


    public Deal findDealByNameAndVolume(String name, int volume){
        return mDealsKeeper.findDealByNameAndVolume(name, volume);
    }


    public String findContractor(String name){
        return mDealsKeeper.findContractorByFirmName(name);
    }

    public String findOwner(String name){
        return mDealsKeeper.findOwnerByFirmName(name);
    }

    public void addNewDealToKeeper(){
        mDealsKeeper.addDeal(mNewDeal.getValue());
        mDealsKeeper.sortDeals();
        mDealsKeeper.serializeDeals();
//        mDealsKeeper.setValue(mDealsKeeper.getValue());
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

    public void setOwner(String s){
        mNewDeal.getValue().setOwner(s);
        mNewDeal.setValue(mNewDeal.getValue());
    }

    public void setSumToPay(int sum){
        mNewDeal.getValue().setToPay(sum);
        mNewDeal.setValue(mNewDeal.getValue());
    }

    public void setPaymentDate(Calendar date){
        mNewDeal.getValue().setPayPlanDate(date);
        mNewDeal.setValue(mNewDeal.getValue());
    }

    public void setType(String type){
        mNewDeal.getValue().setType(type);
        mNewDeal.setValue(mNewDeal.getValue());
    }

    public void setStatus(String status){
        mNewDeal.getValue().setStatus(status);
        mNewDeal.setValue(mNewDeal.getValue());
    }

    public void setProlongationDeal(Deal d){
        mNewDeal.getValue().setProlongationDeal(d);
        mNewDeal.setValue(mNewDeal.getValue());
    }

    public String getProlongationDealName(){
        return mNewDeal.getValue().getProlongationDeal().getName();
    }

    public String getProlongationDealContractor(){
        return mNewDeal.getValue().getProlongationDeal().getContractor();
    }

    public String[] getDealsNames(){
        return mDealsKeeper.getNames();
    }

    public String[] getPps3Names(){
        return mDealsKeeper.getPps3Names();
    }

}