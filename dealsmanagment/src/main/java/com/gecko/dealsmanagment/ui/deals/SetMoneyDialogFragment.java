package com.gecko.dealsmanagment.ui.deals;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gecko.dealsmanagment.R;

import java.util.Calendar;

public class SetMoneyDialogFragment extends DialogFragment implements View.OnClickListener{

    private static final String TAG = "myLog";

    private EditText mMoneyEditText;
    private EditText mDateEditText;
    private Button mOkButton;
    private Button mCancelButton;

    private float mMoneyPaid;
    private Calendar mDateOfPayment;

    private String mDialogTag;

    SetMoneyDialogFragmentListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_enter_payment, null);

        mMoneyEditText = v.findViewById(R.id.dialog_money_edit_text);
        mDateEditText = v.findViewById(R.id.dialog_day_edit_text);

        mOkButton = v.findViewById(R.id.dialog_ok_button);
        mOkButton.setOnClickListener(this);
        mCancelButton = v.findViewById(R.id.dialog_cancel_button);
        mCancelButton.setOnClickListener(this);

        mDialogTag = getArguments().getString("tag");
        Log.d(TAG, "tag from activity = " + mDialogTag);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_ok_button:
                try {
                    mMoneyPaid = Float.parseFloat(mMoneyEditText.getText().toString());
                    mDateOfPayment = Calendar.getInstance();
                    if(!mDateEditText.getText().toString().equals("")){
                        mDateOfPayment.set(Calendar.DAY_OF_MONTH, Integer.parseInt(mDateEditText.getText().toString()));
                    }
                } catch (Exception e){
                    Toast.makeText(getActivity(), "Введены некорретные данные", Toast.LENGTH_LONG ).show();
                }
                mListener.onDialogFragmentDataEntered(mMoneyPaid, mDateOfPayment, mDialogTag);
                break;
            case R.id.dialog_cancel_button:

                break;
        }
        dismiss();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (SetMoneyDialogFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface SetMoneyDialogFragmentListener{
        public void onDialogFragmentDataEntered(float money, Calendar date, String tag);
    }
}
