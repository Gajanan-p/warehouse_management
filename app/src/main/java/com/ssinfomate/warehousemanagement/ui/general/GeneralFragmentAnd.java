package com.ssinfomate.warehousemanagement.ui.general;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStock;
import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockModel;
import com.ssinfomate.warehousemanagement.webservices.general.DeleteGeneralModel;
import com.ssinfomate.warehousemanagement.webservices.general.GeneralInputDetail;
import com.ssinfomate.warehousemanagement.webservices.general.GeneralModel;
import com.ssinfomate.warehousemanagement.webservices.general.GeneralRemarkModel;
import com.ssinfomate.warehousemanagement.webservices.general.RequestGeneralList;
import com.ssinfomate.warehousemanagement.webservices.general.ResponseGeneralList;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralFragmentAnd
        extends Fragment
        implements
        View.OnClickListener,
        IGeneralItem

{

    private int defaultIndex = 0; // Keep the default scanner
    private int dataLength = 0;
    private String statusString = "";
    private boolean bSoftTriggerSelected = false;
    private boolean bDecoderSettingsChanged = false;
    private boolean bExtScannerDisconnected = false;
    private final Object lock = new Object();
    private String TAG_SCANNER="SCANNER";
    private RecyclerView recyclerViewGeneralList;
    public ArrayList<ResponseGeneralList> responseGeneralLists = new ArrayList<>();
    private CheckStock checkStock=new CheckStock();
    private GeneralListAdapter generalListAdapter;
    private ProgressDialog progressDialog;
    private ArrayList<GeneralRemarkModel>remarkModels;
    private GeneralRemarkDialog remarkDialog;
    private LinearLayout ContainerGeneralEntry;
    private LinearLayout ContainerGeneralList;
    private EditText editTextBarcode;
    private EditText editTextRemarkOne;
    private EditText editTextRemarkTwo;
    private TextView textViewProductName;
    private TextView textViewSystemQuantity;
    private TextView textViewUnit;
    private ImageView imageViewGeneralList;
    private ImageView imageViewGeneralEntry;
    private Button buttonOk;
    private Button buttonSubmit;
    private CheckStockModel checkStockModel;
    private ResponseGeneralList responseGeneralList;
    boolean updateFlag=false;

    public static GeneralFragmentAnd newInstance() {
        GeneralFragmentAnd fragment = new GeneralFragmentAnd();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_general, container, false);
        editTextBarcode=view.findViewById(R.id.text_general_barcode);
        editTextBarcode.setOnClickListener(this);
        editTextBarcode.requestFocus();
        editTextRemarkOne=view.findViewById(R.id.text_general_remark_one);
        editTextRemarkOne.setOnClickListener(this);

        editTextRemarkTwo=view.findViewById(R.id.text_general_remark_two);
        editTextRemarkTwo.setOnClickListener(this);

        textViewProductName=view.findViewById(R.id.text_general_product_name);

        textViewSystemQuantity=view.findViewById(R.id.text_general_system_quantity);
        textViewUnit=view.findViewById(R.id.text_general_unit);

        recyclerViewGeneralList=view.findViewById(R.id.rv_general_list);
        recyclerViewGeneralList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewGeneralList.setHasFixedSize(true);

        imageViewGeneralList=view.findViewById(R.id.button_general_list);
        imageViewGeneralList.setOnClickListener(this);

        imageViewGeneralEntry = view.findViewById(R.id.button_general_entry);
        imageViewGeneralEntry.setOnClickListener(this);

        buttonOk=view.findViewById(R.id.button_general_ok);
        buttonOk.setOnClickListener(this);

        buttonSubmit=view.findViewById(R.id.button_general_submit);
        buttonSubmit.setOnClickListener(this);

   //Single Navigation page
        ContainerGeneralEntry= view.findViewById(R.id.container_general_entry);
        ContainerGeneralList = view.findViewById(R.id.container_general_list);

        return view;
    }
    public void onGeneralEntryClicked(){
        ContainerGeneralEntry.setVisibility(View.VISIBLE);
        ContainerGeneralList.setVisibility(View.GONE);
    }
    public void onGeneralListClicked(){
        ContainerGeneralEntry.setVisibility(View.GONE);
        ContainerGeneralList.setVisibility(View.VISIBLE);
    }

    public void SearchStock(){
        progressDialog = createProgressDialog(getContext());

        UserModel userModel= AppPreference.getLoginDataPreferences(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        checkStock.setCoBr_Id(userModel.getCobrId());
        checkStock.setBarcode(editTextBarcode.getText().toString());

        Call<ArrayList<CheckStockModel>> listCheckStock  =
                WebServiceClient.checkStockService(appSetting.getSettingServerURL()).listCheckStock(checkStock);
        listCheckStock.enqueue(new Callback<ArrayList<CheckStockModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CheckStockModel>> call,
                                   Response<ArrayList<CheckStockModel>> response) {
                if (response.isSuccessful() && response.body().get(0).getCoBrId() != null) {
                    changeDataUI(response.body());
                }
                else {
                    Toast.makeText(getContext(),"Item not found !",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
                editTextBarcode.setText("");
            }

            @Override
            public void onFailure(Call<ArrayList<CheckStockModel>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

    public void changeDataUI(ArrayList<CheckStockModel> checkStockModels){
        updateFlag=false;
        checkStockModel=checkStockModels.get(0);
        textViewProductName.setText(checkStockModel.getItemName());
        textViewSystemQuantity.setText(checkStockModel.getClqty());
        textViewUnit.setText(checkStockModel.getUnitName());
    }
    public void changeDataUI(ResponseGeneralList responseGeneralList){
        updateFlag=true;
        this.responseGeneralList=responseGeneralList;
        textViewProductName.setText(responseGeneralList.getItemName());
        textViewSystemQuantity.setText(responseGeneralList.getClQty());
        textViewUnit.setText(responseGeneralList.getUnitName());
        editTextRemarkOne.setText(responseGeneralList.getRemark1());
        editTextRemarkTwo.setText(responseGeneralList.getRemark2());
        onGeneralEntryClicked();
    }

    public void clearDataUI(){
        editTextBarcode.setText("");
        textViewProductName.setText("");
        textViewSystemQuantity.setText("");
        textViewUnit.setText("");
        editTextRemarkOne.setText("");
        editTextRemarkTwo.setText("");
    }
//------------------------------------------------------------------------------------------------------------

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

//-------------------------------------------------------------------------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


//------------------------------------------------------------------------------------------------
    public void SubmitGeneral(){
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        GeneralModel generalModel=new GeneralModel();
        GeneralInputDetail generalInputDetail =new GeneralInputDetail();
        if(checkStock.getCoBr_Id()!=null) {
            if (updateFlag) {
                generalModel.setGeneralID(responseGeneralList.getGeneralID());
                generalInputDetail.setGeneralDetailIdID(responseGeneralList.getGeneralID());

                generalModel.setUpdatedBy(String.valueOf(userModel.getUserId()));

            } else {
                generalInputDetail.setCobrID(checkStock.getCoBr_Id());
                generalInputDetail.setItemId(checkStockModel.getItemId());
                generalModel.setCreatedBy(String.valueOf(userModel.getUserId()));
            }
            generalInputDetail.setRemark1(editTextRemarkOne.getText().toString());
            generalInputDetail.setRemark2(editTextRemarkTwo.getText().toString());
            ArrayList<GeneralInputDetail>generalInputDetails=new ArrayList<>();
            generalInputDetails.add(generalInputDetail);
            generalModel.setGeneralInputDetails(generalInputDetails);

        }else {
            Toast.makeText(getContext(),"Please check barcode details !",Toast.LENGTH_LONG).show();
            return;
        }
        Call<GeneralModel> generalModelCall = WebServiceClient
                        .generalService(appSetting.getSettingServerURL())
                        .setGeneralModelCall(generalModel);

        generalModelCall.enqueue(new Callback<GeneralModel>() {
            @Override
            public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                Toast.makeText(getContext(),response.body().getMsg().toString(),Toast.LENGTH_LONG).show();
                clearDataUI();
                editTextBarcode.requestFocus();

            }
            @Override
            public void onFailure(Call<GeneralModel> call, Throwable t) {
                Toast.makeText(getContext(),"General Not Saved",Toast.LENGTH_LONG).show();
            }
        });
    }
//------------------TODO ----------------------------------------------------------------------------------
    public void getGeneralList(){
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        RequestGeneralList requestGeneralList=new RequestGeneralList();
        requestGeneralList.setCoBrID(userModel.getCobrId());
        Call<ArrayList<ResponseGeneralList>> responseGeneralListCall=WebServiceClient
                .generalService(appSetting.getSettingServerURL())
                .getGeneralList(requestGeneralList);
        responseGeneralListCall.enqueue(new Callback<ArrayList<ResponseGeneralList>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseGeneralList>> call, Response<ArrayList<ResponseGeneralList>> response) {
                if (response.isSuccessful()) {
                    UpdateGeneralList(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseGeneralList>> call, Throwable t) {
                Toast.makeText(getContext(),"General List Not Available",Toast.LENGTH_LONG).show();
            }
        });
    }
//----------------TODO  UpdateGeneralList ------------------------------------------------------------
    public void UpdateGeneralList(ArrayList<ResponseGeneralList> responseGeneralLists){
        this.responseGeneralLists=responseGeneralLists;
        generalListAdapter=new GeneralListAdapter(responseGeneralLists,this);
        recyclerViewGeneralList.setAdapter(generalListAdapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_general_ok:{
                if (!TextUtils.isEmpty(editTextBarcode.getText().toString())){
                    SearchStock();
                }else {
                    Toast.makeText(getActivity(), "Please scan barcode !", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.button_general_submit:{
                validationSubmitGeneral();
                break;
            }
            case R.id.button_general_list:{
                getGeneralList();
                onGeneralListClicked();
                break;
            }
            case R.id.button_general_entry:{
                onGeneralEntryClicked();
                break;
            }

        }
    }
    public void validationSubmitGeneral() {
        if (!TextUtils.isEmpty(editTextRemarkOne.getText())
                && !TextUtils.isEmpty(editTextRemarkTwo.getText().toString())
        ) {
            SubmitGeneral();
        } else {
            Toast.makeText(getActivity(), "Select All Field", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onGeneralItemUpdateClicked(ResponseGeneralList responseGeneralList) {
        changeDataUI(responseGeneralList);
    }
    @Override
    public void onGeneralItemDeleteClicked(ResponseGeneralList responseGeneralList) {
        DeleteGeneralModel deleteGeneralModel=new DeleteGeneralModel();
        deleteGeneralModel.setGeneralID(responseGeneralList.getGeneralID());
        setDeleteGeneralItem(deleteGeneralModel);
    }
//----------------------------------------------------------------------------------------------------------------
    public void setDeleteGeneralItem(DeleteGeneralModel deleteGeneralItem){
        progressDialog = createProgressDialog(getContext());
        AppSetting appSetting=AppPreference.getSettingDataPreferences(getContext());
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        deleteGeneralItem.setUpdatedBy(String.valueOf(userModel.getUserId()));

        Call<DeleteGeneralModel> deleteGeneralModelCall=WebServiceClient
                .generalService(appSetting.getSettingServerURL())
                .setDeleteGeneralItem(deleteGeneralItem);
        deleteGeneralModelCall.enqueue(new Callback<DeleteGeneralModel>() {
            @Override
            public void onResponse(Call<DeleteGeneralModel> call, Response<DeleteGeneralModel> response) {
                Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
               getGeneralList();
               progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<DeleteGeneralModel> call, Throwable t) {
                Toast.makeText(getContext(),"General Item Deleted",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
//---end script ---------------

}