package com.ssinfomate.warehousemanagement.ui.general;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStock;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockModel;
import com.ssinfomate.warehousemanagement.webservices.general.DeleteGeneralModel;
import com.ssinfomate.warehousemanagement.webservices.general.GeneralInputDetail;
import com.ssinfomate.warehousemanagement.webservices.general.GeneralModel;
import com.ssinfomate.warehousemanagement.webservices.general.GeneralRemarkModel;
import com.ssinfomate.warehousemanagement.webservices.general.RequestGeneralList;
import com.ssinfomate.warehousemanagement.webservices.general.ResponseGeneralList;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;
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

public class GeneralFragment
        extends Fragment
        implements
        EMDKManager.EMDKListener,
        Scanner.DataListener,
        Scanner.StatusListener,
        BarcodeManager.ScannerConnectionListener,
        View.OnClickListener,
        IGeneralItem,
        IGeneralRemarkListener
{
    private EMDKManager emdkManager = null;
    private BarcodeManager barcodeManager = null;
    private Scanner scanner = null;
    private List<ScannerInfo> deviceList = null;
    //private int scannerIndex = 0; // Keep the selected scanner
    private int defaultIndex = 0; // Keep the default scanner
    private int dataLength = 0;
    private String statusString = "";
    private boolean bSoftTriggerSelected = false;
    private boolean bDecoderSettingsChanged = false;
    private boolean bExtScannerDisconnected = false;
    private final Object lock = new Object();
    private String TAG_SCANNER="SCANNER";
    private RecyclerView recyclerViewGeneralList;
    public ArrayList<ResponseGeneralList> responseGeneralLists = new ArrayList<>();
    CheckStock checkStock=new CheckStock();
    private GeneralListAdapter generalListAdapter;
    private ProgressDialog progressDialog;
    ArrayList<GeneralRemarkModel>remarkModels;
    GeneralRemarkDialog remarkDialog;
    GeneralRemarkDialog remarkTwoDialog;
    LinearLayout ContainerGeneralEntry;
    LinearLayout ContainerGeneralList;
    EditText editTextBarcode;
    EditText editTextRemarkOne;
    EditText editTextRemarkTwo;
    TextView textViewProductName;
    TextView textViewSystemQuantity;
    TextView textViewUnit;
    ImageView imageViewGeneralList;
    ImageView imageViewGeneralEntry;
    Button buttonOk;
    Button buttonSubmit;
    CheckStockModel checkStockModel;
    ResponseGeneralList responseGeneralList;
    boolean updateFlag=false;

    public static GeneralFragment newInstance() {
        GeneralFragment fragment = new GeneralFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_general, container, false);
        editTextBarcode=view.findViewById(R.id.text_general_barcode);
        editTextBarcode.setEnabled(false);

        editTextRemarkOne=view.findViewById(R.id.text_general_remark_one);
        editTextRemarkOne.setOnClickListener(this);

        editTextRemarkTwo=view.findViewById(R.id.text_general_remark_two);
        editTextRemarkTwo.setOnClickListener(this);

        textViewProductName=view.findViewById(R.id.text_general_product_name);

        textViewSystemQuantity=view.findViewById(R.id.text_general_system_quantity);
        textViewUnit=view.findViewById(R.id.text_general_unit);

        recyclerViewGeneralList=view.findViewById(R.id.rv_general_list);
        recyclerViewGeneralList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewGeneralList.setHasFixedSize(true);

        imageViewGeneralList=view.findViewById(R.id.button_general_list);
        imageViewGeneralList.setOnClickListener(this);

        imageViewGeneralEntry = view.findViewById(R.id.button_general_entry);
        imageViewGeneralEntry.setOnClickListener(this);

        buttonOk=view.findViewById(R.id.button_general_ok);
        buttonOk.setOnClickListener(this);

        buttonSubmit=view.findViewById(R.id.button_general_submit);
        buttonSubmit.setOnClickListener(this);

   //Single Navigation page
        ContainerGeneralEntry= view.findViewById(R.id.container_general_entry);
        ContainerGeneralList = view.findViewById(R.id.container_general_list);


        EMDKResults results = EMDKManager.getEMDKManager(getContext().getApplicationContext(), this);
        if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
            updateStatus("EMDKManager object request failed!");
        }

        return view;
    }
    public void onGeneralEntryClicked(){
        ContainerGeneralEntry.setVisibility(View.VISIBLE);
        ContainerGeneralList.setVisibility(View.GONE);
    }
    public void onGeneralListClicked(){
        ContainerGeneralEntry.setVisibility(View.GONE);
        ContainerGeneralList.setVisibility(View.VISIBLE);
    }

    public void SearchStock(){
        progressDialog = createProgressDialog(getContext());

        UserModel userModel= AppPreference.getLoginDataPreferences(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        checkStock.setCoBr_Id(userModel.getCobrId());
        checkStock.setBarcode(editTextBarcode.getText().toString());

        Call<ArrayList<CheckStockModel>> listCheckStock  =
                WebServiceClient.checkStockService(appSetting.getSettingServerURL()).listCheckStock(checkStock);
        listCheckStock.enqueue(new Callback<ArrayList<CheckStockModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CheckStockModel>> call,
                                   Response<ArrayList<CheckStockModel>> response) {
                if (response.isSuccessful()) {
                    changeDataUI(response.body());
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(getContext(),"Server URL Not Found!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CheckStockModel>> call, Throwable t) {
            }
        });
    }

    public void changeDataUI(ArrayList<CheckStockModel> checkStockModels){
        updateFlag=false;
        checkStockModel=checkStockModels.get(0);
        textViewProductName.setText(checkStockModel.getItemName());
        textViewSystemQuantity.setText(checkStockModel.getClqty());
        textViewUnit.setText(checkStockModel.getUnitName());
    }
    public void changeDataUI(ResponseGeneralList responseGeneralList){
        updateFlag=true;
        this.responseGeneralList=responseGeneralList;
        textViewProductName.setText(responseGeneralList.getItemName());
        textViewSystemQuantity.setText(responseGeneralList.getClQty());
        textViewUnit.setText(responseGeneralList.getUnitName());
        editTextRemarkOne.setText(responseGeneralList.getRemark1());
        onGeneralEntryClicked();
    }

    public void clearDataUI(){
        editTextBarcode.setText("");
        textViewProductName.setText("");
        textViewSystemQuantity.setText("");
        textViewUnit.setText("");
        editTextRemarkOne.setText("");

    }

//------------------------------------------------------------------------------------------------------------
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
//-------------------------------------------------------------------------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
        // The application is in foreground
        if (emdkManager != null) {
            // Acquire the barcode manager resources
            initBarcodeManager();
            // Enumerate scanner devices
            enumerateScannerDevices();
            // Set selected scanner
           //  spinnerScannerDevices.setSelection(scannerIndex);
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

    private void updateStatus(final String status){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                textViewStatus.setText("" + status);
                Log.i(TAG_SCANNER,status);
            }
        });
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
                editTextBarcode.setText("");
                if (result != null) {
                    if(dataLength ++ > 10) { //Clear the cache after 100 scans
                        dataLength = 0;
                    }
                    editTextBarcode.setText(Html.fromHtml(result));
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

    // use button softscan
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
//------------------------------------------------------------------------------------------------
    public void SubmitGeneral(){

        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
      //  List<GeneralRemarkModel> remarkModel =AppPreference.getGeneralRemarkDataPreferences(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        GeneralModel generalModel=new GeneralModel();
        GeneralInputDetail generalInputDetail =new GeneralInputDetail();
        if(updateFlag){
            generalModel.setGeneralID(responseGeneralList.getGeneralID());
            generalInputDetail.setGeneralDetailIdID(responseGeneralList.getGeneralID());

            generalModel.setUpdatedBy(String.valueOf(userModel.getUserId()));
            //generalInputDetail.setItemKey(responseGeneralList.get());
        }else{
            generalInputDetail.setCobrID(checkStock.getCoBr_Id());
            generalInputDetail.setItemId(checkStockModel.getItemId());
            generalModel.setCreatedBy(String.valueOf(userModel.getUserId()));
        }
        generalInputDetail.setRemark1(editTextRemarkOne.getText().toString());
        generalInputDetail.setRemark2(editTextRemarkTwo.getText().toString());
//        if (editTextRemarkOne.getText().equals(editTextRemarkTwo.getText())){
//            Toast.makeText(getActivity(), "Select Different Remark", Toast.LENGTH_SHORT).show();
//            return;
//        }else{
//
//            generalInputDetail.setRemark1(editTextRemarkOne.getText().toString());
//            generalInputDetail.setRemark2("");
////        }

        ArrayList<GeneralInputDetail>generalInputDetails=new ArrayList<>();
        generalInputDetails.add(generalInputDetail);
        generalModel.setGeneralInputDetails(generalInputDetails);
        Call<GeneralModel> generalModelCall = WebServiceClient
                        .generalService(appSetting.getSettingServerURL())
                        .setGeneralModelCall(generalModel);

        generalModelCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                Toast.makeText(getContext(),response.body().getMsg().toString(),Toast.LENGTH_LONG).show();
                clearDataUI();

            }
            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {
                Toast.makeText(getContext(),"General Not Saved",Toast.LENGTH_LONG).show();
            }
        });
    }
