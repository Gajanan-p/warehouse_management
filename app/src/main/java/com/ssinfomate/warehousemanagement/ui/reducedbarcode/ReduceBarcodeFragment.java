package com.ssinfomate.warehousemanagement.ui.reducedbarcode;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.ListItemClick;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.WarehouseDialog;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.RateCatListModel;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.ReduceBarcodeViewModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.RequestBusinessLocation;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
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

public class ReduceBarcodeFragment extends Fragment implements
        View.OnClickListener,
        ListItemClick,
        IRateCatList
{

    NavController navController;
    private ReduceBarcodeViewModel mViewModel;
    AppCompatTextView textViewBusinessLocation;
    //AppCompatTextView textViewReducedPricePrintReport;
    AppCompatTextView textViewSelectPriceList;

    AppCompatButton buttonStartReducedPrice;
    private WarehouseDialog warehouseDialog;
    WarehouseModel warehouseModelBusinessLocation;
    private ArrayList<WarehouseModel> warehouseModels;
    private ArrayList<RateCatListModel> rateCatListModels;
    RateCatListDialog rateCatListDialogs;
    private ProgressDialog progressDialog;

    public static ReduceBarcodeFragment newInstance() {
        return new ReduceBarcodeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reduce_barcode_fragment, container, false);

        textViewBusinessLocation = view.findViewById(R.id.edit_reduced_business_location);
        textViewBusinessLocation.setOnClickListener(this);

//        textViewReducedPricePrintReport = view.findViewById(R.id.edit_reduced_barcode_reduced_price_print_report);
//        textViewReducedPricePrintReport.setOnClickListener(this);
//
//        textViewSelectPriceList = view.findViewById(R.id.edit_reduced_barcode_select_price_list);
//        textViewSelectPriceList.setOnClickListener(this);

        buttonStartReducedPrice = view.findViewById(R.id.btn_reduced_barcode_start_reduced_price);
        buttonStartReducedPrice.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     //   mViewModel = new ViewModelProvider(this).get(ReduceBarcodeViewModel.class);
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
        getOpenSelectBusinessLocation();
    }

    public void onButtonStartReducedPrice() {
         if(AppClassConstant.classPresent()){
             navController.navigate(R.id.action_nav_reduce_barcode_to_saveReducedBarcodeFragment);
         }else{
             navController.navigate(R.id.action_nav_reduce_barcode_to_saveReducedBarcodeFragment_and);
         }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_reduced_barcode_start_reduced_price:{
                onButtonStartReducedPrice();
                break;
            }
//            case R.id.edit_reduced_barcode_select_price_list:{
//                  openRateCatListDialog();
//                  break;
//            }
        }
    }


    void getOpenSelectBusinessLocation(){
        progressDialog = createProgressDialog(getContext());
        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
       // ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());

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
//    void openSelectBusinessLocationDialog(){
////        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
////        ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());
////        RequestBusinessLocation requestBusinessLocation=new RequestBusinessLocation();
////        requestBusinessLocation.setCoID(changeCompanyModel.getCoId());
////        Gson gson =new Gson();
////        JsonObject data=gson.fromJson(gson.toJson(requestBusinessLocation),JsonObject.class);
////        Call<ArrayList<WarehouseModel>> arrayListCall= WebServiceClient
////                .getWarehouseService(BASE_URL)
////                .listWarehouse(data);
////        arrayListCall.enqueue(new Callback<ArrayList<WarehouseModel>>() {
////            @Override
////            public void onResponse(Call<ArrayList<WarehouseModel>> call, Response<ArrayList<WarehouseModel>> response) {
////                warehouseModels =response.body();
//                // warehouseModels.remove(0);
////        if (warehouseModels!=null){
////            if (warehouseModels.size()>0){
////                warehouseDialog=new WarehouseDialog(
////                        getContext(),
////                        warehouseModels,
////                        (ListItemClick) ReduceBarcodeFragment.this,
////                        Constants.BUSINESS_LOCATION);
////                warehouseDialog.show();
////            }
////           }
////
////            @Override
////            public void onFailure(Call<ArrayList<WarehouseModel>> call, Throwable t) {
////                Log.i("error",t.getMessage());
////            }
////        });
//    }

    @Override
    public void onItemClicked(WarehouseModel warehouseModel) {

    }

    @Override
    public void onItemClickedBusinessLocation(WarehouseModel warehouseModel) {
        warehouseModelBusinessLocation=warehouseModel;
        textViewBusinessLocation.setText(warehouseModel.getCobrName());
        onItemClickedFromLocation(warehouseModel);
        AppPreference.setBusinessLocationDataPreferences(getContext(),warehouseModel);
        if (warehouseDialog!=null){
            warehouseDialog.dismiss();}
    }

    @Override
    public void onItemClickedFromLocation(WarehouseModel warehouseModel) {

    }

    @Override
    public void onItemClickedToLocation(ToWarehouse toWarehouse) {

    }
//    void openRateCatListDialog(){
//        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
//        Call<ArrayList<RateCatListModel>> arrayListCall= WebServiceClient
//                .getRateCat(BASE_URL)
//                .getRateCatList();
//       arrayListCall .enqueue(new Callback<ArrayList<RateCatListModel>>() {
//           @Override
//           public void onResponse(Call<ArrayList<RateCatListModel>> call, Response<ArrayList<RateCatListModel>> response) {
//               rateCatListModels =response.body();
//               // warehouseModels.remove(0);
//               rateCatListDialogs=new RateCatListDialog(
//                       getContext(),
//                       rateCatListModels,
//                       ReduceBarcodeFragment.this);
//
//               rateCatListDialogs.show();
//           }
//
//           @Override
//           public void onFailure(Call<ArrayList<RateCatListModel>> call, Throwable t) {
//
//           }
//       });
//    }

    @Override
    public void onRateCatClicked(RateCatListModel rateCatListModel) {
        AppPreference.setRateCatPreferences(getContext(),rateCatListModel);
        textViewSelectPriceList.setText(rateCatListModel.getRatecatName());
        rateCatListDialogs.dismiss();
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