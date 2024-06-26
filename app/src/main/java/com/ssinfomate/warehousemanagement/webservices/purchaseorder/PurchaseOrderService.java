package com.ssinfomate.warehousemanagement.webservices.purchaseorder;


import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PurchaseOrderService {

    @Headers("Content-Type: application/json")
    @POST("api/User/DraftPurchaseOrder")
    Call<RequestPurchaseOrder> setSavePurchaseOrderData(@Body JsonObject saveStockDetails);
}
