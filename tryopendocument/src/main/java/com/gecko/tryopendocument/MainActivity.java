package com.gecko.tryopendocument;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    public static final String TAG = "myLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");


                List<ResolveInfo> activities;
                PackageManager pm = getPackageManager();
                activities = pm.queryIntentActivities(intent, PackageManager.MATCH_ALL);
                if(activities == null){
                    Log.d(TAG, "activities is null");
                }else{
                    Log.d(TAG, "number of activities = " + activities.size());
                }

                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });


    }
}
