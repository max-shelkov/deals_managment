package com.gecko.dealsmanagment.ui.deals;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gecko.dealsmanagment.R;

import static com.gecko.dealsmanagment.GeckoUtils.TAG;

public class EditDealDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView mTitleTextView;
    private EditText mDataEditText;
    private Button mOkButton;
    private Button mCancelButton;
    private String mTag;

    private EditDealDialogFragmentListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_input_data, null);

        mTitleTextView = v.findViewById(R.id.title_text_view_input_data_dialog);
        mDataEditText = v.findViewById(R.id.data_edit_text_input_data_dialog);
        mDataEditText.setInputType(TextView.AUTOFILL_TYPE_TEXT);
        mOkButton = v.findViewById(R.id.ok_button_input_data_dialog);
        mCancelButton = v.findViewById(R.id.cancel_button_input_data_dialog);


        mOkButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        String title = getArguments().getString("title");
        String data = getArguments().getString("data");
        mTag = getArguments().getString("tag");

        mTitleTextView.setText(title);
        mDataEditText.setText(data);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok_button_input_data_dialog:
                mListener.onEditDealDialogFragmentDataEntered(mDataEditText.getText().toString(), mTag );
                break;
            case R.id.cancel_button_input_data_dialog:
                break;
        }
        dismiss();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (EditDealDialogFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }

    }

    public interface EditDealDialogFragmentListener{
        public void onEditDealDialogFragmentDataEntered(String data, String tag);
    }
}
