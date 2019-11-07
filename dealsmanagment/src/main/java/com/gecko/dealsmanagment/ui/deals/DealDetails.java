package com.gecko.dealsmanagment.ui.deals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gecko.dealsmanagment.Deal;
import com.gecko.dealsmanagment.R;

import static com.gecko.dealsmanagment.GeckoUtils.*;

public class DealDetails extends AppCompatActivity {

    public static final String TAG = "myLog";

    TextView mCompanyNameTextView;
    TextView mContractorTextView;
    TextView mStartDateTextView;
    TextView mFinishDateTextView;
    TextView mVolumePriceTextView;
    TextView mVolumeRealTextView;
    TextView mToPayTextView;
    TextView mPaidTextView;
    TextView mToPayDateTextView;
    TextView mPaidDateTextView;
    Button mButtonOk;

    Deal mDeal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details);

        mDeal = (Deal) getIntent().getSerializableExtra("deal");

        mCompanyNameTextView = findViewById(R.id.company_name_text_view);
        mCompanyNameTextView.setText(mDeal.getName());

        mContractorTextView = findViewById(R.id.contractor_text_view);
        String contractor = "[" + mDeal.getContractor()+ "]";
        mContractorTextView.setText(contractor);

        mStartDateTextView = findViewById(R.id.start_date_text_view);
        mStartDateTextView.setText(monthCalendarToString(mDeal.getStartMonth()));

        mFinishDateTextView = findViewById(R.id.finish_date_text_view);
        mFinishDateTextView.setText(monthCalendarToString(mDeal.getFinishMonth()));

        mVolumePriceTextView = findViewById(R.id.v_price_text_view);
        mVolumePriceTextView.setText("Vпрайс " + mDeal.getPriceVolume() + " руб.");

        mVolumeRealTextView = findViewById(R.id.v_real_text_view);
        mVolumeRealTextView.setText("Vфакт " + mDeal.getRealVolume()+ " руб.");

        mToPayTextView = findViewById(R.id.to_pay_text_view);
        mToPayTextView.setText(mDeal.getToPay()+"руб.");

        mPaidTextView = findViewById(R.id.paid_text_view);
        mPaidTextView.setText(mDeal.getPaid()+"руб.");

        mToPayDateTextView = findViewById(R.id.to_pay_date_text_view);
        mToPayDateTextView.setText(dateCalendarToString(mDeal.getPayPlanDate()));

        mPaidDateTextView = findViewById(R.id.paid_date_text_view);
        mPaidDateTextView.setText(dateCalendarToString(mDeal.getPayRealDate()));

        mButtonOk = findViewById(R.id.button_ok);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeal.setName("x"+mDeal.getName());
                prepareDataForCallingActivity();
                finish();
            }
        });
    }

    private void prepareDataForCallingActivity(){
        Intent intent = new Intent();
        intent.putExtra("edited_deal", mDeal);
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause action");
    }


}
