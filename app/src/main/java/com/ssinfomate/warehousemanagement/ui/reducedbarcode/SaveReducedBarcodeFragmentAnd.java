package com.ssinfomate.warehousemanagement.ui.reducedbarcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.PrinterSizeListener;
import com.ssinfomate.warehousemanagement.ui.reducedbarcode.printrecucedbarcode.INewReducedBarcode;
import com.ssinfomate.warehousemanagement.ui.reducedbarcode.printrecucedbarcode.NewSaveReducedAdapter;
import com.ssinfomate.warehousemanagement.ui.reducedbarcode.printrecucedbarcode.ReducedBarPrinterTask;
import com.ssinfomate.warehousemanagement.ui.util.Constants;
import com.ssinfomate.warehousemanagement.ui.util.ListenerZebraPrinter;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStock;
import com.ssinfomate.warehousemanagement.webservices.check_stock.PriceListModel;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.RateCatListModel;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.ReducedBarcodeRemarkList;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.RequestSaveReducedBarcode;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.RequestSaveReducedBarcodeDetail;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.SaveReducedBarcodeModel;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.SaveStockTransferModel;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SaveReducedBarcodeFragmentAnd extends Fragment implements
        View.OnClickListener,
        IOnSaveReducedBarcode,
        IUpdateRateQuantity,
        IRemarkListener,
        ListenerZebraPrinter,
        PrinterSizeListener,
        INewReducedBarcode
{
    NavController navController;
    String TAG_SCANNER="Save Stock Scanner";

    private int dataLength = 0;
    private final Object lock = new Object();
    private AppCompatEditText editSaveReducedBarcode;
    private ProgressDialog progressDialog;
//----------------------------------------------------------------------------------
    public static final String TAG="Print Tag Prince";
    Connection mConnection=null;
    ZebraPrinter mZebraPrinter=null;
    ReducedBarPrinterTask mZebraPrinterTask=null;
    AppCompatTextView appCompatTextViewSelectPrinterSize;

    ArrayList listPrintSizes=new ArrayList();
    NewSaveReducedAdapter adapter;

    ArrayList<SaveReducedBarcodeModel>saveReducedBarcodeModels;
    RecyclerView listReducedBarcode;
//------------------------------------------------------------------------------------
    SaveReducedBarcodeAdapter saveReducedBarcodeAdapter;
    SaveRedBarRateChangeDialog saveRedBarRateChangeDialog;
    RecyclerView listSaveReducedBarcode;
    ArrayList<PriceListModel> listItemStock ;
    ArrayList<RequestSaveReducedBarcodeDetail> list;
    CheckStock checkStock=new CheckStock();
    AppCompatButton buttonSaveReducedBarcodeButtonOk;
    Button buttonSaveReducedBarcode;
    RemarkDialog remarkDialog;
    List<ReducedBarcodeRemarkList>  remarkList ;
    ArrayList<PriceListModel> listSaveMultipleBarcode = new ArrayList<>();
    LinearLayout ContainerSaveReduceBarcode;
    LinearLayout ContainerPrintReduceBarcode;

    int checkClQty;
    int negativeStock;
    public static SaveReducedBarcodeFragmentAnd newInstance() {
        return new SaveReducedBarcodeFragmentAnd();
    }

    private SaveReducedBarcodeModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save_reduced_barcode_fragment, container, false);

        editSaveReducedBarcode = view.findViewById(R.id.edit_save_reduced_barcode);
        editSaveReducedBarcode.setOnClickListener(this);
        listSaveReducedBarcode = view .findViewById(R.id.recycler_reduced_barcode_save);

        buttonSaveReducedBarcodeButtonOk = view.findViewById(R.id.save_reduced_barcode_button_ok);
        buttonSaveReducedBarcodeButtonOk.setOnClickListener(this);

        buttonSaveReducedBarcode = view.findViewById(R.id.save_reduced_barcode);
        buttonSaveReducedBarcode.setOnClickListener(this::createReducedBarcode);

        listItemStock = new ArrayList<>();
        editSaveReducedBarcode.requestFocus();
        ContainerSaveReduceBarcode = view.findViewById(R.id.linear_save_reduced_barcode);
        ContainerPrintReduceBarcode = view.findViewById(R.id.linear_print_reduced_barcode);

        listSaveMultipleBarcode = new ArrayList<>();
