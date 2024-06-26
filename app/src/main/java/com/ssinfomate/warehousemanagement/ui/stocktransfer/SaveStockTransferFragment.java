package com.ssinfomate.warehousemanagement.ui.stocktransfer;

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
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStock;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockModel;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.SaveStockTransfer;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.SaveStockTransferModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.Stocktransferdetail;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.AdjustmentTypeModel;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
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

public class SaveStockTransferFragment extends Fragment implements
        EMDKManager.EMDKListener,
        Scanner.DataListener,
        Scanner.StatusListener,
        BarcodeManager.ScannerConnectionListener,
        View.OnClickListener,
        IOnSaveStock,
        IOnSaveStockQuantity {

    String TAG_SCANNER = "Save Stock Scanner";
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
    private AdjustmentTypeModel adjustmentTypeModel;
    private AppCompatEditText editSaveStockTransferBarcode;
    private ProgressDialog progressDialog;
    AppCompatButton appCompatButtonSaveStockOk;
    RecyclerView recyclerView;
    SaveStockTransferAdapter saveStockTransferAdapter;
    ArrayList<SaveStockTransferModel> listSaveStockTransferModel = new ArrayList<>();
    SaveStockQuantityDialog saveStockQuantityDialog;
    CheckStock checkStock = new CheckStock();
    ArrayList<CheckStockModel> listCheckStockModels;
    Button buttonSaveStockTransfer;
    String stkForm="R";
    int negativeStock;
    int checkClQty;
    public static SaveStockTransferFragment newInstance() {
        return new SaveStockTransferFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save_stock_transfer_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_save_stock_transfer);
        editSaveStockTransferBarcode = view.findViewById(R.id.edit_save_stock_transfer_barcode);
        editSaveStockTransferBarcode.setEnabled(false);

        appCompatButtonSaveStockOk = view.findViewById(R.id.save_stock_button_ok);
        appCompatButtonSaveStockOk.setOnClickListener(this);

        listSaveStockTransferModel = new ArrayList<>();

        buttonSaveStockTransfer = view.findViewById(R.id.save_stock_transfer_save);
        buttonSaveStockTransfer.setOnClickListener(this);

        editSaveStockTransferBarcode.requestFocus();
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
//            Set selected scanner
//            spinnerScannerDevices.setSelection(scannerIndex);
//             Initialize scanner

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
//-------------------------------------------------------------------------------------------------------------
public void searchSaveStock() {
    progressDialog = createProgressDialog(getContext());
    UserModel userModel = AppPreference.getLoginDataPreferences(getContext());
    AppSetting appSetting = AppPreference.getSettingDataPreferences(getContext());
    checkStock.setCoBr_Id(userModel.getCobrId());
    checkStock.setBarcode(editSaveStockTransferBarcode.getText().toString());
    Call<ArrayList<CheckStockModel>> listCheckStock =
            WebServiceClient
                    .checkStockService(appSetting.getSettingServerURL())
                    .listCheckStock(checkStock);
    listCheckStock.enqueue(new Callback<ArrayList<CheckStockModel>>() {
        @Override
        public void onResponse(Call<ArrayList<CheckStockModel>> call,
                               Response<ArrayList<CheckStockModel>> response) {
            listCheckStockModels =response.body();
            if (listCheckStockModels.get(0).getItemId()!=null) {
                negativeStock = Integer.parseInt(listCheckStockModels.get(0).getAllowNegStk());
                checkClQty = (int) Double.parseDouble(listCheckStockModels.get(0).getClqty());
                if (stkForm.equals(listCheckStockModels.get(0).getStkFrom())) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Reduce barcode cannot be transfer!").show();
                } else {
                    if (negativeStock == 1) {
                        changeDataSet(listCheckStockModels);
                    } else if (checkClQty > 0) {
                        changeDataSet(listCheckStockModels);
                    } else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Transfer Quantity cannot be greater than system Qty.!").show();
                    }
                }
                editSaveStockTransferBarcode.setText("");
                progressDialog.dismiss();
            }else {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("No have a data for this barcode.!").show();
                return;
            }
        }

        @Override
        public void onFailure(Call<ArrayList<CheckStockModel>> call, Throwable t) {
            Log.i("Error",t.getMessage());
        }
    });
}

    public void changeDataSet(ArrayList<CheckStockModel> checkStockModels) {
        CheckStockModel checkStockModel = checkStockModels.get(0);
        boolean isFound = false;

        if (listSaveStockTransferModel.size() > 0) {
            for (int j = 0; j < listSaveStockTransferModel.size(); j++) {
                if (listSaveStockTransferModel.get(j).getItemName() != null) {
                    if (listSaveStockTransferModel.get(j).getItemName().equals(checkStockModel.getItemName()))
                    {
                        listSaveStockTransferModel.get(j).setScan_quantity(
                                listSaveStockTransferModel.get(j).getScan_quantity() + 1
                        );

                        isFound = true;

                    }
                }
            }
        } else {
            isFound = false;
        }
        if (!isFound) {
            updateListModel(checkStockModel);
        }
        if (listSaveStockTransferModel != null) {
            saveStockTransferAdapter = new SaveStockTransferAdapter(listSaveStockTransferModel, this,getContext());
            recyclerView.setAdapter(saveStockTransferAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }


    }
    //**********************************************************************************************************
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
//----------------------------------------------------------------------------------------------------------------
    void updateListModel(CheckStockModel checkStockModel) {
        SaveStockTransferModel saveStockTransferModel = new SaveStockTransferModel();
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
                    editSaveStockTransferBarcode.setText("");
                    if (dataLength++ > 10) { //Clear the cache after 100 scans
                        dataLength = 0;
                    }
                    editSaveStockTransferBarcode.setText(Html.fromHtml(result));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_stock_button_ok: {
                validationBarcodeText();
                break;
            }
            case R.id.save_stock_transfer_save:{
                createStockTransfer(v);
            }
        }
    }
    public void validationBarcodeText(){
        if (!TextUtils.isEmpty(editSaveStockTransferBarcode.getText())){
            searchSaveStock();
        }else {
            Toast.makeText(getActivity(), "Select Barcode", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStockItemChange(int position) {
        saveStockQuantityDialog = new SaveStockQuantityDialog(
                getContext(),
                    listSaveStockTransferModel.get(position),
                this,
                position

        );
        saveStockQuantityDialog.show();
    }

    @Override
    public void onStockItemRemove(int position) {
        if (listSaveStockTransferModel.size() > 0) {
            listSaveStockTransferModel.remove(position);
            saveStockTransferAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStockItemDamageReason(int position) {

    }

    @Override
    public void onStockItemChange(SaveStockTransferModel model, int position) {
        saveStockQuantityDialog.dismiss();
        if (listSaveStockTransferModel.size() > 0) {
            listSaveStockTransferModel.get(position).setScan_quantity(model.getScan_quantity());
            saveStockTransferAdapter.notifyDataSetChanged();
        }

    }

//    @Override
//    public void onStockDamageItemChange(DamageReasonModel damageReasonModel, int position) {
//
//    }

    void createStockTransfer(View view) {
        SaveStockTransfer saveStockTransfer = new SaveStockTransfer();
        AdjustmentTypeModel  adjustmentTypeModel = new AdjustmentTypeModel();
        ArrayList<Stocktransferdetail> details = new ArrayList<>();
        ToWarehouse warehouseToLocation = AppPreference.getToWarehouseDataPreferences(getContext());
        UserModel userModel = AppPreference.getLoginDataPreferences(getContext());
        saveStockTransfer.setCobrId(userModel.getCobrId());
        saveStockTransfer.setCreatedBy(String.valueOf(userModel.getUserId()));
        saveStockTransfer.setUserid(String.valueOf(userModel.getUserId()));
        saveStockTransfer.setStatus("1");
        saveStockTransfer.setStkType("T");
        saveStockTransfer.setCorrectedByID(userModel.getLedId());
        saveStockTransfer.setTocobrid(warehouseToLocation.getCoBrId());
        saveStockTransfer.setMachName(Build.PRODUCT);
        Stocktransferdetail detail;
        if (listSaveStockTransferModel != null) {
            if (listSaveStockTransferModel.size() > 0) {
                for (int i = 0; i < listSaveStockTransferModel.size(); i++) {
                    detail = new Stocktransferdetail();
                    SaveStockTransferModel saveStockTransferModel = listSaveStockTransferModel.get(i);
                    int clQty = (int)Integer.parseInt(saveStockTransferModel.getAllowNegStk());
                    detail.setBarcode(saveStockTransferModel.getBarcode());
                    detail.setRemark("");
                    if (clQty==1) {
                        detail.setScanQty(saveStockTransferModel.getScan_quantity());
                    }else{Toast.makeText(getContext(),"Transfer Quantity cannot be greater than system Qty.!",Toast.LENGTH_LONG).show();return;}
                    detail.setStockId(saveStockTransferModel.getItemId());
//                    detail.setTransferQty(saveStockTransferModel.getClqty());
//                    detail.setStockId(saveStockTransferModel.getItemId());

//                    detail.setStatus("0");
                    details.add(detail);
                }

                saveStockTransfer.setStocktransferdetail(details);
                sendStockTransferToServer(saveStockTransfer);

            }
        }
    }

    void sendStockTransferToServer(SaveStockTransfer saveStockTransfer) {
        Gson gson = new Gson();
        JsonObject data = gson.fromJson(gson.toJson(saveStockTransfer), JsonObject.class);
        Log.i("data Json", data.toString());

        AppSetting appSetting = AppPreference.getSettingDataPreferences(getContext());
        Call<SaveStockTransfer> saveStockDetailsCall = WebServiceClient
                .getStockTransferService(appSetting.getSettingServerURL())
                .setSaveStockData(data);
        saveStockDetailsCall.enqueue(new Callback<SaveStockTransfer>() {
            @Override
            public void onResponse(Call<SaveStockTransfer> call, Response<SaveStockTransfer> response) {
                if (response.isSuccessful()) {
                    Log.i("data response", response.body().getMsg());
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    listSaveStockTransferModel.clear();
                    saveStockTransferAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getContext(), "Save Stock Not Saved Successfully", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SaveStockTransfer> call, Throwable t) {
                Log.e("data error", t.getMessage());
                Toast.makeText(getContext(), "Save Stock Not Saved Successfully", Toast.LENGTH_LONG).show();
            }
        });

    }

}