//----------------------------------------------------------------------------------------------------
    public void getGeneralList(){
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        RequestGeneralList requestGeneralList=new RequestGeneralList();
        requestGeneralList.setCoBrID(userModel.getCobrId());
        Call<ArrayList<ResponseGeneralList>> responseGeneralListCall=WebServiceClient
                .generalService(appSetting.getSettingServerURL())
                .getGeneralList(requestGeneralList);
        responseGeneralListCall.enqueue(new Callback<ArrayList<ResponseGeneralList>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseGeneralList>> call, Response<ArrayList<ResponseGeneralList>> response) {
                UpdateGeneralList(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseGeneralList>> call, Throwable t) {
                Toast.makeText(getContext(),"General List Not Available",Toast.LENGTH_LONG).show();
            }
        });
    }
//****************TODO*UpdateGeneralList*******************************************************************************
    void UpdateGeneralList(ArrayList<ResponseGeneralList> responseGeneralLists){
        this.responseGeneralLists=responseGeneralLists;
        generalListAdapter=new GeneralListAdapter(responseGeneralLists,this);
        recyclerViewGeneralList.setAdapter(generalListAdapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_general_ok:{
                SearchStock();
                break;
            }
            case R.id.button_general_submit:{
                validationSubmitGeneral();
                break;
            }
            case R.id.button_general_list:{
                getGeneralList();
                onGeneralListClicked();
                break;
            }
            case R.id.button_general_entry:{
                onGeneralEntryClicked();
                break;
            }
//            case R.id.text_general_remark_one:{
//               loadRemarkOneDialog();
//                break;
//            }
//            case R.id.text_general_remark_two:{
//                loadRemarkTwoDialog();
//                break;
//            }
        }
    }
    public void validationSubmitGeneral() {
        if (!TextUtils.isEmpty(editTextRemarkOne.getText())
        ) {
            SubmitGeneral();
        } else {
            Toast.makeText(getActivity(), "Select All Field", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onGeneralItemUpdateClicked(ResponseGeneralList responseGeneralList) {
        changeDataUI(responseGeneralList);
    }
    @Override
    public void onGeneralItemDeleteClicked(ResponseGeneralList responseGeneralList) {
        DeleteGeneralModel deleteGeneralModel=new DeleteGeneralModel();
        deleteGeneralModel.setGeneralID(responseGeneralList.getGeneralID());
        setDeleteGeneralItem(deleteGeneralModel);
    }
//----------------------------------------------------------------------------------------------------------------
    public void setDeleteGeneralItem(DeleteGeneralModel deleteGeneralItem){
        progressDialog = createProgressDialog(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        deleteGeneralItem.setUpdatedBy(String.valueOf(userModel.getUserId()));

        Call<DeleteGeneralModel> deleteGeneralModelCall=WebServiceClient
                .generalService(appSetting.getSettingServerURL())
                .setDeleteGeneralItem(deleteGeneralItem);
        deleteGeneralModelCall.enqueue(new Callback<DeleteGeneralModel>() {
            @Override
            public void onResponse(Call<DeleteGeneralModel> call, Response<DeleteGeneralModel> response) {
                Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
               getGeneralList();
               progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<DeleteGeneralModel> call, Throwable t) {
                Toast.makeText(getContext(),"General Item Deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
//    -------------------------------------------------------------------------------------------------
    public void loadRemarkOneDialog(){
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        Call<ArrayList<GeneralRemarkModel>> arrayListCall = WebServiceClient
                .generalService(appSetting.getSettingServerURL())
                .getGeneralRemarkList();
        arrayListCall.enqueue(new Callback<ArrayList<GeneralRemarkModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GeneralRemarkModel>> call, Response<ArrayList<GeneralRemarkModel>> response) {
               remarkModels = response.body();

               remarkDialog = new GeneralRemarkDialog(
                       getContext(),remarkModels,
                       GeneralFragment.this);
               remarkDialog.show();
            }
            @Override
            public void onFailure(Call<ArrayList<GeneralRemarkModel>> call, Throwable t) {
                Log.i("error",t.getMessage());
            }
        });
    }
    @Override
    public void onRemarkClicked(GeneralRemarkModel generalRemarkModel) {
        AppPreference.setGeneralRemarkDataPreferences(getContext(),generalRemarkModel);
        editTextRemarkOne.setText(generalRemarkModel.getRemarkName());
        remarkDialog.dismiss();

    }
//    ----------------------------------------------------------------------------------------------
//    void loadRemarkTwoDialog(){
//        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
//        Call<ArrayList<GeneralRemarkModel>> arrayListCall = WebServiceClient
//                .generalService(appSetting.getSettingServerURL())
//                .getGeneralRemarkList();
//        arrayListCall.enqueue(new Callback<ArrayList<GeneralRemarkModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<GeneralRemarkModel>> call, Response<ArrayList<GeneralRemarkModel>> response) {
//                remarkModels = response.body();
//
//                remarkTwoDialog = new GeneralRemarkDialog(
//                        getContext(),remarkModels,
//                        GeneralFragment.this);
//                remarkTwoDialog.show();
//            }
//            @Override
//            public void onFailure(Call<ArrayList<GeneralRemarkModel>> call, Throwable t) {
//                Log.i("error",t.getMessage());
//            }
//        });
//    }
}