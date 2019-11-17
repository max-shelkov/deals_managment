package com.gecko.dealsmanagment.ui.newdeal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gecko.dealsmanagment.Deal;
import com.gecko.dealsmanagment.DealsKeeper;
import com.gecko.dealsmanagment.GeckoUtils;
import com.gecko.dealsmanagment.InputDataDialog;
import com.gecko.dealsmanagment.MainActivity;
import com.gecko.dealsmanagment.R;
import com.gecko.dealsmanagment.ui.InputOwnerDialog;

import java.util.Calendar;

import static com.gecko.dealsmanagment.GeckoUtils.*;

public class NewDealFragment extends Fragment {

    private static final int REQUEST_CODE_DURATION = 100;
    private static final int REQUEST_CODE_VOLUME_PRICE = 101;
    private static final int REQUEST_CODE_VOLUME_REAL = 102;
    private static final int REQUEST_CODE_OWNER = 103;

    private Context mMainActivityCtx;
    private Fragment mFragmentCtx;

    private AutoCompleteTextView mNameTextView;
    private TextView mContractorTextView;
    private TextView mStartDateTextView;
    private TextView mFinishDateTextView;
    private TextView mDurationTextView;
    private TextView mVolumePriceTextView;
    private TextView mVolumeRealTextView;
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
        final TextView textView = root.findViewById(R.id.text_notifications);
        mNameTextView = root.findViewById(R.id.name_text_view);
        mContractorTextView = root.findViewById(R.id.contractor_text_view);
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
        mOwnerTextView = root.findViewById(R.id.owner_new_deal_text_view);
        mOwnerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                InputOwnerDialog iod  = new InputOwnerDialog();
                iod.setTargetFragment(mFragmentCtx, REQUEST_CODE_OWNER);
                Bundle bundle = new Bundle();
                bundle.putSerializable("managers", mNewDealViewModel.findManagersFromDeals());
                iod.setArguments(bundle);
                iod.show(manager, "1010");

            }
        });

        mOkButton = root.findViewById(R.id.new_deal_ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewDealViewModel.addNewDealToKeeper();

            }
        });




        mNameTextView.setThreshold(1);
        String[] names = mNewDealViewModel.getDealsKeeper().getValue().getNames();
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

        mNewDealViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mNewDealViewModel.getDealsKeeper().observe(this, new Observer<DealsKeeper>() {
            @Override
            public void onChanged(DealsKeeper dealsKeeper) {

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
        if (d.getName()!=null) {
            mNameTextView.setText(d.getName());
        } else {
            mNameTextView.setText("");
        }
        if (d.getContractor() != null) {
            mContractorTextView.setText("[" + d.getContractor() + "]");
        } else {
            mContractorTextView.setText("");
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

        mVolumePriceTextView.setText("Vp: " + formattedInt(d.getPriceVolume()));
        mVolumeRealTextView.setText("Vr: " + formattedInt(d.getRealVolume()));
        mOwnerTextView.setText(d.getOwner());
    }

    private void setDate(View v){
        Calendar nextMonth = Calendar.getInstance();
        nextMonth.add(Calendar.MONTH,1);
        nextMonth.set(Calendar.DAY_OF_MONTH, 1);
        new DatePickerDialog(getActivity(), d,
                nextMonth.get(Calendar.YEAR),
                nextMonth.get(Calendar.MONTH),
                nextMonth.get(Calendar.DAY_OF_MONTH))
                .show();
    }



    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar startDate = Calendar.getInstance();
            startDate.set(year, monthOfYear, dayOfMonth);
            mNewDealViewModel.setStartDate(startDate);
        }
    };


    private boolean checkEnteredData() {
        boolean result = true;
        if (mNameTextView.getText().toString().equals("")){
            result = false;
            Toast.makeText(mMainActivityCtx, "не указано название фирмы", Toast.LENGTH_LONG).show();
        }
        return result;
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
                    String owner = data.getStringExtra("manager");
                    Toast.makeText(getActivity(), owner, Toast.LENGTH_SHORT).show();
                    mNewDealViewModel.setOwner(owner);
                    break;
            }

        }
    }
}