package com.gecko.dealsmanagment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InputFromListDialogToActivity extends DialogFragment {

    private InputFromListDialogToActivityListener mListener;

    private String[] mArray;
    private String mTag;
    private String mTitle;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        mArray = (String[]) getArguments().getSerializable("array");
        mTag = getArguments().getString("tag");
        mTitle = getArguments().getString("title");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mTitle);
        builder.setItems(mArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onListItemChosen(mArray[which], mTag);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (InputFromListDialogToActivityListener) context;

    }

    public interface InputFromListDialogToActivityListener{
        public void onListItemChosen(String data, String tag);
    }
}
