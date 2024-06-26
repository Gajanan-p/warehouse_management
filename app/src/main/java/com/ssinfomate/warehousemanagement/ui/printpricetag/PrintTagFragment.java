package com.ssinfomate.warehousemanagement.ui.printpricetag;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
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
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.PrinterSizeDialog;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.PrinterSizeListener;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStock;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.ui.util.Constants;
import com.ssinfomate.warehousemanagement.ui.util.ListenerZebraPrinter;
import com.ssinfomate.warehousemanagement.ui.util.ZebraPrinterTask;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;
import com.ssinfomate.warehousemanagement.webservices.check_stock.PriceListModel;
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
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrintTagFragment extends Fragment implements
        View.OnClickListener,
        ListenerZebraPrinter,
        PrinterSizeListener,
        EMDKManager.EMDKListener,
        Scanner.DataListener,
        Scanner.StatusListener,
        BarcodeManager.ScannerConnectionListener
{

    public static final String TAG="Print Tag Prince";
    AppCompatEditText appCompatEditTextBarcode;
    AppCompatButton appCompatButtonOK;
    AppCompatButton appCompatButtonPPT;

    private RecyclerView rvItemPrintPriceTag;
    public ArrayList<PriceListModel> listPrintPriceTag=new ArrayList<>();
    CheckStock checkStock=new CheckStock();
    private PrintPriceAdapter printPriceAdapter;
    private ProgressDialog progressDialog;

    Connection mConnection=null;
    ZebraPrinter mZebraPrinter=null;
    ZebraPrinterTask mZebraPrinterTask=null;
    PrinterSizeDialog printerSizeDialog;
    AppCompatTextView appCompatTextViewSelectPrinterSize;

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
    private String TAG_SCANNER="SCANNER Check Stock";
    ArrayList listPrintSizes=new ArrayList();
    AppCompatCheckBox compatCheckBoxEnterManually;
    private String labelSize;
    public static PrintTagFragment newInstance() {
        return new PrintTagFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.print_tag_fragment, container, false);

        appCompatEditTextBarcode=view.findViewById(R.id.edit_print_price_tag_barcode);

        appCompatTextViewSelectPrinterSize=view.findViewById(R.id.select_printer_sizes);
        appCompatTextViewSelectPrinterSize.setOnClickListener(this);

        appCompatButtonOK=view.findViewById(R.id.button_print_price_tag_ok);
        appCompatButtonOK.setOnClickListener(this);

        appCompatButtonPPT=view.findViewById(R.id.button_print_price_tag_PPT);
        appCompatButtonPPT.setOnClickListener(this);

        appCompatEditTextBarcode.requestFocus();
        compatCheckBoxEnterManually = view.findViewById(R.id.checkbox_labelEnter_Manually);
        compatCheckBoxEnterManually.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    appCompatEditTextBarcode.setEnabled(true);
                }
                else{
                    appCompatEditTextBarcode.setEnabled(false);
                }
            }
        });

        rvItemPrintPriceTag=view.findViewById(R.id.rv_item_print_price_tag);

        listPrintSizes.add(Constants.PRINTER_SIZES_SMALL);
        listPrintSizes.add(Constants.PRINTER_SIZES_LARGE);
        listPrintSizes.add(Constants.PRINTER_SIZE_LARGE_OFFER);

        onItemClicked(listPrintSizes.get(0).toString());

        EMDKResults results = EMDKManager.getEMDKManager(getContext().getApplicationContext(), this);
        if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
            updateStatus("EMDKManager object request failed!");
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_print_price_tag_ok){
            if (!TextUtils.isEmpty(appCompatTextViewSelectPrinterSize.getText()))
            {
                if(!TextUtils.isEmpty(appCompatEditTextBarcode.getText())
                ) {
                    SearchPrintPriceTag();
                }else{
                    Toast.makeText(getActivity(),"Please Scan the Barcode",Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(getActivity(),"Please Select Print Tag Report",Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId()==R.id.button_print_price_tag_PPT){
            if(listPrintPriceTag.size()>0){
                onPrintTagClicked(listPrintPriceTag.get(0));
                listPrintPriceTag.clear();
                printPriceAdapter.notifyDataSetChanged();
            }

        }
        if(v.getId()==R.id.select_printer_sizes){
            openSelectPrinterDialog();
        }

        if (v.getId()==R.id.checkbox_labelEnter_Manually){

        }

    }


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


    public void SearchPrintPriceTag(){
        progressDialog = createProgressDialog(getContext());
        UserModel userModel= AppPreference.getLoginDataPreferences(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());

        checkStock.setBarcode(appCompatEditTextBarcode.getText().toString());
        checkStock.setCoBr_Id(userModel.getCobrId());

        Call<ArrayList<PriceListModel>> listCheckStock  =
                WebServiceClient
                        .checkStockService(appSetting.getSettingServerURL())
                        .listPriceList(checkStock);
        listCheckStock.enqueue(new Callback<ArrayList<PriceListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PriceListModel>> call,
                                   Response<ArrayList<PriceListModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).getItemId() != null) {
                        changeDataSet(response.body());
                    } else {
                        Toast.makeText(getContext(), "Item not found", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(getContext(),"Server URL Not Found!",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<PriceListModel>> call, Throwable t) {
                Log.i("error",t.getMessage());
            }
        });


    }

    public void changeDataSet(ArrayList<PriceListModel> checkStockModels){
        listPrintPriceTag=checkStockModels;
        if(listPrintPriceTag!=null){
            printPriceAdapter=new PrintPriceAdapter(listPrintPriceTag,labelSize,getContext());
            rvItemPrintPriceTag.setAdapter(printPriceAdapter);
            rvItemPrintPriceTag.setLayoutManager(new LinearLayoutManager(getContext()));
            appCompatEditTextBarcode.setText("");
        }

    }
//-------------------------------------------------------------------------------------------------------
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
//-------------------------------------------------------------------------------------------------------
    @Override
    public void initZPConnection(String BLUETOOTH_MAC_ADDRESS) {
        if (BLUETOOTH_MAC_ADDRESS!=null || BLUETOOTH_MAC_ADDRESS!="") {
            mZebraPrinterTask = new ZebraPrinterTask(this, BLUETOOTH_MAC_ADDRESS);
            mZebraPrinterTask.initializeZPConnection(BLUETOOTH_MAC_ADDRESS);
        }else{
            Toast.makeText(getContext(),"Set Printer Address",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onZPConnection(Connection connection) {
        mConnection=connection;
        if(mConnection!=null){
            initZebraPrinterConnection(connection);
        }


    }

    @Override
    public void initZebraPrinterConnection(Connection connection) {
        mZebraPrinterTask.initializeZebraPrinterConnection(connection);
    }

    @Override
    public void onZebraPrinterConnection(ZebraPrinter zebraPrinter) {
        mZebraPrinter=zebraPrinter;
    }

    private void sendPrint(String textData) {
        try {
            if(mZebraPrinter!=null) {
                boolean isReady = mZebraPrinterTask.checkPrinterStatus(mZebraPrinter);

                if (isReady) {
                    Log.i(TAG, "Printer the Data");
                    PrinterLanguage printerLanguage = mZebraPrinter.getPrinterControlLanguage();
//                    Toast.makeText(getContext(),printerLanguage.name(),Toast.LENGTH_LONG).show();
//                    PrinterNameTemp.setText(printerLanguage.name());
                    Log.i("Printer Language",printerLanguage.name());
                    SGD.SET("device.languages", "zpl", mConnection);

                    mConnection.write(textData.getBytes());
                    //connection.write(createZplProductPrint().getBytes());
//                sendZplReceipt(connection);
//                String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
//                zebraPrinter.printStoredFormat("Volvo",cars);

                } else {
                    if(mZebraPrinter!=null){
                        mZebraPrinterTask.showPrinterStatus(getContext(), mZebraPrinter);
                    }

                }
            }else{
                Log.i(TAG,"Printer not Connected");
                Toast.makeText(getActivity().getApplicationContext(),"Check Printer Settings",Toast.LENGTH_LONG).show();
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
            Log.i(TAG,this.getString(R.string.print_failed) + " " + this.getString(R.string.no_printer));
        } finally {
            try {
                if(mConnection!=null){
                    mConnection.close();}
            } catch (ConnectionException e) {
                e.printStackTrace();
            }
        }
    }


    public void onPrintTagClicked(PriceListModel checkStockModel) {
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        String getMacAdd= String.valueOf(appSetting.getSettingPrinterMACAdd());
        if (getMacAdd!=null){
//        if(!appSetting.getSettingPrinterMACAdd().isEmpty()){
            initZPConnection(appSetting.getSettingPrinterMACAdd());
            String printLabel=null;
            if(mZebraPrinter!=null) {
                try {
                    mZebraPrinter.sendCommand("! U1 set var \"device.languages\" \"zpl\"\r\n");
                    Thread.sleep(500);
                } catch (ConnectionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (appCompatTextViewSelectPrinterSize.getText().toString().trim()
                        .equals(Constants.PRINTER_SIZES_LARGE.trim())) {
                    printLabel = mZebraPrinterTask.getPrintHorizontalZPLLabel(checkStockModel);
                }
                if (appCompatTextViewSelectPrinterSize.getText().toString().trim()
                        .equals(Constants.PRINTER_SIZES_SMALL.trim())) {
                    printLabel = mZebraPrinterTask.getPrintVerticalZPLLabel(checkStockModel);
                }
                if (appCompatTextViewSelectPrinterSize.getText().toString().trim()
                        .equals(Constants.PRINTER_SIZE_LARGE_OFFER.trim())) {
                    printLabel = mZebraPrinterTask.getPrintHorizontalOfferZPLLabel(checkStockModel);
                }
                if (printLabel != null) {
                    sendPrint(printLabel);
                }
            }

        }else{
            Toast.makeText(getContext(),"Set Printer Address",Toast.LENGTH_LONG).show();
        }
    }

    public void openSelectPrinterDialog(){
        printerSizeDialog=new PrinterSizeDialog(getContext(),listPrintSizes, this);
        printerSizeDialog.show();
    }

    @Override
    public void onItemClicked(String labelSize) {
        this.labelSize=labelSize;
        appCompatTextViewSelectPrinterSize.setText("");
        appCompatTextViewSelectPrinterSize.setText(labelSize);
        if(printerSizeDialog!=null){
        printerSizeDialog.dismiss();}
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

}