//        -------------------------------------------------------

        listPrintSizes.add(Constants.PRINTER_SIZES_SMALL);
//        saveReducedBarcodeModels=AppPreference.getNewReducedStockDataPreferences(getContext());
        listReducedBarcode= view.findViewById(R.id.list_item_reduced_barcode);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(SaveReducedBarcodeModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
    }

    public void onSaveReducedBarcodeContainer(){
        ContainerSaveReduceBarcode.setVisibility(View.VISIBLE);
        ContainerPrintReduceBarcode.setVisibility(View.GONE);
    }
    public void onPrintReducedBarcodeContainer(){
        ContainerSaveReduceBarcode.setVisibility(View.GONE);
        ContainerPrintReduceBarcode.setVisibility(View.VISIBLE);
    }

    public void onButtonStartReducedPrice() {
            navController.navigate(R.id.action_saveReducedBarcodeFragment_to_printReducedBarcodeFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        getRemark();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_reduced_barcode_button_ok:{
                validationButtonOk();
            }
            case R.id.save_reduced_barcode:{
              //   onButtonStartReducedPrice();
                break;
            }
//            case R.id.edit_save_reduced_barcode:
//                ((MainActivity) getActivity()).startScanActivity(editSaveReducedBarcode);
//                break;
        }
    }
    void  validationButtonOk(){
        if (!TextUtils.isEmpty(editSaveReducedBarcode.getText())) {
            searchSaveStock();
        }else
        {
            Toast.makeText(getContext(),"Scan the Barcode",Toast.LENGTH_SHORT).show();
        }

    }
