package com.ssinfomate.warehousemanagement.ui.stockcorrection;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
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
import android.widget.Toast;

import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStock;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockModel;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.SaveStockTransferModel;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.AdjustmentTypeModel;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.DamageReasonModel;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.DamageReasonModels;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.StockCorrectionRequest;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.TemplItemStockDetail;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.IOnSaveStock;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.IOnSaveStockQuantity;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveStockCorrectionFragment extends Fragment implements EMDKManager.EMDKListener,
        Scanner.DataListener,
        Scanner.StatusListener,
        BarcodeManager.ScannerConnectionListener,
        View.OnClickListener,
        IOnSaveStock,
        IOnSaveStockQuantity,
        IDamageReason ,
        IAdjustmentType
{
    String TAG_SCANNER = "Save Stock Scanner";
    private EMDKManager emdkManager = null;
    private BarcodeManager barcodeManager = null;
    private Scanner scanner = null;
    private List<ScannerInfo> deviceList = null;
    private AdjustmentTypeModel adjustmentTypeModel;

    //private int scannerIndex = 0; // Keep the selected scanner
    private int defaultIndex = 0; // Keep the default scanner
    private int dataLength = 0;
    private String statusString = "";

    private boolean bSoftTriggerSelected = false;
    private boolean bDecoderSettingsChanged = false;
    private boolean bExtScannerDisconnected = false;
    private final Object lock = new Object();

    private AppCompatEditText editSaveStockCorrectionBarcode;
    private AppCompatButton buttonSaveStockCorrectionBarcodeOk;
    private ProgressDialog progressDialog;

    SaveStockCorrectionAdapter saveStockCorrectionAdapter;
    ArrayList<SaveStockTransferModel> listSaveStockCorrectionModels = new ArrayList<>();
    RecyclerView listItemStockCheckCorrection;
    DamageReasonDialog damageReasonDialog;
    AdjustmentTypeDialog dialogAdjustmentType;

    SaveStockCorrectionQuantityDialog saveStockCorrectionQuantityDialog;
    AppCompatButton saveStockCorrectionSave;
    AppCompatTextView appCompatTextViewAdjustmentType;
    ArrayList<CheckStockModel>listCheckStockModels;
    CheckStock checkStock = new CheckStock();

    public static SaveStockCorrectionFragment newInstance() {
        return new SaveStockCorrectionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.save_stock_correction_fragment, container, false);

        editSaveStockCorrectionBarcode = view.findViewById(R.id.edit_save_stock_correction_barcode);
        editSaveStockCorrectionBarcode.setEnabled(false);

        buttonSaveStockCorrectionBarcodeOk = view.findViewById(R.id.button_save_stock_correction_barcode_ok);
        buttonSaveStockCorrectionBarcodeOk.setOnClickListener(this);

        listItemStockCheckCorrection = view.findViewById(R.id.rv_item_stock_correction_list);

        saveStockCorrectionSave = view.findViewById(R.id.save_stock_correction_save);
        saveStockCorrectionSave.setOnClickListener(this);

        appCompatTextViewAdjustmentType = view.findViewById(R.id.text_adjustment_type);
        appCompatTextViewAdjustmentType.setOnClickListener(this);

        editSaveStockCorrectionBarcode.requestFocus();

        EMDKResults results = EMDKManager.getEMDKManager(getContext().getApplicationContext(), this);
        if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
            updateStatus("EMDKManager object request failed!");
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDamageReason();
//        showAdjustmentDia(0);
        getAdjustment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
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
            switch (connectionState) {
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
        } else {
            bExtScannerDisconnected = false;
            status = statusString + " " + scannerNameExtScanner + ":" + statusExtScanner;
            updateStatus(status);
        }
    }

    @Override
    public void onData(ScanDataCollection scanDataCollection) {
        if ((scanDataCollection != null) && (scanDataCollection.getResult() == ScannerResults.SUCCESS)) {
            ArrayList<ScanDataCollection.ScanData> scanData = scanDataCollection.getScanData();
            for (ScanDataCollection.ScanData data : scanData) {
//                updateData("<font color='gray'>" + data.getLabelType() + "</font> : " + data.getData());
                updateData(data.getData());
            }
        }
    }

    private void updateData(final String result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result != null) {
                    editSaveStockCorrectionBarcode.setText("");
                    if (dataLength++ > 10) { //Clear the cache after 100 scans
                        dataLength = 0;
                    }
                    editSaveStockCorrectionBarcode.setText(Html.fromHtml(result));
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
        switch (state) {
            case IDLE:
                statusString = statusData.getFriendlyName() + " is enabled and idle...";
                updateStatus(statusString);
                // set trigger type
                if (bSoftTriggerSelected) {
                    scanner.triggerType = Scanner.TriggerType.SOFT_ONCE;
                    bSoftTriggerSelected = false;
                } else {
                    scanner.triggerType = Scanner.TriggerType.HARD;
                }
                // set decoders
                if (bDecoderSettingsChanged) {
                    setDecoders();
                    bDecoderSettingsChanged = false;
                }
                // submit read
                if (!scanner.isReadPending() && !bExtScannerDisconnected) {
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
                statusString = statusData.getFriendlyName() + " is disabled.";
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


    private void updateStatus(final String status) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                textViewStatus.setText("" + status);
                Log.i(TAG_SCANNER, status);
            }
        });
    }

    private void initScanner() {
        if (scanner == null) {
            if ((deviceList != null) && (deviceList.size() != 0)) {
                if (barcodeManager != null)
                    scanner = barcodeManager.getDevice(deviceList.get(defaultIndex));
            } else {
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
            } else {
                updateStatus("Failed to initialize the scanner device.");
            }
        }
    }

    private void deInitScanner() {
        if (scanner != null) {
            try {
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
            try {
                scanner.release();
            } catch (Exception e) {
                updateStatus(e.getMessage());
            }
            scanner = null;
        }
    }

    private void initBarcodeManager() {
        barcodeManager = (BarcodeManager) emdkManager.getInstance(EMDKManager.FEATURE_TYPE.BARCODE);
        // Add connection listener
        if (barcodeManager != null) {
            barcodeManager.addConnectionListener(this);
        }
    }

    private void deInitBarcodeManager() {
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
                while (it.hasNext()) {
                    ScannerInfo scnInfo = it.next();
                    friendlyNameList.add(scnInfo.getFriendlyName());
                    if (scnInfo.isDefaultScanner()) {
                        defaultIndex = spinnerIndex;
                    }
                    ++spinnerIndex;
                }
            } else {
                updateStatus("Failed to get the list of supported scanner devices! Please close and restart the application.");
            }
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

    // use button soft scan
    public void softScan(View view) {
        bSoftTriggerSelected = true;
        cancelRead();
    }

    private void cancelRead() {
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
//-------------------------------------------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_stock_correction_save:{
                createStockCorrection();
                break;
            }
            case R.id.button_save_stock_correction_barcode_ok:{
                validationBarcodeText();
                break;
            }
            case R.id.text_adjustment_type:
                showAdjustmentDia(0);
                break;
        }

    }
    public void validationBarcodeText(){
        if (!TextUtils.isEmpty(appCompatTextViewAdjustmentType.getText())&&
              !TextUtils.isEmpty(editSaveStockCorrectionBarcode.getText())){
            SearchItemStock();
        }else {
            Toast.makeText(getContext(), "Select Adjustment Type", Toast.LENGTH_SHORT)
                    .show();
        }

    }
