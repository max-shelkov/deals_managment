package com.gecko.dealsmanagment.ui.deals;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.gecko.dealsmanagment.Deal;
import com.gecko.dealsmanagment.InputFromListDialogToActivity;
import com.gecko.dealsmanagment.InputFromListDialogToFragment;
import com.gecko.dealsmanagment.R;

import java.util.Calendar;

import static com.gecko.dealsmanagment.GeckoUtils.*;

public class DealDetailsActivity extends AppCompatActivity implements SetMoneyDialogFragment.SetMoneyDialogFragmentListener,
        EditDealDialogFragment.EditDealDialogFragmentListener, InputFromListDialogToActivity.InputFromListDialogToActivityListener {

    private static final String TAG = "myLog";
    private static final String DIALOG_PAY_PLAN_TAG = "money_plan_dialog";
    private static final String DIALOG_PAY_FACT_TAG = "money_fact_dialog";
    private static final String DIALOG_NAME_TAG = "name_dialog";
    private static final String DIALOG_CONTRACTOR_TAG = "contractor_dialog";
    private static final String DIALOG_OWNER_TAG = "owner_dialog";
    private static final String DIALOG_TYPE_TAG = "type_dialog";
    private static final String DIALOG_STATUS_TAG = "status_dialog";

    private TextView mTypeTextView;
    private TextView mStatusTextView;
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

    private DialogFragment mSetMoneyDialogFragment;
    private DialogFragment mEditDealDialogFragment;
    private DialogFragment mInputFromListDialog;

    private Deal mDeal;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details);

        mDeal = (Deal) getIntent().getSerializableExtra("deal");
        mSetMoneyDialogFragment = new SetMoneyDialogFragment();
        mEditDealDialogFragment = new EditDealDialogFragment();
        mInputFromListDialog = new InputFromListDialogToActivity();

        mTypeTextView = findViewById(R.id.type_text_view_deal_details);
        mStatusTextView = findViewById(R.id.status_text_view_deal_details);
        mCompanyNameTextView = findViewById(R.id.company_name_text_view);
        mContractorTextView = findViewById(R.id.contractor_text_view_deal_details);
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

                mSetMoneyDialogFragment.setArguments(bundle);
                mSetMoneyDialogFragment.show(getSupportFragmentManager(), DIALOG_PAY_PLAN_TAG);
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
                mSetMoneyDialogFragment.setArguments(bundle);
                mSetMoneyDialogFragment.show(getSupportFragmentManager(), DIALOG_PAY_FACT_TAG);
            }
        });
        mToPayDateTextView = findViewById(R.id.to_pay_date_text_view);
        mPaidDateTextView = findViewById(R.id.paid_date_text_view);
        mOwnerTextView = findViewById(R.id.owner_text_view);
        mButtonOk = findViewById(R.id.button_ok);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mDeal.setName("x"+mDeal.getName());
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
    public void onDialogFragmentMoneyDataEntered(int money, Calendar date, String tag) {
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
        mTypeTextView.setText(mDeal.getType());
        mStatusTextView.setText(mDeal.getStatus());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deal_details_options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_deal_options_menu_item:
                editDeal();
                break;
        }
        return true;
    }

    private void editDeal() {

        mCompanyNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogFragmentToEditData("название фирмы", mDeal.getName(), DIALOG_NAME_TAG);
            }
        });

        mContractorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogFragmentToEditData("юр.лицо", mDeal.getContractor(), DIALOG_CONTRACTOR_TAG);
            }
        });

        mOwnerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogFragmentToEditData("куратор сделки", mDeal.getOwner(), DIALOG_OWNER_TAG);

            }
        });

        mTypeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogFragmentToChooseFromList("тип сделки", Deal.DEAL_TYPES, DIALOG_TYPE_TAG);
            }
        });

        mStatusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogFragmentToChooseFromList("статус сделки", Deal.DEAL_STATUSES, DIALOG_STATUS_TAG);
            }
        });
    }

    private void startDialogFragmentToChooseFromList(String title, String[] array, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putSerializable("array", array);
        bundle.putString("tag", tag);
        mInputFromListDialog.setArguments(bundle);
        mInputFromListDialog.show(getSupportFragmentManager(), tag);
    }

    private void startDialogFragmentToEditData(String title, String currentData, String tag){
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("data", currentData);
        bundle.putString("tag", tag);
        mEditDealDialogFragment.setArguments(bundle);
        mEditDealDialogFragment.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void onEditDealDialogFragmentDataEntered(String data, String tag) {
        Toast.makeText(this, "name = "+ data + " tag = " + tag, Toast.LENGTH_SHORT).show();
        switch (tag){
            case DIALOG_NAME_TAG:
                mDeal.setName(data);
                break;
            case DIALOG_CONTRACTOR_TAG:
                mDeal.setContractor(data);
                break;
            case DIALOG_OWNER_TAG:
                mDeal.setOwner(data);
                break;
        }
        showDeal();
    }


    @Override
    public void onListItemChosen(String data, String tag) {
        switch (tag){
            case DIALOG_TYPE_TAG:
                mDeal.setType(data);
                break;
            case DIALOG_STATUS_TAG:
                mDeal.setStatus(data);
        }
        showDeal();
    }
}