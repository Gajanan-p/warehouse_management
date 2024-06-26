package com.ssinfomate.warehousemanagement.ui.grn;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
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
import android.widget.Toast;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.IOnSaveStock;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.IOnSaveStockQuantity;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.SaveStockQuantityDialog;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.SaveStockTransferAdapter;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStock;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockModel;
import com.ssinfomate.warehousemanagement.webservices.grn.RequestGRNWithoutPurOrder;
import com.ssinfomate.warehousemanagement.webservices.grn.RequestGRNWithoutPurOrderDetails;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.SaveStockTransferModel;
import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierModel;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
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

public class SaveGrnFragment extends Fragment implements
        View.OnClickListener,
        EMDKManager.EMDKListener,
        Scanner.DataListener,
        Scanner.StatusListener,
        BarcodeManager.ScannerConnectionListener,
        IOnSaveStock,
        IOnSaveStockQuantity
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

    private AppCompatEditText appCompatEditTextBarcode;
    private ProgressDialog progressDialog;

    NavController navController;
    Button buttonSaveGRNWithoutPurOrder;
    AppCompatButton buttonBarcodeGrnOk;

    RecyclerView recyclerView;
    SaveStockTransferAdapter saveStockTransferAdapter;
    ArrayList<SaveStockTransferModel> listSaveStockTransferModel;
    SavePurchaseWithoutPODialog saveStockQuantityDialog;
    CheckStock checkStock=new CheckStock();


    public static SaveGrnFragment newInstance() {
        return new SaveGrnFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.save_grn_fragment, container, false);

        recyclerView=view.findViewById(R.id.rv_item_grn_list);

        appCompatEditTextBarcode=view.findViewById(R.id.edit_grn_barcode);

        listSaveStockTransferModel=new ArrayList<>();

        buttonBarcodeGrnOk = view.findViewById(R.id.button_save_grn_ok);
        buttonBarcodeGrnOk.setOnClickListener(this);

        buttonSaveGRNWithoutPurOrder = view.findViewById(R.id.save_grn_save);
        buttonSaveGRNWithoutPurOrder.setOnClickListener(this::createGRNWithoutPurOrder);
        appCompatEditTextBarcode.requestFocus();
        EMDKResults results = EMDKManager.getEMDKManager(getContext().getApplicationContext(), this);
        if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
            updateStatus("EMDKManager object request failed!");
        }
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }
    //-------------------------------------------------------------------------------------------------
    public void searchSaveStock(){
        progressDialog = createProgressDialog(getContext());
        UserModel userModel= AppPreference.getLoginDataPreferences(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());

        checkStock.setCoBr_Id(userModel.getCobrId());
        checkStock.setBarcode(appCompatEditTextBarcode.getText().toString());

        Call<ArrayList<CheckStockModel>> listCheckStock  = WebServiceClient
                .checkStockService(appSetting.getSettingServerURL())
                .listCheckStock(checkStock);
        listCheckStock.enqueue(new Callback<ArrayList<CheckStockModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CheckStockModel>> call,
                                   Response<ArrayList<CheckStockModel>> response) {
                if (checkStock.getBarcode()!=null){
                    changeDataSet(response.body());}
                else {
                    Toast.makeText(getContext(),"Scan Correct Barcode",Toast.LENGTH_SHORT).show();
                }
                appCompatEditTextBarcode.setText("");
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<CheckStockModel>> call, Throwable t) {
                Log.i("error",t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    public void changeDataSet(ArrayList<CheckStockModel> checkStockModels){
        CheckStockModel checkStockModel=checkStockModels.get(0);
        boolean isFound=false;
        if(listSaveStockTransferModel.size()>0){
            for(int j=0;j<listSaveStockTransferModel.size();j++){
                if(listSaveStockTransferModel.get(j).getItemName().equals(checkStockModel.getItemName())){
                    listSaveStockTransferModel.get(j).setScan_quantity(
                            listSaveStockTransferModel.get(j).getScan_quantity()+1
                    );
                    isFound=true;
                }
            }
        }else{
            isFound=false;
        }

        if(!isFound){
            updateListModel(checkStockModel);
        }
        if(listSaveStockTransferModel!=null){
            saveStockTransferAdapter=new SaveStockTransferAdapter(listSaveStockTransferModel,
                    this,getContext());
            recyclerView.setAdapter(saveStockTransferAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

//    ------------------------------------------------------------------------------------------
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
//---------------------------Update Purchase----------------------------------------------------

    public void updateListModel(CheckStockModel checkStockModel){
        SaveStockTransferModel saveStockTransferModel=new SaveStockTransferModel();
        saveStockTransferModel.setCoBrId(checkStockModel.getCoBrId());
        saveStockTransferModel.setAltbarcode1(checkStockModel.getAltbarcode1());
        saveStockTransferModel.setBarcode(checkStockModel.getBarcode());
        saveStockTransferModel.setClqty(checkStockModel.getClqty());
        saveStockTransferModel.setConvQty(checkStockModel.getConvQty());
        saveStockTransferModel.setItemCode(checkStockModel.getItemCode());
        saveStockTransferModel.setItemId(checkStockModel.getItemId());
        saveStockTransferModel.setItemName(checkStockModel.getItemName());
        saveStockTransferModel.setItemStatus(checkStockModel.getItemStatus());
        saveStockTransferModel.setItemsubgrpId(checkStockModel.getItemsubgrpId());
        saveStockTransferModel.setItemsubgrpName(checkStockModel.getItemsubgrpName());
        saveStockTransferModel.setUnitName(checkStockModel.getUnitName());
        saveStockTransferModel.setScan_quantity(1);
        saveStockTransferModel.setMrp(checkStockModel.getMrp());

        listSaveStockTransferModel.add(saveStockTransferModel);
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
            ArrayList<ScanDataCollection.ScanData> scanData = scanDataCollection.getScanData();
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
                if (result != null) {
                    if(dataLength ++ > 100) { //Clear the cache after 100 scans
                        appCompatEditTextBarcode.setText("");
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

//---------------IOnSaveStock------------------------------------------------------------------------------------------

    @Override
    public void onStockItemChange(SaveStockTransferModel model, int position) {
        saveStockQuantityDialog.dismiss();
        if(listSaveStockTransferModel.size()>0){
            listSaveStockTransferModel.get(position).setClqty(model.getClqty());
            saveStockTransferAdapter.notifyDataSetChanged();
        }
    }

//    @Override
//    public void onStockDamageItemChange(DamageReasonModel damageReasonModel, int position) {
//
//    }

//--------------------------------------------------------------------------------------------
    @Override
    public void onStockItemChange(int position) {
        saveStockQuantityDialog=new SavePurchaseWithoutPODialog(
                getContext(),
                listSaveStockTransferModel.get(position),
                this,
                position
        );
        saveStockQuantityDialog.show();
    }

    @Override
    public void onStockItemRemove(int position) {
        if(listSaveStockTransferModel.size()>0){
            listSaveStockTransferModel.remove(position);
            saveStockTransferAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onStockItemDamageReason(int position) {

    }

//-------------------------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_save_grn_ok:{
                validationBarcodeText();
            }
        }
    }
    public void validationBarcodeText(){
        if (!TextUtils.isEmpty(appCompatEditTextBarcode.getText())){
            searchSaveStock();
        }else {
            Toast.makeText(getActivity(), "Select Barcode", Toast.LENGTH_SHORT).show();
        }
    }
//----------------------------------------------------------------------------------------------

    public void createGRNWithoutPurOrder(View view){

        RequestGRNWithoutPurOrder requestGRNWithoutPurOrder = new RequestGRNWithoutPurOrder();
        ArrayList<RequestGRNWithoutPurOrderDetails> details = new ArrayList<>();

        WarehouseModel warehouseBusinessLocation =AppPreference.getBusinessLocationDataPreferences(getContext());
        SupplierModel supplierModel = AppPreference.getSupplierDataPreferences(getContext());
        ToWarehouse warehouse = AppPreference.getToWarehouseDataPreferences(getContext());
        UserModel userModel= AppPreference.getLoginDataPreferences(getContext());

        requestGRNWithoutPurOrder.setCoBrId(userModel.getCobrId());
        requestGRNWithoutPurOrder.setCoBrId(warehouseBusinessLocation.getCobrId());
        requestGRNWithoutPurOrder.setUserId(userModel.getUserId());
        requestGRNWithoutPurOrder.setSuplID(Integer.parseInt(supplierModel.getLedId()));
        requestGRNWithoutPurOrder.setMachName(Build.PRODUCT);

        RequestGRNWithoutPurOrderDetails detail;
        if (listSaveStockTransferModel!=null){
            if (listSaveStockTransferModel.size()>0){
                for (int i=0; i<listSaveStockTransferModel.size(); i++){
                    detail = new RequestGRNWithoutPurOrderDetails();
                    SaveStockTransferModel saveStockTransferModel = listSaveStockTransferModel.get(i);
                    detail.setTotQty(saveStockTransferModel.getScan_quantity());
                    detail.setItemId(saveStockTransferModel.getItemId());
                    detail.setBarcode(saveStockTransferModel.getBarcode());
                    details.add(detail);
                }
                requestGRNWithoutPurOrder.setDetails(details);
                sendGRNToServer(requestGRNWithoutPurOrder);
            }
           }
        }

    public void sendGRNToServer(RequestGRNWithoutPurOrder requestGRNWithoutPurOrder){
        progressDialog = createProgressDialog(getContext());
        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(requestGRNWithoutPurOrder),JsonObject.class);

        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        Call<RequestGRNWithoutPurOrder> saveGRNDetailsCall = WebServiceClient
                .setWithoutPurOrderServices(appSetting.getSettingServerURL())
                .setSaveWithoutPurOrderData(data);
        saveGRNDetailsCall.enqueue(new Callback<RequestGRNWithoutPurOrder>() {
            @Override
            public void onResponse(Call<RequestGRNWithoutPurOrder> call, Response<RequestGRNWithoutPurOrder> response) {
                if(response.isSuccessful()){
                    Log.i("data response",response.body().getMsg());
                    Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                    listSaveStockTransferModel.clear();
                    saveStockTransferAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } else{
                    Toast.makeText(getContext(),"Stock Not Saved Successfully",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<RequestGRNWithoutPurOrder> call, Throwable t) {

            }
        });
    }

}