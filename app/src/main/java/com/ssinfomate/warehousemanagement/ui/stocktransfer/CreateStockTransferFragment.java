package com.ssinfomate.warehousemanagement.ui.stocktransfer;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.Toast;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.ListItemClick;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.ToWarehouseDialog;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.WarehouseDialog;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.CreateStockTransferViewModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.RequestBusinessLocation;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
import com.ssinfomate.warehousemanagement.ui.company.ChangeCompanyModel;
import com.ssinfomate.warehousemanagement.ui.util.Constants;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateStockTransferFragment extends Fragment implements View.OnClickListener, ListItemClick {

    private CreateStockTransferViewModel mViewModel;
    NavController navController;

    AppCompatButton buttonCompactSaveStockTransfer;


    AppCompatTextView selectCreateStockTransferBusinessLocation;
    AppCompatTextView selectCreateStockTransferFromLocation;
    AppCompatTextView selectCreateStockTransferToLocation;



    private WarehouseDialog warehouseDialog;
    private ArrayList<WarehouseModel>warehouseModels;

    private ToWarehouseDialog toWarehouseDialog;
    private ArrayList<ToWarehouse> toWarehouseArrayList;

    public static CreateStockTransferFragment newInstance() {
        return new CreateStockTransferFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_stock_transfer_fragment, container, false);

        selectCreateStockTransferBusinessLocation=view.findViewById(R.id.select_create_stock_transfer_business_location);
        selectCreateStockTransferFromLocation=view.findViewById(R.id.select_create_stock_transfer_from_warehouse);
//        selectCreateStockTransferToLocation=view.findViewById(R.id.select_create_stock_transfer_to_warehouse);
//        selectCreateStockTransferToLocation.setOnClickListener(this);

        buttonCompactSaveStockTransfer=view.findViewById(R.id.button_create_stock_transfer_start_stock_transfer);
        buttonCompactSaveStockTransfer.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreateStockTransferViewModel.class);
        // TODO: Use the ViewModel
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
    }
    public void onSaveStockTransferClicked(){
        if(!TextUtils.isEmpty(selectCreateStockTransferBusinessLocation.getText())
                && !TextUtils.isEmpty(selectCreateStockTransferFromLocation.getText())
        ){
            navController.navigate(R.id.action_nav_create_stock_transfer_to_nav_save_stock_transfer);
        }
        else{
            Toast.makeText(getActivity(),"Select All Field",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_create_stock_transfer_start_stock_transfer:{
                onSaveStockTransferClicked();
                break;
            }
            case R.id.select_create_stock_transfer_business_location:
            {
                openSelectBusinessLocationDialog();
                break;
            }
//            case R.id.select_create_stock_transfer_to_warehouse:
//            {
//                openSelectToWarehouseDialog();
//                break;
//            }
        }
    }

    void openSelectBusinessLocationDialog(){
        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
        ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("coID");
        stringBuilder.append(":");
        stringBuilder.append(changeCompanyModel.getCoId());

        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(stringBuilder),JsonObject.class);


        Call<ArrayList<WarehouseModel>> arrayListCall= WebServiceClient
                .getWarehouseService(BASE_URL)
                .listWarehouse(data);
        arrayListCall.enqueue(new Callback<ArrayList<WarehouseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<WarehouseModel>> call, Response<ArrayList<WarehouseModel>> response) {
                warehouseModels =response.body();
               // warehouseModels.remove(0);
                warehouseDialog=new WarehouseDialog(getContext(),warehouseModels, CreateStockTransferFragment.this, Constants.BUSINESS_LOCATION);
                warehouseDialog.show();
            }

            @Override
            public void onFailure(Call<ArrayList<WarehouseModel>> call, Throwable t) {
                Log.i("error",t.getMessage());
            }
        });
    }

    void openSelectToWarehouseDialog(){
        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
        ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());
        RequestBusinessLocation requestBusinessLocation=new RequestBusinessLocation();

        requestBusinessLocation.setCoID(changeCompanyModel.getCoId());

        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(requestBusinessLocation),JsonObject.class);

        Call<ArrayList<WarehouseModel>> arrayListCall= WebServiceClient
                .getWarehouseService(BASE_URL)
                .listWarehouse(data);
        arrayListCall.enqueue(new Callback<ArrayList<WarehouseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<WarehouseModel>> call, Response<ArrayList<WarehouseModel>> response) {
                warehouseModels =response.body();
                //warehouseModels.remove(0);
                warehouseDialog=new WarehouseDialog(
                        getContext(),
                        warehouseModels,
                        CreateStockTransferFragment.this,
                        Constants.TO_WAREHOUSE);
                warehouseDialog.show();
            }

            @Override
            public void onFailure(Call<ArrayList<WarehouseModel>> call, Throwable t) {
                Log.i("error",t.getMessage());
            }
        });
    }



    @Override
    public void onItemClicked(WarehouseModel warehouseModel) {


    }

    @Override
    public void onItemClickedBusinessLocation(WarehouseModel warehouseModel) {
        Constants.warehouseModelBusinessLocation=warehouseModel;
        selectCreateStockTransferBusinessLocation.setText(warehouseModel.getCobrName());
        onItemClickedFromLocation(warehouseModel);
        warehouseDialog.dismiss();
    }

    @Override
    public void onItemClickedFromLocation(WarehouseModel warehouseModel) {
        Constants.warehouseModelFromLocation=warehouseModel;
        selectCreateStockTransferBusinessLocation.setText(warehouseModel.getCobrName());
        warehouseDialog.dismiss();
    }

    @Override
    public void onItemClickedToLocation(ToWarehouse warehouseModel) {
        Constants.warehouseModelToLocation=warehouseModel;
        selectCreateStockTransferBusinessLocation.setText(warehouseModel.getCoBrName());
        toWarehouseDialog.dismiss();
    }
}