package com.gecko.dealsmanagment.ui.newdeal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gecko.dealsmanagment.Deal;
import com.gecko.dealsmanagment.GeckoUtils;
import com.gecko.dealsmanagment.InputDataDialog;
import com.gecko.dealsmanagment.MainActivity;
import com.gecko.dealsmanagment.R;
import com.gecko.dealsmanagment.InputFromListDialogToFragment;

import java.util.Calendar;

import static com.gecko.dealsmanagment.GeckoUtils.*;

public class NewDealFragment extends Fragment {

    private static final int REQUEST_CODE_DURATION = 100;
    private static final int REQUEST_CODE_VOLUME_PRICE = 101;
    private static final int REQUEST_CODE_VOLUME_REAL = 102;
    private static final int REQUEST_CODE_OWNER = 103;
    private static final int REQUEST_CODE_SUM_TO_PAY = 104;
    private static final int REQUEST_CODE_DEAL_TYPE = 105;
    private static final int REQUEST_CODE_DEAL_STATUS = 106;
    private static final int REQUEST_CODE_DEAL_PROLONGATION = 107;
    private static final int REQUEST_CODE_OVERSELL = 108;
    private static final int REQUEST_CODE_DEAL_PRE_PROLONGATION = 109;
    private static final int REQUEST_CODE_DEAL_BREAK = 110;

    private Context mMainActivityCtx;
    private Fragment mFragmentCtx;

    private LinearLayout mRootLinearLayout;
    private TextView mTypeTextView;
    private TextView mStatusTextView;
    private AutoCompleteTextView mNameTextView;
    private ToggleButton mNameToggleButton;
    private AutoCompleteTextView mContractorTextView;
    private ToggleButton mContractorToggleButton;
    private TextView mStartDateTextView;
    private TextView mFinishDateTextView;
    private TextView mDurationTextView;
    private TextView mVolumePriceTextView;
    private TextView mVolumeRealTextView;
    private TextView mSumToPayTextView;
    private TextView mPlanPaymentDateTextView;
    private TextView mOwnerTextView;
    private Button mOkButton;

    private DialogFragment mInputDataDialog;

    private NewDealViewModel mNewDealViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        mNewDealViewModel =
                ViewModelProviders.of(this).get(NewDealViewModel.class);

//        mNewDeal = new Deal();
        mMainActivityCtx = MainActivity.getAppContext();
        mFragmentCtx = this;
        mInputDataDialog = new InputDataDialog();

