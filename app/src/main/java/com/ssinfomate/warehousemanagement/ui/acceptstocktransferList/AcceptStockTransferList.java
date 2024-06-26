package com.ssinfomate.warehousemanagement.ui.acceptstocktransferList;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.UpdateDetail;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.UpdateHead;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.UpdateSaveStockDetails;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ssinfomate.warehousemanagement.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptStockTransferList extends Fragment implements IAcceptStock{

    ArrayList<AcceptStockTransferListModel> acceptStockTransferListModels;
    RecyclerView recyclerViewAcceptStockTransferList;
    RecyclerView.LayoutManager layoutManager;
    AcceptStockTransferListAdapter acceptStockTransferListAdapter;
    StockQuantityDialog stockQuantityDialog;
    AppCompatButton buttonAcceptStockUpdateSave;


    public static AcceptStockTransferList newInstance() {
        return new AcceptStockTransferList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accept_stock_transfer_list_fragment, container, false);

        recyclerViewAcceptStockTransferList =(RecyclerView) view.findViewById(R.id.id_recycle_view_accept_stock_transfer);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewAcceptStockTransferList.setLayoutManager(layoutManager);
        recyclerViewAcceptStockTransferList.setHasFixedSize(true);
        buttonAcceptStockUpdateSave=view.findViewById(R.id.button_accept_stock_update_save);
        buttonAcceptStockUpdateSave.setOnClickListener(this::submitUpdateStock);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAcceptStockTransfer();
    }

    void loadAcceptStockTransfer(){
        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());

        RequestAcceptStockTransferList requestAcceptStockTransferList=new RequestAcceptStockTransferList();
        requestAcceptStockTransferList.setCoBrID(userModel.getCobrId());
        requestAcceptStockTransferList.setUserType(userModel.getUserTypeId());
        Call<ArrayList<AcceptStockTransferListModel>> arrayListCall= WebServiceClient
                .iAcceptStockTransferList(BASE_URL)
                .getAcceptStockTransferListModel(requestAcceptStockTransferList);
        arrayListCall.enqueue(new Callback<ArrayList<AcceptStockTransferListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AcceptStockTransferListModel>> call, Response<ArrayList<AcceptStockTransferListModel>> response) {
                acceptStockTransferListModels = response.body();
                if(acceptStockTransferListModels!=null){
                    acceptStockTransferListAdapter = new AcceptStockTransferListAdapter(
                            acceptStockTransferListModels,
                            AcceptStockTransferList.this
                    );
                    recyclerViewAcceptStockTransferList.setAdapter(acceptStockTransferListAdapter);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<AcceptStockTransferListModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

    @Override
    public void onStockItemQuantityClicked(int position) {
        stockQuantityDialog=new StockQuantityDialog(getContext(),
                acceptStockTransferListModels.get(position),AcceptStockTransferList.this,position);
        stockQuantityDialog.show();
    }

    @Override
    public void onStockItemQuantityUpdateClicked(int position, int quantity) {
        acceptStockTransferListModels.get(position).setScanQty(String.valueOf(quantity));
        acceptStockTransferListAdapter.notifyDataSetChanged();
        stockQuantityDialog.dismiss();
    }

    void submitUpdateStock(View view){
        UpdateSaveStockDetails saveStockDetails=new UpdateSaveStockDetails();
        ArrayList<UpdateDetail> details=new ArrayList<>();
        UserModel userModel= AppPreference.getLoginDataPreferences(getContext());

        UpdateHead updateHead=new UpdateHead();
        updateHead.setTransferDataId(acceptStockTransferListModels.get(0).getTransferDataID());
        updateHead.setUpdatedBy(String.valueOf(userModel.getUserId()));
        ArrayList<UpdateHead>updateHeads=new ArrayList<>();
        updateHeads.add(updateHead);
        saveStockDetails.setHead(updateHeads);

//        saveStockDetails.setUserid(String.valueOf(userModel.getUserId()));
//        saveStockDetails.setCreatedBy(String.valueOf(userModel.getUserId()));
//        saveStockDetails.setCobrId(String.valueOf(warehouseFromLocation.getCoBrId()));
        UpdateDetail detail;
        if(acceptStockTransferListModels!=null){
            if(acceptStockTransferListModels.size()>0){
                for(int i=0;i<acceptStockTransferListModels.size();i++){
                    detail=new UpdateDetail();
                    AcceptStockTransferListModel saveStockTransferModel=acceptStockTransferListModels.get(i);

                    detail.setTransferDataDetailID(saveStockTransferModel.getTransferDataDetailID());
//                    detail.setTransferDataID(saveStockTransferModel.getTransferDataID());
//                    detail.setBusinessLocation(warehouseBusinessLocation.getCoBrId());
//                    detail.setFromWarehouse(warehouseFromLocation.getCoBrId());
//                    detail.setAccQtyFrmWerehouse(saveStockTransferModel.getScanQty());
//                    detail.setCobrId(warehouseFromLocation.getCoBrId());
//                    detail.setToWarehouse(warehouseToLocation.getCoBrId());
//                    detail.setScanQty(Math.round(Float.parseFloat(saveStockTransferModel.getScanQty())));
                    detail.setAccQtyFrmWerehouse(
                            saveStockTransferModel.getScanQty());
                    detail.setStockId(saveStockTransferModel.getItemKey());

//                    detail.setUpdatedBy(userModel.getUserName());

//                    detail.setUserid(String.valueOf(userModel.getUserId()))
                    detail.setStatus("2");
                    details.add(detail);
                }

                saveStockDetails.setDetails(details);
                sendStockTransferToServer(saveStockDetails);

            }
        }
    }

    void sendStockTransferToServer(UpdateSaveStockDetails saveStockDetails){
        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(saveStockDetails),JsonObject.class);
        Log.i("data Json",data.toString());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        Call<UpdateSaveStockDetails> saveStockDetailsCall=WebServiceClient
                .getStockTransferService(appSetting.getSettingServerURL())
                .setUpdateQuantityStockData(data);
        saveStockDetailsCall.enqueue(new Callback<UpdateSaveStockDetails>() {
            @Override
            public void onResponse(Call<UpdateSaveStockDetails> call, Response<UpdateSaveStockDetails> response) {
                if(response.isSuccessful()){
                    Log.i("data response",response.body().getMsg());
                    Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                    loadAcceptStockTransfer();
//                    listSaveStockTransferModel.clear();
//                    acceptStockTransferListAdapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(getContext(),"Save Stock Not Saved Successfully",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateSaveStockDetails> call, Throwable t) {
                Log.e("data error",t.getMessage());
                Toast.makeText(getContext(),"Save Stock Not Saved Successfully",Toast.LENGTH_LONG).show();
            }
        });

    }

}