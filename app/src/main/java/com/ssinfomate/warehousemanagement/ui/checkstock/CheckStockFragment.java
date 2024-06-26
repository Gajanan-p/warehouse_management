package com.ssinfomate.warehousemanagement.ui.checkstock;


import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.ListItemClick;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.WarehouseDialog;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStock;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockModel;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.RequestBusinessLocation;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
import com.ssinfomate.warehousemanagement.ui.dialog.DialogHelper;
import com.ssinfomate.warehousemanagement.ui.util.Constants;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.EMDKResults;
import com.symbol.emdk.barcode.BarcodeManager;
import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;
import com.symbol.emdk.barcode.ScannerConfig;
import com.symbol.emdk.barcode.ScannerException;
import com.symbol.emdk.barcode.ScannerInfo;
import com.symbol.emdk.barcode.ScannerResults;
import com.symbol.emdk.barcode.StatusData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckStockFragment extends Fragment
        implements
        View.OnClickListener,
        ListItemClick,
        EMDKManager.EMDKListener,
        Scanner.DataListener,
        Scanner.StatusListener,
        BarcodeManager.ScannerConnectionListener,
        CompoundButton.OnCheckedChangeListener
{

//------------------------ Initialize Variables--------------------------------------------

    private EMDKManager emdkManager = null;
    private BarcodeManager barcodeManager = null;
    private Scanner scanner = null;
    private ProgressDialog progressDialog;

//    private TextView textViewData = null;
//    private TextView textViewStatus = null;

    private List<ScannerInfo> deviceList = null;

    //private int scannerIndex = 0; // Keep the selected scanner
    private int defaultIndex = 0; // Keep the default scanner
    private int dataLength = 0;
    private String statusString = "";

    private boolean bSoftTriggerSelected = false;
    private boolean bDecoderSettingsChanged = false;
    private boolean bExtScannerDisconnected = false;
    private final Object lock = new Object();

    private AppCompatEditText appCompatEditTextBarcode;
    private AppCompatTextView appCompatTextViewWarehouseSelect;
    private AppCompatButton appCompatButtonSearch;

    private RecyclerView listItemStockCheck;
    public ArrayList<CheckStockModel> listCheckStock=new ArrayList<>();
    CheckStock checkStock=new CheckStock();
    private CheckStockAdapter checkStockAdapter;

    private WarehouseDialog warehouseDialog;
    private ArrayList<WarehouseModel>warehouseModels;

    private String TAG_SCANNER="SCANNER Check Stock";
    private AppCompatButton buttonOpenCamera;

    public static CheckStockFragment newInstance() {
        return new CheckStockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.check_stock_fragment, container, false);
        appCompatEditTextBarcode=view.findViewById(R.id.edit_check_stock_barcode);
        appCompatEditTextBarcode.setEnabled(false);
        appCompatEditTextBarcode.requestFocus();

        appCompatTextViewWarehouseSelect=view.findViewById(R.id.text_check_stock_warehouse_select);
        appCompatTextViewWarehouseSelect.setOnClickListener(this);

        appCompatButtonSearch=view.findViewById(R.id.button_check_stock_search);
        appCompatButtonSearch.setOnClickListener(this);

        listItemStockCheck=view.findViewById(R.id.rv_item_check_stock);
        checkStockAdapter=new CheckStockAdapter(listCheckStock,getContext());
        listItemStockCheck.setAdapter(checkStockAdapter);
        listItemStockCheck.setLayoutManager(new LinearLayoutManager(getContext()));

//       buttonOpenCamera = view.findViewById(R.id.button_check_stock_camera);
//       buttonOpenCamera.setVisibility(View.GONE);
//
        try {
            EMDKResults results = EMDKManager.getEMDKManager(getContext().getApplicationContext(),
            this);
            if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
                updateStatus("EMDKManager object request failed!");
            }
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        getOpenWarehouse();
        // The application is in foreground
        if (emdkManager != null) {
            // Acquire the barcode manager resources
            initBarcodeManager();
            // Enumerate scanner devices
            enumerateScannerDevices();
            // Set selected scanner
//            spinnerScannerDevices.setSelection(scannerIndex);
            // Initialize scanner

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        // The application is in background
        // Release the barcode manager resources
        deInitScanner();
        deInitBarcodeManager();
    }
    //--------------------// TODO: onClick()----------------------------------------------------------------------


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.text_check_stock_warehouse_select){
         //   openSelectWarehouseDialog();
        }else if(!TextUtils.isEmpty(appCompatEditTextBarcode.getText()))
        {
            SearchStock();
        }else
        {
            Toast.makeText(getActivity(), "Plz Select Warehouse", Toast.LENGTH_SHORT).show();
        }
    }