        View root = inflater.inflate(R.layout.fragment_new_deal, container, false);
        mRootLinearLayout = root.findViewById(R.id.root_linear_layout_new_deal);
        mTypeTextView = root.findViewById(R.id.type_new_deal_text_view);
        mTypeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                InputFromListDialogToFragment iod  = new InputFromListDialogToFragment();
                iod.setTargetFragment(mFragmentCtx, REQUEST_CODE_DEAL_TYPE);
                Bundle bundle = new Bundle();
                bundle.putString("title", "тип сделки");
                bundle.putSerializable("array", Deal.DEAL_TYPES);
                iod.setArguments(bundle);
                iod.show(manager, "not_used_tag");
            }
        });
        mStatusTextView = root.findViewById(R.id.status_new_deal_text_view);
        mStatusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                InputFromListDialogToFragment iod  = new InputFromListDialogToFragment();
                iod.setTargetFragment(mFragmentCtx, REQUEST_CODE_DEAL_STATUS);
                Bundle bundle = new Bundle();
                bundle.putString("title", "тип сделки");
                bundle.putSerializable("array", Deal.DEAL_STATUSES);
                iod.setArguments(bundle);
                iod.show(manager, "not_used_tag");
            }
        });
        mNameTextView = root.findViewById(R.id.name_text_view_new_deal);
        mNameToggleButton = root.findViewById(R.id.firm_name_toggle_button_new_deal);
        mNameToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNameTextView.setEnabled(isChecked);
                if (!isChecked){
                    String name = mNameTextView.getText().toString();
                    String contractor = mContractorTextView.getText().toString();
                    mNewDealViewModel.setFirmName(name);
                    mContractorTextView.setText(contractor);
                    mRootLinearLayout.requestFocus();
                }
            }
        });
        mContractorTextView = root.findViewById(R.id.contractor_text_view_deal_details);
        mContractorToggleButton = root.findViewById(R.id.contractor_toggle_button_new_deal);
        mContractorToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mContractorTextView.setEnabled(isChecked);
                if (!isChecked){
                    String name = mNameTextView.getText().toString();
                    String contractor = mContractorTextView.getText().toString();
                    mNewDealViewModel.setContractor(contractor);
                    mNameTextView.setText(name);
                    mRootLinearLayout.requestFocus();
                }
            }
        });
        mStartDateTextView = root.findViewById(R.id.start_date_new_deal_text_view);
        mStartDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        mDurationTextView = root.findViewById(R.id.duration_new_deal_text_view);
        mDurationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInputDataDialog("срок размещения", REQUEST_CODE_DURATION);
            }
        });

        mVolumePriceTextView = root.findViewById(R.id.volume_price_new_deal_text_view);
        mVolumePriceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInputDataDialog("прайсовый объем", REQUEST_CODE_VOLUME_PRICE);
            }
        });
        mVolumeRealTextView = root.findViewById(R.id.volume_real_new_deal_text_view);
        mVolumeRealTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInputDataDialog("фактический объем", REQUEST_CODE_VOLUME_REAL);
            }
        });
        mFinishDateTextView = root.findViewById(R.id.finish_date_new_deal_text_view);
        mSumToPayTextView = root.findViewById(R.id.to_pay_new_deal_text_view);
        mSumToPayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInputDataDialog("к оплате в текущем месяце", REQUEST_CODE_SUM_TO_PAY);
            }
        });
        mPlanPaymentDateTextView = root.findViewById(R.id.payment_date_plan_new_deal_text_view);
        mPlanPaymentDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        mOwnerTextView = root.findViewById(R.id.owner_new_deal_text_view);
        mOwnerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                InputFromListDialogToFragment iod  = new InputFromListDialogToFragment();
                iod.setTargetFragment(mFragmentCtx, REQUEST_CODE_OWNER);
                Bundle bundle = new Bundle();
                bundle.putString("title", "куратор сделки");
                bundle.putSerializable("array", mNewDealViewModel.findManagersFromDeals());
                iod.setArguments(bundle);
                iod.show(manager, "not_used_tag");
            }
        });

        mOkButton = root.findViewById(R.id.new_deal_ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEnteredData()){
                    mNewDealViewModel.addNewDealToKeeper();
                }

            }
        });




        mNameTextView.setThreshold(1);
        String[] names = mNewDealViewModel.getDealsNames();
        if(names != null) {
            mNameTextView.setAdapter(new ArrayAdapter<>(mMainActivityCtx, android.R.layout.simple_dropdown_item_1line, names));
        }
        mNameTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String firmName = mNameTextView.getText().toString();
                String contractor = mNewDealViewModel.findContractor(firmName);
                mNewDealViewModel.setFirmName(firmName);
                mNewDealViewModel.setContractor(contractor);
            }
        });

        mNewDealViewModel.getNewDeal().observe(this, new Observer<Deal>() {
            @Override
            public void onChanged(Deal deal) {
                showDeal(deal);
            }
        });

        if (MainActivity.getAppContext() == null){
            Log.d(TAG, "activity context = null");
        } else {
            Log.d(TAG, "activity context != null");
        }

        return root;
    }

    private void showDeal(Deal d) {
        Log.d(TAG, "showDeal called");

        if (d.getType()!=null){
            mTypeTextView.setText(d.getType());
        } else {
            mTypeTextView.setHint("deal's type");
        }

        if (d.getStatus()!=null){
            mStatusTextView.setText(d.getStatus());
        } else {
            mStatusTextView.setHint("deal's status");
        }

        if (d.getName() != null) {
            mNameTextView.setText(d.getName());
        } else {
            mNameTextView.setText("");
        }

        if (d.getContractor() != null) {
            mContractorTextView.setText(d.getContractor(), TextView.BufferType.EDITABLE);
        } else {
            mContractorTextView.setText("", TextView.BufferType.EDITABLE);
        }

        if (d.getStartMonth() != null){
            mStartDateTextView.setText(monthCalendarToString(d.getStartMonth()));
        } else {
            mStartDateTextView.setText("no date");
        }
        mDurationTextView.setText(""+d.getDuration());

        if (d.getFinishMonth() != null){
            mFinishDateTextView.setText(monthCalendarToString(d.getFinishMonth()));
        } else {
            mFinishDateTextView.setText("no date");
        }
        mSumToPayTextView.setText(formattedInt(d.getToPay()));
        mVolumePriceTextView.setText("Vp: " + formattedInt(d.getPriceVolume()));
        mVolumeRealTextView.setText("Vr: " + formattedInt(d.getRealVolume()));
        if(d.getPayPlanDate() != null){
            mPlanPaymentDateTextView.setText(dateCalendarToString(d.getPayPlanDate()));
        } else {
            mPlanPaymentDateTextView.setText("no date");
        }

        if (d.getOwner()==null || d.getOwner().equals("")){
            mOwnerTextView.setText("куратор не указан");
        } else {
            mOwnerTextView.setText(d.getOwner());
        }


    }

    private void setDate(View v){
        final View clickedView = v;
        DatePickerDialog.OnDateSetListener setDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar date = Calendar.getInstance();
                date.set(year, month, dayOfMonth);
                if (clickedView.getId() == mStartDateTextView.getId()) mNewDealViewModel.setStartDate(date);
                if (clickedView.getId() == mPlanPaymentDateTextView.getId()) mNewDealViewModel.setPaymentDate(date);
            }
        };

        Calendar openDate = Calendar.getInstance();
        if (clickedView.getId() == mStartDateTextView.getId()) {
            openDate.add(Calendar.MONTH,1);
            openDate.set(Calendar.DAY_OF_MONTH, 1);
        }

        new DatePickerDialog(getActivity(), setDateListener,
                openDate.get(Calendar.YEAR),
                openDate.get(Calendar.MONTH),
                openDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void chooseProlongationDeal(){
        FragmentManager manager = getFragmentManager();
        InputFromListDialogToFragment inputDialog  = new InputFromListDialogToFragment();
        inputDialog.setTargetFragment(mFragmentCtx, REQUEST_CODE_DEAL_PROLONGATION);
        Bundle bundle = new Bundle();
        bundle.putString("title", "продление сделки");
        bundle.putSerializable("array", mNewDealViewModel.findProlongations());
        inputDialog.setArguments(bundle);
        inputDialog.show(manager, "not_used_tag");
    }

    private void chooseOversellDeal(){
        FragmentManager manager = getFragmentManager();
        InputFromListDialogToFragment inputDialog  = new InputFromListDialogToFragment();
        inputDialog.setTargetFragment(mFragmentCtx, REQUEST_CODE_OVERSELL);
        Bundle bundle = new Bundle();
        bundle.putString("title", "допродажа");
        bundle.putSerializable("array", mNewDealViewModel.getDealsNames());
        inputDialog.setArguments(bundle);
        inputDialog.show(manager, "not_used_tag");
    }

    private void choosePps3Deal() {
        FragmentManager manager = getFragmentManager();
        InputFromListDialogToFragment inputDialog  = new InputFromListDialogToFragment();
        inputDialog.setTargetFragment(mFragmentCtx, REQUEST_CODE_DEAL_PRE_PROLONGATION);
        Bundle bundle = new Bundle();
        bundle.putString("title", "ППС3");
        bundle.putSerializable("array", mNewDealViewModel.getPps3Names());
        inputDialog.setArguments(bundle);
        inputDialog.show(manager, "not_used_tag");
    }




    private boolean checkEnteredData() {
        if (mNewDealViewModel.getNewDeal().getValue().getName() == null){
            Toast.makeText(mMainActivityCtx, "не указано название фирмы", Toast.LENGTH_LONG).show();
            return false;
        } else if (mNewDealViewModel.getNewDeal().getValue().getContractor() == null){
            Toast.makeText(mMainActivityCtx, "не указано юридическое лицо", Toast.LENGTH_LONG).show();
            return false;
        } else if (mNewDealViewModel.getNewDeal().getValue().getOwner() == null){
            Toast.makeText(mMainActivityCtx, "не указан куратор сделки", Toast.LENGTH_LONG).show();
            return false;
        } else if (mNewDealViewModel.getNewDeal().getValue().getDuration() == 0){
            Toast.makeText(mMainActivityCtx, "срок сделки не может быть 0", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }

    private void startInputDataDialog(String title, int requestCode){
        mInputDataDialog = new InputDataDialog();
        mInputDataDialog.setTargetFragment(mFragmentCtx, requestCode);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        mInputDataDialog.setArguments(bundle);
        mInputDataDialog.show(getFragmentManager(), mInputDataDialog.getClass().getName());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_VOLUME_PRICE:
                    String sNum = data.getStringExtra("data");
                    int num = GeckoUtils.msXlsCellToInt(sNum);
                    mNewDealViewModel.setPriceVolume(num);
                    break;
                case REQUEST_CODE_VOLUME_REAL:
                    String stringVolumeReal = data.getStringExtra("data");
                    int volumeReal = GeckoUtils.msXlsCellToInt(stringVolumeReal);
                    mNewDealViewModel.setRealVolume(volumeReal);
                    break;
                case REQUEST_CODE_DURATION:
                    String stringDuration = data.getStringExtra("data");
                    int duration = Integer.parseInt(stringDuration);
                    mNewDealViewModel.setDuration((short)duration);
                    break;
                case REQUEST_CODE_OWNER:
                    String owner = data.getStringExtra("data");
                    mNewDealViewModel.setOwner(owner);
                    break;
                case REQUEST_CODE_SUM_TO_PAY:
                    String stringSum = data.getStringExtra("data");
                    Toast.makeText(getActivity(), stringSum, Toast.LENGTH_SHORT).show();
                    int sumToPay = GeckoUtils.msXlsCellToInt(stringSum);
                    mNewDealViewModel.setSumToPay(sumToPay);
                    break;
                case REQUEST_CODE_DEAL_TYPE:
                    String type = data.getStringExtra("data");
                    mNewDealViewModel.setType(type);
                    break;
                case REQUEST_CODE_DEAL_STATUS:
                    String status = data.getStringExtra("data");
                    mNewDealViewModel.setStatus(status);
                    if (status.equals("Продление")) {
                        chooseProlongationDeal();
                    } else if (status.equals("Допродажа")){
                        chooseOversellDeal();
                    } else if (status.equals("ППС3")){
                        choosePps3Deal();
                    }
                    break;
                case REQUEST_CODE_DEAL_PROLONGATION:
                    String deal = data.getStringExtra("data");
                    String firmName = deal.substring(0, deal.indexOf(":"));
                    String priceVolumeStr = deal.substring(deal.indexOf(":")+1);
                    int priceVolume = GeckoUtils.msXlsCellToInt(priceVolumeStr);
                    Deal prolong = (mNewDealViewModel.findProlongationDealByNameAndVolume(firmName, priceVolume));
                    mNewDealViewModel.setProlongationDeal(prolong);
                    mNewDealViewModel.setFirmName(mNewDealViewModel.getProlongationDealName());
                    mNewDealViewModel.setContractor(mNewDealViewModel.getProlongationDealContractor());
                    mNewDealViewModel.setOwner(mNewDealViewModel.findOwner(firmName));
                    mNameToggleButton.setChecked(false);
                    mContractorToggleButton.setChecked(false);
                    break;
                case REQUEST_CODE_OVERSELL:
                case REQUEST_CODE_DEAL_PRE_PROLONGATION:
                    String name = data.getStringExtra("data");
                    mNewDealViewModel.setFirmName(name);
                    mNewDealViewModel.setContractor(mNewDealViewModel.findContractor(name));
                    mNewDealViewModel.setOwner(mNewDealViewModel.findOwner(name));
                    mNameToggleButton.setChecked(false);
                    mContractorToggleButton.setChecked(false);
                    break;
            }

        }
    }




}