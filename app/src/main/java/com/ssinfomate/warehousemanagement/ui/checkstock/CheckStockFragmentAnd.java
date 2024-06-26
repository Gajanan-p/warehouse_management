package com.ssinfomate.warehousemanagement.ui.checkstock;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ssinfomate.warehousemanagement.MainActivity;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.ListItemClick;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.WarehouseDialog;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStock;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockModel;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.RequestBusinessLocation;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
import com.ssinfomate.warehousemanagement.ui.dialog.DialogHelper;
import com.ssinfomate.warehousemanagement.ui.util.Constants;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.symbol.emdk.barcode.ScannerInfo;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckStockFragmentAnd extends Fragment implements
        View.OnClickListener,
        ListItemClick,
        CompoundButton.OnCheckedChangeListener
{

//------------------------ Initialize Variables--------------------------------------------

    private ProgressDialog progressDialog;

    private AppCompatEditText appCompatEditTextBarcode;
    private AppCompatTextView appCompatTextViewWarehouseSelect;
    private AppCompatButton appCompatButtonSearch;

    private RecyclerView listItemStockCheck;
    public ArrayList<CheckStockModel> listCheckStock=new ArrayList<>();
    CheckStock checkStock=new CheckStock();
    private CheckStockAdapter checkStockAdapter;

    private WarehouseDialog warehouseDialog;
    private ArrayList<WarehouseModel>warehouseModels;


    public static CheckStockFragmentAnd newInstance() {
        return new CheckStockFragmentAnd();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.check_stock_fragment, container, false);
        appCompatEditTextBarcode=view.findViewById(R.id.edit_check_stock_barcode);
        appCompatEditTextBarcode.setOnClickListener(this);

        appCompatTextViewWarehouseSelect=view.findViewById(R.id.text_check_stock_warehouse_select);
        appCompatTextViewWarehouseSelect.setOnClickListener(this);
        appCompatButtonSearch=view.findViewById(R.id.button_check_stock_search);

        appCompatButtonSearch.setOnClickListener(this);
        listItemStockCheck=view.findViewById(R.id.rv_item_check_stock);
        checkStockAdapter=new CheckStockAdapter(listCheckStock,getContext());
        listItemStockCheck.setAdapter(checkStockAdapter);
        listItemStockCheck.setLayoutManager(new LinearLayoutManager(getContext()));
        appCompatEditTextBarcode.requestFocus();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        getOpenWarehouse();
     }

    @Override
    public void onPause() {
        super.onPause();
    }

//--------------------// TODO: onClick()----------------------------------------------------------------------

    @Override
    public void onClick(View v) {
//        if(v.getId()==R.id.edit_check_stock_barcode){
//            ((MainActivity) getActivity()).startScanActivity(appCompatEditTextBarcode);
//        }
        if(v.getId()==R.id.text_check_stock_warehouse_select){
         //   openSelectWarehouseDialog();
        } if(v.getId()==R.id.button_check_stock_search){
            if(!TextUtils.isEmpty(appCompatEditTextBarcode.getText()))
            {
                SearchStock();
            }else
            {
                Toast.makeText(getActivity(), "Plz Select Warehouse", Toast.LENGTH_SHORT).show();
            }
        }
    }

//--------------------// TODO: SearchStock()----------------------------------------------------------------------

    public void SearchStock(){
        progressDialog = createProgressDialog(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        checkStock.setBarcode(appCompatEditTextBarcode.getText().toString());

        Call<ArrayList<CheckStockModel>> listCheckStock  =
                WebServiceClient.checkStockService(appSetting.getSettingServerURL()).listCheckStock(checkStock);
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
//                    appCompatEditTextBarcode.setText("");
//                    return;
//                }
            }
            @Override
            public void onFailure(Call<ArrayList<CheckStockModel>> call, Throwable t) {
                DialogHelper.getAlertWithMessage("Stock Load",t.getMessage(),getContext());
            }
        });
    }

    public void changeDataSet(ArrayList<CheckStockModel> checkStockModels){
        listCheckStock=checkStockModels;
        if (checkStockModels.get(0).getItemId()!=null) {
            checkStockAdapter = new CheckStockAdapter(listCheckStock, getContext());
            listItemStockCheck.setAdapter(checkStockAdapter);
            listItemStockCheck.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else {
            Toast.makeText(getContext(),"Item not found  !",Toast.LENGTH_LONG).show();
        }
    }

    public void getOpenWarehouse(){
        progressDialog = createProgressDialog(getContext());
        String BASE_URL=AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
//      ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        RequestBusinessLocation requestBusinessLocation=new RequestBusinessLocation();
        requestBusinessLocation.setCoID(userModel.getCobrId());
        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(requestBusinessLocation),JsonObject.class);

        Call<ArrayList<WarehouseModel>> arrayListCall=WebServiceClient
                .getWarehouseService(BASE_URL)
                .listWarehouse(data);
        arrayListCall.enqueue(new Callback<ArrayList<WarehouseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<WarehouseModel>> call, Response<ArrayList<WarehouseModel>> response) {
                warehouseModels =response.body();
                if (response.isSuccessful()) {
                    if (warehouseModels.size() > 0) {
                        onItemClicked(warehouseModels.get(0));
                    }
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(getContext(),"Server URL Not Found!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<WarehouseModel>> call, Throwable t) {
                Log.i("error",t.getMessage());
                progressDialog.dismiss();
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

    public void openSelectWarehouseDialog(){

//        String BASE_URL=AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
//        ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());
//
//        RequestBusinessLocation requestBusinessLocation=new RequestBusinessLocation();
//        requestBusinessLocation.setCoID(changeCompanyModel.getCoId());
//
//        Gson gson =new Gson();
//        JsonObject data=gson.fromJson(gson.toJson(requestBusinessLocation),JsonObject.class);
//
//        Call<ArrayList<WarehouseModel>> arrayListCall=WebServiceClient
//                .getWarehouseService(BASE_URL)
//                .listWarehouse(data);
//        arrayListCall.enqueue(new Callback<ArrayList<WarehouseModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<WarehouseModel>> call, Response<ArrayList<WarehouseModel>> response) {
//                warehouseModels =response.body();
//                warehouseModels.remove(0);

        if (warehouseModels!=null) {
            if (warehouseModels.size() > 0) {
                warehouseDialog = new WarehouseDialog(
                        getContext(),
                        warehouseModels,
                        CheckStockFragmentAnd.this,
                        Constants.BUSINESS_LOCATION);
                warehouseDialog.show();
            }
        }

//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<WarehouseModel>> call, Throwable t) {
//                Log.i("error",t.getMessage());
//            }
//        });

    }

    @Override
    public void onItemClicked(WarehouseModel warehouseModel) {
        checkStock.setCoBr_Id(warehouseModel.getCobrId());
        appCompatTextViewWarehouseSelect.setText(warehouseModel.getCobrName());
        if (warehouseDialog!=null){
        warehouseDialog.dismiss();}
    }

    @Override
    public void onItemClickedBusinessLocation(WarehouseModel warehouseModel) {
    }

    @Override
    public void onItemClickedFromLocation(WarehouseModel warehouseModel) {
    }

    @Override
    public void onItemClickedToLocation(ToWarehouse warehouseModel) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}