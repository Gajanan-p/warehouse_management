package com.ssinfomate.warehousemanagement.ui.home;


import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProvider;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.utils.AppClassConstant;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;

import org.jetbrains.annotations.NotNull;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment
        extends Fragment
        implements View.OnClickListener {

    private HomeViewModel mViewModel;
    private ProgressDialog progressDialog;
    String BASE_URL;
    NavController navController;
    AppCompatImageView imageViewCompatStockCorrection;

    AppCompatImageView imageViewCompatStockTransfer;

    AppCompatImageView imageViewCompatCheckStock;

    AppCompatImageView imageViewCompatPurchaseOrder;

    AppCompatImageView imageViewCompatGRN;

    AppCompatImageView imageViewCompatPrintPriceTag;

    AppCompatImageView imageViewCompatReduceBarcode;

    AppCompatImageView imageViewCompatGeneral;
    AppSetting appSetting;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        appSetting= AppPreference.getSettingDataPreferences(getContext());
        if(appSetting==null){
          //  Toast.makeText(getContext(),"Please set server URL..",Toast.LENGTH_LONG).show();
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Please set server URL !").show();
        }else {
            if(appSetting.getSettingServerURL()==null || appSetting.getSettingServerURL()==""){
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Please set server URL !").show();
            }else{
                BASE_URL=appSetting.getSettingServerURL();
            }

        }
        /*----------Stock Correction Fragment------------------------------*/
        imageViewCompatStockCorrection = view.findViewById(R.id.id_home_fragment_stock_correction_img);
        imageViewCompatStockCorrection.setOnClickListener(this);

        /*----------Stock Transfer Fragment------------------------------*/
        imageViewCompatStockTransfer = view.findViewById(R.id.id_home_fragment_stock_transfer_img);
        imageViewCompatStockTransfer.setOnClickListener(this);

        /*----------Check Stock  Fragment------------------------------*/
        imageViewCompatCheckStock = view.findViewById(R.id.id_home_fragment_check_stock_img);
        imageViewCompatCheckStock.setOnClickListener(this);

        /*----------Purchase Order Fragment------------------------------*/
        imageViewCompatPurchaseOrder = view.findViewById(R.id.id_home_fragment_purchase_order_img);
        imageViewCompatPurchaseOrder.setOnClickListener(this);

        /*----------GRN Fragment------------------------------*/
        imageViewCompatGRN = view.findViewById(R.id.id_home_fragment_grn_img);
        imageViewCompatGRN.setOnClickListener(this);

        /*----------Print Price Tag Fragment------------------------------*/
        imageViewCompatPrintPriceTag = view.findViewById(R.id.id_home_fragment_print_price_tag_img);
        imageViewCompatPrintPriceTag.setOnClickListener(this);

        /*----------Reduce Barcode Fragment------------------------------*/
        imageViewCompatReduceBarcode = view.findViewById(R.id.id_home_fragment_reduce_barcode_img);
        imageViewCompatReduceBarcode.setOnClickListener(this);

        imageViewCompatGeneral=view.findViewById(R.id.id_home_fragment_general_img);
        imageViewCompatGeneral.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  AppPreference.clearLoginDataPreferences(getActivity());
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        UserModel userModel=AppPreference.getLoginDataPreferences(getContext());
        if(userModel!=null){
            if(userModel.getIsLogin()==1){
               // checkCredentialWithServer(userModel);
                //navController.navigate(R.id.action_nav_Login_to_nav_home);
            }else{
                navController.navigate(R.id.nav_Login);
            }
        }else{
            navController.navigate(R.id.nav_Login);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    /*----------Stock Correction Fragment------------------------------*/
    public void onStockCorrectionClicked() {
        if(WebServiceClient.loginService(BASE_URL)!=null){
            navController.navigate(R.id.action_nav_home_to_nav_stock_correction);
        }else{
            Toast.makeText(getActivity(),"Please check Server URL..",Toast.LENGTH_LONG).show();
        }

    }

    /*----------Stock Transfer Fragment------------------------------*/
    public void onStockTransferClicked() {

        if (WebServiceClient.loginService(BASE_URL) != null) {
            navController.navigate(R.id.action_nav_home_to_nav_stock_transfer);
        } else {
            Toast.makeText(getActivity(), "Please check Server URL..", Toast.LENGTH_LONG).show();
        }
    }

    /*----------Check Stock  Fragment------------------------------*/
    public void onCheckStockClicked() {
            if (WebServiceClient.loginService(BASE_URL) != null) {
                if (AppClassConstant.classPresent()) {
                    navController.navigate(R.id.action_nav_home_to_nav_check_stock);
                } else {
                    navController.navigate(R.id.action_nav_home_to_nav_check_stock_and);
                }

            } else {
                Toast.makeText(getActivity(), "Please check Server URL..", Toast.LENGTH_LONG).show();
            }
        }
    /*----------Purchase Order Fragment------------------------------*/
    public void onPurchaseOrderClicked() {
        if (WebServiceClient.loginService(BASE_URL) != null) {
            navController.navigate(R.id.action_nav_home_to_nav_purchase_order);
        }
        else {
            Toast.makeText(getActivity(), "Please check Server URL..", Toast.LENGTH_LONG).show();
        }
    }
    /*----------GRN Fragment------------------------------*/
    public void onGRNClicked() {
        if (WebServiceClient.loginService(BASE_URL) != null) {
            navController.navigate(R.id.action_nav_home_to_nav_manage_grn);
        } else {
            Toast.makeText(getActivity(), "Please check Server URL..", Toast.LENGTH_LONG).show();
        }
    }
    /*----------Print Price Tag Fragment------------------------------*/
    public void onPrintPriceTagClicked() {
        if (WebServiceClient.loginService(BASE_URL) != null) {
            if (AppClassConstant.classPresent()) {
                navController.navigate(R.id.action_nav_home_to_nav_print_price_tag);
            } else {
                navController.navigate(R.id.action_nav_home_to_nav_print_price_tag_and);
            }
        } else {
            Toast.makeText(getActivity(), "Please check Server URL..", Toast.LENGTH_LONG).show();
        }
    }
    /*----------Reduce Barcode Fragment------------------------------*/
    public void onReduceBarcodeClicked() {
        if (WebServiceClient.loginService(BASE_URL) != null) {
            navController.navigate(R.id.action_nav_home_to_nav_reduce_barcode);
        } else {
            Toast.makeText(getActivity(), "Please check Server URL..", Toast.LENGTH_LONG).show();
        }
    }
    /*----------Reduce Barcode Fragment------------------------------*/
    public void onGeneralClicked() {
        if (WebServiceClient.loginService(BASE_URL) != null) {
            if (AppClassConstant.classPresent()) {
                navController.navigate(R.id.action_nav_home_to_nav_general);
            } else {
                navController.navigate(R.id.action_nav_home_to_nav_general_and);
            }
        } else {
            Toast.makeText(getActivity(), "Please check Server URL..", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v) {
        if(appSetting==null){
            Toast.makeText(getContext(),"Please set server URL....",Toast.LENGTH_LONG).show();
        }else {
            if(appSetting.getSettingServerURL()==null || appSetting.getSettingServerURL()==""){
                Toast.makeText(getContext(),"Please set server URL....",Toast.LENGTH_LONG).show();
            }else{
                switch (v.getId()) {
                    case R.id.id_home_fragment_stock_correction_img: {
                        onStockCorrectionClicked();

                        break;
                    }

                    case R.id.id_home_fragment_stock_transfer_img: {
                        onStockTransferClicked();
                        break;
                    }

                    case R.id.id_home_fragment_check_stock_img: {
                        onCheckStockClicked();
                        break;
                    }

                    case R.id.id_home_fragment_purchase_order_img: {
                        onPurchaseOrderClicked();
                        break;
                    }

                    case R.id.id_home_fragment_grn_img: {
                        onGRNClicked();
                        break;
                    }
                    case R.id.id_home_fragment_print_price_tag_img: {
                        onPrintPriceTagClicked();
                        break;
                    }
                    case R.id.id_home_fragment_reduce_barcode_img: {
                       onReduceBarcodeClicked();
                        break;
                    }
                    case R.id.id_home_fragment_general_img: {
                        onGeneralClicked();
                        break;
                    }
                }
            }
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

//    void checkCredentialWithServer(UserModel userModel){
////        progressDialog = createProgressDialog(getActivity());
//
//            User user=new User(userModel.getLoginName(),userModel.getUserPwd());
//            String BASE_URL=appSetting.getSettingServerURL();
//
//            if(WebServiceClient.loginService(BASE_URL)!=null){
//             Toast.makeText(getActivity(),"Please check Server URL..sdfdgdfg",Toast.LENGTH_LONG).show();
//            }
//
//
//    }

}