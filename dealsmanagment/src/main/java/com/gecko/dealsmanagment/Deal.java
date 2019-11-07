package com.gecko.dealsmanagment;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

public class Deal implements Serializable {

    public static final String DEAL_STATUS_NEW = "new_deal";
    public static final String DEAL_STATUS_CURRENT = "current_deal";
    public static final String DEAL_STATUS_BREAK = "break_deal";
    public static final String DEAL_STATUS_REGIONAL_OUT = "regional_out_deal";
    public static final String DEAL_STATUS_REGIONAL_IN = "regional_in_deal";
    public static final String DEAL_STATUS_OVERSELL = "oversell_deal";
    public static final String DEAL_STATUS_PROLONGATION = "prolongation_deal";


    private UUID mId;           //1
    private String mOwner;      //2
    private String mName;       //3
    private String mContractor; //4
    private String mStatus;     //5
    private float mAmount;      //6
    private float mPriceVolume; //7
    private float mRealVolume;  //8
    private float mDiscount;    //9
    private float mBalance;     //10
    private float mToPay;       //11
    private Calendar mPayPlanDate; //12
    private float mPaid;        //13
    private Calendar mPayRealDate;  //14
    private Calendar mStartMonth;   //15
    private Calendar mFinishMonth;  //16
    private short mDuration;    //17
//    private boolean mCleared;


/*
    public Deal(String owner, String name, String contractor, String status,
                float amount, float discount, int startMonth, short duration) {
        mId = UUID.randomUUID();
        mOwner = owner;
        mName = name;
        mContractor = contractor;
        mStatus = status;
        mAmount = amount;
        mDiscount = discount;
        mStartMonth = startMonth;
        mDuration = duration;

        mBalance = 0;
        mRealVolume = mAmount/mDuration;
        mPriceVolume = mRealVolume*100/(100-mDiscount);
        mToPay = 0;
        mPaid = 0;
        mPayPlanDate = null;
        mPayRealDate = null;
        mFinishMonth = mStartMonth+mDuration-1;
    }
*/

    public Deal(String owner, String name, String contractor, String status,
                float priceVolume, float realVolume, Calendar startMonth, short duration) {
        mId = UUID.randomUUID();
        mOwner = owner;
        mName = name;
        mContractor = contractor;
        mStatus = status;
        mPriceVolume = priceVolume;
        mRealVolume = realVolume;
        mStartMonth = startMonth;
        mDuration = duration;

        mBalance = 0;
        mToPay = 0;
        mPayPlanDate = null;
        mPaid = 0;
        mPayRealDate = null;
        mFinishMonth = Calendar.getInstance();
        mFinishMonth.set(mStartMonth.get(Calendar.YEAR), mStartMonth.get(Calendar.MONTH), mStartMonth.get(Calendar.DAY_OF_MONTH));
        mFinishMonth.add(Calendar.MONTH, duration-1);
        mDiscount = 100 - 100*mRealVolume/mPriceVolume;
        mAmount = mRealVolume * duration;


    }

    public UUID getId() {
        return mId;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Id = " +mId;
        return s;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getContractor() {
        return mContractor;
    }

    public void setToPay(float toPay) {
        mToPay = toPay;
    }

    public float getToPay() {
        return mToPay;
    }

    public float getPriceVolume() {
        return mPriceVolume;
    }

    public float getRealVolume() {
        return mRealVolume;
    }

    public float getBalance() {
        return mBalance;
    }

    public void setBalance(float balance) {
        mBalance = balance;
    }

    public Calendar getPayPlanDate() {
        return mPayPlanDate;
    }

    public void setPayPlanDate(Calendar payPlanDate) {
        mPayPlanDate = payPlanDate;
    }

    public float getPaid() {
        return mPaid;
    }

    public void setPaid(float paid) {
        mPaid = paid;
    }

    public Calendar getPayRealDate() {
        return mPayRealDate;
    }

    public void setPayRealDate(Calendar payRealDate) {
        mPayRealDate = payRealDate;
    }

    public short getDuration() {
        return mDuration;
    }

    public void setDuration(short duration) {
        mDuration = duration;
        mFinishMonth = mStartMonth;
        mFinishMonth.add(Calendar.MONTH, duration);
    }

    public Calendar getStartMonth() {
        return mStartMonth;
    }

    public void setStartMonth(Calendar startMonth) {
        mStartMonth = startMonth;
    }

    public Calendar getFinishMonth() {
        return mFinishMonth;
    }

    public void setFinishMonth(Calendar finishMonth) {
        mFinishMonth = finishMonth;
    }


}
