package com.gecko.dealsmanagment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InputDataDialog extends DialogFragment implements View.OnClickListener {


    private TextView mTitleTextView;
    private EditText mInputDataEditText;
    private Button mOkButton;
    private Button mCancelButton;

    private String mData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_input_data, null);
        mTitleTextView = v.findViewById(R.id.title_text_view_input_data_dialog);
        mInputDataEditText = v.findViewById(R.id.data_edit_text_input_data_dialog);
        mOkButton = v.findViewById(R.id.ok_button_input_data_dialog);
        mOkButton.setOnClickListener(this);
        mCancelButton = v.findViewById(R.id.cancel_button_input_data_dialog);
        mCancelButton.setOnClickListener(this);

        String title = getArguments().getString("title");
        mTitleTextView.setText(title);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok_button_input_data_dialog:

                Intent intent = new Intent();
                intent.putExtra("data", mInputDataEditText.getText().toString());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                break;
            case R.id.cancel_button_input_data_dialog:
                break;
        }
        dismiss();
    }


}
