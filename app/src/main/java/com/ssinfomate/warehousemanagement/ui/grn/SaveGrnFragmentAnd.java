package com.ssinfomate.warehousemanagement.ui.grn;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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
import com.ssinfomate.warehousemanagement.ui.stocktransfer.IOnSaveStock;
import com.ssinfomate.warehousemanagement.ui.stocktransfer.IOnSaveStockQuantity;
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

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
SaveGrnFragmentAnd extends Fragment implements
        View.OnClickListener,
        IOnSaveStock,
        IOnSaveStockQuantity
{


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

    public static SaveGrnFragmentAnd newInstance() {
        return new SaveGrnFragmentAnd();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.save_grn_fragment, container, false);

        recyclerView=view.findViewById(R.id.rv_item_grn_list);

        appCompatEditTextBarcode=view.findViewById(R.id.edit_grn_barcode);
        appCompatEditTextBarcode.setOnClickListener(this);

        listSaveStockTransferModel=new ArrayList<>();

        buttonBarcodeGrnOk = view.findViewById(R.id.button_save_grn_ok);
        buttonBarcodeGrnOk.setOnClickListener(this);

        buttonSaveGRNWithoutPurOrder = view.findViewById(R.id.save_grn_save);
        buttonSaveGRNWithoutPurOrder.setOnClickListener(this::createGRNWithoutPurOrder);

        appCompatEditTextBarcode.requestFocus();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        // The application is in background
        // Release the barcode manager resources

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
//                if (response.body().get(0).getCoBrId()!=null) {
                    changeDataSet(response.body());
                    appCompatEditTextBarcode.setText("");
                    progressDialog.dismiss();
//                }else {
//                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("No have a data for this barcode.!").show();
//                    progressDialog.dismiss();
//                    return;
//                }
            }

            @Override
            public void onFailure(Call<ArrayList<CheckStockModel>> call, Throwable t) {

            }
        });
    }
//-----------------------TODO-------------------------------------------------------------------
    public void changeDataSet(ArrayList<CheckStockModel> checkStockModels){
        CheckStockModel checkStockModel=checkStockModels.get(0);
        boolean isFound=false;
        if (checkStockModel.getItemId()!=null) {
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
        } else {
            Toast.makeText(getContext(),"Item not found !",Toast.LENGTH_LONG).show();
        }
    }

//-----------------------TODO-------------------------------------------------------------------
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
//---------------------------TODO Update Purchase------------------------------------------------

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
        saveStockTransferModel.setMrp(checkStockModel.getMrp());

        listSaveStockTransferModel.add(saveStockTransferModel);
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
//---------------TODO IOnSaveStock-----------------------------------------------------------------

    @Override
    public void onStockItemChange(SaveStockTransferModel model, int position) {
        saveStockQuantityDialog.dismiss();
        if(listSaveStockTransferModel.size()>0){
            listSaveStockTransferModel.get(position).setClqty(model.getClqty());
            saveStockTransferAdapter.notifyDataSetChanged();
        }
    }


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
                break;
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

    void sendGRNToServer(RequestGRNWithoutPurOrder requestGRNWithoutPurOrder){
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
//                    buttonSaveGRNWithoutPurOrder.setClickable(false);
                    progressDialog.dismiss();
                } else{
                    Toast.makeText(getContext(),"Stock Not Saved Successfully",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestGRNWithoutPurOrder> call, Throwable t) {
                Log.i("Error",t.getMessage());
            }
        });
    }


}