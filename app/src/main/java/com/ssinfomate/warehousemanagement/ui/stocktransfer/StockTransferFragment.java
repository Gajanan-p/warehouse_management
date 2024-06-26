package com.ssinfomate.warehousemanagement.ui.stocktransfer;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.PickHeldStockTransferModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.RequestBusinessLocation;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.RequestPickHeldStockTransfer;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
import com.ssinfomate.warehousemanagement.ui.company.ChangeCompanyModel;
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

public class  StockTransferFragment extends Fragment implements
        View.OnClickListener,
        ListItemClick {

    AppCompatTextView selectCreateStockTransferBusinessLocation;
    AppCompatTextView selectCreateStockTransferFromLocation;
    AppCompatTextView selectCreateStockTransferToLocation;

    WarehouseModel warehouseModelBusinessLocation;
    WarehouseModel warehouseModelFromLocation;
    ToWarehouse warehouseModelToLocation;

    private WarehouseDialog warehouseDialog;
    private ToWarehouseDialog toWarehouseDialog;
    private ArrayList<WarehouseModel> warehouseModels;
    private ArrayList<ToWarehouse> toWarehouses;
    private ProgressDialog progressDialog;

    NavController navController;
    AppCompatImageView imageViewCompatCreateStockTransfer;
    AppCompatImageView imageViewCompatPickHeldStockTransfer;

    View STF_CST_Visibility;
    View STF_PHST_Visibility;

    AppCompatButton buttonCompactSaveStockTransfer;

    ArrayList<PickHeldStockTransferModel> pickHeldStockTransferModels;
    PickHedAdapter pickHedAdapter;
    RecyclerView recyclerViewPickHeldList;

    AppCompatTextView editPickHeldStockTransferBusinessLocation;
    AppCompatButton buttonPickHeldStockTransferClear;
    AppCompatButton buttonShowHeldStockTransfer;

    public static StockTransferFragment newInstance() {
        return new StockTransferFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.stock_transfer_fragment, container, false);

        /*-----------------Create Stock Transfer--------*/
        imageViewCompatCreateStockTransfer=view.findViewById(R.id.id_stock_transfer_fragment_create_stock_transfer_img);
        imageViewCompatCreateStockTransfer.setOnClickListener(this);


        selectCreateStockTransferBusinessLocation=view.findViewById(R.id.select_create_stock_transfer_business_location);
        selectCreateStockTransferBusinessLocation.setOnClickListener(this);

        selectCreateStockTransferFromLocation=view.findViewById(R.id.select_create_stock_transfer_from_warehouse);

        selectCreateStockTransferToLocation=view.findViewById(R.id.select_create_stock_transfer_to_warehouse);
        selectCreateStockTransferToLocation.setOnClickListener(this);

        buttonCompactSaveStockTransfer=view.findViewById(R.id.button_create_stock_transfer_start_stock_transfer);
        buttonCompactSaveStockTransfer.setOnClickListener(this);

        /*-----------------Pick Held Stock Transfer-------------*/
        imageViewCompatPickHeldStockTransfer=view.findViewById(R.id.id_stock_transfer_fragment_pick_held_stock_transfer_img);
        imageViewCompatPickHeldStockTransfer.setOnClickListener(this);

//        single page navigation
        STF_CST_Visibility=view.findViewById(R.id.STF_CST_Visibility);
        STF_PHST_Visibility=view.findViewById(R.id.STF_PHST_Visibility);
/*------------Save Stock Transfer---------------------------------------*/

        editPickHeldStockTransferBusinessLocation=view.findViewById(R.id.edit_pick_held_stock_transfer_business_location);
        editPickHeldStockTransferBusinessLocation.setOnClickListener(this);

        buttonPickHeldStockTransferClear=view.findViewById(R.id.button_pick_held_stock_transfer_clear);
        buttonPickHeldStockTransferClear.setOnClickListener(this);

        buttonShowHeldStockTransfer=view.findViewById(R.id.button_show_held_stock_transfer);
        buttonShowHeldStockTransfer.setOnClickListener(this);

        recyclerViewPickHeldList =(RecyclerView) view.findViewById(R.id.id_recycle_view_pick_held_list);
        recyclerViewPickHeldList.setLayoutManager( new LinearLayoutManager(getContext()));
        recyclerViewPickHeldList.setHasFixedSize(true);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

    /*--------Create Stock Transfer--------*/
    public void onCreateStockTransferClicked(){
        //navController.navigate(R.id.action_nav_stock_transfer_to_nav_create_stock_transfer);
        //        single page navigation
        STF_PHST_Visibility.setVisibility(View.GONE);
        STF_CST_Visibility.setVisibility(View.VISIBLE);

    }
    /*-----------------Pick Held Stock Transfer-------------*/
    public void onPickHeldStockTransferClicked(){
        //navController.navigate(R.id.action_nav_stock_transfer_to_nav_pick_held_stock_transfer);
        //        single page navigation
        STF_CST_Visibility.setVisibility(View.GONE);
        STF_PHST_Visibility.setVisibility(View.VISIBLE);

    }
    /*------------Save Stock Transfer---------------------------------------*/
    public void onSaveStockTransferClicked() {
        if(AppClassConstant.classPresent()){
            navController.navigate(R.id.action_nav_stock_transfer_to_nav_save_stock_transfer);
        }else{
            navController.navigate(R.id.action_nav_stock_transfer_to_nav_save_stock_transfer_and);
        }
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        getOpenBusinessLocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_stock_transfer_fragment_create_stock_transfer_img:{
                onCreateStockTransferClicked();
                break;
            }
            case R.id.id_stock_transfer_fragment_pick_held_stock_transfer_img: {
                onPickHeldStockTransferClicked();
                break;
            }
            case R.id.button_create_stock_transfer_start_stock_transfer:{
                if (!TextUtils.isEmpty(selectCreateStockTransferBusinessLocation.getText())&&!TextUtils.isEmpty(selectCreateStockTransferToLocation.getText())) {
                    onSaveStockTransferClicked();
                }else {Toast.makeText(getContext(),"Fill All Fields",Toast.LENGTH_SHORT).show();}
                break;
            }
            case R.id.select_create_stock_transfer_business_location:
            {
            //    openSelectBusinessLocationDialog();
                break;
            }
            case R.id.edit_pick_held_stock_transfer_business_location:{
              //  openSelectBusinessLocationDialog();
                break;
            }
            case R.id.select_create_stock_transfer_to_warehouse:
            {
                openSelectToWarehouseDialog();
                break;
            }
            case R.id.button_pick_held_stock_transfer_clear:
            {
                clearPickHeldStockTransferBusinessLocation();
                break;
            }

            case R.id.button_show_held_stock_transfer:{
                validationButtonShowHeldStockTransferList() ;
                break;
            }
        }
    }

    public void validationButtonShowHeldStockTransferList(){
        if (!TextUtils.isEmpty(editPickHeldStockTransferBusinessLocation.getText())){
            loadPickHeldStockTransfer();
        }else {
            Toast.makeText(getActivity(), "Select Business Location", Toast.LENGTH_SHORT).show();
        }
    }
    void getOpenBusinessLocation(){
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
//
//        Call<ArrayList<WarehouseModel>> arrayListCall= WebServiceClient
//                .getWarehouseService(BASE_URL)
//                .listWarehouse(data);
//        arrayListCall.enqueue(new Callback<ArrayList<WarehouseModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<WarehouseModel>> call, Response<ArrayList<WarehouseModel>> response) {
//                warehouseModels =response.body();
               // warehouseModels.remove(0);
        if (warehouseModels!=null){
            if (warehouseModels.size()>0){
                warehouseDialog=new WarehouseDialog(
                        getContext(),
                        warehouseModels,
                        StockTransferFragment.this,
                        Constants.BUSINESS_LOCATION);
                warehouseDialog.show();}
            }
//            @Override
//            public void onFailure(Call<ArrayList<WarehouseModel>> call, Throwable t) {
//                Log.i("error",t.getMessage());
//            }
//        });
    }

    void openSelectToWarehouseDialog(){
        progressDialog = createProgressDialog(getContext());
        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
        ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());

        Call<ArrayList<ToWarehouse>> arrayListCall= WebServiceClient
                .getWarehouseService(BASE_URL)
                .listToWarehouse();

        arrayListCall.enqueue(new Callback<ArrayList<ToWarehouse>>() {
            @Override
            public void onResponse(Call<ArrayList<ToWarehouse>> call, Response<ArrayList<ToWarehouse>> response) {
                toWarehouses =response.body();
              //  warehouseModels.remove(0);
                toWarehouseDialog=new ToWarehouseDialog(
                        getContext(),
                        toWarehouses,
                        StockTransferFragment.this,
                        Constants.TO_WAREHOUSE);
                progressDialog.dismiss();

                toWarehouseDialog.show();

            }

            @Override
            public void onFailure(Call<ArrayList<ToWarehouse>> call, Throwable t) {
                Log.i("error",t.getMessage());
            }
        });
    }

    @Override
    public void onItemClicked(WarehouseModel warehouseModel) {
    }

    @Override
    public void onItemClickedBusinessLocation(WarehouseModel warehouseModel) {
        warehouseModelBusinessLocation=warehouseModel;

        selectCreateStockTransferBusinessLocation.setText(warehouseModel.getCobrName());
        editPickHeldStockTransferBusinessLocation.setText(warehouseModel.getCobrName());
        onItemClickedFromLocation(warehouseModel);
        AppPreference.setBusinessLocationDataPreferences(getContext(),warehouseModel);
        if (warehouseDialog!=null){
        warehouseDialog.dismiss();}
    }

    @Override
    public void onItemClickedFromLocation(WarehouseModel warehouseModel) {
        warehouseModelFromLocation=warehouseModel;
        AppPreference.setFromWarehouseDataPreferences(getContext(),warehouseModel);
        selectCreateStockTransferFromLocation.setText(warehouseModel.getCobrName());
        if (warehouseDialog!=null){
        warehouseDialog.dismiss();}
    }

    @Override
    public void onItemClickedToLocation(ToWarehouse toWarehouseModel) {
        warehouseModelToLocation=toWarehouseModel;
        AppPreference.setToWarehouseDataPreferences(getContext(),toWarehouseModel);
        selectCreateStockTransferToLocation.setText(toWarehouseModel.getCoBrName());
        toWarehouseDialog.dismiss();
    }


    void clearPickHeldStockTransferBusinessLocation(){
        editPickHeldStockTransferBusinessLocation.setText("");
    }

    void loadPickHeldStockTransfer(){
        progressDialog = createProgressDialog(getContext());
        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());

        RequestPickHeldStockTransfer requestPickHeldStockTransfer=new RequestPickHeldStockTransfer();
        requestPickHeldStockTransfer.setCobrId(warehouseModelBusinessLocation.getCobrId());
        requestPickHeldStockTransfer.setUserid(String.valueOf(userModel.getUserId()));


        Call<ArrayList<PickHeldStockTransferModel>> arrayListCall= WebServiceClient
                .getStockTransferService(BASE_URL)
                .getPickHeldStockData(requestPickHeldStockTransfer);

        arrayListCall.enqueue(new Callback<ArrayList<PickHeldStockTransferModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PickHeldStockTransferModel>> call, Response<ArrayList<PickHeldStockTransferModel>> response) {
                pickHeldStockTransferModels = response.body();
                if(pickHeldStockTransferModels!=null){
                    pickHedAdapter = new PickHedAdapter(pickHeldStockTransferModels);
                    recyclerViewPickHeldList.setAdapter(pickHedAdapter);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<PickHeldStockTransferModel>> call, Throwable t) {

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

}