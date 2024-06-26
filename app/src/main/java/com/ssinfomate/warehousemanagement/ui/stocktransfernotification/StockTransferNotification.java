package com.ssinfomate.warehousemanagement.ui.stocktransfernotification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.Detail;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.SaveStockDetails;
import com.ssinfomate.warehousemanagement.webservices.stock_notification.StockTransferNotificationResponse;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockTransferNotification extends Fragment implements View.OnClickListener ,IStockStatus{

    ArrayList<StockTransferNotificationResponse> stockCorrectionResponses;
    RecyclerView recyclerViewStockTransferNotificationList;
    RecyclerView.LayoutManager layoutManager;
    StockTransferNotificationAdapter stockTransferNotificationAdapter;

    private StockTransferNotificationViewModel mViewModel;

    private AppCompatButton buttonStockTranferNotificationAccept;
    private AppCompatButton buttonStockTranferNotificationCancel;
    private AppCompatButton buttonStockTranferNotificationSubmit;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_transfer_notification_fragment, container, false);
        recyclerViewStockTransferNotificationList =(RecyclerView) view.findViewById(R.id.id_recycle_view_stock_transfer_notification);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewStockTransferNotificationList.setLayoutManager(layoutManager);
        recyclerViewStockTransferNotificationList.setHasFixedSize(true);

        buttonStockTranferNotificationAccept=view.findViewById(R.id.button_stock_tranfer_notification_accept);
        buttonStockTranferNotificationAccept.setOnClickListener(this);
        buttonStockTranferNotificationCancel=view.findViewById(R.id.button_stock_tranfer_notification_cancel);
        buttonStockTranferNotificationCancel.setOnClickListener(this);
        buttonStockTranferNotificationSubmit=view.findViewById(R.id.button_stock_tranfer_notification_submit);
        buttonStockTranferNotificationSubmit.setOnClickListener(this);

        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());

        RequestStockTransferNotification requestStockTransferNotification=new RequestStockTransferNotification();
        requestStockTransferNotification.setCoBrID(userModel.getCobrId());
        requestStockTransferNotification.setUserType(userModel.getUserTypeId());

        Call<ArrayList<StockTransferNotificationResponse>> arrayListCall= WebServiceClient
                .iPickHeldNotification(BASE_URL)
                .getStockTransferNotificationViewModel(requestStockTransferNotification);
        arrayListCall.enqueue(new Callback<ArrayList<StockTransferNotificationResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<StockTransferNotificationResponse>> call,
                                   Response<ArrayList<StockTransferNotificationResponse>> response) {
                stockCorrectionResponses = response.body();
                if(stockCorrectionResponses!=null){
                    updateUiList();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<StockTransferNotificationResponse>> call, Throwable t) {

            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StockTransferNotificationViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_stock_tranfer_notification_cancel:{
                sendStockTransferToServer(updateCancelList());
                break;
            }
            case R.id.button_stock_tranfer_notification_accept:{
                sendStockTransferToServer(updateAcceptList());
                break;
            }
            case R.id.button_stock_tranfer_notification_submit:{
                sendStockTransferToServer(updateSubmitList());
                break;
            }
        }
    }


    void updateUiList(){
        if(stockCorrectionResponses!=null){
            stockTransferNotificationAdapter = new StockTransferNotificationAdapter(
                    stockCorrectionResponses,
                    this,getContext());
            recyclerViewStockTransferNotificationList.setAdapter(stockTransferNotificationAdapter);
        }
    }

    SaveStockDetails updateAcceptList(){
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        SaveStockDetails saveStockDetails=new SaveStockDetails();
        Detail detail;
        ArrayList<Detail> details=new ArrayList<>();
        for(int i=0;i< stockCorrectionResponses.size();i++){
            detail=new Detail();
            StockTransferNotificationResponse saveStockTransferModel=stockCorrectionResponses.get(i);
            detail.setTransferDataDetailID(saveStockTransferModel.getTransferDataDetailID());
            detail.setTransferDataID(saveStockTransferModel.getTransferDataID());
            detail.setBusinessLocation(saveStockTransferModel.getBusinessName());

            detail.setFromWarehouse(saveStockTransferModel.getFromWarehouseId());
//            detail.setCobrId(String.valueOf(saveStockTransferModel.getCompanyId()));
            detail.setToWarehouse(saveStockTransferModel.getToWarehouse());
            int q=Math.round(Float.parseFloat(saveStockTransferModel.getScanQty()));
            detail.setScanQty(q);
            detail.setTransferQty(saveStockTransferModel.getTransferQty());
            detail.setStockId(saveStockTransferModel.getItemKey());
            detail.setUpdatedBy(String.valueOf(userModel.getUserId()));
//            detail.setUserid(String.valueOf(userModel.getUserId()));
            detail.setStatus("1");
            details.add(detail);
        }
        saveStockDetails.setDetails(details);
        return saveStockDetails;
    }

    SaveStockDetails updateCancelList(){
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        SaveStockDetails saveStockDetails=new SaveStockDetails();
        Detail detail;
        ArrayList<Detail> details=new ArrayList<>();
        for(int i=0;i< stockCorrectionResponses.size();i++){
            detail=new Detail();
            StockTransferNotificationResponse saveStockTransferModel=stockCorrectionResponses.get(i);
            detail.setTransferDataDetailID(saveStockTransferModel.getTransferDataDetailID());
            detail.setTransferDataID(saveStockTransferModel.getTransferDataID());
            detail.setBusinessLocation(saveStockTransferModel.getBusinessName());

            detail.setFromWarehouse(saveStockTransferModel.getFromWarehouseId());
//            detail.setCobrId(String.valueOf(saveStockTransferModel.getCompanyId()));
            detail.setToWarehouse(saveStockTransferModel.getToWarehouse());
            int q=Math.round(Float.parseFloat(saveStockTransferModel.getScanQty()));
            detail.setScanQty(q);
            detail.setTransferQty(saveStockTransferModel.getTransferQty());
            detail.setStockId(saveStockTransferModel.getItemKey());
            detail.setUpdatedBy(String.valueOf(userModel.getUserId()));
//            detail.setUserid(String.valueOf(userModel.getUserId()));
            detail.setStatus("3");
            details.add(detail);
        }
        saveStockDetails.setDetails(details);


        return saveStockDetails;
    }

    SaveStockDetails updateSubmitList(){
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        SaveStockDetails saveStockDetails=new SaveStockDetails();
        Detail detail;
        ArrayList<Detail> details=new ArrayList<>();
        for(int i=0;i< stockCorrectionResponses.size();i++){
            detail=new Detail();
            StockTransferNotificationResponse saveStockTransferModel=stockCorrectionResponses.get(i);

            detail.setTransferDataDetailID(saveStockTransferModel.getTransferDataDetailID());
            detail.setTransferDataID(saveStockTransferModel.getTransferDataID());
            detail.setBusinessLocation(saveStockTransferModel.getBusinessName());
            detail.setFromWarehouse(saveStockTransferModel.getFromWarehouseId());
//            detail.setCobrId(String.valueOf(saveStockTransferModel.getCompanyId()));
            detail.setToWarehouse(saveStockTransferModel.getToWarehouse());
            detail.setScanQty(Math.round(Float.parseFloat(saveStockTransferModel.getScanQty())));
            detail.setTransferQty(saveStockTransferModel.getTransferQty());
            detail.setStockId(saveStockTransferModel.getItemKey());
            detail.setUpdatedBy(String.valueOf(userModel.getUserId()));
//            detail.setUserid(String.valueOf(userModel.getUserId()));
            detail.setStatus(saveStockTransferModel.getStatus());
            details.add(detail);
        }
        saveStockDetails.setDetails(details);
        return saveStockDetails;
    }

    void sendStockTransferToServer(SaveStockDetails saveStockDetails){
       // UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(saveStockDetails),JsonObject.class);
        Log.i("data Json",data.toString());

        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        Call<SaveStockDetails> saveStockDetailsCall=WebServiceClient
                .getStockTransferService(appSetting.getSettingServerURL())
                .setUpdateStatusStockData(data);
        saveStockDetailsCall.enqueue(new Callback<SaveStockDetails>() {
            @Override
            public void onResponse(Call<SaveStockDetails> call, Response<SaveStockDetails> response) {
                if(response.isSuccessful()){
                    //Log.i("data responce",response.body().getMsg());
                    Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                    stockCorrectionResponses.clear();
                    updateUiList();


                }else{
                    Toast.makeText(getContext(),"Stock Not Updated Successfully",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SaveStockDetails> call, Throwable t) {
                Log.e("data error",t.getMessage());
                Toast.makeText(getContext(),"Stock Not Updated Successfully",Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onCanceledClicked(int position) {
        stockCorrectionResponses.get(position).setStatus("3");
        stockTransferNotificationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAcceptedClicked(int position) {
        stockCorrectionResponses.get(position).setStatus("1");
        stockTransferNotificationAdapter.notifyDataSetChanged();
    }
}