//--------------------// TODO: SearchStock()----------------------------------------------------------------------

    public void SearchStock(){

       progressDialog = createProgressDialog(getContext());

        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        checkStock.setBarcode(appCompatEditTextBarcode.getText().toString());

        Call<ArrayList<CheckStockModel>> listCheckStock  =
                WebServiceClient.checkStockService(appSetting.getSettingServerURL()).listCheckStock(checkStock);
        listCheckStock.enqueue(new Callback<ArrayList<CheckStockModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CheckStockModel>> call,
                                   Response<ArrayList<CheckStockModel>> response) {
                    changeDataSet(response.body());
                    appCompatEditTextBarcode.setText("");
                    progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<CheckStockModel>> call, Throwable t) {
                DialogHelper.getAlertWithMessage("Stock Load",t.getMessage(),getContext());
            }
        });

    }

    public void changeDataSet(ArrayList<CheckStockModel> checkStockModels){
        listCheckStock=checkStockModels;
        if (checkStockModels.get(0).getItemId()!=null) {
            checkStockAdapter = new CheckStockAdapter(listCheckStock, getContext());
            listItemStockCheck.setAdapter(checkStockAdapter);
            listItemStockCheck.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else {
            Toast.makeText(getContext(),"Item not found  !",Toast.LENGTH_LONG).show();
        }
    }

    void getOpenWarehouse(){
        progressDialog = createProgressDialog(getContext());
        String BASE_URL=AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
//      ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        RequestBusinessLocation requestBusinessLocation=new RequestBusinessLocation();
        requestBusinessLocation.setCoID(userModel.getCobrId());
        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(requestBusinessLocation),JsonObject.class);

        Call<ArrayList<WarehouseModel>> arrayListCall=WebServiceClient
                .getWarehouseService(BASE_URL)
                .listWarehouse(data);
        arrayListCall.enqueue(new Callback<ArrayList<WarehouseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<WarehouseModel>> call, Response<ArrayList<WarehouseModel>> response) {
                if (response.isSuccessful()) {
                    warehouseModels = response.body();
                    if (warehouseModels.size() > 0) {
                        onItemClicked(warehouseModels.get(0));
                    }
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(getContext(),"Server URL Not Found!",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<WarehouseModel>> call, Throwable t) {
                Log.i("error",t.getMessage());
            }
        });
    }

    public ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.blue(100)));
        dialog.setContentView(R.layout.dialog_progress_layout);
        return dialog;
    }

    void openSelectWarehouseDialog(){
//        String BASE_URL=AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
//        ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());
//
//        RequestBusinessLocation requestBusinessLocation=new RequestBusinessLocation();
//        requestBusinessLocation.setCoID(changeCompanyModel.getCoId());
//
//        Gson gson =new Gson();
//        JsonObject data=gson.fromJson(gson.toJson(requestBusinessLocation),JsonObject.class);
//
//        Call<ArrayList<WarehouseModel>> arrayListCall=WebServiceClient
//                .getWarehouseService(BASE_URL)
//                .listWarehouse(data);
//        arrayListCall.enqueue(new Callback<ArrayList<WarehouseModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<WarehouseModel>> call, Response<ArrayList<WarehouseModel>> response) {
//                warehouseModels =response.body();
                //warehouseModels.remove(0);
        if (warehouseModels!=null) {
            if (warehouseModels.size() > 0) {
                warehouseDialog = new WarehouseDialog(
                        getContext(),
                        warehouseModels,
                        CheckStockFragment.this,
                        Constants.BUSINESS_LOCATION);
                warehouseDialog.show();
            }
        }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<WarehouseModel>> call, Throwable t) {
//                Log.i("error",t.getMessage());
//            }
//        });
    }

    @Override
    public void onItemClicked(WarehouseModel warehouseModel) {
        checkStock.setCoBr_Id(warehouseModel.getCobrId());
        appCompatTextViewWarehouseSelect.setText(warehouseModel.getCobrName());
        if (warehouseDialog!=null){
        warehouseDialog.dismiss();}
    }

    @Override
    public void onItemClickedBusinessLocation(WarehouseModel warehouseModel) {
    }

    @Override
    public void onItemClickedFromLocation(WarehouseModel warehouseModel) {
    }

    @Override
    public void onItemClickedToLocation(ToWarehouse warehouseModel) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }

    @Override
    public void onOpened(EMDKManager emdkManager) {
        updateStatus("EMDK open success!");
        this.emdkManager = emdkManager;
        // Acquire the barcode manager resources
        initBarcodeManager();
        // Enumerate scanner devices
        enumerateScannerDevices();
//        deInitScanner();

        // Set default scanner
        initScanner();
        //spinnerScannerDevices.setSelection(defaultIndex);
    }

    @Override
    public void onClosed() {
// Release all the resources
        if (emdkManager != null) {
            emdkManager.release();
            emdkManager = null;
        }
        updateStatus("EMDK closed unexpectedly! Please close and restart the application.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Release all the resources
        if (emdkManager != null) {
            emdkManager.release();
            emdkManager = null;
        }
    }
//--------------------// TODO: onConnectionChange----------------------------------------------------------------------

    @Override
    public void onConnectionChange(ScannerInfo scannerInfo, BarcodeManager.ConnectionState connectionState) {
        String status;
        String scannerName = "";
        String statusExtScanner = connectionState.toString();
        String scannerNameExtScanner = scannerInfo.getFriendlyName();
        if (deviceList.size() != 0) {
            scannerName = deviceList.get(defaultIndex).getFriendlyName();
        }
        if (scannerName.equalsIgnoreCase(scannerNameExtScanner)) {
            switch(connectionState) {
                case CONNECTED:
                    bSoftTriggerSelected = false;
                    synchronized (lock) {
                        initScanner();
                        bExtScannerDisconnected = false;
                    }
                    break;
                case DISCONNECTED:
                    bExtScannerDisconnected = true;
                    synchronized (lock) {
                        deInitScanner();
                    }
                    break;
            }
            status = scannerNameExtScanner + ":" + statusExtScanner;
            updateStatus(status);
        }
        else {
            bExtScannerDisconnected = false;
            status =  statusString + " " + scannerNameExtScanner + ":" + statusExtScanner;
            updateStatus(status);
        }
    }

    @Override
    public void onData(ScanDataCollection scanDataCollection) {
        if ((scanDataCollection != null) && (scanDataCollection.getResult() == ScannerResults.SUCCESS)) {
            ArrayList <ScanDataCollection.ScanData> scanData = scanDataCollection.getScanData();
            for(ScanDataCollection.ScanData data : scanData) {
//                updateData("<font color='gray'>" + data.getLabelType() + "</font> : " + data.getData());
                updateData(data.getData());
            }
        }
    }

    private void updateData(final String result){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                appCompatEditTextBarcode.setText("");
                if (result != null) {
                    if(dataLength ++ > 10) { //Clear the cache after 100 scans
                        dataLength = 0;
                    }
                    appCompatEditTextBarcode.setText(Html.fromHtml(result));
//                    textViewData.append("\n");
//                    ((View) findViewById(R.id.scrollViewData)).post(new Runnable()
//                    {
//                        public void run()
//                        {
//                            ((ScrollView) findViewById(R.id.scrollViewData)).fullScroll(View.FOCUS_DOWN);
//                        }
//                    });
                }
            }
        });

    }


    @Override
    public void onStatus(StatusData statusData) {
        StatusData.ScannerStates state = statusData.getState();
        switch(state) {
            case IDLE:
                statusString = statusData.getFriendlyName()+" is enabled and idle...";
                updateStatus(statusString);
                // set trigger type
                if(bSoftTriggerSelected) {
                    scanner.triggerType = Scanner.TriggerType.SOFT_ONCE;
                    bSoftTriggerSelected = false;
                } else {
                    scanner.triggerType = Scanner.TriggerType.HARD;
                }
                // set decoders
                if(bDecoderSettingsChanged) {
                    setDecoders();
                    bDecoderSettingsChanged = false;
                }
                // submit read
                if(!scanner.isReadPending() && !bExtScannerDisconnected) {
                    try {
                        scanner.read();
                    } catch (ScannerException e) {
                        updateStatus(e.getMessage());
                    }
                }
                break;
            case WAITING:
                statusString = "Scanner is waiting for trigger press...";
                updateStatus(statusString);
                break;
            case SCANNING:
                statusString = "Scanning...";
                updateStatus(statusString);
                break;
            case DISABLED:
                statusString = statusData.getFriendlyName()+" is disabled.";
                updateStatus(statusString);
                break;
            case ERROR:
                statusString = "An error has occurred.";
                updateStatus(statusString);
                break;
            default:
                break;
        }

    }

    private void updateStatus(final String status){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                textViewStatus.setText("" + status);
                Log.i(TAG_SCANNER,status);
            }
        });
    }

    private void initScanner() {
        if (scanner == null) {
            if ((deviceList != null) && (deviceList.size() != 0)) {
                if (barcodeManager != null)
                    scanner = barcodeManager.getDevice(deviceList.get(defaultIndex));
            }
            else {
                updateStatus("Failed to get the specified scanner device! Please close and restart the application.");
                return;
            }
            if (scanner != null) {
                scanner.addDataListener(this);
                scanner.addStatusListener(this);
                try {
                    scanner.enable();
                } catch (ScannerException e) {
                    updateStatus(e.getMessage());
                    deInitScanner();
                }
            }else{
                updateStatus("Failed to initialize the scanner device.");
            }
        }

    }

    private void deInitScanner() {
        if (scanner != null) {
            try{
                scanner.disable();
            } catch (Exception e) {
                updateStatus(e.getMessage());
            }

            try {
                scanner.removeDataListener(this);
                scanner.removeStatusListener(this);
            } catch (Exception e) {
                updateStatus(e.getMessage());
            }

            try{
                scanner.release();
            } catch (Exception e) {
                updateStatus(e.getMessage());
            }
            scanner = null;
        }
    }

    private void initBarcodeManager(){
        barcodeManager = (BarcodeManager) emdkManager.getInstance(EMDKManager.FEATURE_TYPE.BARCODE);
        // Add connection listener
        if (barcodeManager != null) {
            barcodeManager.addConnectionListener(this);
        }
    }
    private void deInitBarcodeManager(){
        if (emdkManager != null) {
            emdkManager.release(EMDKManager.FEATURE_TYPE.BARCODE);
        }
    }
