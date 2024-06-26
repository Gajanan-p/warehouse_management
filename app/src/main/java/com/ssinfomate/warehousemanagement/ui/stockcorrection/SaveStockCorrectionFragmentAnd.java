package com.ssinfomate.warehousemanagement.ui.stockcorrection;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveStockCorrectionFragmentAnd extends Fragment implements
        View.OnClickListener,
        IOnSaveStock,
        IOnSaveStockQuantity,
        IDamageReason ,
        IAdjustmentType
{
    String TAG_SCANNER = "Save Stock Scanner";

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
    String compare = "D";
    public static SaveStockCorrectionFragmentAnd newInstance() {
        return new SaveStockCorrectionFragmentAnd();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.save_stock_correction_fragment, container, false);

        editSaveStockCorrectionBarcode = view.findViewById(R.id.edit_save_stock_correction_barcode);
        editSaveStockCorrectionBarcode.setOnClickListener(this);
        buttonSaveStockCorrectionBarcodeOk = view.findViewById(R.id.button_save_stock_correction_barcode_ok);
        buttonSaveStockCorrectionBarcodeOk.setOnClickListener(this);

        listItemStockCheckCorrection = view.findViewById(R.id.rv_item_stock_correction_list);

        saveStockCorrectionSave = view.findViewById(R.id.save_stock_correction_save);
        saveStockCorrectionSave.setOnClickListener(this);

        appCompatTextViewAdjustmentType = view.findViewById(R.id.text_adjustment_type);
        appCompatTextViewAdjustmentType.setOnClickListener(this);

        editSaveStockCorrectionBarcode.requestFocus();

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
    public void onDestroy() {
        super.onDestroy();

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




    private void updateStatus(final String status) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                textViewStatus.setText("" + status);
                Log.i(TAG_SCANNER, status);
            }
        });
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
//            case R.id.edit_save_stock_correction_barcode:
//                    ((MainActivity) getActivity()).startScanActivity(editSaveStockCorrectionBarcode);
//                break;
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
        progressDialog =createProgressDialog(getContext());
        AppSetting appSetting = AppPreference.getSettingDataPreferences(getContext());
        WarehouseModel warehouseModel = AppPreference.getBusinessLocationDataPreferences(getContext());
        checkStock.setCoBr_Id(warehouseModel.getCobrId());
       // editSaveStockCorrectionBarcode.setText("");
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
//                    if (listCheckStockModels.get(0).getCoBrId()!=null) {
                        changeDataSet(listCheckStockModels);
                        editSaveStockCorrectionBarcode.setText("");
                        progressDialog.dismiss();
//                    }else {
//                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("No have a data for this barcode.!").show();
//                        progressDialog.dismiss();
//                        return;
//                    }
                   }else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("No have a data for this barcode.!").show();
                        progressDialog.dismiss();
                        return;
                    }
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
        if (checkStockModel.getItemId()!= null) {
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
            if (listSaveStockCorrectionModels.get(0).getItemId()!= null) {
                saveStockCorrectionAdapter = new SaveStockCorrectionAdapter(listSaveStockCorrectionModels, this,getContext(),adjustmentTypeModel);
                listItemStockCheckCorrection.setAdapter(saveStockCorrectionAdapter);
                listItemStockCheckCorrection.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
            Toast.makeText(getContext(),"Item not found !",Toast.LENGTH_LONG).show();
            }
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
   public void getDamageReason(){
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

    public void createStockCorrection() {
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
    public void sendStockTransferToServer(StockCorrectionRequest stockCorrectionRequest) {
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
//                    saveStockCorrectionSave.setClickable(false);
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
        saveStockTransferModel.setAllowNegStk(checkStockModel.getAllowNegStk());
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
                SaveStockCorrectionFragmentAnd.this,
                position
        );
        damageReasonDialog.show();



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
                SaveStockCorrectionFragmentAnd.this,position
               );

        dialogAdjustmentType.show();
    }
    void getAdjustment(){

        ArrayList<AdjustmentTypeModel> adjustmentTypeModels = AdjustmentTypeModel.getAdjustmentTypeList();
       appCompatTextViewAdjustmentType.setText(adjustmentTypeModels.get(0).getReason());
//       adjustmentTypeModel.setId(adjustmentTypeModels.get(0).getId());
        this.adjustmentTypeModel =adjustmentTypeModels.get(0);
        AppPreference.seAdjustmentTypeDataPreferences(getContext(),adjustmentTypeModels.get(0));
    }

    @Override
    public void onAdjustmentTypeClicked(AdjustmentTypeModel adjustmentTypeModel, int position) {
        AppPreference.seAdjustmentTypeDataPreferences(getContext(),adjustmentTypeModel);
       this.adjustmentTypeModel=adjustmentTypeModel;
       appCompatTextViewAdjustmentType.setText(adjustmentTypeModel.getReason());
       if (dialogAdjustmentType!=null){
        dialogAdjustmentType.dismiss();
       }
    }
}