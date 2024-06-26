package com.ssinfomate.warehousemanagement.utils;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ssinfomate.warehousemanagement.MainActivity;

public class BaseActivity extends AppCompatActivity implements LogOutListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApp) getApplication()).registerSessionListener(this);
        ((MyApp) getApplication()).startUserSession();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        ((MyApp) getApplication()).onUserInteracted();
    }

    @Override
    public void onSessionLogOut() {
        ((MyApp)getApplication()).cancelTime();

        AppPreference.clearLoginDataPreferences(this);
        startActivity(new Intent(BaseActivity.this, MainActivity.class));
        finish();
    }
}
