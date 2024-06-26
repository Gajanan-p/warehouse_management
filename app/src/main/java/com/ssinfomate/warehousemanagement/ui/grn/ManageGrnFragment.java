package com.ssinfomate.warehousemanagement.ui.grn;


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
import android.widget.LinearLayout;
import android.widget.Toast;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.ListItemClick;
import com.ssinfomate.warehousemanagement.ui.dialog.warehouse.WarehouseDialog;
import com.ssinfomate.warehousemanagement.ui.purchaseorder.CustomDialog;
import com.ssinfomate.warehousemanagement.ui.purchaseorder.CustomItemClickListener;
import com.ssinfomate.warehousemanagement.ui.purchaseorder.ISupplierInterface;
import com.ssinfomate.warehousemanagement.ui.purchaseorder.SupplierDialog;
import com.ssinfomate.warehousemanagement.ui.util.Constants;
import com.ssinfomate.warehousemanagement.webservices.grn.HeldGrnListModel;
import com.ssinfomate.warehousemanagement.webservices.grn.PONumberListModel;
import com.ssinfomate.warehousemanagement.webservices.grn.RequestCobrID;
import com.ssinfomate.warehousemanagement.webservices.grn.RequestHeldGrn;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.RequestBusinessLocation;
import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierModel;
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

