package com.gecko.dealsmanagment;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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


    private UUID mId;           //+
    private String mOwner;      //+
    private String mName;       //
    private String mContractor; //
    private String mStatus;     //
    private float mAmount;      //
    private float mPriceVolume; //
    private float mRealVolume;  //
    private float mDiscount;    //
    private float mBalance;     //
    private float mToPay;       //
    private Calendar mPayPlanDate; //
    private float mPaid;        //
    private Calendar mPayRealDate;  //
    private Calendar mStartMonth;   //
    private Calendar mFinishMonth;  //
    private short mDuration;    //
    private boolean mCleared;


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
        mFinishMonth = mStartMonth;
        mFinishMonth.add(Calendar.MONTH, duration);
        mDiscount = 100 - 100*mRealVolume/mPriceVolume;
        mAmount = mRealVolume * duration;
        mCleared = false;

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
    }
}
