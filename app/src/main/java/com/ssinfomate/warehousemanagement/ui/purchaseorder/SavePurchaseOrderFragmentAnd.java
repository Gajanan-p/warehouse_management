package com.ssinfomate.warehousemanagement.ui.purchaseorder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.grn.SavePurchaseWithoutPODialog;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.SaveStockTransferAdapter;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStock;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockModel;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.purchaseorder.RequestPurchaseOrder;
import com.ssinfomate.warehousemanagement.webservices.purchaseorder.RequestPurchaseOrderDetails;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.SaveStockTransferModel;
import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierModel;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.IOnSaveStock;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.IOnSaveStockQuantity;
import com.ssinfomate.warehousemanagement.ui.util.IBackPageMovement;
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

public class SavePurchaseOrderFragmentAnd extends Fragment implements
        View.OnClickListener,
        IOnSaveStock,
        IOnSaveStockQuantity,
        IBackPageMovement {

    String TAG_SCANNER="Save Stock Scanner";

    //private int scannerIndex = 0; // Keep the selected scanner
    private int defaultIndex = 0; // Keep the default scanner
    private int dataLength = 0;
    private String statusString = "";


    private ProgressDialog progressDialog;
    private AppCompatEditText editSaveStockTransferBarcode;

    NavController navController;
    AppCompatButton appCompatButtonSaveStockOk;
    Button buttonSaveStockTransfer;
    ArrayList<CheckStockModel> listCheckStockModels;
    RecyclerView recyclerView;
    SaveStockTransferAdapter saveStockTransferAdapter;
    ArrayList<SaveStockTransferModel> listSaveStockTransferModel;
    SavePurchaseWithoutPODialog saveStockQuantityDialog;
    CheckStock checkStock=new CheckStock();



    public static SavePurchaseOrderFragmentAnd newInstance() {
        return new SavePurchaseOrderFragmentAnd();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.save_purchase_order_fragment, container, false);

        recyclerView=view.findViewById(R.id.recycler_save_stock_transfer);

        editSaveStockTransferBarcode=view.findViewById(R.id.edit_save_stock_transfer_barcode);
        editSaveStockTransferBarcode.setOnClickListener(this);
        appCompatButtonSaveStockOk=view.findViewById(R.id.save_stock_button_ok);
        appCompatButtonSaveStockOk.setOnClickListener(this);

        listSaveStockTransferModel=new ArrayList<>();

        buttonSaveStockTransfer=view.findViewById(R.id.save_purchase_order);
        buttonSaveStockTransfer.setOnClickListener(this::createStockTransfer);

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
//-------------------------------------------------------------------------------------------------
    public void searchSaveStock(){
        progressDialog = createProgressDialog(getContext());
        UserModel userModel= AppPreference.getLoginDataPreferences(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());

        checkStock.setCoBr_Id(userModel.getCobrId());
        //editSaveStockTransferBarcode.setText("");
        checkStock.setBarcode(editSaveStockTransferBarcode.getText().toString());

        Call<ArrayList<CheckStockModel>> listCheckStock  =
                WebServiceClient.checkStockService(appSetting.getSettingServerURL())
                        .listCheckStock(checkStock);
        listCheckStock.enqueue(new Callback<ArrayList<CheckStockModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CheckStockModel>> call,
                                   Response<ArrayList<CheckStockModel>> response) {
                listCheckStockModels = response.body();
                if (listCheckStockModels.size()>0) {
//                    if (listCheckStockModels.get(0).getCoBrId()!=null) {
                        changeDataSet(listCheckStockModels);
                        progressDialog.dismiss();
//                    }else {
//                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("No have a data for this barcode.!").show();
//                        progressDialog.dismiss();
//                        editSaveStockTransferBarcode.setText("");
//                        return;
//                    }
                } else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("No have a data for this barcode.!").show();
                    progressDialog.dismiss();
                    editSaveStockTransferBarcode.setText("");
                    return;
                }
                editSaveStockTransferBarcode.setText("");

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
        if (checkStockModel.getItemId() != null) {
            if (listSaveStockTransferModel.size() > 0) {
                for (int j = 0; j < listSaveStockTransferModel.size(); j++) {
                    if (listSaveStockTransferModel.get(j).getItemName().equals(checkStockModel.getItemName())) {
                        listSaveStockTransferModel.get(j).setScan_quantity(
                                listSaveStockTransferModel.get(j).getScan_quantity() + 1
                        );
                        isFound = true;
                    }
                }
            } else {
                isFound = false;
            }

        if (!isFound) {
            updateListModel(checkStockModel);
        }
        if (listSaveStockTransferModel.get(0).getItemId() != null) {
            saveStockTransferAdapter = new SaveStockTransferAdapter(listSaveStockTransferModel,
                    this, getContext());

            recyclerView.setAdapter(saveStockTransferAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            Toast.makeText(getContext(), "Item not found !", Toast.LENGTH_LONG).show();
        }
    }else {
            Toast.makeText(getContext(),"Item not found !",Toast.LENGTH_LONG).show();
        }
    }
//----------------------------------------------------------------------------------------------
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

    void updateListModel(CheckStockModel checkStockModel){
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
        saveStockTransferModel.setAllowNegStk(checkStockModel.getAllowNegStk());
        saveStockTransferModel.setMrp(checkStockModel.getMrp());

        listSaveStockTransferModel.add(saveStockTransferModel);
    }


    private void updateData(final String result){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editSaveStockTransferBarcode.setText("");
                if (result != null) {
                    if(dataLength ++ > 10) { //Clear the cache after 100 scans
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


//    private void updateStatus(final String status){
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////                textViewStatus.setText("" + status);
//                Log.i(TAG_SCANNER,status);
//            }
//        });
//    }


    public void onSavePOClickBack() {
//
//        if(
//                !TextUtils.isEmpty(editStockCorrectionBusinessLocation.getText()) &&
//                        !TextUtils.isEmpty(editStockCorrectionWarehouse.getText())
//        ){
            navController.navigate(R.id.action_nav_save_purchase_order_to_nav_purchase_order);
//        }else{
//            Toast.makeText(getActivity(), "Select all field", Toast.LENGTH_SHORT).show();
//        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_stock_button_ok:{
                validationBarcodeText();
                break;
            }
            case R.id.save_purchase_order:{
                onSavePOClickBack();
                break;
            }
//            case R.id.edit_save_stock_transfer_barcode:
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
//--------------------------------------------------------------------------------------
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
//--------------Save Purchase Order---------------------------------------

    public void createStockTransfer(View view){

        RequestPurchaseOrder requestPurchaseOrder = new RequestPurchaseOrder();
        ArrayList<RequestPurchaseOrderDetails> details=new ArrayList<>();

        WarehouseModel warehouseBusinessLocation=AppPreference.getBusinessLocationDataPreferences(getContext());
        SupplierModel supplierModel=AppPreference.getSupplierDataPreferences(getContext());
        ToWarehouse warehouseToLocation=AppPreference.getToWarehouseDataPreferences(getContext());
        UserModel userModel= AppPreference.getLoginDataPreferences(getContext());

        requestPurchaseOrder.setCoBrId(userModel.getCobrId());
        requestPurchaseOrder.setCoBrId(warehouseBusinessLocation.getCobrId());
        requestPurchaseOrder.setUserId(userModel.getUserId());
        requestPurchaseOrder.setSuplID(Integer.parseInt(supplierModel.getLedId()));
        requestPurchaseOrder.setMachName(Build.PRODUCT);

        RequestPurchaseOrderDetails detail;
        if(listSaveStockTransferModel!=null){
            if(listSaveStockTransferModel.size()>0){
                for(int i=0;i<listSaveStockTransferModel.size();i++){
                    detail=new RequestPurchaseOrderDetails();
                    SaveStockTransferModel saveStockTransferModel=listSaveStockTransferModel.get(i);
                    detail.setItemId(saveStockTransferModel.getItemId());
                    detail.setTotQty(saveStockTransferModel.getScan_quantity());
                    details.add(detail);
                }

                requestPurchaseOrder.setDetails(details);
                sendStockTransferToServer(requestPurchaseOrder);

            }
        }
    }

    public void sendStockTransferToServer(RequestPurchaseOrder requestPurchaseOrder){
        progressDialog = createProgressDialog(getContext());
        // multiple
        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(requestPurchaseOrder),JsonObject.class);
        Log.i("data Json",data.toString());

        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        Call<RequestPurchaseOrder> saveStockDetailsCall=WebServiceClient
                .setPurchaseOrderService(appSetting.getSettingServerURL())
                .setSavePurchaseOrderData(data);
        saveStockDetailsCall.enqueue(new Callback<RequestPurchaseOrder>() {
            @Override
            public void onResponse(Call<RequestPurchaseOrder> call, Response<RequestPurchaseOrder> response) {
                if(response.isSuccessful()){
                    Log.i("data response",response.body().getMsg());
                    Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                    listSaveStockTransferModel.clear();
                    saveStockTransferAdapter.notifyDataSetChanged();
//                    buttonSaveStockTransfer.setClickable(false);

                }else{
                    Toast.makeText(getContext(),"Save Stock Not Saved Successfully",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RequestPurchaseOrder> call, Throwable t) {
                Log.e("data error",t.getMessage());
                Toast.makeText(getContext(),"Save Stock Not Saved Successfully",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackClicked(View view) {
        startActivity(new Intent(getActivity(), PurchaseOrderFragment.class));

    }
}