public class ManageGrnFragment extends Fragment implements
        View.OnClickListener,
        ListItemClick,
        ISupplierInterface,
        IPurchaseNumber,
        CustomItemClickListener
{

   // private ManageGrnViewModel mViewModel;

    NavController navController;
    AppCompatImageView imageViewCompactCreateGRN;
    AppCompatImageView imageViewCompactPickHeldGRN;
    AppCompatButton buttonCompactStartGrn;
    AppCompatTextView appCompatTextViewBusinessLocation;
    AppCompatTextView appCompatTextViewWarehouse;
    AppCompatTextView appCompatTextViewSupplier;
    AppCompatTextView editTextPurchaseOrderNumber;
    AppCompatButton appCompatButtonOK;
    AppCompatButton buttonGrnWithPO;
    AppCompatButton buttonGrnWithoutPO;
    AppCompatButton appCompatButtonShowHeldGrnList;
    AppCompatTextView appCompatTextViewHeldGrnList;

    View CGRN_Visibility;
    View PHGRN_Visibility;

    LinearLayout ContainerGRNWithPO;
    LinearLayout ContainerGRNWithoutPO;

    private WarehouseDialog warehouseDialog;
    private WarehouseModel warehouseModelBusinessLocation;
    private ArrayList<WarehouseModel> warehouseModels;

    private PONumberListModel poNumberListModelLoad;
    private ArrayList<SupplierModel> supplierModels;
    private ArrayList<PONumberListModel> poNumberListModels;
    private PurchaseNumberDialog purchaseNumberDialog;
    private ProgressDialog progressDialog;
    private CustomDialog customDialog;
    ArrayList<HeldGrnListModel> heldGrnListModels;
    PickHeldGRNAdapter heldGRNAdapter;
    RecyclerView recyclerViewHeldGrnList;

    public static ManageGrnFragment newInstance() {
        return new ManageGrnFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.manage_grn_fragment, container, false);

//create grn-------------------------------------------------------------------------

        imageViewCompactCreateGRN=view.findViewById(R.id.id_grn_fragment_create_grn_img);
        imageViewCompactCreateGRN.setOnClickListener(this);

//pick held grn----------------------------------------------------------------------

        imageViewCompactPickHeldGRN=view.findViewById(R.id.id_grn_fragment_pick_held_grn_img);
        imageViewCompactPickHeldGRN.setOnClickListener(this);

//Single Navigation------------------------------------------------------------------

        CGRN_Visibility=view.findViewById(R.id.CGRN_Visibility);
        PHGRN_Visibility=view.findViewById(R.id.PHGRN_Visibility);

//start grn---------------------------------------------------------------------------

        buttonCompactStartGrn=view.findViewById(R.id.id_btn_create_grn_start_grn);
        buttonCompactStartGrn.setOnClickListener(this);

        appCompatTextViewBusinessLocation = view.findViewById(R.id.edit_create_grn_business_location);
        appCompatTextViewBusinessLocation.setOnClickListener(this);

        appCompatTextViewWarehouse = view.findViewById(R.id.edit_create_grn_warehouse);
        appCompatTextViewWarehouse.setOnClickListener(this);

        appCompatTextViewSupplier = view.findViewById(R.id.edit_create_grn_supplier);
        appCompatTextViewSupplier.setOnClickListener(this);

        editTextPurchaseOrderNumber = view.findViewById(R.id.edit_create_grn_PO_NO);
        editTextPurchaseOrderNumber.setOnClickListener(this);

//--------Purchase Order Number Start OK----------------------------------------------

        appCompatButtonOK = view.findViewById(R.id.button_create_grn_ok);
        appCompatButtonOK.setOnClickListener(this);

        buttonGrnWithPO = view.findViewById(R.id.button_with_purchase_order);
        buttonGrnWithPO.setOnClickListener(this);

        buttonGrnWithoutPO = view.findViewById(R.id.button_without_purchase_order);
        buttonGrnWithoutPO.setOnClickListener(this);

        ContainerGRNWithPO = view.findViewById(R.id.container_grn_with_po);
        ContainerGRNWithoutPO = view.findViewById(R.id.container_grn_without_po);

        appCompatTextViewHeldGrnList = view.findViewById(R.id.edit_held_grn_business_location);
        appCompatTextViewHeldGrnList.setOnClickListener(this);

        appCompatButtonShowHeldGrnList = view.findViewById(R.id.button_held_grn_show_held_list);
        appCompatButtonShowHeldGrnList.setOnClickListener(this);

        recyclerViewHeldGrnList = view.findViewById(R.id.id_recycle_view_held_Grn_list);
        recyclerViewHeldGrnList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewHeldGrnList.setHasFixedSize(true);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }
    public void onCreateGRNClicked(){
// navController.navigate(R.id.action_nav_manage_grn_to_nav_create_grn);

        PHGRN_Visibility.setVisibility(View.GONE);
        CGRN_Visibility.setVisibility(View.VISIBLE);
    }
    public void onPickHeldGRNClicked(){
//navController.navigate(R.id.action_nav_manage_grn_to_nav_pick_held_grn);

        CGRN_Visibility.setVisibility(View.GONE);
        PHGRN_Visibility.setVisibility(View.VISIBLE);
    }
    public  void onGrnWithPOButtonClicked(){

        ContainerGRNWithPO.setVisibility(View.VISIBLE);
        ContainerGRNWithoutPO.setVisibility(View.GONE);
    }
    public  void onGrnWithoutPOButtonClicked(){

        ContainerGRNWithPO.setVisibility(View.GONE);
        ContainerGRNWithoutPO.setVisibility(View.VISIBLE);
    }

    public  void onSaveGRNWithPurOrder(){
        if (!TextUtils.isEmpty(editTextPurchaseOrderNumber.getText())) {
            navController.navigate(R.id.action_nav_manage_grn_to_saveWithPurNoFragment);
        }else{
        }
    }

