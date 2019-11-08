package com.gecko.dealsmanagment.ui.deals;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gecko.dealsmanagment.R;

public class SetMoneyDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "myLog";

    Dialog mDialog;

    EditText mMoneyEditText;
    EditText mDateEditText;
    Button mOkButton;
    Button mCancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mDialog = getDialog();
        mDialog.setTitle("поступление");
        View v = inflater.inflate(R.layout.dialog_enter_payment, null);

        mMoneyEditText = v.findViewById(R.id.dialog_money_edit_text);
        mDateEditText = v.findViewById(R.id.dialog_day_edit_text);

        mOkButton = v.findViewById(R.id.dialog_ok_button);
        mOkButton.setOnClickListener(this);
        mCancelButton = v.findViewById(R.id.dialog_cancel_button);
        mCancelButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
