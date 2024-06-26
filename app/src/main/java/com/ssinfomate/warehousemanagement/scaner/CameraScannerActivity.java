package com.ssinfomate.warehousemanagement.scaner;


import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ssinfomate.warehousemanagement.R;



public class CameraScannerActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scanner);

    }

    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT < 23) {

        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (android.os.Build.VERSION.SDK_INT < 23) {

        }
    }

}