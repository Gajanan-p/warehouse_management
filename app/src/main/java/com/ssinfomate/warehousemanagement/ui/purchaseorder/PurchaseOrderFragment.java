package com.ssinfomate.warehousemanagement.ui.purchaseorder;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.ListItemClick;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.ToWarehouseDialog;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.WarehouseDialog;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.RequestBusinessLocation;
import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierModel;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
import com.ssinfomate.warehousemanagement.ui.util.Constants;
import com.ssinfomate.warehousemanagement.utils.AppClassConstant;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseOrderFragment extends Fragment implements
        View.OnClickListener,
        ListItemClick,
        ISupplierInterface,
        CustomItemClickListener
{
    NavController navController;
    AppCompatButton buttonCompactSavePurchaseOrder;

    AppCompatTextView textPurchaseOrderSourceToBusinessLocation;
   // AppCompatTextView textPurchaseOrderDeliverToBusinessLocation;
    AppCompatTextView textPurchaseOrderSupplier;

    private SupplierDialog supplierDialog;
    private WarehouseDialog warehouseDialog;
    private ToWarehouseDialog toWarehouseDialog;
    private ArrayList<SupplierModel>supplierModels ;
    private ArrayList<WarehouseModel> warehouseModels;
    private ArrayList<ToWarehouse> toWarehouses;
    private ProgressDialog progressDialog;
    private CustomDialog customDialog;
    public static PurchaseOrderFragment newInstance() {
        return new PurchaseOrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purchase_order_fragment, container, false);

        textPurchaseOrderSourceToBusinessLocation=view.findViewById(R.id.text_purchase_order_source_business_location);
        textPurchaseOrderSourceToBusinessLocation.setOnClickListener(this);
//      textPurchaseOrderDeliverToBusinessLocation=view.findViewById(R.id.text_purchase_order_deliver_to_business_location);
//      textPurchaseOrderDeliverToBusinessLocation.setOnClickListener(this);
        textPurchaseOrderSupplier=view.findViewById(R.id.text_purchase_order_supplier);
        textPurchaseOrderSupplier.setOnClickListener(this);


        buttonCompactSavePurchaseOrder=view.findViewById(R.id.id_btn_purchase_order_save_purchase_order);
        buttonCompactSavePurchaseOrder.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        getBusinessLocation();
    }

    public void onSavePurchaseOrderClicked(){
        if(
                !TextUtils.isEmpty(textPurchaseOrderSourceToBusinessLocation.getText()) &&
                        !TextUtils.isEmpty(textPurchaseOrderSupplier.getText())
        ){
           if(AppClassConstant.classPresent()){
               navController.navigate(R.id.action_nav_purchase_order_to_nav_save_purchase_order);
           }else{
               navController.navigate(R.id.action_nav_purchase_order_to_nav_save_purchase_order_and);
           }
        }else{
            Toast.makeText(getActivity(), "Select all field", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_purchase_order_save_purchase_order:{
                onSavePurchaseOrderClicked();
                break;
            }
            case R.id.text_purchase_order_source_business_location:{
               // openSelectBusinessLocationDialog();
                break;
            }
//            case R.id.text_purchase_order_deliver_to_business_location:{
//                openSelectToWarehouseDialog();
//                break;
//            }
            case R.id.text_purchase_order_supplier:{
                openSelectSupplierDialog();
                break;
            }
        }
    }

    void openSelectSupplierDialog(){
        progressDialog = createProgressDialog(getContext());
        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();

        Call<ArrayList<SupplierModel>> arrayListCall= WebServiceClient
                .getSupplierService(BASE_URL)
                .getSupplierList();
        arrayListCall.enqueue(new Callback<ArrayList<SupplierModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SupplierModel>> call, Response<ArrayList<SupplierModel>> response) {
                supplierModels =response.body();
                if (supplierModels!=null){
                    getFilterList(supplierModels);
                    progressDialog.dismiss();
                }
                // warehouseModels.remove(0);
//                supplierDialog=new SupplierDialog(
//                        getContext(),
//                        supplierModels,
//                        PurchaseOrderFragment.this);
//                progressDialog.dismiss();
//
            }
            @Override
            public void onFailure(Call<ArrayList<SupplierModel>> call, Throwable t) {
                Log.i("error",t.getMessage());
            }
        });
    }

    public void getFilterList(ArrayList<SupplierModel> supplierModels){
        customDialog = new CustomDialog(getContext(),
                supplierModels,
                PurchaseOrderFragment.this);
        customDialog.show();
    }
