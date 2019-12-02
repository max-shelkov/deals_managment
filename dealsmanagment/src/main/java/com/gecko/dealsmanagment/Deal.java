package com.gecko.dealsmanagment;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

public class Deal implements Serializable, Comparable<Deal>{

    public static final String DEAL_STATUS_NEW = "Новый";
    public static final String DEAL_STATUS_CURRENT = "Текущий";
    public static final String DEAL_STATUS_BREAK = "Расторжение";
    public static final String DEAL_STATUS_OVERSELL = "Допродажа";
    public static final String DEAL_STATUS_PROLONGATION = "Продление";
    public static final String DEAL_STATUS_PREPROLONGATION = "ППС3";
    public static final String DEAL_STATUS_ARCHIVE = "Архив";

    public static final String[] DEAL_STATUSES =
            {DEAL_STATUS_NEW, DEAL_STATUS_CURRENT, DEAL_STATUS_BREAK,
            DEAL_STATUS_OVERSELL, DEAL_STATUS_PROLONGATION, DEAL_STATUS_PREPROLONGATION};

    public static final String DEAL_TYPE_SELL = "Продажи";
    public static final String DEAL_TYPE_EXCHANGE = "Бартер";
    public static final String DEAL_TYPE_REGIONAL_OUT = "Исх.Рег";
    public static final String DEAL_TYPE_REGIONAL_IN = "Вх.Рег";

    public static final String[] DEAL_TYPES =
            {DEAL_TYPE_SELL, DEAL_TYPE_EXCHANGE,
            DEAL_TYPE_REGIONAL_OUT, DEAL_TYPE_REGIONAL_IN, };

    private UUID mId;           //1
    private String mOwner;      //2
    private String mName;       //3
    private String mContractor; //4
    private String mStatus;     //5
    private String mType;
    private int mAmount;      //6
    private int mPriceVolume; //7
    private int mRealVolume;  //8
    private float mDiscount;    //9
    private int mBalance;     //10
    private int mToPay;       //11
    private Calendar mPayPlanDate; //12
    private int mPaid;        //13
    private Calendar mPayRealDate;  //14
    private Calendar mStartMonth;   //15
    private Calendar mFinishMonth;  //16
    private int mDuration;    //17
    private Deal mProlongationDeal;
    private boolean prolonged;
    private String mNote;


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

    public Deal(){
        mId = UUID.randomUUID();

        mStartMonth = Calendar.getInstance();
        mStartMonth.add(Calendar.MONTH, 1);
        mStartMonth.set(Calendar.DAY_OF_MONTH,1);

        mType = DEAL_TYPE_SELL;
        mStatus = DEAL_STATUS_NEW;
    }

    public Deal(String owner, String name, String contractor,
                int priceVolume, int realVolume, Calendar startMonth, int duration) {
        mId = UUID.randomUUID();
        mOwner = owner;
        mName = name;
        mContractor = contractor;
        mType = DEAL_TYPE_SELL;
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
        mStatus = defineStatus();

    }

    public UUID getId() {
        return mId;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "Id = " +mId+ " name = " + mName;
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

    public void setToPay(int toPay) {
        mToPay = toPay;
    }

    public int getToPay() {
        return mToPay;
    }

    public int getPriceVolume() {
        return mPriceVolume;
    }

    public int getRealVolume() {
        return mRealVolume;
    }

    public int getBalance() {
        return mBalance;
    }

    public void setBalance(int balance) {
        mBalance = balance;
    }

    public Calendar getPayPlanDate() {
        return mPayPlanDate;
    }

    public void setPayPlanDate(Calendar payPlanDate) {
        mPayPlanDate = payPlanDate;
    }

    public int getPaid() {
        return mPaid;
    }

    public void setPaid(int paid) {
        mPaid = paid;
    }

    public Calendar getPayRealDate() {
        return mPayRealDate;
    }

    public void setPayRealDate(Calendar payRealDate) {
        mPayRealDate = payRealDate;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(short duration) {
        mDuration = duration;
        mFinishMonth = calculateFinishMonth(mStartMonth, mDuration);
        mAmount = mRealVolume * mDuration;
        mStatus = defineStatus();
    }

    public Calendar getStartMonth() {
        return mStartMonth;
    }

    public void setStartMonth(Calendar startMonth) {
        mStartMonth = startMonth;
        mFinishMonth = calculateFinishMonth(mStartMonth, mDuration);
        mAmount = mRealVolume * mDuration;
        mStatus = defineStatus();
    }

    private Calendar calculateFinishMonth(Calendar startMonth, int duration){
        Calendar finishMonth;
        if(startMonth!=null && duration>0){
            finishMonth = Calendar.getInstance();
            finishMonth.set(startMonth.get(Calendar.YEAR), startMonth.get(Calendar.MONTH), startMonth.get(Calendar.DAY_OF_MONTH));
            finishMonth.add(Calendar.MONTH, duration-1);
        } else {
            finishMonth = null;
        }
        return finishMonth;
    }

    public Calendar getFinishMonth() {
        return mFinishMonth;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public boolean isProlonged(){
        if(mProlongationDeal == null){
            return false;
        } else {
            return true;
        }
    }

    public Deal getProlongationDeal() {
        return mProlongationDeal;
    }

    public void setProlongationDeal(Deal prolongationDeal) {
        mProlongationDeal = prolongationDeal;
    }

    public int getAmount() {
        return mAmount;
    }

    public int monthsLeft(){
        int x = (mFinishMonth.compareTo(mStartMonth))/30;

        return x;
    }
    public void setContractor(String contractor) {
        mContractor = contractor;
    }

    public void setPriceVolume(int priceVolume) {
        mPriceVolume = priceVolume;
    }

    public void setRealVolume(int realVolume) {
        mRealVolume = realVolume;
        if (mPriceVolume>0) mDiscount = 100 - 100*mRealVolume / mPriceVolume;
        if (mDuration>0) mAmount = mRealVolume * mDuration;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public void defineAndSetStatus(){
        setStatus(defineStatus());
    }

    @Override
    public int compareTo(Deal d) {
        return this.getName().compareTo(d.getName());
    }

    private String defineStatus(){
        int monthsLeft = GeckoUtils.monthsBetween(Calendar.getInstance(), mFinishMonth);
        switch (monthsLeft){
            case 1:
                return DEAL_STATUS_PROLONGATION;
            case 2:
                return DEAL_STATUS_PREPROLONGATION;
            default:
                if (monthsLeft<0) {
                    return DEAL_STATUS_ARCHIVE;
                } else {
                    return DEAL_STATUS_CURRENT;
                }
        }

    }
}
