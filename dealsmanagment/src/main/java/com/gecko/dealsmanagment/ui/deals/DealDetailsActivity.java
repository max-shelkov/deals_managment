package com.gecko.dealsmanagment.ui.deals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.gecko.dealsmanagment.Deal;
import com.gecko.dealsmanagment.R;

import java.util.Calendar;

import static com.gecko.dealsmanagment.GeckoUtils.*;

public class DealDetailsActivity extends AppCompatActivity implements SetMoneyDialogFragment.SetMoneyDialogFragmentListener {

    private static final String TAG = "myLog";
    private static final String DIALOG_PAY_PLAN_TAG = "money_plan_dialog";
    private static final String DIALOG_PAY_FACT_TAG = "money_fact_dialog";

    private TextView mCompanyNameTextView;
    private TextView mContractorTextView;
    private TextView mAmountTextView;
    private ProgressBar mPeriodProgressBar;
    private TextView mStartDateTextView;
    private TextView mFinishDateTextView;
    private TextView mVolumePriceTextView;
    private TextView mVolumeRealTextView;
    private TextView mToPayTextView;
    private TextView mPaidTextView;
    private TextView mToPayDateTextView;
    private TextView mPaidDateTextView;
    private TextView mOwnerTextView;
    private Button   mButtonOk;

    private DialogFragment mDialogFragment;

    private Deal mDeal;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details);

        mDeal = (Deal) getIntent().getSerializableExtra("deal");
        mDialogFragment = new SetMoneyDialogFragment();

        mCompanyNameTextView = findViewById(R.id.company_name_text_view);
        mContractorTextView = findViewById(R.id.contractor_text_view);
        mAmountTextView = findViewById(R.id.amount_text_view);
        mPeriodProgressBar = findViewById(R.id.period_progress_bar);
        mPeriodProgressBar.setMax(mDeal.getDuration());
        mStartDateTextView = findViewById(R.id.start_date_text_view);
        mFinishDateTextView = findViewById(R.id.finish_date_text_view);
        mVolumePriceTextView = findViewById(R.id.v_price_text_view);
        mVolumeRealTextView = findViewById(R.id.v_real_text_view);
        mToPayTextView = findViewById(R.id.to_pay_text_view);
        mToPayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("tag", DIALOG_PAY_PLAN_TAG);
                bundle.putInt("money", mDeal.getToPay());
                bundle.putSerializable("date", mDeal.getPayPlanDate());

                mDialogFragment.setArguments(bundle);
                mDialogFragment.show(getSupportFragmentManager(), DIALOG_PAY_PLAN_TAG);
            }
        });
        mPaidTextView = findViewById(R.id.paid_text_view);
        mPaidTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("tag", DIALOG_PAY_FACT_TAG);
                bundle.putInt("money", mDeal.getPaid());
                bundle.putSerializable("date", mDeal.getPayRealDate());
                mDialogFragment.setArguments(bundle);
                mDialogFragment.show(getSupportFragmentManager(), DIALOG_PAY_FACT_TAG);
            }
        });
        mToPayDateTextView = findViewById(R.id.to_pay_date_text_view);
        mPaidDateTextView = findViewById(R.id.paid_date_text_view);
        mOwnerTextView = findViewById(R.id.owner_text_view);
        mButtonOk = findViewById(R.id.button_ok);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeal.setName("x"+mDeal.getName());
                prepareDataForCallingActivity();
                finish();
            }
        });
        showDeal();
    }

    private void prepareDataForCallingActivity(){
        Intent intent = new Intent();
        intent.putExtra("edited_deal", mDeal);
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onDialogFragmentDataEntered(int money, Calendar date, String tag) {
        switch (tag){
            case DIALOG_PAY_PLAN_TAG:
                mDeal.setToPay(money);
                mDeal.setPayPlanDate(date);
                break;
            case DIALOG_PAY_FACT_TAG:
                mDeal.setPaid(money);
                mDeal.setPayRealDate(date);
                break;
        }
        showDeal();
    }

    private void showDeal(){
        mCompanyNameTextView.setText(mDeal.getName());
        String contractor = "[" + mDeal.getContractor()+ "]";
        mContractorTextView.setText(contractor);
        mAmountTextView.setText("сумма сделки "+formattedInt(mDeal.getAmount())+ " руб.");
        mStartDateTextView.setText(monthCalendarToString(mDeal.getStartMonth()));
        mFinishDateTextView.setText(monthCalendarToString(mDeal.getFinishMonth()));
        mPeriodProgressBar.setProgress(monthsBetween(mDeal.getStartMonth(), Calendar.getInstance()));
        mVolumePriceTextView.setText("Vп " + formattedInt(mDeal.getPriceVolume()) + " руб.");
        mVolumeRealTextView.setText("Vф " + formattedInt(mDeal.getRealVolume())+ " руб.");
        mToPayTextView = findViewById(R.id.to_pay_text_view);
        mToPayTextView.setText(formattedInt(mDeal.getToPay())+" руб.");
        mPaidTextView.setText(formattedInt(mDeal.getPaid())+" руб.");
        mToPayDateTextView.setText(dateCalendarToString(mDeal.getPayPlanDate()));
        mPaidDateTextView.setText(dateCalendarToString(mDeal.getPayRealDate()));
        mOwnerTextView.setText(mDeal.getOwner());
    }
}