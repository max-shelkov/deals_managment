package com.gecko.dealsmanagment.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InputOwnerDialog extends DialogFragment {


    private String[] mManagers;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mManagers = (String[]) getArguments().getSerializable("managers");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("куратор сделки");
        builder.setItems(mManagers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getActivity(), "выбран кот" + mManagers[which], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("manager", mManagers[which]);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            }
        });
        return builder.create();
    }
}
