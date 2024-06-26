package com.ssinfomate.warehousemanagement.ui.stocktransfer;

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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveStockTransferFragmentAnd extends Fragment implements

        View.OnClickListener,
        IOnSaveStock,
        IOnSaveStockQuantity {

    String TAG_SCANNER = "Save Stock Scanner";

    private int dataLength = 0;
    private String statusString = "";


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
    int checkClQty;
    int negativeStock;
    public static SaveStockTransferFragmentAnd newInstance() {
        return new SaveStockTransferFragmentAnd();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save_stock_transfer_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_save_stock_transfer);
        editSaveStockTransferBarcode = view.findViewById(R.id.edit_save_stock_transfer_barcode);
        editSaveStockTransferBarcode.setOnClickListener(this);
        appCompatButtonSaveStockOk = view.findViewById(R.id.save_stock_button_ok);
        appCompatButtonSaveStockOk.setOnClickListener(this);

        listSaveStockTransferModel = new ArrayList<>();

        buttonSaveStockTransfer = view.findViewById(R.id.save_stock_transfer_save);
        buttonSaveStockTransfer.setOnClickListener(this);
        editSaveStockTransferBarcode.requestFocus();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

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
       // editSaveStockTransferBarcode.setText("");
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
                if (listCheckStockModels.size()>0) {
                    if (listCheckStockModels.get(0).getCoBrId()!=null) {
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
                    }else {
                        Toast.makeText(getContext(), "Item not found !", Toast.LENGTH_LONG).show();
                    }
                }else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("No have a data for this barcode.!").show();
                    return;
                }
                editSaveStockTransferBarcode.setText("");
                progressDialog.dismiss();
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
        if (checkStockModel.getItemId()!=null) {
            if (listSaveStockTransferModel.size() > 0) {
                for (int j = 0; j < listSaveStockTransferModel.size(); j++) {
                    if (listSaveStockTransferModel.get(j).getItemName() != null) {
                        if (listSaveStockTransferModel.get(j).getItemName().equals(checkStockModel.getItemName())) {
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
            if (listSaveStockTransferModel.get(0).getItemId() != null) {
                saveStockTransferAdapter = new SaveStockTransferAdapter(listSaveStockTransferModel, this, getContext());
                recyclerView.setAdapter(saveStockTransferAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                editSaveStockTransferBarcode.setText(" ");
            } else {
                Toast.makeText(getContext(), "Item not found !", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Item not found !", Toast.LENGTH_LONG).show();
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
        saveStockTransferModel.setAllowNegStk(checkStockModel.getAllowNegStk());
        saveStockTransferModel.setScan_quantity(1);
        saveStockTransferModel.setMrp(checkStockModel.getMrp());

        listSaveStockTransferModel.add(saveStockTransferModel);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

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
                break;
            }
//            case R.id.button_save_stock_transfer_scan:
//                ((MainActivity) getActivity()).startScanActivity(editSaveStockTransferBarcode);
//                break;
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
                    }else if (checkClQty>=0){detail.setScanQty(saveStockTransferModel.getScan_quantity());}
                    else {Toast.makeText(getContext(),"Transfer Quantity cannot be greater than system Qty.!",Toast.LENGTH_LONG).show(); return;}
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
//                    buttonSaveStockTransfer.setClickable(false);

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