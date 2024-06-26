package com.ssinfomate.warehousemanagement.ui.stockcorrection;

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
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.WarehouseDialog;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.RequestBusinessLocation;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.AdjustmentTypeModel;
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

public class StockCorrectionFragment extends Fragment implements
        View.OnClickListener,
        ListItemClick,
        IAdjustmentType
      //  IDamageReason
{

   // private HomeViewModel mViewModel;
    NavController navController;
    AppCompatImageView imageViewCompatCreateStockCorrection;

    AppCompatImageView imageViewCompatPickHeldStockCorrection;
    View SCF_CSC_Visibility;
    View SCF_PHSC_Visibility;

    AppCompatButton buttonCompactSaveStockCorrection;
    AppCompatButton buttonCompactClear;
    AppCompatTextView editStockCorrectionBusinessLocation;
    AppCompatTextView editPickHeldStockCorrectionBusinessLocation;
    AppCompatTextView editStockCorrectionWarehouse;

    AppCompatTextView textViewSelectStockCorrectionReason;

    private WarehouseDialog warehouseDialog;
    private ProgressDialog progressDialog;
    WarehouseModel warehouseModelBusinessLocation;
    private ArrayList<WarehouseModel> warehouseModels;

    ArrayList<HeldStockCorrectionModel>heldStockCorrectionModels;
    ArrayList<HeldStockCorrectionModel>heldStockCorrectionModelsFinal=new ArrayList<>();

    AdjustmentTypeDialog adjustmentTypeDialog;
    RecyclerView recyclerViewHeldStockCorrectionList;
    HeldStockCorrectionAdapter heldStockCorrectionAdapter;
    AppCompatButton buttonShowPickHeldStockCorrectionList;


    public static StockCorrectionFragment newInstance() {
        return new StockCorrectionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.stock_correction_fragment, container, false);
        /*-------------------Create Stock Correction------------------------*/
        imageViewCompatCreateStockCorrection=view.findViewById(R.id.id_stock_fragment_create_stock_correction_img);
        imageViewCompatCreateStockCorrection.setOnClickListener(this);

        /*------------------Pick Held Correction----------------*/
        imageViewCompatPickHeldStockCorrection=view.findViewById(R.id.id_stock_fragment_pick_held_stock_correction_img);
        imageViewCompatPickHeldStockCorrection.setOnClickListener(this);
        //Single Navigation
        SCF_CSC_Visibility=view.findViewById(R.id.SCF_CSC_Visibility);
        SCF_PHSC_Visibility=view.findViewById(R.id.SCF_PHSC_Visibility);


         editStockCorrectionBusinessLocation=view.findViewById(R.id.edit_stock_correction_business_location);
         editStockCorrectionBusinessLocation.setOnClickListener(this);

         editPickHeldStockCorrectionBusinessLocation=view.findViewById(R.id.edit_pick_held_stock_correction_business_location);
         editPickHeldStockCorrectionBusinessLocation.setOnClickListener(this);

         editStockCorrectionWarehouse=view.findViewById(R.id.edit_stock_correction_warehouse);
         editStockCorrectionWarehouse.setOnClickListener(this);

        textViewSelectStockCorrectionReason=view.findViewById(R.id.text_select_stock_correction_reason);
        textViewSelectStockCorrectionReason.setOnClickListener(this);

        buttonShowPickHeldStockCorrectionList=view.findViewById(R.id.button_show_pick_held_stock_correction_list);
        buttonShowPickHeldStockCorrectionList.setOnClickListener(this);

        buttonCompactSaveStockCorrection = view.findViewById(R.id.id_btn_stock_correction_frg_save_stock_correction);
        buttonCompactSaveStockCorrection.setOnClickListener(this);

        buttonCompactClear  = view.findViewById(R.id.button_pick_held_stock_correction_clear);
        buttonCompactClear.setOnClickListener(this);

        recyclerViewHeldStockCorrectionList = view.findViewById(R.id.id_recycle_view_held_stock_correction_list);
        recyclerViewHeldStockCorrectionList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewHeldStockCorrectionList.setHasFixedSize(true);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getOpenBusinessLocation();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }
