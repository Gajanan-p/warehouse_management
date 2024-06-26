package com.ssinfomate.warehousemanagement.webservices;


import android.widget.Toast;

import com.ssinfomate.warehousemanagement.webservices.check_stock.CheckStockService;
import com.ssinfomate.warehousemanagement.webservices.company.CompanyService;
import com.ssinfomate.warehousemanagement.webservices.general.GeneralService;
import com.ssinfomate.warehousemanagement.webservices.grn.IGRN;
import com.ssinfomate.warehousemanagement.webservices.grn.IPurchaseNumber;
import com.ssinfomate.warehousemanagement.webservices.grn.ISaveGrnAgainstPO;
import com.ssinfomate.warehousemanagement.webservices.login.LoginService;
import com.ssinfomate.warehousemanagement.webservices.purchaseorder.PurchaseOrderService;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.IRateCat;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.IReducedBarcode;
import com.ssinfomate.warehousemanagement.webservices.reducedbarcode.IRemark;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.IAcceptStockTransferList;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.IPickHeldNotification;
import com.ssinfomate.warehousemanagement.webservices.save_stock_trans.StockTransferService;
import com.ssinfomate.warehousemanagement.webservices.stock_correction.IStockCorrection;
import com.ssinfomate.warehousemanagement.webservices.supplier.SupplierService;
import com.ssinfomate.warehousemanagement.webservices.warehouse.WarehouseService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceClient {
    public static Retrofit retrofit;
    public static LoginService loginService;
    public static CheckStockService checkStockService;
    public static WarehouseService warehouseService;
    public static CompanyService companyService;
    public static StockTransferService stockTransferService;
    public static IPickHeldNotification iPickHeldNotification;
    public static IAcceptStockTransferList iAcceptStockTransferList;
    public static SupplierService supplierService;
    public static IStockCorrection iStockCorrection;
    public static PurchaseOrderService purchaseOrderService;
    public static GeneralService generalService;
    public static IGRN igrnServices;
    public static IPurchaseNumber iPurchaseNumber;
    public static ISaveGrnAgainstPO iSaveGrnAgainstPO;
    public static IReducedBarcode iReducedBarcode;
    public static IRateCat iRateCat;
    public static IRemark iRemark;
    //public static String BASE_URL="http://irlandapi.ssinfomate.in/";

    public static Retrofit getRetrofitClient(String BASE_URL){
        if(retrofit==null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                try {

                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }catch (Exception e){
                }

        }
        return retrofit;
    }

    public static LoginService loginService(String BASE_URL){
        if(loginService==null){
            if(getRetrofitClient(BASE_URL)!=null){
                loginService = getRetrofitClient(BASE_URL).create(LoginService.class);
            }
        }
        return loginService;
    }

    public static CheckStockService checkStockService(String BASE_URL){
        if(checkStockService==null){
            if(getRetrofitClient(BASE_URL)!=null) {
                checkStockService = getRetrofitClient(BASE_URL).create(CheckStockService.class);
            }
        }
        return checkStockService;
    }

    public static WarehouseService getWarehouseService(String BASE_URL){
        if(warehouseService==null){
            if(getRetrofitClient(BASE_URL)!=null) {
                warehouseService = getRetrofitClient(BASE_URL).create(WarehouseService.class);
            }
        }
        return warehouseService;
    }

    public static CompanyService getCompanyService(String BASE_URL){

        if(companyService==null){
            if(getRetrofitClient(BASE_URL)!=null) {
                companyService = getRetrofitClient(BASE_URL).create(CompanyService.class);
            }
        }
        return companyService;

    }

    public static StockTransferService getStockTransferService(String BASE_URL){
            if (stockTransferService == null) {
                if (getRetrofitClient(BASE_URL) != null) {
                    stockTransferService = getRetrofitClient(BASE_URL).create(StockTransferService.class);
                }
            }
        return stockTransferService;
    }

    public static IPickHeldNotification iPickHeldNotification(String BASE_URL)
    {
        if (iPickHeldNotification==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                iPickHeldNotification = getRetrofitClient(BASE_URL).create(IPickHeldNotification.class);
            }
        }
        return iPickHeldNotification;
    }
    public static IAcceptStockTransferList iAcceptStockTransferList(String BASE_URL)
    {
        if (iAcceptStockTransferList==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                iAcceptStockTransferList = getRetrofitClient(BASE_URL).create(IAcceptStockTransferList.class);
            }
        }
        return iAcceptStockTransferList;
    }

    public static IStockCorrection iHeldStockCorrection(String BASE_URL)
    {
        if (iStockCorrection ==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                iStockCorrection = getRetrofitClient(BASE_URL).create(IStockCorrection.class);
            }
        }
        return iStockCorrection;
    }

    public static SupplierService getSupplierService(String BASE_URL)
    {
        if (supplierService ==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                supplierService = getRetrofitClient(BASE_URL).create(SupplierService.class);
            }
        }
        return supplierService;
    }

    public static IRateCat getRateCat(String BASE_URL)
    {
        if (iRateCat ==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                iRateCat = getRetrofitClient(BASE_URL).create(IRateCat.class);
            }
        }
        return iRateCat;
    }

    public static PurchaseOrderService setPurchaseOrderService(String BASE_URL)
    {
        if (purchaseOrderService ==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                purchaseOrderService = getRetrofitClient(BASE_URL).create(PurchaseOrderService.class);
            }
        }
        return purchaseOrderService;
    }

    public static GeneralService generalService(String BASE_URL)
    {
        if (generalService ==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                generalService = getRetrofitClient(BASE_URL).create(GeneralService.class);
            }
        }
        return generalService;
    }
    public static IGRN setWithoutPurOrderServices(String BASE_URL)
    {
        if (igrnServices ==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                igrnServices = getRetrofitClient(BASE_URL).create(IGRN.class);
            }
        }
        return igrnServices;
    }
    public static IPurchaseNumber getPurchaseNumber(String BASE_URL)
    {
        if (iPurchaseNumber ==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                iPurchaseNumber = getRetrofitClient(BASE_URL).create(IPurchaseNumber.class);
            }
        }
        return iPurchaseNumber;
    }

    public static ISaveGrnAgainstPO setSaveGrnAgainstPO(String BASE_URL)
    {
        if (iSaveGrnAgainstPO ==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                iSaveGrnAgainstPO = getRetrofitClient(BASE_URL).create(ISaveGrnAgainstPO.class);
            }
        }
        return iSaveGrnAgainstPO;
    }

    public static IReducedBarcode getReducedBarcode(String BASE_URL)
    {
        if (iReducedBarcode ==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                iReducedBarcode = getRetrofitClient(BASE_URL).create(IReducedBarcode.class);
            }
        }
        return iReducedBarcode;
    }

    public static IRemark getRemarkList(String BASE_URL)
    {
        if (iRemark==null) {
            if(getRetrofitClient(BASE_URL)!=null) {
                iRemark = getRetrofitClient(BASE_URL).create(IRemark.class);
            }
        }
        return iRemark;
    }
}