//----------------------------------------------------------------------------------------------------------
 public void SearchItemStock() {
    AdjustmentTypeModel model = AppPreference.getAdjustmentTypeDataPreferences(getContext());
    progressDialog =createProgressDialog(getContext());
    AppSetting appSetting = AppPreference.getSettingDataPreferences(getContext());
    WarehouseModel warehouseModel = AppPreference.getBusinessLocationDataPreferences(getContext());

    checkStock.setCoBr_Id(warehouseModel.getCobrId());
    checkStock.setBarcode(editSaveStockCorrectionBarcode.getText().toString());

    Call<ArrayList<CheckStockModel>> listCheckStock =
            WebServiceClient
                    .checkStockService(appSetting.getSettingServerURL())
                    .listCheckStock(checkStock);
    listCheckStock.enqueue(new Callback<ArrayList<CheckStockModel>>() {
        @Override
        public void onResponse(Call<ArrayList<CheckStockModel>> call,
                               Response<ArrayList<CheckStockModel>> response) {
            listCheckStockModels = response.body();

            if (listCheckStockModels.size()>0){
                changeDataSet(listCheckStockModels);
            }else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("No have a data for this barcode.!").show();
                return;
            }


            progressDialog.dismiss();
            editSaveStockCorrectionBarcode.setText("");

        }

        @Override
        public void onFailure(Call<ArrayList<CheckStockModel>> call, Throwable t) {
            Log.e("data error",t.getMessage());
        }
    });
}
//----------------------------------------------------------------------------------------------------------
    public void changeDataSet(ArrayList<CheckStockModel> checkStockModels) {
        CheckStockModel checkStockModel = checkStockModels.get(0);
        boolean isFund = false;
            if (listSaveStockCorrectionModels.size() > 0) {
                for (int j = 0; j < listSaveStockCorrectionModels.size(); j++) {
                    if (listSaveStockCorrectionModels.get(j).getItemName().equals(checkStockModel.getItemName())) {
                        listSaveStockCorrectionModels.get(j).setScan_quantity(
                                listSaveStockCorrectionModels.get(j).getScan_quantity() + 1
                        );
                        isFund = true;
                    }
                }
            } else {
                isFund = false;
            }
        if (!isFund) {
            updateListModel(checkStockModel);
        }
        if (listSaveStockCorrectionModels.get(0).getItemId() != null) {
            saveStockCorrectionAdapter = new SaveStockCorrectionAdapter(listSaveStockCorrectionModels, this,getContext(),adjustmentTypeModel);
            listItemStockCheckCorrection.setAdapter(saveStockCorrectionAdapter);
            listItemStockCheckCorrection.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            Toast.makeText(getContext(),"Item not found !",Toast.LENGTH_LONG).show();
        }
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
//------------------------------------------getDamageReason()-----------------------------------------------------------
void getDamageReason(){
    Gson gson = new Gson();
    JsonObject data = gson.fromJson("{\"cobr_id\": \"01\"}", JsonObject.class);
    Log.i("data Json", data.toString());

    AppSetting appSetting = AppPreference.getSettingDataPreferences(getContext());
    Call<ArrayList<DamageReasonModels>> damageReasonModelsCall = WebServiceClient
            .iHeldStockCorrection(appSetting.getSettingServerURL())
            .setDamageReasonModels(data);
    damageReasonModelsCall.enqueue(new Callback<ArrayList<DamageReasonModels>>() {
        @Override
        public void onResponse(Call<ArrayList<DamageReasonModels>> call,
                               Response<ArrayList<DamageReasonModels>> response) {
            if (response.isSuccessful()) {
                AppPreference.setDamageReasonDataPreferences(getContext(),response.body());
                // Toast.makeText(getContext(),"Data Save Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Save Stock Correction Not Saved Successfully", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<ArrayList<DamageReasonModels>> call, Throwable t) {
            Log.e("data error", t.getMessage());
            Toast.makeText(getContext(), "Save Stock Correction Not Saved Successfully", Toast.LENGTH_LONG).show();
        }
    });
}
//-----------------------------------------createStockCorrection()------------------------------------------------

    void createStockCorrection() {
        StockCorrectionRequest stockCorrectionRequest = new StockCorrectionRequest();
       // DamageReasonModels damageReasonModel = new DamageReasonModels();
        ArrayList<TemplItemStockDetail> details = new ArrayList<>();
        WarehouseModel warehouseBusinessLocation = AppPreference.getBusinessLocationDataPreferences(getContext());
      //  WarehouseModel warehouseFromLocation = AppPreference.getFromWarehouseDataPreferences(getContext());
      //  ToWarehouse warehouseToLocation = AppPreference.getToWarehouseDataPreferences(getContext());
        UserModel userModel = AppPreference.getLoginDataPreferences(getContext());

        stockCorrectionRequest.setCobrId(userModel.getCobrId());
      //  stockCorrectionRequest.setCobrId(warehouseBusinessLocation.getCobrId());
        stockCorrectionRequest.setCreatedBy(String.valueOf(userModel.getUserId()));
        stockCorrectionRequest.setUserid(String.valueOf(userModel.getUserId()));
        stockCorrectionRequest.setCorrectedByID(userModel.getLedId());
       // stockCorrectionRequest.setStkType(damageReasonModel.getReason());
        stockCorrectionRequest.setStkType(adjustmentTypeModel.getId());
        stockCorrectionRequest.setMachName(Build.PRODUCT);
        stockCorrectionRequest.setStatus("0");

        TemplItemStockDetail templItemStockDetail;
        if (listSaveStockCorrectionModels != null) {
            if (listSaveStockCorrectionModels.size() > 0) {
                for (int i = 0; i < listSaveStockCorrectionModels.size(); i++) {
                    templItemStockDetail = new TemplItemStockDetail();
                    SaveStockTransferModel saveStockTransferModel = listSaveStockCorrectionModels.get(i);
                    templItemStockDetail.setBusinessLocation(warehouseBusinessLocation.getCobrId());
                    templItemStockDetail.setStockId(saveStockTransferModel.getItemId());
                    int qt = Math.round(Float.parseFloat(saveStockTransferModel.getClqty()));
                    templItemStockDetail.setScanQty(String.valueOf(saveStockTransferModel.getScan_quantity()));//****clqty
                    templItemStockDetail.setBarcode(saveStockTransferModel.getBarcode());
                    if (saveStockTransferModel.getDamageReasonName()==null){
                        Toast.makeText(getContext(),"Select Damage Reason",Toast.LENGTH_LONG).show();
                        return;
                    }
                    templItemStockDetail.setRemark(saveStockTransferModel.getDamageReasonName());
                    details.add(templItemStockDetail);
                }

                stockCorrectionRequest.setTemplItemStockDetail(details);
                sendStockTransferToServer(stockCorrectionRequest);

            }
        }
    }

//-----------------------------------------------------------------------------------------------------------
    void sendStockTransferToServer(StockCorrectionRequest stockCorrectionRequest) {
        progressDialog = createProgressDialog(getContext());
        Gson gson = new Gson();
        JsonObject data = gson.fromJson(gson.toJson(stockCorrectionRequest), JsonObject.class);
        Log.i("data Json", data.toString());

        AppSetting appSetting = AppPreference.getSettingDataPreferences(getContext());
        Call<StockCorrectionRequest> saveStockDetailsCall = WebServiceClient
                .iHeldStockCorrection(appSetting.getSettingServerURL())
                .setStockCorrectionModel(data);
        saveStockDetailsCall.enqueue(new Callback<StockCorrectionRequest>() {
            @Override
            public void onResponse(Call<StockCorrectionRequest> call, Response<StockCorrectionRequest> response) {
                if (response.isSuccessful()) {
                    Log.i("data response", response.body().getMsg());
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    listSaveStockCorrectionModels.clear();
                    saveStockCorrectionAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getContext(), "Save Stock Correction Not Saved Successfully", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<StockCorrectionRequest> call, Throwable t) {
                Log.e("data error", t.getMessage());
                Toast.makeText(getContext(), "Save Stock Correction Not Saved Successfully", Toast.LENGTH_LONG).show();
            }
        });

    }

//*******************************************************************************************************************
    void updateListModel(CheckStockModel checkStockModel) {
        SaveStockTransferModel saveStockTransferModel = new SaveStockTransferModel();
        saveStockTransferModel.setCoBrId(checkStockModel.getCoBrId());
        saveStockTransferModel.setAltbarcode1(checkStockModel.getAltbarcode1());
        saveStockTransferModel.setBarcode(checkStockModel.getBarcode());
        saveStockTransferModel.setClqty(checkStockModel.getClqty());
        saveStockTransferModel.setConvQty(checkStockModel.getConvQty());
        saveStockTransferModel.setItemCode(checkStockModel.getItemCode());
        saveStockTransferModel.setItemId(checkStockModel.getItemId()) ;
        saveStockTransferModel.setItemName(checkStockModel.getItemName());
        saveStockTransferModel.setItemStatus(checkStockModel.getItemStatus());
        saveStockTransferModel.setItemsubgrpId(checkStockModel.getItemsubgrpId()) ;
        saveStockTransferModel.setItemsubgrpName(checkStockModel.getItemsubgrpName());
        saveStockTransferModel.setUnitName(checkStockModel.getUnitName());
        saveStockTransferModel.setScan_quantity(1);
        saveStockTransferModel.setMrp(checkStockModel.getMrp());

        listSaveStockCorrectionModels.add(saveStockTransferModel);
    }

    @Override
    public void onStockItemChange(int position) {
        saveStockCorrectionQuantityDialog = new SaveStockCorrectionQuantityDialog(
                getContext(),
                listSaveStockCorrectionModels.get(position),
                this,
                position
        );
        saveStockCorrectionQuantityDialog.show();
    }

    @Override
    public void onStockItemRemove(int position) {
        if (listSaveStockCorrectionModels.size() > 0) {
            listSaveStockCorrectionModels.remove(position);
            saveStockCorrectionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStockItemDamageReason(int position) {
        List<DamageReasonModels> damageReasonModel= AppPreference.getDamageReasonDataPreferences(getContext());

        damageReasonDialog = new DamageReasonDialog(
                getContext(),
                damageReasonModel,
                SaveStockCorrectionFragment.this,
                position
        );
        damageReasonDialog.show();

   //        damageReasonDialog = new DamageReasonDialog(
//                getContext(),
//                DamageReasonModel.getDamageReasonList(),
//                SaveStockCorrectionFragment.this,
//                position
//        );
//        damageReasonDialog.show();

    }

    @Override
    public void onStockItemChange(SaveStockTransferModel model, int position) {
        saveStockCorrectionQuantityDialog.dismiss();
        if (listSaveStockCorrectionModels.size() > 0) {
             listSaveStockCorrectionModels.get(position).setScan_quantity
                     (model.getScan_quantity());
            saveStockCorrectionAdapter.notifyDataSetChanged();
        }

    }

//    @Override
//    public void onStockDamageItemChange(DamageReasonModel damageReasonModel, int position) {
//        int i = damageReasonModel.getId();
//        Log.i("data", damageReasonModel.getReason());
//    }

    @Override
    public void onDamageReasonClicked(DamageReasonModel damageReasonModel, int position) {
        updateDamageReason(damageReasonModel, position);
    }

    void updateDamageReason(DamageReasonModel damageReasonModel, int position) {
        listSaveStockCorrectionModels.get(position).setDamageReasonName(damageReasonModel.getReason());
        listSaveStockCorrectionModels.get(position).setDamageReasonId(String.valueOf(damageReasonModel.getId()));
        saveStockCorrectionAdapter.notifyDataSetChanged();
        damageReasonDialog.dismiss();

    }

    void showAdjustmentDia(int position){
        dialogAdjustmentType = new AdjustmentTypeDialog(
                getContext(),
                 AdjustmentTypeModel.getAdjustmentTypeList(),
                SaveStockCorrectionFragment.this,position
               );

        dialogAdjustmentType.show();
    }
    void getAdjustment(){
        ArrayList<AdjustmentTypeModel> models = AdjustmentTypeModel.getAdjustmentTypeList();
       appCompatTextViewAdjustmentType.setText(models.get(0).getReason());
       this.adjustmentTypeModel =models.get(0);
    }

    @Override
    public void onAdjustmentTypeClicked(AdjustmentTypeModel adjustmentTypeModel, int position) {
       this.adjustmentTypeModel=adjustmentTypeModel;
       appCompatTextViewAdjustmentType.setText(adjustmentTypeModel.getReason());
       if (dialogAdjustmentType!=null){
        dialogAdjustmentType.dismiss();
       }
    }
}