package com.ssinfomate.warehousemanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ssinfomate.warehousemanagement.ui.company.ChangeCompanyModel;
import com.ssinfomate.warehousemanagement.webservices.general.GeneralRemarkModel;
import com.ssinfomate.warehousemanagement.webservices.grn.PONumberListModel;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.RateCatListModel;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.ReducedBarcodeRemarkList;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.SaveReducedBarcodeModel;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.AdjustmentTypeModel;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.DamageReasonModels;
import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierModel;
import com.ssinfomate.warehousemanagement.webservices.warehouse.ToWarehouse;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppPreference {
    private static String TAG="appDataPreferences";
    public static String appPreferences="appPreferences";

    public static String appLoginPreferences="appLoginPreferences";
    public static String appLoginCrashPreferences="appLoginCrashPreferences";

    public static String appLoginPreferencesKey="appLoginPreferencesKey";

    public static String appSettingPreferences="appSettingPreferences";

    public static String appSettingPreferencesKey="appSettingPreferencesKey";
    public static String appCompanyPreferencesKey="appCompanyPreferencesKey";
    public static String appBusinessLocationPreferencesKey="appBusinessPreferencesKey";
    public static String appFromWarehousePreferencesKey="appFromWarehousePreferencesKey";
    public static String appToWarehousePreferencesKey="appToWarehousePreferencesKey";
    public static String appSupplierPreferencesKey="appSupplierPreferencesKey";
    public static String appRateCatPreferencesKey="appRateCatPreferencesKey";
    public static String appPurchaseNumberPreferenceKey="appPurNumberPreferenceKey";
    public static String appDamageReasonPreferencesKey="appDamageReasonPreferencesKey";
    public static String appNewReducedStockDataPreferencesKey="appNewReducedStockDataPreferencesKey";
    public static String appRemarkListPreferencesKey="appRemarkListPreferencesKey";
    public static String appGeneralRemarkListPreferencesKey="appGeneralRemarkListPreferencesKey";
    public static String appAdjustmentTypePreferencesKey="appAdjustmentTypePreferencesKey";

    public static SharedPreferences sharedLoginPreferences;
    public static SharedPreferences sharedSettingPreferences;
    public static SharedPreferences sharedAppPreferences;
    private SharedPreferences.Editor preferenceEditor;

    public static SharedPreferences getAppSharedPreferences(Context context){
        if(sharedAppPreferences==null){
            sharedAppPreferences=context.getSharedPreferences(AppPreference.appPreferences
                    , Context.MODE_PRIVATE);
        }
        return sharedAppPreferences;
    }

    public static SharedPreferences getLoginSharedPreferences(Context context){
        if(sharedLoginPreferences==null){
            sharedLoginPreferences=context.getSharedPreferences(AppPreference.appLoginPreferences, Context.MODE_PRIVATE);
        }
        return sharedLoginPreferences;
    }

    public static SharedPreferences getSettingSharedPreferences(Context context){
        if(sharedSettingPreferences==null){
            sharedSettingPreferences=context.getSharedPreferences(AppPreference.appSettingPreferences, Context.MODE_PRIVATE);
        }
        return sharedSettingPreferences;
    }

//    public boolean isAdminItemAddAllowedForDialog() {
//        return this.preferenceEditor.getBoolean(appLoginCrashPreferences, false);
//    }
//
//    public void setAdminItemAddAllowedForDialog(boolean value) {
//        this.preferenceEditor.putBoolean(appLoginCrashPreferences, value);
//        this.preferenceEditor.commit();
//    }

//    public static void setIsLoginDataPreferences(){
//
//        SharedPreferences.Editor editor=getLoginSharedPreferences(this).edit();
//        editor.putBoolean(appLoginCrashPreferences,false);
//        editor.commit();
//    }
//
//    public static UserModel getIsLoginDataPreferences(Context context){
//        String data=getLoginSharedPreferences(context).getString(appLoginPreferencesKey,"");
//        Gson gson = new Gson();
//        UserModel userModel= gson.fromJson(data,UserModel.class);
//        Log.i(TAG,"get user model data");
//        Log.i(TAG,data);
//        return userModel;
//    }

    public static void setLoginDataPreferences(Context context, UserModel userModel){
        Gson gson = new Gson();
        String data=gson.toJson(userModel);
        Log.i(TAG,"Set user model data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getLoginSharedPreferences(context).edit();
        editor.putString(appLoginPreferencesKey,data);
        editor.commit();
    }

    public static UserModel getLoginDataPreferences(Context context){
        String data=getLoginSharedPreferences(context).getString(appLoginPreferencesKey,"");
        Gson gson = new Gson();
        UserModel userModel= gson.fromJson(data,UserModel.class);
        Log.i(TAG,"get user model data");
        Log.i(TAG,data);
        return userModel;
    }

    public static boolean clearLoginDataPreferences(Context context){
        SharedPreferences.Editor editor=getLoginSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
        editor.commit();
        Log.i(TAG,"Clear login Preferences");
        return true;
    }
    public static boolean clearSettingDataPreferences(Context context){
        SharedPreferences.Editor editor=getSettingSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
        editor.commit();
        Log.i(TAG,"Clear setting Preferences");
        return true;
    }

    public static void setSettingDataPreferences(Context context, AppSetting appSetting){
        Gson gson = new Gson();
        String data=gson.toJson(appSetting);
        Log.i(TAG,"Set Setting model data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getSettingSharedPreferences(context).edit();
        editor.putString(appSettingPreferencesKey,data);
        editor.commit();
    }

    public static AppSetting getSettingDataPreferences(Context context){
        String data=getSettingSharedPreferences(context).getString(appSettingPreferencesKey,"");
        Gson gson = new Gson();
       AppSetting appSetting= gson.fromJson(data,AppSetting.class);
        Log.i(TAG,"get Setting model data");
        Log.i(TAG,data);
        return appSetting;
    }

    public static void setCompanyDataPreferences(Context context, ChangeCompanyModel changeCompanyModel){
        Gson gson = new Gson();
        String data=gson.toJson(changeCompanyModel);
        Log.i(TAG,"Set Company data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appCompanyPreferencesKey,data);
        editor.commit();
    }

    public static ChangeCompanyModel getCompanyDataPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appCompanyPreferencesKey,"");
        Gson gson = new Gson();
        ChangeCompanyModel changeCompanyModel= gson.fromJson(data,ChangeCompanyModel.class);
        Log.i(TAG,"get Company data "+ data);
        Log.i(TAG,data);
        return changeCompanyModel;
    }


    public static void setBusinessLocationDataPreferences(Context context, WarehouseModel warehouseModel){
        Gson gson = new Gson();
        String data=gson.toJson(warehouseModel);
        Log.i(TAG,"Set Company data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appBusinessLocationPreferencesKey,data);
        editor.commit();
    }

    public static WarehouseModel getBusinessLocationDataPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appBusinessLocationPreferencesKey,"");
        Gson gson = new Gson();
        WarehouseModel warehouseModel= gson.fromJson(data,WarehouseModel.class);
        Log.i(TAG,"get Company data "+ data);
        Log.i(TAG,data);
        return warehouseModel;
    }
    public static void setFromWarehouseDataPreferences(Context context, WarehouseModel warehouseModel){
        Gson gson = new Gson();
        String data=gson.toJson(warehouseModel);
        Log.i(TAG,"Set Company data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appFromWarehousePreferencesKey,data);
        editor.commit();
    }

    public static WarehouseModel getFromWarehouseDataPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appFromWarehousePreferencesKey,"");
        Gson gson = new Gson();
        WarehouseModel warehouseModel= gson.fromJson(data,WarehouseModel.class);
        Log.i(TAG,"get Company data "+ data);
        Log.i(TAG,data);
        return warehouseModel;
    }

    public static void setToWarehouseDataPreferences(Context context, ToWarehouse toWarehouseModel){
        Gson gson = new Gson();
        String data=gson.toJson(toWarehouseModel);
        Log.i(TAG,"Set Company data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appToWarehousePreferencesKey,data);
        editor.commit();
    }

    public static ToWarehouse getToWarehouseDataPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appToWarehousePreferencesKey,"");
        Gson gson = new Gson();
        ToWarehouse warehouseModel= gson.fromJson(data,ToWarehouse.class);
        Log.i(TAG,"get Company data "+ data);
        Log.i(TAG,data);
        return warehouseModel;
    }


    public static void setPurchaseNumberPreferences(Context context, PONumberListModel poNumberListModel){
        Gson gson = new Gson();
        String data=gson.toJson(poNumberListModel);
        Log.i(TAG,"Set Po Data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appPurchaseNumberPreferenceKey,data);
        editor.commit();
    }

    public static PONumberListModel getPurchaseNumberPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appPurchaseNumberPreferenceKey,"");
        Gson gson = new Gson();
        PONumberListModel poNumberListModel= gson.fromJson(data,PONumberListModel.class);
        Log.i(TAG,"get Company data "+ data);
        Log.i(TAG,data);
        return poNumberListModel;
    }

    public static SupplierModel getSupplierDataPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appSupplierPreferencesKey,"");
        Gson gson = new Gson();
        SupplierModel supplierModel= gson.fromJson(data,SupplierModel.class);
        Log.i(TAG,"get Supplier data "+ data);
        Log.i(TAG,data);
        return supplierModel;
    }

    public static void setSupplierDataPreferences(Context context, SupplierModel supplierModel){
        Gson gson = new Gson();
        String data=gson.toJson(supplierModel);
        Log.i(TAG,"Set Supplier data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appSupplierPreferencesKey,data);
        editor.commit();
    }

    public static RateCatListModel getRateCatPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appRateCatPreferencesKey,"");
        Gson gson = new Gson();
        RateCatListModel rateCatListModel= gson.fromJson(data,RateCatListModel.class);
        Log.i(TAG,"get RateCat data "+ data);
        Log.i(TAG,data);
        return rateCatListModel;
    }

        public static void setRateCatPreferences(Context context, RateCatListModel rateCatListModel){
        Gson gson = new Gson();
        String data=gson.toJson(rateCatListModel);
        Log.i(TAG,"Set RateCat data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appRateCatPreferencesKey,data);
        editor.commit();
    }
    public static List<DamageReasonModels> getDamageReasonDataPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appDamageReasonPreferencesKey,"");
        Gson gson = new Gson();
        List damageReasonModel=  Arrays.asList( new Gson().fromJson(data, DamageReasonModels[].class));
        Log.i(TAG,"get Damage ReasonModels data "+ data);
        Log.i(TAG,data);
        return damageReasonModel;
    }

    public static void setDamageReasonDataPreferences(Context context,
                                                      ArrayList<DamageReasonModels>  damageReasonModels){
        Gson gson = new Gson();
        String data=gson.toJson(damageReasonModels);
        Log.i(TAG,"Set Damage ReasonModels data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appDamageReasonPreferencesKey,data);
        editor.commit();
    }

    public static List<SaveReducedBarcodeModel> getNewReducedStockDataPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appNewReducedStockDataPreferencesKey,"");
        Gson gson = new Gson();
        List newReducedStockModels=  Arrays.asList( new Gson().fromJson(data, SaveReducedBarcodeModel[].class));
        Log.i(TAG,"get New Reduced Barcode  data "+ data);

        return  newReducedStockModels;
    }

    public static void setNewReducedStockDataPreferences(Context context,
                                                      ArrayList<SaveReducedBarcodeModel>  newReducedStockModels){
        Gson gson = new Gson();
        String data=gson.toJson(newReducedStockModels);
        Log.i(TAG,"Set New Reduced Barcode data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appNewReducedStockDataPreferencesKey,data);
        editor.commit();
    }
    public static List<ReducedBarcodeRemarkList> getRemarkPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appRemarkListPreferencesKey,"");
        Gson gson = new Gson();
        List reducedBarcodeRemarkLists=  Arrays.asList( new Gson().fromJson(data, ReducedBarcodeRemarkList[].class));
        Log.i(TAG,"get Reduced Barcode Remark data "+ data);
        Log.i(TAG,data);
        return reducedBarcodeRemarkLists;
    }

    public static void setRemarkDataPreferences(Context context,
                                                      ArrayList<ReducedBarcodeRemarkList>  reducedBarcodeRemarkLists){
        Gson gson = new Gson();
        String data=gson.toJson(reducedBarcodeRemarkLists);
        Log.i(TAG,"Set Reduced Barcode Remark data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appRemarkListPreferencesKey,data);
        editor.commit();
    }
    public static GeneralRemarkModel getGeneralRemarkDataPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appGeneralRemarkListPreferencesKey,"");
        Gson gson = new Gson();
        GeneralRemarkModel generalRemarkModel = gson.fromJson(data,GeneralRemarkModel.class);
        Log.i(TAG,"get Supplier data "+ data);
        Log.i(TAG,data);
        return generalRemarkModel;
    }

    public static void setGeneralRemarkDataPreferences(Context context,
                                                       GeneralRemarkModel  generalRemarkModels){
        Gson gson = new Gson();
        String data=gson.toJson(generalRemarkModels);
        Log.i(TAG,"Set General Remark data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appGeneralRemarkListPreferencesKey,data);
        editor.commit();
    }

    public static AdjustmentTypeModel getAdjustmentTypeDataPreferences(Context context){
        String data=getAppSharedPreferences(context).getString(appAdjustmentTypePreferencesKey,"");
        Gson gson = new Gson();
        AdjustmentTypeModel adjustmentTypeModel = gson.fromJson(data,AdjustmentTypeModel.class);
        Log.i(TAG,"get Supplier data "+ data);
        Log.i(TAG,data);
        return adjustmentTypeModel;
    }

    public static void seAdjustmentTypeDataPreferences(Context context,
                                                       AdjustmentTypeModel  adjustmentTypeModel){
        Gson gson = new Gson();
        String data=gson.toJson(adjustmentTypeModel);
        Log.i(TAG,"Set General Remark data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getAppSharedPreferences(context).edit();
        editor.putString(appAdjustmentTypePreferencesKey,data);
        editor.commit();
    }
}
