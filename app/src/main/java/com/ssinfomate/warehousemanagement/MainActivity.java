package com.ssinfomate.warehousemanagement;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import com.ssinfomate.warehousemanagement.scaner.barcodereader.BarcodeCaptureActivity;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.BaseActivity;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private UserModel userModel;
    private NavController navController;
    private NavigationView navigationView;
    private EditText barcodeEditTextFromFragment;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_Login
                )
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        userModel=AppPreference.getLoginDataPreferences(getApplicationContext());

    }

    public void checkForLogin(){
            userModel= AppPreference.getLoginDataPreferences(getApplicationContext());
            if(userModel!=null){
                if(userModel.getIsLogin()==1){
                    //navController.navigate(R.id.action_nav_Login_to_nav_home);
                    showMenuItem();
                }else{
                    navController.navigate(R.id.nav_Login);
                    hideMenuItem();
                }
            }else{
                navController.navigate(R.id.nav_Login);
                hideMenuItem();
            }
        }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForLogin();
        Log.e(TAG, "onResume()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }

    private void hideMenuItem() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_logout).setVisible(false);
    }

    private void showMenuItem(){
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_logout).setVisible(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void startScanActivity(EditText editText){
        barcodeEditTextFromFragment=editText;
        Log.i("Message123","Message123");
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash,true);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    if(barcodeEditTextFromFragment!=null){
                    barcodeEditTextFromFragment.setText(barcode.displayValue);}
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    Log.d(TAG, "No barcode captured, intent data is null..!");
                }
            } else {

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}