//--------------------// TODO: enumerateScannerDevices()----------------------------------------------------------------------

    private void enumerateScannerDevices() {
        if (barcodeManager != null) {
            List<String> friendlyNameList = new ArrayList<String>();
            int spinnerIndex = 0;
            deviceList = barcodeManager.getSupportedDevicesInfo();
            if ((deviceList != null) && (deviceList.size() != 0)) {
                Iterator<ScannerInfo> it = deviceList.iterator();
                while(it.hasNext()) {
                    ScannerInfo scnInfo = it.next();
                    friendlyNameList.add(scnInfo.getFriendlyName());
                    if(scnInfo.isDefaultScanner()) {
                        defaultIndex = spinnerIndex;
                    }
                    ++spinnerIndex;
                }
            }
            else {
                updateStatus("Failed to get the list of supported scanner devices! Please close and restart the application.");
            }
//            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, friendlyNameList);
//            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinnerScannerDevices.setAdapter(spinnerAdapter);
        }
    }
//--------------------// TODO: setDecoders()----------------------------------------------------------------------

    private void setDecoders() {
        if (scanner != null) {
            try {
                ScannerConfig config = scanner.getConfig();
                // Set EAN8
                //config.decoderParams.ean8.enabled = checkBoxEAN8.isChecked();
                // Set EAN13
                //config.decoderParams.ean13.enabled = checkBoxEAN13.isChecked();
                // Set Code39
                // config.decoderParams.code39.enabled= checkBoxCode39.isChecked();
                //Set Code128
                config.decoderParams.code128.enabled = true;
                scanner.setConfig(config);
            } catch (ScannerException e) {
                updateStatus(e.getMessage());
            }
        }
    }

    // use button Soft Scan

    public void softScan(View view) {
        bSoftTriggerSelected = true;
        cancelRead();
    }

    private void cancelRead(){
        if (scanner != null) {
            if (scanner.isReadPending()) {
                try {
                    scanner.cancelRead();
                } catch (ScannerException e) {
                    updateStatus(e.getMessage());
                }
            }
        }
    }


}