//save grn
    public void onSaveGrnClicked(){
        if (!TextUtils.isEmpty(appCompatTextViewBusinessLocation.getText())
                &&!TextUtils.isEmpty(appCompatTextViewWarehouse.getText())
                &&!TextUtils.isEmpty(appCompatTextViewSupplier.getText())
        ){
            if(AppClassConstant.classPresent()){
            navController.navigate(R.id.action_nav_manage_grn_to_nav_save_grn);}
            else{
                navController.navigate(R.id.action_nav_manage_grn_to_nav_save_grn_and);}
        }else{
            Toast.makeText(getActivity(),"Select all fields",Toast.LENGTH_SHORT).show();
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
            case R.id.id_grn_fragment_create_grn_img:{
                onCreateGRNClicked();
                break;
            }
            case R.id.id_grn_fragment_pick_held_grn_img: {
                onPickHeldGRNClicked();
                break;
            }
            case R.id.id_btn_create_grn_start_grn:{
                onSaveGrnClicked();
                break;
            }
//            case R.id.edit_create_grn_business_location:{
//             //   openSelectBusinessLocationDialog();
//                break;
//            }
//            case R.id.edit_create_grn_warehouse:{
//             //   openSelectBusinessLocationDialog();
//                break;
//            }
            case R.id.edit_create_grn_supplier:{
                openSelectSupplierDialog();
                break;
            }
            case R.id.button_with_purchase_order:{
                onGrnWithPOButtonClicked();
                break;
            }
            case R.id.button_without_purchase_order:{
                onGrnWithoutPOButtonClicked();
                break;
            }
            case R.id.edit_create_grn_PO_NO:{
                onPurNumberClicked();
                break;
            }
            case R.id.button_create_grn_ok:{
                onClickPOListButton();
                break;
            }
            case R.id.edit_held_grn_business_location:{
            //    openSelectBusinessLocationDialog();
                break;
            }
            case R.id.button_held_grn_show_held_list:{
                loadPickHeldGRNList();
                break;
            }

        }
    }

    public void onClickPOListButton(){
        if (!TextUtils.isEmpty(editTextPurchaseOrderNumber.getText())){
            onSaveGRNWithPurOrder();
        }else
        {
            Toast.makeText(getContext(),"Select Purchase Number",Toast.LENGTH_SHORT).show();
        }
    }

    public void getOpenBusinessLocation(){
        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
        //ChangeCompanyModel changeCompanyModel=AppPreference.getCompanyDataPreferences(getContext());
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

    public void openSelectBusinessLocationDialog() {
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
//                warehouseModels =response.body();
        // warehouseModels.remove(0);
        if (warehouseModels != null) {
            if (warehouseModels.size() > 0) {
                warehouseDialog = new WarehouseDialog(
                        getContext(),
                        warehouseModels,
                        (ListItemClick) ManageGrnFragment.this,
                        Constants.BUSINESS_LOCATION);
                warehouseDialog.show();
            }
        }

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
        appCompatTextViewBusinessLocation.setText(warehouseModel.getCobrName());
        appCompatTextViewWarehouse.setText(warehouseModel.getCobrName());
        appCompatTextViewHeldGrnList.setText(warehouseModel.getCobrName());
        onItemClickedFromLocation(warehouseModel);
        AppPreference.setBusinessLocationDataPreferences(getContext(),warehouseModel);
        if (warehouseDialog!=null){
            warehouseDialog.dismiss();}
    }

    @Override
    public void onItemClickedFromLocation(WarehouseModel warehouseModel) {
        appCompatTextViewWarehouse.setText(warehouseModel.getCobrName());
    }

    @Override
    public void onItemClickedToLocation(ToWarehouse toWarehouse) {

    }

//-----------------------------------------------------------------------------------------------
    public void openSelectSupplierDialog(){
        progressDialog = createProgressDialog(getContext());
        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();

        Call<ArrayList<SupplierModel>> arrayListCall= WebServiceClient
                .getSupplierService(BASE_URL)
                .getSupplierList();
        arrayListCall.enqueue(new Callback<ArrayList<SupplierModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SupplierModel>> call, Response<ArrayList<SupplierModel>> response) {
                supplierModels =response.body();
                // warehouseModels.remove(0);
                if (supplierModels!=null) {
                    getFilterList(supplierModels);
                    progressDialog.dismiss();
                }
//                supplierDialog=new SupplierDialog(
//                        getContext(),
//                        supplierModels,
//                        ManageGrnFragment.this);
//                progressDialog.dismiss();
//                supplierDialog.show();
            }
            @Override
            public void onFailure(Call<ArrayList<SupplierModel>> call, Throwable t) {
                Log.i("error",t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    public void getFilterList(ArrayList<SupplierModel> supplierModels){
        customDialog = new CustomDialog(getContext(),
                supplierModels,
                ManageGrnFragment.this);
        customDialog.show();
    }

    @Override
    public void onItemClick(SupplierModel supplierModel, int position) {
        AppPreference.setSupplierDataPreferences(getContext(),supplierModel);
        appCompatTextViewSupplier.setText(supplierModel.getLedName());
        customDialog.dismiss();
    }

    @Override
    public void onSupplierClicked(SupplierModel supplierModel) {

    }

//-----------PO Number List------------------------------------------------------------------------

    public void onPurNumberClicked(){
        progressDialog = createProgressDialog(getContext());
        String BASE_URL= AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
        UserModel userModel = AppPreference.getLoginDataPreferences(getContext());

        RequestCobrID requestCobrID = new RequestCobrID();
        requestCobrID.setCoBrId(userModel.getCobrId());
        Gson gson =new Gson();
        JsonObject data=gson.fromJson(gson.toJson(requestCobrID),JsonObject.class);

        Call<ArrayList<PONumberListModel>> arrayListCall = WebServiceClient
                .getPurchaseNumber(BASE_URL)
                .listPurchaseNumber(data);
        arrayListCall.enqueue(new Callback<ArrayList<PONumberListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PONumberListModel>> call, Response<ArrayList<PONumberListModel>> response) {
               if (response.isSuccessful()
                       && response.body().size()!=0) {
                   poNumberListModels = response.body();
                   if (poNumberListModels.get(0).getDocNumber() != null) {
                       purchaseNumberDialog = new PurchaseNumberDialog(getContext(),
                               poNumberListModels,
                               ManageGrnFragment.this);
                       progressDialog.dismiss();
                       purchaseNumberDialog.show();
                   }
               }else {
                   Toast.makeText(getContext(),"No record found please try again !",Toast.LENGTH_LONG).show();
                   progressDialog.dismiss();
               }

            }

            @Override
            public void onFailure(Call<ArrayList<PONumberListModel>> call, Throwable t) {
                Log.i("error",t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onPoNumberClicked(PONumberListModel poNumberListModel) {
        poNumberListModelLoad = poNumberListModel;
        editTextPurchaseOrderNumber.setText(poNumberListModel.getDocNumber());
        AppPreference.setPurchaseNumberPreferences(getContext(), poNumberListModel);
        if (purchaseNumberDialog!=null) {
            purchaseNumberDialog.dismiss();
        }
    }

    public void loadPickHeldGRNList(){
        progressDialog = createProgressDialog(getContext());
        String BASE_URL=AppPreference.getSettingDataPreferences(getContext()).getSettingServerURL();
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        RequestHeldGrn requestHeldGrn = new RequestHeldGrn();

        requestHeldGrn.setCobrId(userModel.getCobrId());
        requestHeldGrn.setUserId(userModel.getUserId().toString());
        requestHeldGrn.setUserType(userModel.getUserTypeId());

        Call<ArrayList<HeldGrnListModel>> arrayListCall = WebServiceClient
                .setWithoutPurOrderServices(BASE_URL)
                .getListPickHeldGrn(requestHeldGrn);
        arrayListCall.enqueue(new Callback<ArrayList<HeldGrnListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<HeldGrnListModel>> call, Response<ArrayList<HeldGrnListModel>> response) {
                heldGrnListModels = response.body();
                if (heldGrnListModels!=null){
                  //heldStockCorrectionModelsFinal.addAll(response.body());
                    updatePickHeldList();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<HeldGrnListModel>> call,Throwable t) {
                Log.i("error",t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    public void updatePickHeldList(){
        heldGRNAdapter = new PickHeldGRNAdapter(heldGrnListModels);
        recyclerViewHeldGrnList.setAdapter(heldGRNAdapter);
        if(heldGrnListModels.size()>0){}else{
            Toast.makeText(getContext(),"No Record Found",Toast.LENGTH_LONG).show();
        }
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