//----------TODO --------------------------------------------------------------------------------------
    void getBusinessLocation(){
        progressDialog = createProgressDialog(getContext());
        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
      //  ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());
        UserModel userModel = AppPreference.getLoginDataPreferences(getContext());
        RequestBusinessLocation requestBusinessLocation=new RequestBusinessLocation();

        requestBusinessLocation.setCoID(userModel.getCobrId());

        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(requestBusinessLocation),JsonObject.class);

        Call<ArrayList<WarehouseModel>> arrayListCall= WebServiceClient
                .getWarehouseService(BASE_URL)
                .listWarehouse(data);
        arrayListCall.enqueue(new Callback<ArrayList<WarehouseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<WarehouseModel>> call, Response<ArrayList<WarehouseModel>> response) {
                if (response.isSuccessful()) {
                    warehouseModels = response.body();
                    // warehouseModels.remove(0);
                    if (warehouseModels.size() > 0) {
                        onItemClickedBusinessLocation(warehouseModels.get(0));
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
            }
        });
    }

    void openSelectBusinessLocationDialog(){
//        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
//        ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());
//
//        RequestBusinessLocation requestBusinessLocation=new RequestBusinessLocation();
//
//        requestBusinessLocation.setCoID(changeCompanyModel.getCoId());
//
//        Gson gson =new Gson();
//        JsonObject data=gson.fromJson(gson.toJson(requestBusinessLocation),JsonObject.class);
//
//        Call<ArrayList<WarehouseModel>> arrayListCall= WebServiceClient
//                .getWarehouseService(BASE_URL)
//                .listWarehouse(data);
//        arrayListCall.enqueue(new Callback<ArrayList<WarehouseModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<WarehouseModel>> call, Response<ArrayList<WarehouseModel>> response) {
//                warehouseModels =response.body();
//                // warehouseModels.remove(0);
        if(warehouseModels!=null){
            if(warehouseModels.size()>0){
                warehouseDialog=new WarehouseDialog(
                        getContext(),
                        warehouseModels,
                        PurchaseOrderFragment.this,
                        Constants.BUSINESS_LOCATION);
                warehouseDialog.show();}
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

    }

    @Override
    public void onItemClickedBusinessLocation(WarehouseModel warehouseModel) {
        textPurchaseOrderSourceToBusinessLocation.setText(warehouseModel.getCobrName());
        AppPreference.setBusinessLocationDataPreferences(getContext(),warehouseModel);
        if(warehouseDialog!=null){
        warehouseDialog.dismiss();}
    }
    @Override
    public void onItemClickedFromLocation(WarehouseModel warehouseModel) {
    }

    @Override
    public void onItemClickedToLocation(ToWarehouse toWarehouseModel) {
        AppPreference.setToWarehouseDataPreferences(getContext(),toWarehouseModel);
      //textPurchaseOrderDeliverToBusinessLocation.setText(toWarehouseModel.getCoBrName());
        toWarehouseDialog.dismiss();
    }

    @Override
    public void onSupplierClicked(SupplierModel supplierModel) {
//        AppPreference.setSupplierDataPreferences(getContext(),supplierModel);
//        textPurchaseOrderSupplier.setText(supplierModel.getLedName());
//        supplierDialog.dismiss();
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

    @Override
    public void onItemClick(SupplierModel model, int position) {
        AppPreference.setSupplierDataPreferences(getContext(),model);
        textPurchaseOrderSupplier.setText(model.getLedName());
        customDialog.dismiss();
    }
}