/*...........*/

    public void searchSaveStock(){
        progressDialog = createProgressDialog(getContext());
        UserModel userModel= AppPreference.getLoginDataPreferences(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        checkStock.setCoBr_Id(userModel.getCobrId());
        checkStock.setBarcode(editSaveReducedBarcode.getText().toString());

        Call<ArrayList<PriceListModel>> listCheckStock  =
                WebServiceClient
                        .checkStockService(appSetting.getSettingServerURL())
                        .listPriceList(checkStock);
        listCheckStock.enqueue(new Callback<ArrayList<PriceListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PriceListModel>> call,
                                   Response<ArrayList<PriceListModel>> response) {

                listItemStock = response.body();

                if (listItemStock.get(0).getCoBrId()!=null) {
                    negativeStock = (int) Integer.parseInt(listItemStock.get(0).getAllowNegStk());
                    checkClQty =(int) Double.parseDouble(listItemStock.get(0).getClqty());
                    if (negativeStock == 1) {
                        changeDataSet(listItemStock);
                    } else if (checkClQty>0) {
                        changeDataSet(listItemStock);
                    }else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("No. of sticker print cannot be greater than Total Qty.!").show();
                    }
                }else {
                    Toast.makeText(getContext(),"Item not found !",Toast.LENGTH_LONG).show();
                    editSaveReducedBarcode.setText("");
                    progressDialog.dismiss();

                }
                progressDialog.dismiss();
                editSaveReducedBarcode.setText("");
            }
            @Override
            public void onFailure(Call<ArrayList<PriceListModel>> call, Throwable t) {

            }
        });


    }

    public void changeDataSet(ArrayList<PriceListModel> checkStockModels) {
        PriceListModel checkStockModel = checkStockModels.get(0);
        boolean isFund = false;
        if (listItemStock.size() > 0) {
            for (int j = 0; j < listItemStock.size(); j++) {
                if (listItemStock.get(j).getItemName()
                        .equals(checkStockModel.getItemName())) {
//                    checkStockModels.get(j).setScan_quantity(
//                            checkStockModels.get(j).getScan_quantity() + 1
//                    );
                    isFund = true;
                }
            }

        } else {
            isFund = false;
        }
        if (!isFund) {
          updateListModel(checkStockModel);
        }
        if (listItemStock.get(0).getItemId() != null) {
            saveReducedBarcodeAdapter = new SaveReducedBarcodeAdapter(listItemStock,this,getContext());
            listSaveReducedBarcode.setAdapter(saveReducedBarcodeAdapter);
            listSaveReducedBarcode.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else {
            Toast.makeText(getContext(),"Item not found !",Toast.LENGTH_LONG).show();
        }
    }

    public void updateListModel(PriceListModel checkStockModel) {
        PriceListModel checkStockModel1 = new PriceListModel();
        checkStockModel1.setCoBrId(checkStockModel.getCoBrId());
        checkStockModel1.setAltbarcode1(checkStockModel.getAltbarcode1());
        checkStockModel1.setBarcode(checkStockModel.getBarcode());
        checkStockModel1.setClqty(checkStockModel.getClqty());
        checkStockModel1.setConvQty(checkStockModel.getConvQty());
        checkStockModel1.setItemCode(checkStockModel.getItemCode());
        checkStockModel1.setItemId(checkStockModel.getItemId());
        checkStockModel1.setItemName(checkStockModel.getItemName());
        checkStockModel1.setItemStatus(checkStockModel.getItemStatus());
        checkStockModel1.setItemsubgrpId(checkStockModel.getItemsubgrpId());
        checkStockModel1.setItemsubgrpName(checkStockModel.getItemsubgrpName());
        checkStockModel1.setUnitName(checkStockModel.getUnitName());
        checkStockModel1.setClqty(checkStockModel.getClqty());
        checkStockModel1.setMrp(checkStockModel.getMrp());
        checkStockModel1.setSaleRate(checkStockModel.getSaleRate());
        checkStockModel1.setSaleRateBeforeTax(checkStockModel.getSaleRateBeforeTax());
        checkStockModel1.setAllowNegStk(checkStockModel.getAllowNegStk());

        listItemStock.add(checkStockModel1);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onStockItemUpdate(int position) {
        progressDialog = createProgressDialog(getContext());
        saveRedBarRateChangeDialog = new SaveRedBarRateChangeDialog(
                getContext(),
                listItemStock.get(position),
                this,
                position
        );
        progressDialog.dismiss();
        saveRedBarRateChangeDialog.show();
    }

    @Override
    public void onUpdateRateChange(PriceListModel checkStockModel, int position) {

        saveRedBarRateChangeDialog.dismiss();
        if (listItemStock.size() > 0) {
            listItemStock.get(position).setNewsalerate
                    (checkStockModel.getNewsalerate());

            listItemStock.get(position).setNoofstrickertoprint(
                    checkStockModel.getNoofstrickertoprint()
            );
            listItemStock.get(position).setRateper(
                    checkStockModel.getRateper()
            );

        }else {
            listItemStock.get(position).setNewsaleratebeforetax(
                    checkStockModel.getNewsaleratebeforetax()

            );
            listItemStock.get(position).setNoofstrickertoprint(
                    checkStockModel.getNoofstrickertoprint()
            );

            listItemStock.get(position).setRateper(
                    checkStockModel.getRateper()
            );
        }
        saveReducedBarcodeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChangeNewRate(RequestSaveReducedBarcodeDetail requestSaveReducedBarcodeDetail, int position) {

        saveRedBarRateChangeDialog.dismiss();
        if (list.size() > 0) {
            list.get(position).setNewsalerate
                    (requestSaveReducedBarcodeDetail.getNewsalerate());
        }else {
            list.get(position).setNewsaleratebeforetax(
                    requestSaveReducedBarcodeDetail.getNewsaleratebeforetax()
            );
        }
        saveReducedBarcodeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStockItemRemove(int position) {
    }

    void getRemark(){
        AppSetting appSetting = AppPreference.getSettingDataPreferences(getContext());
        Call<ArrayList<ReducedBarcodeRemarkList>> arrayListCall = WebServiceClient
                .getRemarkList(appSetting.getSettingServerURL())
                .getRemarkList();
        arrayListCall.enqueue(new Callback<ArrayList<ReducedBarcodeRemarkList>>() {
            @Override
            public void onResponse(Call<ArrayList<ReducedBarcodeRemarkList>> call,
                                   Response<ArrayList<ReducedBarcodeRemarkList>> response) {
                if (response.isSuccessful()) {
                    AppPreference.setRemarkDataPreferences(getContext(),response.body());
                    // Toast.makeText(getContext(),"Data Save Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Save Stock Correction Not Saved Successfully", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReducedBarcodeRemarkList>> call, Throwable t) {
                Log.e("data error", t.getMessage());
                Toast.makeText(getContext(), "Save Stock Correction Not Saved Successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRemarkClicked(int position) {
        List<ReducedBarcodeRemarkList> reducedBarcodeRemarkLists = AppPreference.getRemarkPreferences(getContext());
        remarkDialog = new RemarkDialog(getContext(),
                reducedBarcodeRemarkLists,
                SaveReducedBarcodeFragmentAnd.this,
                position);
        remarkDialog.show();
        //remarkDialog = new RemarkDialog()
    }


    @Override
    public void onRemarkNameClicked(ReducedBarcodeRemarkList reducedBarcodeRemarkList, int position) {
        updateReducedBarcodeRemark(reducedBarcodeRemarkList,position);
    }
    void updateReducedBarcodeRemark(ReducedBarcodeRemarkList reducedBarcodeRemarkList, int position) {
        listItemStock.get(0).setRemarkId(reducedBarcodeRemarkList.getRemarkId());
        listItemStock.get(0).setRemarkName(String.valueOf(reducedBarcodeRemarkList.getRemarkName()));
        saveReducedBarcodeAdapter.notifyDataSetChanged();
        remarkDialog.dismiss();

    }

//------------------------------------------------------------------------------------------

    public void createReducedBarcode(View view) {
        remarkList =  AppPreference.getRemarkPreferences(getContext());
        RequestSaveReducedBarcode requestSaveReducedBarcode = new RequestSaveReducedBarcode();
        ArrayList<RequestSaveReducedBarcodeDetail> details = new ArrayList<>();
        UserModel userModel = AppPreference.getLoginDataPreferences(getContext());

        requestSaveReducedBarcode.setCoBrId(userModel.getCobrId());
        requestSaveReducedBarcode.setRatecatId("0");
        requestSaveReducedBarcode.setCreatedBy(userModel.getUserId().toString());
        requestSaveReducedBarcode.setMachName(Build.PRODUCT);

        RequestSaveReducedBarcodeDetail detail;
        if(listItemStock!=null){
            if(listItemStock.size()>0){
                for(int i=0;i<listItemStock.size();i++){
                    detail=new RequestSaveReducedBarcodeDetail();
                    PriceListModel checkStockModel=listItemStock.get(i);
                    int totalClQty = (int)Integer.parseInt(checkStockModel.getAllowNegStk());
                    detail.setItemId(checkStockModel.getStkKey().toString());
                    detail.setNewsalerate(checkStockModel.getNewsalerate());
                    detail.setOldSaleRate(checkStockModel.getSaleRate());
                    detail.setOldSaleRateBeforeTax(checkStockModel.getSaleRateBeforeTax());
                    detail.setNewsaleratebeforetax(checkStockModel.getNewsaleratebeforetax());
                    if (totalClQty==1 ) {
                        detail.setNoofstrickertoprint(checkStockModel.getNoofstrickertoprint());

                    }else if (checkClQty>0){
                        detail.setNoofstrickertoprint(checkStockModel.getNoofstrickertoprint());
                    }else {
                        Toast.makeText(getContext(),"No. of sticker print cannot be greater than Total Qty.! ",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (checkStockModel.getRateper()==null) {
                        Toast.makeText(getContext(),"Please check remark and percentage and no of stickers ",Toast.LENGTH_LONG).show();
                        return;
                    }
                    detail.setRatePerc(checkStockModel.getRateper());
                    detail.setBarcode(checkStock.getBarcode());
                    if (checkStockModel.getRemarkId()==null){
                        Toast.makeText(getContext(),"Please check remark and percentage and no of stickers",Toast.LENGTH_LONG).show();
                        return;
                    }
                    detail.setRemark(checkStockModel.getRemarkName());
                    if (checkStockModel.getStkcatrtId()==null) {
                        detail.setStkcatrtIds("0");
                    }else {
                        detail.setStkcatrtIds(checkStockModel.getStkcatrtId());
                    }

                    details.add(detail);
                }
                requestSaveReducedBarcode.setDetails(details);
                sendReducedBarcodeDataToServer(requestSaveReducedBarcode);
            }
        }
    }

    public void sendReducedBarcodeDataToServer(RequestSaveReducedBarcode requestSaveReducedBarcode){
        // multiple
        progressDialog = createProgressDialog(getContext());
        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(requestSaveReducedBarcode),JsonObject.class);
        Log.i("data Json",data.toString());

        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        Call<ArrayList<SaveReducedBarcodeModel>> saveStockDetailsCall=WebServiceClient
                .getReducedBarcode(appSetting.getSettingServerURL())
                .setSaveReducedBarcodeData(data);
        saveStockDetailsCall.enqueue(new Callback<ArrayList<SaveReducedBarcodeModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SaveReducedBarcodeModel>> call,
                                   Response<ArrayList<SaveReducedBarcodeModel>> response) {
                if(response.isSuccessful()){
                    saveReducedBarcodeModels = response.body();
                    Toast.makeText(getContext(),"Save Successfully",Toast.LENGTH_LONG).show();
                    listItemStock.clear();
                    saveReducedBarcodeAdapter.notifyDataSetChanged();
                    AppPreference.setNewReducedStockDataPreferences(getContext(),response.body());
                    onPrintReducedBarcodeContainer();
                    UpdateGeneralList(saveReducedBarcodeModels);
//                    buttonSaveReducedBarcode.setClickable(false);
                      progressDialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"Stock Not Saved Successfully",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<SaveReducedBarcodeModel>> call, Throwable t) {
                Log.e("data error",t.getMessage());
                Toast.makeText(getContext(),"Stock Not Saved Successfully",Toast.LENGTH_LONG).show();
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



//------------------------------------------------------------------------------------------------

    private void UpdateGeneralList(ArrayList<SaveReducedBarcodeModel> saveReducedBarcodeModels) {

    if (saveReducedBarcodeModels!=null) {
        adapter = new NewSaveReducedAdapter(saveReducedBarcodeModels,this);
        listReducedBarcode.setAdapter(adapter);
        listReducedBarcode.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}

    @Override
    public void initZPConnection(String BLUETOOTH_MAC_ADDRESS) {
        mZebraPrinterTask = new ReducedBarPrinterTask(this, BLUETOOTH_MAC_ADDRESS);
        mZebraPrinterTask.initializeZPConnection(BLUETOOTH_MAC_ADDRESS);
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

//                connection.write(createZplProductPrint().getBytes());
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

        public void onPrintTagClicked(SaveReducedBarcodeModel saveReducedBarcodeModel) {
            AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
            String testMacAdd=String.valueOf(appSetting.getSettingPrinterMACAdd());
            if (testMacAdd!=null){
//        if(!appSetting.getSettingPrinterMACAdd().isEmpty()){
                initZPConnection(appSetting.getSettingPrinterMACAdd());
                String printLabel=null;
                if(mZebraPrinter!=null) {
                    try {
                        mZebraPrinter.sendCommand("! U1 set var \"device.languages\" \"zpl\"\r\n");
                        Thread.sleep(0);
                    } catch (ConnectionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (saveReducedBarcodeModels!=null) {
                        printLabel = mZebraPrinterTask.getPrintVerticalZPLLabel(saveReducedBarcodeModel);
                    }
                    if (printLabel != null) {
                        sendPrint(printLabel);
                        //  saveReducedBarcodeModels.clear();
                    }
                }
            }else{
                Toast.makeText(getContext(),"Set Printer Address",Toast.LENGTH_LONG).show();
            }
            adapter.notifyDataSetChanged();
        }

    @Override
    public void onItemClicked(String labelSize) {
        appCompatTextViewSelectPrinterSize.setText(labelSize);
    }

    @Override
    public void onNewReducedBarcode(SaveReducedBarcodeModel saveReducedBarcodeModel) {
        if (saveReducedBarcodeModels.size()>0){
            //for(int i=0;i<Integer.parseInt(saveReducedBarcodeModels.get(0).getNoOfstickerstoprint());i++){
            onPrintTagClicked(saveReducedBarcodeModels.get(0));//}

            onSaveReducedBarcodeContainer();
            saveReducedBarcodeModels.clear();
            adapter.notifyDataSetChanged();

//            onButtonPrintReducedBarcode();
//              saveReducedBarcodeModels.clear();
        }
    }
}