/*------------------create stock correction-------------------*/
    public void onCreateStockCorrectionClicked(){
        //navController.navigate(R.id.action_nav_stock_correction_to_nav_create_stock_correction);
        SCF_PHSC_Visibility.setVisibility(View.GONE);
        SCF_CSC_Visibility.setVisibility(View.VISIBLE);

    }
/*-------------pick held stock correction------------*/
    public void onPickHeldStockCorrection(){
        //navController.navigate(R.id.action_nav_stock_correction_to_nav_pick_held_stock_correction);
        SCF_CSC_Visibility.setVisibility(View.GONE);
        SCF_PHSC_Visibility.setVisibility(View.VISIBLE);

    }
/*----------------Save Stock Correction-------------------*/
    public void onSaveStockCorrectionClick() {

        if(!TextUtils.isEmpty(editStockCorrectionBusinessLocation.getText()) &&
                !TextUtils.isEmpty(editStockCorrectionWarehouse.getText())

        ){

            if(AppClassConstant.classPresent()){navController.navigate(R.id.action_nav_stock_correction_to_nav_save_stock_correction);}
            else {
                navController.navigate(R.id.action_nav_stock_correction_to_nav_save_stock_correction_and);
            }
        }else{
            Toast.makeText(getActivity(), "Select all field", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_stock_fragment_create_stock_correction_img:{
                onCreateStockCorrectionClicked();
                break;
            }
            case R.id.id_stock_fragment_pick_held_stock_correction_img:{
                onPickHeldStockCorrection();
                break;
            }
            case R.id.id_btn_stock_correction_frg_save_stock_correction: {
                onSaveStockCorrectionClick();
                break;
            }
            case R.id.edit_stock_correction_business_location: {
                openSelectBusinessLocationDialog();
                break;
            }
//            case R.id.edit_pick_held_stock_correction_business_location: {
//              //  openSelectBusinessLocationDialog();
//                break;
//            }
//            case R.id.edit_stock_correction_warehouse:{
//            //    openSelectBusinessLocationDialog();
//                break;
//            }
            case R.id.button_show_pick_held_stock_correction_list: {
                validationButtonShowHeldStockCorrectionList();
                break;
            }
            case R.id.text_select_stock_correction_reason:{
                onStockItemAdjustmentType(0);
                break;
            }
            case R.id.button_pick_held_stock_correction_clear:{
                clearText();
                break;
            }

        }
    }
    public void validationButtonShowHeldStockCorrectionList(){
        if (!TextUtils.isEmpty(editPickHeldStockCorrectionBusinessLocation.getText())){
            loadPickHeldStockCorrection();
        }else {
            Toast.makeText(getActivity(), "Select Business Location", Toast.LENGTH_SHORT).show();
        }
    }

    public void getOpenBusinessLocation(){
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
                Log.i("Error",t.getMessage());
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

    public void openSelectBusinessLocationDialog(){
//        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
//        ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());
//        RequestBusinessLocation requestBusinessLocation=new RequestBusinessLocation();
//        requestBusinessLocation.setCoID(changeCompanyModel.getCoId());
//        Gson gson =new Gson();
//        JsonObject data=gson.fromJson(gson.toJson(requestBusinessLocation),JsonObject.class);
//        Call<ArrayList<WarehouseModel>> arrayListCall= WebServiceClient
//                .getWarehouseService(BASE_URL)
//                .listWarehouse(data);
//        arrayListCall.enqueue(new Callback<ArrayList<WarehouseModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<WarehouseModel>> call, Response<ArrayList<WarehouseModel>> response) {
//        if(warehouseModels!=null){
//            if(warehouseModels.size()>0){
//                warehouseDialog=new WarehouseDialog(
//                        getContext(),
//                        warehouseModels,
//                        StockCorrectionFragment.this,
//                        Constants.BUSINESS_LOCATION);
//                warehouseDialog.show();}
//        }
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
        warehouseModelBusinessLocation=warehouseModel;
        editStockCorrectionBusinessLocation.setText(warehouseModel.getCobrName());
        editPickHeldStockCorrectionBusinessLocation.setText(warehouseModel.getCobrName());
      //  textViewSelectStockCorrectionReason.setText();
        onItemClickedFromLocation(warehouseModel);
        AppPreference.setBusinessLocationDataPreferences(getContext(),warehouseModel);
        if (warehouseDialog!=null){
        warehouseDialog.dismiss();}
    }

    @Override
    public void onItemClickedFromLocation(WarehouseModel warehouseModel) {
        if (warehouseModels!=null){
        editStockCorrectionWarehouse.setText(warehouseModel.getCobrName());}
    }

    @Override
    public void onItemClickedToLocation(ToWarehouse toWarehouse) {

    }

    public void loadPickHeldStockCorrection(){
        progressDialog = createProgressDialog(getContext());

        String BASE_URL=AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
//        WarehouseModel warehouseModel=AppPreference.getBusinessLocationDataPreferences(getContext());
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        RequestHeldStockCorrection requestHeldStockCorrection = new RequestHeldStockCorrection();

        requestHeldStockCorrection.setCobrId(userModel.getCobrId());
        requestHeldStockCorrection.setUserType(userModel.getUserTypeId());
//        requestHeldStockCorrection.setStockUpdateOptionStatus("");

        Call<ArrayList<HeldStockCorrectionModel>> arrayListCall = WebServiceClient
                .iHeldStockCorrection(BASE_URL)
                .getHeldStockCorrectionModel(requestHeldStockCorrection);
        arrayListCall.enqueue(new Callback<ArrayList<HeldStockCorrectionModel>>() {
            @Override
            public void onResponse(Call<ArrayList<HeldStockCorrectionModel>> call, Response<ArrayList<HeldStockCorrectionModel>> response) {
                heldStockCorrectionModels = response.body();
                if (heldStockCorrectionModels!=null){
                    heldStockCorrectionModelsFinal.addAll(response.body());
                    updatePickHeldList();
                }
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ArrayList<HeldStockCorrectionModel>> call,
                                  Throwable t) {
            }
        });
    }

    void updatePickHeldList(){
        heldStockCorrectionAdapter = new HeldStockCorrectionAdapter(heldStockCorrectionModels,getContext());
        recyclerViewHeldStockCorrectionList.setAdapter(heldStockCorrectionAdapter);
        if(heldStockCorrectionModels.size()>0){}
            else{
            Toast.makeText(getContext(),"No Record Found!",Toast.LENGTH_LONG).show();
            }
    }

    public void onStockItemAdjustmentType(int position) {
        if (heldStockCorrectionModels!=null) {
            adjustmentTypeDialog = new AdjustmentTypeDialog(
                    getContext(),
                    AdjustmentTypeModel.getAdjustmentTypeList(),
                    StockCorrectionFragment.this,
                    position
            );
            adjustmentTypeDialog.show();
        }else{
            Toast.makeText(getContext(),"Please View List First",Toast.LENGTH_LONG).show();
        }



//        adjustmentTypeModels = new A(
//                getContext(),
//                DamageReasonModel.getDamageReasonList(),
//                SaveStockCorrectionFragment.this,
//                position
//        );
//        damageReasonDialog.show();
    }
    @Override
    public void onAdjustmentTypeClicked(AdjustmentTypeModel adjustmentTypeModel, int position) {
        shortAdjustmentType(adjustmentTypeModel, position);
        textViewSelectStockCorrectionReason.setText(adjustmentTypeModel.getReason());
        adjustmentTypeDialog.dismiss();
    }
    void shortAdjustmentType(AdjustmentTypeModel adjustmentTypeModel, int position) {
        Log.i("Adjustment",String.valueOf(adjustmentTypeModel.getReason()));
        Log.i("Adjustment",String.valueOf(adjustmentTypeModel.getId()));

        shortHeldStockCorrectionList(adjustmentTypeModel);
    }
    //-------------------TODO--------------------------------------------------------------------------
    void shortHeldStockCorrectionList(AdjustmentTypeModel adjustmentTypeModel){
        heldStockCorrectionModels.clear();
        for(int i=0;i<heldStockCorrectionModelsFinal.size();i++){
            if(heldStockCorrectionModelsFinal
                    .get(i)
                    .getStkType().equals(
                            (adjustmentTypeModel.getId())
                    )){
                heldStockCorrectionModels.add(heldStockCorrectionModelsFinal.get(i));
            }
        }

     updatePickHeldList();// heldStockCorrectionAdapter.notifyDataSetChanged();
    }
    //-------------------TODO--------------------------------------------------------------------------

    public void clearText(){
        editPickHeldStockCorrectionBusinessLocation.setText("");
    }

}